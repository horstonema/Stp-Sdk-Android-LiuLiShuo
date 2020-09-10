package com.aiedevice.sdkdemo.bean.sdcard;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PicbookList implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("list")
    private List<PicBookData> picbookList;
    private int total;

    public List<PicBookData> getPicbookList() {
        return picbookList;
    }

    public int getTotal() {
        return total;
    }

}
