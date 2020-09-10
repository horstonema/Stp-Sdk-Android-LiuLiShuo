package com.aiedevice.sdkdemo.presenter;

import com.aiedevice.sdkdemo.view.PreConnectActivityView;

public interface PreConnectActivityPresenter extends Presenter<PreConnectActivityView> {
    void validConnectConditions();

    void startBluetoothStateChangeMonitor();

    void stopBluetoothStateChangeMonitor();
}

