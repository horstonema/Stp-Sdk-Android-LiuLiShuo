package com.aiedevice.sdkdemo.bean;


/**
 * Created by LiuYanBing on 2017/3/8.
 * 绑定设备失败情况下的数据获取
 */

public class MasterDatailError extends DeviceDetail {
    /**
     * 是否已绑定管理员
     */
    private boolean isBinded;
    /**
     * 绑定管理员的手机号码
     */
    private String bindtel;

    public boolean isBinded() {
        return isBinded;
    }

    public void setBinded(boolean binded) {
        isBinded = binded;
    }

    public String getBindtel() {
        return bindtel;
    }

    public void setBindtel(String bindtel) {
        this.bindtel = bindtel;
    }
}
