package com.ultimate.infits.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatMessage extends ViewModel {

    public final static String MSG_TYPE_SENT = "MSG_TYPE_SENT";
    public final static String MSG_TYPE_RECEIVED = "MSG_TYPE_RECEIVED";

        public  String senderId;
        public String receiverId;
        public String message;
        public String time;
        public String messageBy;
        public String read;
        private String type;
        public String filename;

    String msg;

    MutableLiveData<String> mutableLiveData =new MutableLiveData<>();

    public void SetText(String s){
        mutableLiveData.setValue(s);
    }

    public MutableLiveData<String> getText(){
        return mutableLiveData;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

        public ChatMessage(String senderId, String receiverId, String message, String time, String messageBy, String read,String type,String filename) {
            this.senderId = senderId;
            this.receiverId = receiverId;
            this.message = message;
            this.time = time;
            this.messageBy=messageBy;
            this.read=read;
            this.setType(type);
            this.filename = filename;
        }

        public String getSenderId() {
            return senderId;
        }

        public String getReceiverId() {
            return receiverId;
        }

        public String getMessage() {
            return message;
        }

        public String getTime() {
            return time;
        }

        public String getMessageBy() {
            return messageBy;
        }

        public String getRead() {
            return read;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }


}
