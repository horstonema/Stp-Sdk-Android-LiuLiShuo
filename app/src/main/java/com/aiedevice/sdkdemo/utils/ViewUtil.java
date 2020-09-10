package com.aiedevice.sdkdemo.utils;

import android.util.DisplayMetrics;

import com.aiedevice.sdk.SDKConfig;
import com.aiedevice.sdkdemo.MyApplication;

/**
 * Created by monster on 17-6-2.
 */

public class ViewUtil {

    public static void initSize() {
        DisplayMetrics metrics = MyApplication.getInstance().getResources().getDisplayMetrics();
        if (SDKConfig.WINDOW_WIDTH <= 0) {
            SDKConfig.WINDOW_WIDTH = metrics.widthPixels;
        }
        if (SDKConfig.WINDOW_HEIGHT <= 0) {
            SDKConfig.WINDOW_HEIGHT = metrics.heightPixels;
        }
    }
}
