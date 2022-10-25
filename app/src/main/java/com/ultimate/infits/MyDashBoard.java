package com.ultimate.infits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MyDashBoard extends AppCompatActivity {

    ImageView dietChart,wayToMetrics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dash_board);

        ids();
        intents();

    }

    private void intents() {
        dietChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyDashBoard.this,MyDietChart.class));
            }
        });
        wayToMetrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyDashBoard.this,MyMatrics.class));
            }
        });


    }

    private void ids() {
        dietChart=findViewById(R.id.dietChart);
        wayToMetrics=findViewById(R.id.wayToMetrics);
    }
}