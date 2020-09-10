package com.aiedevice.sdkdemo.bean;

import java.util.ArrayList;

/**
 * Created by NOTE-026 on 2016/7/7.
 */
public class CollectionReponseData {
    private ArrayList<CollectionResponse> list;
    /**
     * 总数
     */
    private int count;

    public CollectionReponseData() {
    }

    public ArrayList<CollectionResponse> getList() {
        return list;
    }

    public void setList(ArrayList<CollectionResponse> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
