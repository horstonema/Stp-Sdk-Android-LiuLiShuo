package com.aiedevice.sdkdemo.bean;

import java.io.Serializable;

public class WifiResultData implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;

    public static final String RESULT_SUCCESS = "success";

    public static final String RESULT_FAILURE = "failure";

    private String mainctl;

    private String result;

    private String reason;

    private boolean isBinded;

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

    public boolean isBinded() {
        return isBinded;
    }

    public void setIsBinded(boolean isBinded) {
        this.isBinded = isBinded;
    }

    public String getBindtel() {
        return bindtel;
    }

    public void setBindtel(String bindtel) {
        this.bindtel = bindtel;
    }
}
