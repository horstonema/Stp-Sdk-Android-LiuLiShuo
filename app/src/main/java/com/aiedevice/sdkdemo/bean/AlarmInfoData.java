package com.aiedevice.sdkdemo.bean;

/**
 * Created by wang_kevin on 2017/10/23.
 */

public class AlarmInfoData {
    private String alarmId;
    private int timer;
    private int type;
    private String name;
    private String sound;
    private String repeat;
    private int status;
    private String extra;
    private int week;

    public String getAlarmId() {
        return alarmId;
    }

    public int getTimer() {
        return timer;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getSound() {
        return sound;
    }

    public String getRepeat() {
        return repeat;
    }

    public int getStatus() {
        return status;
    }

    public String getExtra() {
        return extra;
    }
}
