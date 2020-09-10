package com.aiedevice.sdkdemo.bean;

import java.io.Serializable;

/**
 * Created by Ruby on 2016/7/27 0027.
 * 设备信息
 */
public class InfoItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;
    private String val;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
