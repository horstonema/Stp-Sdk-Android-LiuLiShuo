package com.aiedevice.sdkdemo.bean;

public class DeviceBindInfo {
    private String mainctl;
    private String result;
    private String reason;
    private boolean isBinded;
    private boolean isFirstBinded;
    private String bindtel;

    public String getMainctl() {
        return mainctl;
    }

    public void setMainctl(String mainctl) {
        this.mainctl = mainctl;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isIsBinded() {
        return isBinded;
    }

    public void setIsBinded(boolean isBinded) {
        this.isBinded = isBinded;
    }

    public boolean isIsFirstBinded() {
        return isFirstBinded;
    }

    public void setIsFirstBinded(boolean isFirstBinded) {
        this.isFirstBinded = isFirstBinded;
    }

    public String getBindtel() {
        return bindtel;
    }

    public void setBindtel(String bindtel) {
        this.bindtel = bindtel;
    }
}
