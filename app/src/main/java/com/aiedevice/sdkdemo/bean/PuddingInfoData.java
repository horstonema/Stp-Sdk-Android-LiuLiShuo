package com.aiedevice.sdkdemo.bean;


import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanghuatao on 2017/1/24.
 */

public class PuddingInfoData implements Serializable {
    private List<InfoItem> list;

    public List<InfoItem> getList() {
        return list;
    }

    public void setList(List<InfoItem> list) {
        this.list = list;
    }
}
