package com.aiedevice.sdkdemo.bean.state;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.aiedevice.sdkdemo.bean.DateUtil;

import java.io.Serializable;

public class PlayInfoContent implements Serializable {
    /**
     * moring call
     */
    public static final String FLAG_CHILDREN_MORNIGT = "morning";
    /**
     * night cal
     */
    public static final String FLAG_CHILDREN_NIGHT = "bedtime";
    /**
     * mornig and night call history
     */
    public static final String FLAG_CHILDREN_HISTORY = "alarmhistory";

    /**
     * 互动故事
     */
    public static final String FLAG_INTTERSTORY = "interstory";

    private static final long serialVersionUID = 1L;

    private String url;

    private String title;

    private int catid;

    private int id;

    /**
     * 播放时长 单位秒
     */
    private long length;

    /**
     * 封面图
     */
    private String pic;
    /**
     * 专辑名
     */
    private String cname;

    /**
     * 收藏id
     */
    @SerializedName("fid")
    private int favoriteId;

    private String flag;

    //区分来源
    private String src;

    @SerializedName("img_large")
    /**专辑封面图*/
    private String cateImageUrl;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public boolean isFarorite() {
        return favoriteId > 0 ? true : false;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCateImageUrl() {
        return cateImageUrl;
    }

    public void setCateImageUrl(String cateImageUrl) {
        this.cateImageUrl = cateImageUrl;
    }

    /**
     * 获取格式化过的 总时长
     *
     * @return
     */
    public String getFormatLength() {
        return DateUtil.formatPlayTotalTime(length);
    }

    public boolean isChildrenMorning() {
        return TextUtils.equals(flag, FLAG_CHILDREN_MORNIGT);
    }

    public boolean isChildrenNight() {
        return TextUtils.equals(flag, FLAG_CHILDREN_NIGHT);
    }

    public boolean isChildrenHistory() {
        return TextUtils.equals(flag, FLAG_CHILDREN_HISTORY);
    }

    public boolean isInterStory() {
        return TextUtils.equals(flag, FLAG_INTTERSTORY);
    }

    @Override
    public String toString() {
        return "PlayInfoContent{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", catid=" + catid +
                ", id=" + id +
                ", length=" + length +
                ", pic='" + pic + '\'' +
                ", cname='" + cname + '\'' +
                ", favoriteId=" + favoriteId +
                ", flag='" + flag + '\'' +
                ", src='" + src + '\'' +
                ", cateImageUrl='" + cateImageUrl + '\'' +
                '}';
    }
}
