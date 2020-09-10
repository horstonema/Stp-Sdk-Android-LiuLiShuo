package com.aiedevice.sdkdemo.presenter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import com.aiedevice.bean.Result;
import com.aiedevice.common.ResultListener;
import com.aiedevice.jssdk.device.DeviceManager;
import com.aiedevice.sdkdemo.view.SendWifiInfoActivityView;
import com.esp.iot.blufi.communiation.BlufiConfigureParams;

public class SendWifiInfoActivityPresenterImpl extends BasePresenter<SendWifiInfoActivityView> implements SendWifiInfoActivityPresenter {
    private String TAG = SendWifiInfoActivityPresenterImpl.class.getSimpleName();

    private Context context;
    private DeviceManager mDeviceManager;

    public SendWifiInfoActivityPresenterImpl(Context context) {
        mDeviceManager = new DeviceManager();

        this.context = context;
    }

    @Override
    public void startConfigure(BluetoothDevice device, BlufiConfigureParams params) {
        mDeviceManager.startConfigure(context, device, params, new ResultListener() {
            @Override
            public void onSuccess(Result result) {
                Log.d(TAG, "[startConfigure-succ] result=" + result.getResult());

                if (getActivityView() != null)
                    getActivityView().onSendWifiSuccessful();
            }

            @Override
            public void onError(int errCode, String errMsg) {
                Log.d(TAG, "[startConfigure-fail] errCode=" + errCode + " errMsg=" + errMsg);
                if (getActivityView() != null)
                    getActivityView().onSendWifiFailure(errCode, errMsg);
            }
        });
    }

    @Override
    public void stopConfigure() {
        mDeviceManager.stopConfigure();
    }
}
