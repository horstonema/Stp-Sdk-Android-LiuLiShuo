package com.aiedevice.sdkdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wang_kevin on 2017/9/13.
 */

public class SearchDataBean implements Serializable {
    private List<PlayResourceEntity> resources;

    public List<PlayResourceEntity> getResources() {
        return resources;
    }
}
