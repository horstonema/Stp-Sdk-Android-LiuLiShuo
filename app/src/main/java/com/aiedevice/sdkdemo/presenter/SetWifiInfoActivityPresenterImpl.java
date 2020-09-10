package com.aiedevice.sdkdemo.presenter;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.aiedevice.sdkdemo.AppConstant;
import com.aiedevice.sdkdemo.utils.NetworkUtil;
import com.aiedevice.sdkdemo.view.SetWifiInfoActivityView;
import com.esp.iot.blufi.communiation.BlufiConfigureParams;
import com.esp.iot.blufi.communiation.IBlufiCommunicator;
import com.espressif.libs.net.NetUtil;
import com.aiedevice.sdk.AccountUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.content.Context.WIFI_SERVICE;

public class SetWifiInfoActivityPresenterImpl extends BasePresenter<SetWifiInfoActivityView> implements SetWifiInfoActivityPresenter {
    public static final String TAG = SetWifiInfoActivityPresenterImpl.class.getSimpleName();
    private final WifiManager mWifiManager;
    private Context context;
    private List<ScanResult> scans;

    public SetWifiInfoActivityPresenterImpl(Context context) {
        this.context = context;
        mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        scans = new LinkedList<>();
    }

    @Override
    public void getCurrentSSID(Context context) {
        String connectWifiSsid = NetworkUtil.getConnectWifiSsid(context);
        if (getActivityView() != null) {
            getActivityView().showCurrentWifi(connectWifiSsid);
        }

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null)
                return;
            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())) {
                context.unregisterReceiver(this);
                Log.d(TAG, "scan wifi SCAN_RESULTS_AVAILABLE_ACTION " + intent);
                updateWifi();
                if (getActivityView() != null) {
                    getActivityView().showWifiList(scans);
                }
            }
        }
    };

    @Override
    public void startScanWifi() {
        startScanWifi(true);
    }

    @Override
    public void startScanWifi(boolean forceScan) {
        if (!mWifiManager.isWifiEnabled()) {
            if (getActivityView() != null) {
                getActivityView().showOpenWifiDialog();
            }
            return;
        }

        if (forceScan)
            scans.clear();
        else if (!scans.isEmpty()) {
            if (getActivityView() != null) {
                getActivityView().showWifiList(scans);
            }
            return;
        }

        context.registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        // 由等待1.5秒改成接收广播
        mWifiManager.startScan();
        Log.d(TAG, "startScan Wifi");
    }

    @Override
    public void stopScanWifi() {

    }

    @Override
    public BlufiConfigureParams getBlufiConfigureParam(BluetoothDevice device, String ssid, String pwd) {
        BlufiConfigureParams params = new BlufiConfigureParams();
        params.setOpMode(IBlufiCommunicator.OP_MODE_STA);
        String encodedString = Base64.encodeToString(ssid.getBytes(AppConstant.CHARSET_UTF_8), Base64.NO_WRAP);
        params.setStaSSID(encodedString);
        StringBuilder builder = new StringBuilder();
        builder.append("v1#");
        builder.append(encode(pwd));
        builder.append('#');
        builder.append(encode(AccountUtil.getUserId()));
        builder.append('#');
        String newPwd = builder.toString();
        params.setStaPassword(newPwd);

        int freq = -1;
        if (ssid.equals(NetworkUtil.getConnectWifiSsid(context).replace("\"", ""))) {
            freq = NetworkUtil.getConnectionFrequncy(context);
            if (freq != -1) {
                params.setWifiChannel(NetUtil.getWifiChannel(freq));
            }
        }
        if (freq == -1) {
            for (ScanResult sr : scans) {
                if (ssid.equals(sr.SSID)) {
                    freq = sr.frequency;
                    params.setWifiChannel(NetUtil.getWifiChannel(freq));
                    break;
                }
            }
        }
        params.setStaRespRequire(true);
        String[] mac = device.getAddress().split(":");
        byte[] meshId = new byte[mac.length];
        for (int i = 0; i < mac.length; i++) {
            meshId[i] = (byte) Integer.parseInt(mac[i], 16);
        }
        params.setMeshID(meshId);

        params.setMeshRoot(true);
        params.setConfigureSequence(0);
        return params;
    }

    private synchronized void updateWifi() {
        List<ScanResult> scanResults = mWifiManager.getScanResults();
        scans.clear();
        if (scanResults == null)
            return;

        Map<String, ScanResult> wifiMap = new HashMap<>(scanResults.size());
        for (ScanResult scanResult : scanResults) {
            Log.d(TAG, "[updateWifi] ssid=" + scanResult.SSID + " frequency=" + scanResult.frequency);
            if (TextUtils.isEmpty(scanResult.SSID))
                continue;

            if (wifiMap.get(scanResult.SSID) == null) {
                wifiMap.put(scanResult.SSID, scanResult);
            } else {
                if (!is5GHz(scanResult.frequency))
                    wifiMap.put(scanResult.SSID, scanResult);
            }
        }

        scans.addAll(wifiMap.values());
    }

    public static boolean is5GHz(int freq) {
        return freq > 4900 && freq < 5900;
    }

    private String encode(String str) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (ch == '#') {
                builder.append('\\');
            } else if (ch == '\\') {
                builder.append('\\');
            }
            builder.append(ch);
        }
        return builder.toString();
    }
}
