package com.aiedevice.sdkdemo.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zhanghuatao on 2016/11/17.
 */
public class HomePageCateItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String ACT_RES = "res";//是资源标记
    //专辑id 没有rid 和cid 时
    private int id;
    private String act;
    private String title;
    private String description;
    private int total;
    private String thumb;
    private String img;
    @SerializedName("new")
    private boolean news;
    private boolean hots;
    //资源id
    private int rid;
    //cid 专辑id  有 cid和rid时
    private int cid;
    /**
     * 分类id
     */
    private int moudleId;

    //begin 双语课程
    private int star;
    private boolean locked;
    //end   双语课程

    /**
     * 是否是每一行的最后一个 自己添加字段
     */
    private boolean isLineLast;

    private int star_total;

    /* 宝宝动态中的图片 */
    private String src;

    /*  宝宝动态中判断类型：0:图片；1:视频 */
    private int type;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isBabyVideo() {
        return type == 1;
    }

    public int getStarTotal() {
        return star_total;
    }

    public void setStarTotal(int star_total) {
        this.star_total = star_total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isNews() {
        return news;
    }

    public void setNews(boolean news) {
        this.news = news;
    }

    public boolean isHots() {
        return hots;
    }

    public void setHots(boolean hots) {
        this.hots = hots;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getMoudleId() {
        return moudleId;
    }

    public void setMoudleId(int moudleId) {
        this.moudleId = moudleId;
    }

    public boolean isLineLast() {
        return isLineLast;
    }

    public void setLineLast(boolean lineLast) {
        isLineLast = lineLast;
    }

    public boolean isResource() {
        return TextUtils.equals(act, ACT_RES);
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

}
