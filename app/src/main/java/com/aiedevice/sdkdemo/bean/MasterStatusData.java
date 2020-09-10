package com.aiedevice.sdkdemo.bean;

import com.google.gson.annotations.SerializedName;
import com.aiedevice.sdkdemo.bean.state.MsgInfoData;
import com.aiedevice.sdkdemo.bean.state.PlayInfoData;

import java.io.Serializable;

/**
 * Created by zhanghuatao on 2016/12/7.
 */

public class MasterStatusData implements Serializable {

    private boolean online;

    private PlayInfoData playinfo;

    private MsgInfoData msginfo;

    private FamilyDynamicsMomentEntity moment;

    private boolean power;//false未充电 true充电
    @SerializedName("power_supply")
    private boolean powerSupply;//false未插电 true插电
    private int battery;//电量 0 - 100

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public PlayInfoData getPlayinfo() {
        return playinfo;
    }

    public void setPlayinfo(PlayInfoData playinfo) {
        this.playinfo = playinfo;
    }

    public MsgInfoData getMsginfo() {
        return msginfo;
    }

    public void setMsginfo(MsgInfoData msginfo) {
        this.msginfo = msginfo;
    }

    public FamilyDynamicsMomentEntity getMoment() {
        return moment;
    }

    public void setMoment(FamilyDynamicsMomentEntity moment) {
        this.moment = moment;
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public boolean isPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(boolean powerSupply) {
        this.powerSupply = powerSupply;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    @Override
    public String toString() {
        return "MasterStatusData{" +
                "online=" + online +
                ", playinfo=" + playinfo +
                ", msginfo=" + msginfo +
                ", moment=" + moment +
                ", power=" + power +
                ", powerSupply=" + powerSupply +
                ", battery=" + battery +
                '}';
    }
}
