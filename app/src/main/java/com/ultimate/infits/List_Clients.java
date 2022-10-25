package com.ultimate.infits;

import android.graphics.Bitmap;

import java.io.Serializable;

public class List_Clients implements Serializable {

    private String plan_type;
    private String client_list_name;
    private Bitmap client_list_image;
    private String client_list_startdate;
    private String client_list_enddate;
    private boolean status;

    public List_Clients(String plan_type, String client_list_name, Bitmap client_list_image, String client_list_startdate, String client_list_enddate, boolean status) {
        this.plan_type = plan_type;
        this.client_list_name = client_list_name;
        this.client_list_image = client_list_image;
        this.client_list_startdate = client_list_startdate;
        this.client_list_enddate = client_list_enddate;
        this.status=status;
    }

    public List_Clients(String plan, String clientID, Bitmap profile) {
        this.plan_type = plan;
        this.client_list_name = clientID;
        this.client_list_image = profile;
        this.client_list_startdate = "nil";
        this.client_list_enddate = "nil";
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public String getClient_list_name() {
        return client_list_name;
    }

    public void setClient_list_name(String client_list_name) {
        this.client_list_name = client_list_name;
    }

    public Bitmap getClient_list_image() {
        return client_list_image;
    }

    public void setClient_list_image(Bitmap client_list_image) {
        this.client_list_image = client_list_image;
    }

    public String getClient_list_startdate() {
        return client_list_startdate;
    }

    public void setClient_list_startdate(String client_list_startdate) {
        this.client_list_startdate = client_list_startdate;
    }

    public String getClient_list_enddate() {
        return client_list_enddate;
    }

    public void setClient_list_enddate(String client_list_enddate) {
        this.client_list_enddate = client_list_enddate;
    }
}
