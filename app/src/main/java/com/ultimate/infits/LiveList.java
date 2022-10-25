package com.ultimate.infits;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class LiveList extends AppCompatActivity {

    String[] permissionArray = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_PHONE_STATE,Manifest.permission.MODIFY_AUDIO_SETTINGS};
    int requestCode = 1;


    String url = String.format("%slive.php",DataFromDatabase.ipConfig);

    ArrayList<String> nowTitle = new ArrayList<>();
    ArrayList<String> nowDate = new ArrayList<>();
    ArrayList<String> nowTime = new ArrayList<>();
    ArrayList<String> nowNote = new ArrayList<>();

    ArrayList<String> upTitle = new ArrayList<>();
    ArrayList<String> upDate = new ArrayList<>();
    ArrayList<String> upTime = new ArrayList<>();
    ArrayList<String> upNote = new ArrayList<>();

    ArrayList<String> endTitle = new ArrayList<>();
    ArrayList<String> endDate = new ArrayList<>();
    ArrayList<String> endTime = new ArrayList<>();
    ArrayList<String> endNote = new ArrayList<>();

    public static LiveList act;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_list);
        act = this;
        if (!isPermissionGranted()) {
            askPermissions();
        }
        RecyclerView upcomingList = findViewById(R.id.upcoming_live);
        upcomingList.setAdapter(new UpcomingListAdapter(getApplicationContext(), upTitle, upDate, upTime, upNote));
        upcomingList.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView nowStreaming = findViewById(R.id.streaming_live);
        nowStreaming.setAdapter(new NowStreamingLive(getApplicationContext(), upTitle, upDate, upTime, upNote));
        nowStreaming.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView endStreaming = findViewById(R.id.live_history);
        endStreaming.setAdapter(new EndStreamingLive(getApplicationContext(), endTitle, endDate, endTime, endNote));
        endStreaming.setLayoutManager(new LinearLayoutManager(this));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y-MM-dd");
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");

        Date time1 = new Date();
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(time1);
        calendar1.add(Calendar.DATE, 1);

        Date dateToday1 = calendar1.getTime();
        Calendar calendar = Calendar.getInstance();
        Calendar dateToday = Calendar.getInstance();

        String currentTime = simpleTimeFormat.format(calendar.getTime());

        System.out.println("Azar"+simpleDateFormat.format(time1));

        try {
            Date current = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
            calendar.setTime(current);
            calendar.add(Calendar.DATE,1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,response -> {
                if(response.equals("failure")){
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("live");
                        for (int i = 0;i < jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String status = object.getString("status");
                            String date = object.getString("date");
                            String time = object.getString("time");
                            String title = object.getString("title");
                            String note = object.getString("note");
                            Date liveTimeToday = new SimpleDateFormat("HH:mm:ss").parse(time);
                            Calendar calendarLive = Calendar.getInstance();
                            calendarLive.setTime(liveTimeToday);
                            calendarLive.add(Calendar.DATE,1);

                            Date todayDate = simpleDateFormat.parse(simpleDateFormat.format(time1.getTime()));

                            Date liveTime = calendarLive.getTime();

                            Date liveDate = simpleDateFormat.parse(date);

                            if (liveDate.compareTo(todayDate) > 0 && dateToday1.after(calendarLive.getTime())){
                                System.out.println("Inside UP");
                                System.out.println(date.equals(simpleDateFormat.format(dateToday.getTime())));
                                System.out.println();
                                upTitle.add(title);
                                upDate.add(date);
                                upTime.add(time);
                                upNote.add(note);
                                upcomingList.setAdapter(new UpcomingListAdapter(getApplicationContext(),upTitle,upDate,upTime,upNote));
                            }
                            else if (liveDate.compareTo(todayDate) <= 0 && dateToday1.after(calendarLive.getTime())){
                                if (status.equals("ended")){
                                    endTitle.add(title);
                                    endDate.add(date);
                                    endTime.add(time);
                                    endNote.add(note);
                                    endStreaming.setAdapter(new EndStreamingLive(getApplicationContext(), endTitle, endDate, endTime, endNote));
                                }
                                else if (status.equals("upcoming")){
                                    System.out.println("Inside Now");
                                    nowTitle.add(title);
                                    nowDate.add(date);
                                    nowTime.add(time);
                                    nowNote.add(note);
                                    nowStreaming.setAdapter(new NowStreamingLive(getApplicationContext(),nowTitle,nowDate,nowTime,nowNote));
                                }
                            }
                        }
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
        },error -> {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void GotoLive(String room,String date){
        Intent intent = new Intent(this,LiveAct.class);
        intent.putExtra("room",room);
        intent.putExtra("date",date);
        startActivity(intent);
    }
    private void askPermissions() {
        ActivityCompat.requestPermissions(this, permissionArray, requestCode);
    }

    private boolean isPermissionGranted() {
        for (String per : permissionArray) {
            if (ActivityCompat.checkSelfPermission(this, per) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
}