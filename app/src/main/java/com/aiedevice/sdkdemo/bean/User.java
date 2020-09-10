package com.aiedevice.sdkdemo.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    @SerializedName("userid")
    private String userId;

    private String name;

    @SerializedName("headimg")
    private String avatar;

    private String phone;

    private String rights;

    private boolean manager;

    private boolean inrange;// 是否在家

    @Override
    public boolean equals(Object o) {
        try {
            return getUserId().equals(((User) o).getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return TextUtils.isEmpty(name) ? phone : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isInrange() {
        return inrange;
    }

    public void setInrange(boolean inrange) {
        this.inrange = inrange;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }
}
