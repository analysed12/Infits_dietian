package com.ultimate.infits;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomePage extends AppCompatActivity {

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(WelcomePage.this, LoginScreen.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}