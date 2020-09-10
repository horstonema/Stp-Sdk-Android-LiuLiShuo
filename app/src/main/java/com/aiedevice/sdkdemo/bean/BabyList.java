package com.aiedevice.sdkdemo.bean;

import com.aiedevice.sdk.account.AccountManager;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BabyList implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("items")
    public List<AccountManager.BabyMessage> babyList;
}