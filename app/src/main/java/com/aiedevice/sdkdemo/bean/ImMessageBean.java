package com.aiedevice.sdkdemo.bean;

import java.util.List;

/**
 * Created by wang_kevin on 2017/9/13.
 */

public class ImMessageBean {
    private int total;
    private List<MessageItem> list;

    public List<MessageItem> getList() {
        return list;
    }

    static class MessageItem {
        private int id;
        private int type;
        private String content;
        private String created_at;
        private int sendtype;
    }
}
