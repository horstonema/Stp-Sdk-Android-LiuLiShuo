package com.aiedevice.sdkdemo.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zhanghuatao on 2016/6/24.
 */
public class FamilyDynamicsTrackEntity implements Serializable {

    public String getUserPush() {
        return userPush;
    }

    public void setUserPush(String userPush) {
        this.userPush = userPush;
    }

    public String getFaceTrack() {
        return faceTrack;
    }

    public void setFaceTrack(String faceTrack) {
        this.faceTrack = faceTrack;
    }

    @SerializedName("user_push")
    private String userPush;
    @SerializedName("face_track")
    private String faceTrack;

}
