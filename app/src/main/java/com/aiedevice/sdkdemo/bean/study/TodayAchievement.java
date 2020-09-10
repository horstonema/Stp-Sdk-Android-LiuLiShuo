package com.aiedevice.sdkdemo.bean.study;

import java.io.Serializable;

public class TodayAchievement implements Serializable {

    /**
     * readCount : 今日阅读次数
     * clickCount : 今日点击次数
     * studyTime : 今日学习时长
     * thumbUrl : 点读绘本封面
     * bookName : 点读绘本书名
     * bookClickCount : 点读绘本点读次数
     */

    private int readCount;
    private int clickCount;
    private int studyTime;
    private String thumbUrl;
    private String bookName;
    private int bookClickCount;

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(int studyTime) {
        this.studyTime = studyTime;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getBookClickCount() {
        return bookClickCount;
    }

    public void setBookClickCount(int bookClickCount) {
        this.bookClickCount = bookClickCount;
    }
}
