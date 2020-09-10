package com.aiedevice.sdkdemo.bean;

import java.io.Serializable;

/**
 * Created by zhanghuatao on 2016/6/24.
 */
public class FamilyDynamicsMomentEntity implements Serializable {


    public int getMaxid() {
        return maxid;
    }

    public void setMaxid(int maxid) {
        this.maxid = maxid;
    }

    private int maxid;

    @Override
    public String toString() {
        return "FamilyDynamicsMomentEntity{" +
                "maxid=" + maxid +
                '}';
    }
}
