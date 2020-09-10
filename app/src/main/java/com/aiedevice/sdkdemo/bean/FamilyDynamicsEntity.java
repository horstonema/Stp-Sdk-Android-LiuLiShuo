package com.aiedevice.sdkdemo.bean;

import java.io.Serializable;

/**
 * Created by zhanghuatao on 2016/6/24.
 */
public class FamilyDynamicsEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int PIC_TYPE = 0;
    public static final int VIDEO_TYPE = 1;

    private int id;
    private int type;
    private String content;
    private String thumb;

    private long time;

    private boolean showTopLine;
    private boolean showBottomLine;

    private int category;

    private int length;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public boolean isShowTopLine() {
        return showTopLine;
    }

    public void setShowTopLine(boolean showTopLine) {
        this.showTopLine = showTopLine;
    }

    public boolean isShowBottomLine() {
        return showBottomLine;
    }

    public void setShowBottomLine(boolean showBottomLine) {
        this.showBottomLine = showBottomLine;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isVideo() {
        return VIDEO_TYPE == type ? true : false;
    }

}
