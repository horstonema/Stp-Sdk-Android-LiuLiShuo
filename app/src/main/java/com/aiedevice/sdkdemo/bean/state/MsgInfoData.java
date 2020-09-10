package com.aiedevice.sdkdemo.bean.state;

import java.io.Serializable;

public class MsgInfoData implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    private int maxid;

    public int getMaxid() {
        return maxid;
    }

    public void setMaxid(int maxid) {
        this.maxid = maxid;
    }

    @Override
    public String toString() {
        return "MsgInfoData{" +
                "maxid=" + maxid +
                '}';
    }
}
