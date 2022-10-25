package com.ultimate.infits;

import java.io.Serializable;
import java.util.List;

public class AcceptRejectList implements Serializable {

    private String plan_type;
    private String client_name;
    private String client_image;
    //private int product_price;
    // private int product_url;

    public AcceptRejectList(String img, String name, String plantype) {
        this.client_image = img;
        this.client_name = name;
        this.plan_type = plantype;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_image() {
        return client_image;
    }

    public void setClient_image(String client_image) {
        this.client_image = client_image;
    }
}
