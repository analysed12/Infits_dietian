package com.ultimate.infits;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ChatLogList implements Serializable {

    private Bitmap profile_pic;
    private String client_name;
    private String client_msg;
    private String msg_by;
    private String client_time;
    private String read;

    public ChatLogList(Bitmap profile_pic, String client_name, String client_msg, String msg_by,String client_time, String read) {
        this.profile_pic = profile_pic;
        this.client_name = client_name;
        this.client_msg = client_msg;
        this.client_time = client_time;
        this.msg_by=msg_by;
        this.read = read;
    }

    public Bitmap getProfile_pic() {
        return profile_pic;
    }

    public String getMsg_by() {
        return msg_by;
    }

    public String getClient_name() {
        return client_name;
    }

    public String getClient_msg() {
        return client_msg;
    }

    public String getClient_time() {
        return client_time;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }
}