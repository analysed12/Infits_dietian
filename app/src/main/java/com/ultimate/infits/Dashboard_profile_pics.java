package com.ultimate.infits;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Dashboard_profile_pics implements Serializable {

    private Bitmap dashboard_photos;

    public Dashboard_profile_pics(Bitmap dashboard_photos) {
        this.dashboard_photos = dashboard_photos;
    }

    public Bitmap getDashboard_photos() {
        return dashboard_photos;
    }

    public void setDashboard_photos(Bitmap dashboard_photos) {
        this.dashboard_photos = dashboard_photos;
    }
}
