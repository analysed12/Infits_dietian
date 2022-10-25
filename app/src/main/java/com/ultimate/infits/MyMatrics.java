package com.ultimate.infits;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ultimate.infits.adapter.FoodAdapter;
import com.ultimate.infits.model.FoodItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyMatrics extends AppCompatActivity {

    String url ="http://192.168.1.5/infits/metrics.php";
    ProgressBar StepsProgress,HeartProgress,WaterProgress,SleepProgress,WeightTrackProgress,CalorieProgress;
    TextView myCaloriesTrack,myWeightTrack,mySleepMin,mySleepHour,myWater,myHeartRate,mySteps;
    TextView myGoalCaloriesTrack,myGoalWeightTrack,myGoalSleep,myGoalWater,downBPM,upBPM,myGoalSteps;
    LinearLayout stepsLL,heartRateLL,waterLL,sleepLL,weightLL,caloriesLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_matrics);

        ids();
        refreshData();


        myIntent();


    }

    private void myIntent() {
        stepsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyMatrics.this,MyStats.class));
            }
        });
        caloriesLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyMatrics.this,MyStats.class));
            }
        });
        heartRateLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyMatrics.this,MyStats.class));
            }
        });
        weightLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyMatrics.this,MyStats.class));
            }
        });
        waterLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyMatrics.this,MyStats.class));
            }
        });
        sleepLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyMatrics.this,MyStats.class));
            }
        });
    }

    private void refreshData() {

        StringRequest req =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray item = new JSONArray(response);
                    for (int i = 0; i < item.length(); i++) {
                        JSONObject jsonObj = item.getJSONObject(i);
                        String stepsgoal =jsonObj.getString("stepsgoal");
                        String steps =jsonObj.getString("steps");
                        String watergoal =jsonObj.getString("watergoal");
                        String water =jsonObj.getString("water");
                        String sleepgoal =jsonObj.getString("sleepgoal");
                        String sleephrs =jsonObj.getString("sleephrs");
                        String sleepmins =jsonObj.getString("sleepmins");
                        String weightgoal =jsonObj.getString("weightgoal");
                        String weight =jsonObj.getString("weight");
                        Toast.makeText(MyMatrics.this, water, Toast.LENGTH_SHORT).show();
                        myWater.setText(water);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map =new HashMap<>();
                map.put("userId",DataFromDatabase.dietitianuserID);
                return map;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);


    }


    private void ids(){

        myCaloriesTrack=findViewById(R.id.myCaloriesTrack);
        myWeightTrack=findViewById(R.id.myWeightTrack);
        mySleepHour=findViewById(R.id.mySleepHour);
        mySleepMin=findViewById(R.id.mySleepMin);
        myWater=findViewById(R.id.myWater);
        myHeartRate=findViewById(R.id.myHeartRate);
        mySteps=findViewById(R.id.mySteps);

        stepsLL=findViewById(R.id.stepsLL);
        caloriesLL=findViewById(R.id.caloriesLL);
        heartRateLL=findViewById(R.id.heartRateLL);
        waterLL=findViewById(R.id.waterLL);
        weightLL=findViewById(R.id.weightLL);
        sleepLL=findViewById(R.id.sleepLL);

        StepsProgress=findViewById(R.id.StepsProgress);
        HeartProgress=findViewById(R.id.HeartProgress);
        WaterProgress=findViewById(R.id.WaterProgress);
        SleepProgress=findViewById(R.id.SleepProgress);
        WeightTrackProgress=findViewById(R.id.WeightTrackProgress);
        CalorieProgress=findViewById(R.id.CalorieProgress);

        myGoalCaloriesTrack=findViewById(R.id.myGoalCaloriesTrack);
        myGoalSleep=findViewById(R.id.myGoalSleep);
        myGoalSteps=findViewById(R.id.myGoalSteps);
        myGoalWeightTrack=findViewById(R.id.myGoalWeightTrack);
        myGoalWater=findViewById(R.id.myGoalWater);
        upBPM=findViewById(R.id.upBPM);
        downBPM=findViewById(R.id.downBPM);
    }
}