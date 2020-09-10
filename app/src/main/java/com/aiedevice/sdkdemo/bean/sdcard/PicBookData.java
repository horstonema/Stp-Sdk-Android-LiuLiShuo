package com.aiedevice.sdkdemo.bean.sdcard;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PicBookData implements Serializable {
    private static final long serialVersionUID = 1L;
    // 0:等待下载 1:下载中 2:下载完成 3:下载失败
    public static final int STATUS_WAIT_DOWNLOAD = 0;
    public static final int STATUS_DOWNLOADING = 1;
    public static final int STATUS_DOWNLOAD_FINISH = 2;
    public static final int STATUS_DOWNLOAD_FAIL = 3;
    private static final String[] statusDesc = new String[]{"等待下载", "下载中", "下载完成", "下载失败"};

    @SerializedName("mid")
    private String bookId;
    private int status;
    private int progress;
    @SerializedName("created_at")
    private long createdTime;
    @SerializedName("updated_at")
    private long updatedTime;

    @SerializedName("name")
    private String bookName;
    private String author;
    private String cover;
    private int size;

    public String getBookId() {
        return bookId;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusDesc() {
        if (status >= 0 && status < statusDesc.length) {
            return statusDesc[status];
        } else {
            return "";
        }
    }

    public int getProgress() {
        return progress;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public long getUpdatedTime() {
        return updatedTime;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getCover() {
        return cover;
    }

    public int getSize() {
        return size;
    }
}
