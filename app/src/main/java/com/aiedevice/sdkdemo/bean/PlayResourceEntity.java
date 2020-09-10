package com.aiedevice.sdkdemo.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlayResourceEntity implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private String content;
    /**
     * 播放总时长 单位 秒
     */
    private long length;
    /**
     * 收藏id
     */
    @SerializedName("fid")
    private int favoriteId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public boolean isFavorite() {
        return favoriteId > 0 ? true : false;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }
}
