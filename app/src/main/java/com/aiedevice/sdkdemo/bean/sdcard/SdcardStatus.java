package com.aiedevice.sdkdemo.bean.sdcard;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SdcardStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("SdMemUsed")
    private int memoryUsed;

    @SerializedName("SdMemTotal")
    private int memoryTotal;

    public int getMemoryUsed() {
        return memoryUsed;
    }

    public int getMemoryTotal() {
        return memoryTotal;
    }

}
