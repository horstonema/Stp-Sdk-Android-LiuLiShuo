package com.aiedevice.sdkdemo.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhanghuatao on 2017/1/25.
 */

public class MasterMaxData implements Serializable {

    private String name;//模块名
    private String version;//版本名
    private int vcode;//版本code
    @SerializedName("update_modules")
    private List<UpdateModules> modules;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getVcode() {
        return vcode;
    }

    public void setVcode(int vcode) {
        this.vcode = vcode;
    }

    public List<UpdateModules> getModules() {
        return modules;
    }

    public void setModules(List<UpdateModules> modules) {
        this.modules = modules;
    }

}
