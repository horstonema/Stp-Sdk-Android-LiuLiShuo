package com.aiedevice.sdkdemo.bean;

import java.util.ArrayList;

/**
 * Created by wang_kevin on 2017/10/23.
 */

public class AlarmListData {
    private static final long serialVersionUID = 1L;

    private int total;
    private ArrayList<AlarmInfoData> alarms;

    public ArrayList<AlarmInfoData> getAlarms() {
        return alarms;
    }
}
