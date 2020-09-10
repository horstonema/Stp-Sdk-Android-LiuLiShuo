package com.aiedevice.sdkdemo.bean.sdcard;

import java.io.Serializable;

public class PicbookDownloadStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private int mtype;
    private String title;
    private String body;
    private DownloadStatusExtra extra;

    public int getMtype() {
        return mtype;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public DownloadStatusExtra getExtra() {
        return extra;
    }

    public static class DownloadStatusExtra {
        private String mid;
        private int progress;

        public String getMid() {
            return mid;
        }

        public int getProgress() {
            return progress;
        }

    }

}
