package com.aiedevice.sdkdemo.bean;

import java.io.Serializable;
import java.util.List;

public class ChatData implements Serializable {
    private static final long serialVersionUID = 1L;

    private int total;
    private List<ChatEntity> list;

    public int getTotal() {
        return total;
    }

    public List<ChatEntity> getChatEntityList() {
        return list;
    }
}
