package com.ultimate.infits;

import java.io.Serializable;

public class DashboardMessages implements Serializable {

    private String consultation_type;
    private String consultation_patient_name;
    private String consultation_patient_image;
    //private int product_price;
    // private int product_url;

    public DashboardMessages(String img, String name, String type) {
        this.consultation_patient_image = img;
        this.consultation_patient_name = name;
        this.consultation_type = type;
    }

    public String getConsultation_type() {
        return consultation_type;
    }

    public void setConsultation_type(String consultation_type) {
        this.consultation_type = consultation_type;
    }

    public String getConsultation_patient_name() {
        return consultation_patient_name;
    }

    public void setConsultation_patient_name(String consultation_patient_name) {
        this.consultation_patient_name = consultation_patient_name;
    }

    public String getConsultation_patient_image() {
        return consultation_patient_image;
    }

    public void setConsultation_patient_image(String consultation_patient_image) {
        this.consultation_patient_image = consultation_patient_image;
    }
}
