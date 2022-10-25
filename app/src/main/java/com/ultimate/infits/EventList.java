package com.ultimate.infits;

import java.io.Serializable;

public class EventList implements Serializable {
    private String cal_apt_type;

    public void setCal_apt_type(String cal_apt_type) {
        this.cal_apt_type = cal_apt_type;
    }

    public void setCal_apt_clientname(String cal_apt_clientname) {
        this.cal_apt_clientname = cal_apt_clientname;
    }

    public void setCal_apt_time(String cal_apt_time) {
        this.cal_apt_time = cal_apt_time;
    }

    public void setCal_apt_duration(String cal_apt_duration) {
        this.cal_apt_duration = cal_apt_duration;
    }

    public void setCal_apt_location(String cal_apt_location) {
        this.cal_apt_location = cal_apt_location;
    }

    public void setCal_apt_note(String cal_apt_note) {
        this.cal_apt_note = cal_apt_note;
    }

    public void setCal_apt_title(String cal_apt_title) {
        this.cal_apt_title = cal_apt_title;
    }

    public void setCal_apt_link(String cal_apt_link) {
        this.cal_apt_link = cal_apt_link;
    }

    public void setCal_apt_date_month(String cal_apt_date_month) {
        this.cal_apt_date_month = cal_apt_date_month;
    }

    public void setCal_apt_date_date(String cal_apt_date_date) {
        this.cal_apt_date_date = cal_apt_date_date;
    }

    private String cal_apt_clientname;
    private String cal_apt_time;
    private String cal_apt_duration;
    private String cal_apt_location;
    private String cal_apt_note;
    private String cal_apt_title;
    private String cal_apt_link;
    private String cal_apt_date_month;
    private String cal_apt_date_date;
    private String cal_apt_notifyme;

    public EventList(String cal_apt_type, String cal_apt_clientname, String cal_apt_time,String cal_apt_duration,String cal_apt_location,String cal_apt_note,String cal_apt_title,String cal_apt_date_month,String cal_apt_date_date,String cal_apt_link,String cal_apt_notifyme) {
        this.cal_apt_type = cal_apt_type;
        this.cal_apt_duration=cal_apt_duration;
        this.cal_apt_clientname = cal_apt_clientname;
        this.cal_apt_time = cal_apt_time;
        this.cal_apt_location=cal_apt_location;
        this.cal_apt_title=cal_apt_title;
        this.cal_apt_note=cal_apt_note;
        this.cal_apt_date_month=cal_apt_date_month;
        this.cal_apt_date_date=cal_apt_date_date;
        this.cal_apt_link=cal_apt_link;
        this.cal_apt_notifyme=cal_apt_notifyme;
    }

    public String getCal_apt_type() {
        return cal_apt_type;
    }

    public String getCal_apt_clientname() {
        return cal_apt_clientname;
    }

    public String getCal_apt_time() {
        return cal_apt_time;
    }

    public String getCal_apt_duration() {
        return cal_apt_duration;
    }

    public String getCal_apt_location() {
        return cal_apt_location;
    }

    public String getCal_apt_note() {
        return cal_apt_note;
    }

    public String getCal_apt_title() {
        return cal_apt_title;
    }

    public String getCal_apt_date_month() {
        return cal_apt_date_month;
    }

    public String getCal_apt_date_date() {
        return cal_apt_date_date;
    }

    public String getCal_apt_link() {
        return cal_apt_link;
    }

    public void setCal_apt_notifyme(String cal_apt_notifyme) {
        this.cal_apt_notifyme = cal_apt_notifyme;
    }
    public String getCal_apt_notifyme() {
        return cal_apt_notifyme;
    }
}
