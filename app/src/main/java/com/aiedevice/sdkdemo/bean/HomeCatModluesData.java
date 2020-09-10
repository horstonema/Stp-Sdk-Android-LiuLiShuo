package com.aiedevice.sdkdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanghuatao on 2016/11/24.
 */
public class HomeCatModluesData implements Serializable {

    private List<HomePageCateItem> categories;

    private int total;

    public List<HomePageCateItem> getCategories() {
        return categories;
    }

    public void setCategories(List<HomePageCateItem> categories) {
        this.categories = categories;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int tatol) {
        this.total = tatol;
    }

}
