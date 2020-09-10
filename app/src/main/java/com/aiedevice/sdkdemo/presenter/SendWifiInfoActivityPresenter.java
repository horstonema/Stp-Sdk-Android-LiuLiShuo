package com.aiedevice.sdkdemo.presenter;

import android.bluetooth.BluetoothDevice;

import com.aiedevice.sdkdemo.view.SendWifiInfoActivityView;
import com.esp.iot.blufi.communiation.BlufiConfigureParams;

public interface SendWifiInfoActivityPresenter extends Presenter<SendWifiInfoActivityView> {
    void startConfigure(BluetoothDevice device, BlufiConfigureParams params);

    void stopConfigure();
}
