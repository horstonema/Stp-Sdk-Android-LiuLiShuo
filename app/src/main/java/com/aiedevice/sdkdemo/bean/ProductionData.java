package com.aiedevice.sdkdemo.bean;

import java.io.Serializable;

/**
 * Created by yangxin on 16/3/12.
 */
public class ProductionData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String production;
    private String module;

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
