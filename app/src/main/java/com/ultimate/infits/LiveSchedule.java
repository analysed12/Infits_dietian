package com.ultimate.infits;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LiveSchedule extends AppCompatActivity{
    TextView sun_date,mon_date,tue_date,wed_date,thur_date,fri_date,sat_date;
    LinearLayout l1,l2,l3,l4,l5,l6,l7;
    String selected_date;
    String selected_month;
    int specific_d;
    RequestQueue queue;
    DataFromDatabase dataFromDatabase;
    String url = String.format("%slivesetter.php",DataFromDatabase.ipConfig);
    Button cancel_live;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_live);
        setUpUI();
        TextView[] dateArr = {sun_date,mon_date,tue_date,wed_date,thur_date,fri_date,sat_date};
        final String[] start_time = new String[1];
        final String[] end_time = new String[1];

        TextView display_month,display_time,display_date;
        String display_month1 = null,display_time1,display_date1;
        TimePicker tt;
        ImageView iv1;
        EditText title_aptment,note_aptment;
        display_date=findViewById(R.id.new_appointment_date);
        display_month=findViewById(R.id.new_appointment_month);
        display_time=findViewById(R.id.new_appointment_time);
        cancel_live = findViewById(R.id.new_live_cancel);
        tt=findViewById(R.id.timePicker1);
        ArrayList<String> days = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd ");
        SimpleDateFormat sdf1= new SimpleDateFormat("MMM");
        SimpleDateFormat sdf3=new SimpleDateFormat("hh:mm a");
        Calendar cal = Calendar.getInstance();
        start_time[0]=sdf3.format(cal.getTime());
        display_time.setText(start_time[0]);
        DateFormat dateFormat1 = new SimpleDateFormat("dd");
        Calendar cal1 = Calendar.getInstance();
        Date date = cal1.getTime();
        selected_date = dateFormat1.format(date);
        Toast.makeText(getApplicationContext(),"selected_date="+selected_date,Toast.LENGTH_SHORT).show();
        selected_month=sdf1.format(date);
        display_month.setText(selected_month);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            cal.add(Calendar.DAY_OF_YEAR, 0);
            days.add(sdf.format(cal.getTime()));
            for(int i = 0; i< 6; i++){
                cal.add(Calendar.DAY_OF_YEAR, 1);
                    days.add(sdf.format(cal.getTime()));
            }
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
            cal.add(Calendar.DAY_OF_YEAR, -1);
            days.add(sdf.format(cal.getTime()));
            for(int i = 0; i< 6; i++){
                cal.add(Calendar.DAY_OF_YEAR, 1);
                days.add(sdf.format(cal.getTime()));
            }
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY){
            cal.add(Calendar.DAY_OF_YEAR, -2);
            days.add(sdf.format(cal.getTime()));
            for(int i = 0; i< 6; i++){
                cal.add(Calendar.DAY_OF_YEAR, 1);
                days.add(sdf.format(cal.getTime()));
            }
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY){
            cal.add(Calendar.DAY_OF_YEAR, -3);
            days.add(sdf.format(cal.getTime()));
            for(int i = 0; i< 6; i++){
                cal.add(Calendar.DAY_OF_YEAR, 1);
                days.add(sdf.format(cal.getTime()));
            }
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY){
            cal.add(Calendar.DAY_OF_YEAR, -4);
            days.add(sdf.format(cal.getTime()));
            for(int i = 0; i< 6; i++){
                cal.add(Calendar.DAY_OF_YEAR, 1);
                days.add(sdf.format(cal.getTime()));
            }
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY){
            cal.add(Calendar.DAY_OF_YEAR, -5);
            days.add(sdf.format(cal.getTime()));
            for(int i = 0; i< 6; i++){
                cal.add(Calendar.DAY_OF_YEAR, 1);
                days.add(sdf.format(cal.getTime()));
            }
        }
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            cal.add(Calendar.DAY_OF_YEAR, -6);
            days.add(sdf.format(cal.getTime()));
            for(int i = 0; i< 6; i++){
                cal.add(Calendar.DAY_OF_YEAR, 1);
                days.add(sdf.format(cal.getTime()));
            }
        }

        for (int a = 0 ; a < dateArr.length;a++) {
            dateArr[a].setText(days.get(a));
        }

        specific_d=60;

        iv1= findViewById(R.id.calendar_back);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveSchedule.super.onBackPressed();
            }
        });
        l1=findViewById(R.id.sunday);
        l2=findViewById(R.id.monday);
        l3=findViewById(R.id.tuesday);
        l4=findViewById(R.id.wednesday);
        l5=findViewById(R.id.thursday);
        l6=findViewById(R.id.friday);
        l7=findViewById(R.id.saturday);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setBackgroundColor(Color.parseColor("#C6E0FF"));
                l2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l7.setBackgroundColor(Color.parseColor("#FFFFFF"));
              selected_date=sun_date.getText().toString().trim();
                display_date.setText(selected_date);
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l2.setBackgroundColor(Color.parseColor("#C6E0FF"));
                l1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l7.setBackgroundColor(Color.parseColor("#FFFFFF"));
               selected_date=mon_date.getText().toString().trim();
                display_date.setText(selected_date);

            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l3.setBackgroundColor(Color.parseColor("#C6E0FF"));
                l1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l7.setBackgroundColor(Color.parseColor("#FFFFFF"));
               selected_date=tue_date.getText().toString().trim();
                display_date.setText(selected_date);

            }
        });
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l4.setBackgroundColor(Color.parseColor("#C6E0FF"));
                l1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                selected_date=wed_date.getText().toString().trim();
                display_date.setText(selected_date);

            }
        });
        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l5.setBackgroundColor(Color.parseColor("#C6E0FF"));
                l1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                selected_date=thur_date.getText().toString().trim();
                display_date.setText(selected_date);
            }
        });
        l6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l6.setBackgroundColor(Color.parseColor("#C6E0FF"));
                l1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                selected_date=fri_date.getText().toString().trim();
                display_date.setText(selected_date);

            }
        });
        l7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l7.setBackgroundColor(Color.parseColor("#C6E0FF"));
                l1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                l6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                selected_date=sat_date.getText().toString().trim();
                display_date.setText(selected_date);
            }
        });
        tt.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                int h=tt.getHour();
                int m=tt.getMinute();
                String am_pm;
                    if (h < 12)
                        am_pm = "am";
                    else {
                        am_pm = "pm";
                        h = h - 12;
                    }
                    start_time[0] = String.valueOf(h) + ":" + String.valueOf(m) + " " + am_pm;
                    display_date.setText(selected_date);
                    display_month.setText(selected_month);
                    display_time.setText(start_time[0]);
            }
        });
        title_aptment=findViewById(R.id.new_appointment_title_edt);
         note_aptment=findViewById(R.id.new_appointment_note_edt);
         Button appt_save = findViewById(R.id.new_live_save);
         appt_save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String apt_title=title_aptment.getEditableText().toString();
                 String apt_note=note_aptment.getEditableText().toString();
                 if(apt_title.equals("")||(apt_title.equals(" "))||
                         (apt_note.equals(""))|| (apt_note.equals(" "))){
                     Toast.makeText(getApplicationContext(),"Enter all details",Toast.LENGTH_SHORT).show();
                 }
                 else
                 {
                     queue = Volley.newRequestQueue(getApplicationContext());
                     Log.d("weeksetter","before");
                     StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
                         System.out.println(response);
                         if (!response.equals("failure")){
                             Log.d("weeksetter","success");
                             Log.d("response weeksetter",response);
                             Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(getApplicationContext(),LiveList.class));
                         }
                         else {
                             Log.d("weeksetter","failure");
                             Toast.makeText(getApplicationContext(), "Couldn't save the appointment", Toast.LENGTH_SHORT).show();
                         }
                     },error -> {
                         Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();})
                     {
                         @NonNull
                         @Override
                         protected Map<String, String> getParams() throws AuthFailureError {
                             Map<String, String> data = new HashMap<>();
                             Calendar c = Calendar.getInstance();
                             int year = c.get(Calendar.YEAR);
                             int month = c.get(Calendar.MONTH)+1;
                             String hour = String.valueOf(tt.getHour());
                             String minute =String.valueOf(tt.getMinute());
                             if (tt.getHour()==0){
                                 hour = "12";
                             }
                             if(tt.getHour()<=9 && tt.getHour()>0){
                                 hour = "0"+hour;
                             }
                             if(tt.getMinute()<=9){
                                 minute = "0"+minute;
                             }
                            String dateandtime = year+"-"+month+"-"+selected_date+" "+hour+":"+minute+":00";
                            String duration = String.valueOf(specific_d);
                            String title =  title_aptment.getText().toString();
                            String note = note_aptment.getText().toString();
                             data.put("dietitianuserID", "Rahul");
                             data.put("dateandtime",dateandtime);
                             data.put("title",title);
                             data.put("note",note);
                             data.put("status","upcoming");
                             return data;
                         }
                     };
                     RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                     requestQueue.add(stringRequest);
                 }
             }
         });
         cancel_live.setOnClickListener(v->{
             startActivity(new Intent(getApplicationContext(),LiveList.class));
         });
    }
    private void setUpUI(){
        sun_date = findViewById(R.id.sun_date);
        mon_date = findViewById(R.id.mon_date);
        tue_date = findViewById(R.id.tue_date);
        wed_date = findViewById(R.id.wed_date);
        thur_date = findViewById(R.id.thur_date);
        fri_date = findViewById(R.id.fri_date);
        sat_date = findViewById(R.id.sat_date);
    }
}