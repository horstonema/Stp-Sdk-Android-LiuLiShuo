package com.aiedevice.sdkdemo.presenter;

import com.aiedevice.sdkdemo.bean.EspBleDevice;
import com.aiedevice.sdkdemo.view.SearchDeviceActivityView;

import java.util.List;

public interface SearchDeviceActivityPresenter extends Presenter<SearchDeviceActivityView> {
    void startScan();

    void stopScan();

    List<EspBleDevice> getEspBleDevices();

    void doNextStep();
}
