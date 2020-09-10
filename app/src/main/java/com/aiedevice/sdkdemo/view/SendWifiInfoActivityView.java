package com.aiedevice.sdkdemo.view;

public interface SendWifiInfoActivityView extends BaseView {
    void onSendWifiSuccessful();

    void onSendWifiFailure(int errCode, String errMsg);
}
