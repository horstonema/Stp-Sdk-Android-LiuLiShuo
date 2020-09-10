package com.aiedevice.sdkdemo.bean;

import java.io.Serializable;

public class ChatEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int type;
    private String content;
    private String created_at;
    private int sendtype;
    private int length;
    private int size;

    public int getId() {return id;}
    public int getType() {return type;}
    public String getContent() {return content;}
    public String getCreated_at() {return created_at;}
    public int getSendtype() {return sendtype;}
    public int getLength() {return length;}
    public int getSize() {return size;}
}
