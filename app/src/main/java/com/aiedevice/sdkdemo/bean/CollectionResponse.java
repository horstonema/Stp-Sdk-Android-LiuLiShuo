package com.aiedevice.sdkdemo.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NOTE-026 on 2016/7/7.
 */
public class CollectionResponse {
    private int id;//收藏id
    private int rid;//资源id
    private String title;//标题
    private int cid;//分类id
    private String pic;//分类图片
    private int age;//收藏年龄
    private long length;//播放时长 单位秒
    /**
     * 是否有效 1 为有效 0为无效
     */
    private int available;
    private String cname;//分类标题
    private long created_at;
    @SerializedName("res_db")
    private String resDb;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getCateName() {
        return cname;
    }

    public void setCateName(String cateName) {
        this.cname = cateName;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public String getResDb() {
        return resDb;
    }

    public void setResDb(String resDb) {
        this.resDb = resDb;
    }
}
