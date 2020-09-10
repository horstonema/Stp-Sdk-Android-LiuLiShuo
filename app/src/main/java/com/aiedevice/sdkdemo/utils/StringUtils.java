package com.aiedevice.sdkdemo.utils;

import android.text.TextUtils;

public class StringUtils {
    public static String fillMac(String mac) {
        if (TextUtils.isEmpty(mac)) {
            return null;
        }
        String[] array = mac.split(":");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            String sub = "";
            if (TextUtils.isEmpty(array[i])) {
                sub = "00";
            } else if (array[i].length() < 2) {
                sub = "0" + array[i];
            } else {
                sub = array[i];
            }
            if (TextUtils.isEmpty(sb)) {
                sb.append(sub);
            } else {
                sb.append(":").append(sub);
            }
        }
        return sb.toString().toLowerCase();
    }

}

