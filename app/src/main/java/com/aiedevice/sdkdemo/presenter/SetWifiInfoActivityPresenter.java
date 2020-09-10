package com.aiedevice.sdkdemo.presenter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.aiedevice.sdkdemo.view.SetWifiInfoActivityView;
import com.esp.iot.blufi.communiation.BlufiConfigureParams;

public interface SetWifiInfoActivityPresenter extends Presenter<SetWifiInfoActivityView> {
    void getCurrentSSID(Context context);

    void startScanWifi();

    void startScanWifi(boolean forceScan);

    void stopScanWifi();

    BlufiConfigureParams getBlufiConfigureParam(BluetoothDevice device, String ssid, String pwd);
}
