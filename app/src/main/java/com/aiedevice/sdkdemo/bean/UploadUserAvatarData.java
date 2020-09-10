package com.aiedevice.sdkdemo.bean;

import java.io.Serializable;

public class UploadUserAvatarData implements Serializable {
    /**  */
    private static final long serialVersionUID = 1L;

    private String img;
    private String large;
    private String thumb;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
