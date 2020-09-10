package com.aiedevice.sdkdemo.bean;

import java.io.Serializable;

/**
 * Created by zhanghuatao on 2017/1/25.
 */

public class UpdateModules implements Serializable {

    private String name;//模块名
    private String version;//版本名
    private int vcode;//版本code
    private String channel;//渠道
    private String feature;//更新说明

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVcode() {
        return vcode;
    }

    public void setVcode(int vcode) {
        this.vcode = vcode;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }
}
