package com.aiedevice.sdkdemo;

import android.app.Application;

import com.aiedevice.sdk.push.PushMessageManager;
import com.aiedevice.sdkdemo.logic.MyPushMessageListener;

public class MyApplication extends Application {
    private static MyApplication instance;


    public static MyApplication getInstance() {
        if (instance == null) {
            throw new NullPointerException("App instance hasn't registered");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        //初始化个推推送
        initPushMessageListener();
    }

    private void initPushMessageListener() {
        PushMessageManager.getInstance().addPushMessageListener(new MyPushMessageListener());
    }

    private void releasePushMessageListener() {
        PushMessageManager.getInstance().clearPushMessageListener();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        //释放个推推送
        releasePushMessageListener();
    }

}
