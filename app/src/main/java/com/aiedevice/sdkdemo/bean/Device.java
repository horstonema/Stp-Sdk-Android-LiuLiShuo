package com.aiedevice.sdkdemo.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.aiedevice.sdk.base.bean.JuanReqData;

public class Device extends JuanReqData {

    /**  */
    private static final long serialVersionUID = 1L;

    @SerializedName("devid")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("devtype")
    private int deviceType;

    private int power;
    private String eventtm;

    @Override
    public boolean equals(Object o) {
        try {
            return id.equals(((Device) o).id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getEventtm() {
        try {
            if (!TextUtils.isEmpty(eventtm)) {
                if (eventtm.contains("1970-01-01")) {
                    return null;
                }
                String orgTime = eventtm.replace("T", " ").replace("Z", "");
                return orgTime.substring(5, orgTime.length() - 3).replace("-", "月");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eventtm;
    }

    public void setEventtm(String eventtm) {
        this.eventtm = eventtm;
    }
}
