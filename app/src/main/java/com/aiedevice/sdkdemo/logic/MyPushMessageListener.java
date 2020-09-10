package com.aiedevice.sdkdemo.logic;

import android.util.Log;

import com.aiedevice.sdk.push.PushMessageListener;
import com.aiedevice.sdk.push.bean.PushMessage;

public class MyPushMessageListener implements PushMessageListener {
    private static final String TAG = MyPushMessageListener.class.getSimpleName();

    @Override
    public void onReceivePushMessage(PushMessage message) {
        String msg = message.getMsgData();
        Log.d(TAG, "[onReceivePushMessage] msg=" + msg);
    }
}
