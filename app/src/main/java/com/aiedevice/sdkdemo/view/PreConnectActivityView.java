package com.aiedevice.sdkdemo.view;

import com.aiedevice.sdkdemo.view.BaseView;

public interface PreConnectActivityView extends BaseView {
    void showOpenBluetoothDialog();

    void showOpenLocationDialog();

    void gotoFindDeviceActivity();
}
