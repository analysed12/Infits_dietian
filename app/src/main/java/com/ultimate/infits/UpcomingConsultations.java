package com.ultimate.infits;

import android.graphics.Bitmap;

import java.io.Serializable;

public class UpcomingConsultations implements Serializable {

    private String consultation_date;
    private String consultation_time;
    private String consultation_patient;
    private Bitmap consultation_patient_image;
    private String consultation_patient_mobile;
    //private int product_price;
   // private int product_url;

    public UpcomingConsultations(String date, String time, Bitmap img, String name, String mobile) {
        this.consultation_date=date;
        this.consultation_time=time;
        this.consultation_patient=name;
        this.consultation_patient_image=img;
        this.consultation_patient_mobile=mobile;
    }

    public String getConsultation_date() {
        return consultation_date;
    }

    public void setConsultation_date(String consultation_date) {
        this.consultation_date = consultation_date;
    }

    public String getConsultation_time() {
        return consultation_time;
    }

    public void setConsultation_time(String consultation_time) {
        this.consultation_time = consultation_time;
    }

    public String getConsultation_patient() {
        return consultation_patient;
    }

    public void setConsultation_patient(String consultation_patient) {
        this.consultation_patient = consultation_patient;
    }

    public Bitmap getConsultation_patient_image() {
        return consultation_patient_image;
    }

    public void setConsultation_patient_image(Bitmap consultation_patient_image) {
        this.consultation_patient_image = consultation_patient_image;
    }

    public String getConsultation_patient_mobile() {
        return consultation_patient_mobile;
    }

    public void setConsultation_patient_mobile(String consultation_patient_mobile) {
        this.consultation_patient_mobile = consultation_patient_mobile;
    }
}
