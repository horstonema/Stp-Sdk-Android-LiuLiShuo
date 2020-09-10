package com.aiedevice.sdkdemo.bean.sdcard;

import java.io.Serializable;

public class PicBookDetail extends PicBookData {
    private static final long serialVersionUID = 1L;

    private String id;
    private String readGuideHtml;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setReadGuideHtml(String readGuideHtml) {
        this.readGuideHtml = readGuideHtml;
    }

    public String getReadGuideHtml() {
        return readGuideHtml;
    }

}

