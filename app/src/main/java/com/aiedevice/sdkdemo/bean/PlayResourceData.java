package com.aiedevice.sdkdemo.bean;

import java.io.Serializable;
import java.util.List;

public class PlayResourceData implements Serializable {

    /**  */
    private static final long serialVersionUID = 1L;

    private int count;

    private int pages;

    private List<PlayResourceEntity> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<PlayResourceEntity> getList() {
        return list;
    }

    public void setList(List<PlayResourceEntity> list) {
        this.list = list;
    }

}
