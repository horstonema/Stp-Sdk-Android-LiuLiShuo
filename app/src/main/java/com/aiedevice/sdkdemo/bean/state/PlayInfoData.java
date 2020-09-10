package com.aiedevice.sdkdemo.bean.state;

import java.io.Serializable;

public class PlayInfoData implements Serializable {

    public static final String START_STATUS = "start";
    public static final String STOP_STATUS = "stop";
    public static final String READYING_STATUS = "readying";
    public static final String READY_STATUS = "ready";
    public static final String PAUSE_STATUS = "pause";

    public static final String TYPE_APP = "app";
    public static final String TYPE_VOICE = "voice";

    private static final long serialVersionUID = 1L;

    private String status;

    private String type;

    private PlayInfoExtras extras;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PlayInfoExtras getExtras() {
        return extras;
    }

    public void setExtras(PlayInfoExtras extras) {
        this.extras = extras;
    }

    /**
     * app播放的
     */
    public boolean isApp() {
        return TYPE_APP.equals(type);
    }

    /**
     * 主控播放的
     */
    public boolean isMaster() {
        return TYPE_VOICE.equals(type);
    }

    @Override
    public String toString() {
        return "PlayInfoData{" +
                "status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", extras=" + extras +
                '}';
    }
}
