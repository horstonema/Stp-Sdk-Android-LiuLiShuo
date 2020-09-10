package com.aiedevice.sdkdemo.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MainctrlListData implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;

    @SerializedName("mcids")
    private ArrayList<DeviceDetail> masters;

    public ArrayList<DeviceDetail> getMasters() {
        return masters;
    }

    public void setMasters(ArrayList<DeviceDetail> masters) {
        this.masters = masters;
    }

}
