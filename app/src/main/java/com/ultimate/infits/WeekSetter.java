package com.ultimate.infits;

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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeekSetter extends AppCompatActivity{
    TextView sun_date,mon_date,tue_date,wed_date,thur_date,fri_date,sat_date;
    LinearLayout l1,l2,l3,l4,l5,l6,l7;
    String selected_date;
    String selected_month;
    int specific_d;
    RequestQueue queue;
    DataFromDatabase dataFromDatabase;
    String url = String.format("%sweeksetter.php",DataFromDatabase.ipConfig);
   // String client_names[]={"Select one from drop down","Michael Simpson","Michael Simpson","Michael Simpson","Michael Simpson"};
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_setter);
        setUpUI();
        TextView[] dateArr = {sun_date,mon_date,tue_date,wed_date,thur_date,fri_date,sat_date};
        final String[] start_time = new String[1];
        final String[] end_time = new String[1];

        TextView display_month,display_time,display_date;
        String display_month1 = null,display_time1,display_date1;
        TimePicker tt;
        ImageView iv1;
        EditText title_aptment,loc_aptment,note_aptment;
        display_date=findViewById(R.id.new_appointment_date);
        display_month=findViewById(R.id.new_appointment_month);
        display_time=findViewById(R.id.new_appointment_time);
        tt=findViewById(R.id.timePicker1);

        ArrayList<String> days = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd ");
        SimpleDateFormat sdf1= new SimpleDateFormat("MMM");
        SimpleDateFormat sdf3=new SimpleDateFormat("hh:mm a");
        Calendar cal = Calendar.getInstance();
        start_time[0]=sdf3.format(cal.getTime());
        display_time.setText(start_time[0]+"("+specific_d+" minutes)");
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
                WeekSetter.super.onBackPressed();
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

      Button dd1=findViewById(R.id.duration_10m);
        Button dd2=findViewById(R.id.duration_20m);
        Button dd3=findViewById(R.id.duration_30m);
        Button dd4=findViewById(R.id.duration_40m);
        Button dd5=findViewById(R.id.duration_50m);
        Button dd6=findViewById(R.id.duration_60m);
        Button dd7=findViewById(R.id.duration_1h10m);
        Button dd8=findViewById(R.id.duration_1h20m);
        Button dd9=findViewById(R.id.duration_1h30m);
        dd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dd1.setBackgroundColor(Color.parseColor("#EFF8FF"));
                dd2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                specific_d=10;
                display_time.setText(start_time[0]+"( "+specific_d+"minutes )");
            }
        });
        dd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dd2.setBackgroundColor(Color.parseColor("#EFF8FF"));
                dd1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                specific_d=20;
                display_time.setText(start_time[0]+"( "+specific_d+"minutes )");
            }
        });
        dd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dd3.setBackgroundColor(Color.parseColor("#EFF8FF"));
                dd2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                specific_d=30;
                display_time.setText(start_time[0]+"( "+specific_d+"minutes )");
            }
        });
        dd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dd4.setBackgroundColor(Color.parseColor("#EFF8FF"));
                dd2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                specific_d=40;
                display_time.setText(start_time[0]+"( "+specific_d+"minutes )");
            }
        });
        dd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dd5.setBackgroundColor(Color.parseColor("#EFF8FF"));
                dd2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                specific_d=50;
                display_time.setText(start_time[0]+"( "+specific_d+"minutes )");
            }
        });
        dd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dd6.setBackgroundColor(Color.parseColor("#EFF8FF"));
                dd2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                specific_d=60;
                display_time.setText(start_time[0]+"( "+specific_d+"minutes )");
            }
        });
        dd7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dd7.setBackgroundColor(Color.parseColor("#EFF8FF"));
                dd2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                specific_d=80;
                display_time.setText(start_time[0]+"( "+specific_d+"minutes )");
            }
        });
        dd8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dd8.setBackgroundColor(Color.parseColor("#EFF8FF"));
                dd2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                specific_d=100;
                display_time.setText(start_time[0]+"( "+specific_d+"minutes )");
            }
        });
        dd9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dd9.setBackgroundColor(Color.parseColor("#EFF8FF"));
                dd2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                dd1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                specific_d=120;
                display_time.setText(start_time[0]+"( "+specific_d+"minutes )");
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
                    display_time.setText(start_time[0]+"( "+specific_d+"minutes )");

              /* DateFormat obj= new SimpleDateFormat("hh:mm");
                Date date = null;
                try {
                    date = obj.parse(start_time[0]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal= Calendar.getInstance();
                cal.setTime(date);
                if(specific_d==60)
                    cal.add(Calendar.HOUR,1);
                else
                    cal.add(Calendar.MINUTE,specific_d);
                end_time[0]= String.valueOf(cal.getTime());
                String date12=start_time[0];
                SimpleDateFormat f2 = new SimpleDateFormat("HH:MM a");
                try {
                    Date date1 = f2.parse(end_time[0]);
                    date12 = obj.format(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                display_time.setText(start_time[0]+"-"+date12);*/
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.all_clients_under_me_spinner);
        ArrayList<String> client_names = new ArrayList<>();
        client_names.add("No clientID selected");
        client_names.addAll(DataFromDatabase.clientsID);
        Log.d("weeksetter clientsID", String.valueOf(client_names)+"    "+String.valueOf(DataFromDatabase.clientsID));
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item,client_names );
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinner.setAdapter(spinnerArrayAdapter);


        title_aptment=findViewById(R.id.new_appointment_title_edt);
         loc_aptment=findViewById(R.id.new_appointment_location_edt);
         note_aptment=findViewById(R.id.new_appointment_note_edt);
         Button appt_save=(Button) findViewById(R.id.new_appointment_save);
         appt_save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String apt_title=title_aptment.getEditableText().toString();
                 String apt_location=loc_aptment.getEditableText().toString();
                 String apt_note=note_aptment.getEditableText().toString();
                 if(apt_title.equals("")||(apt_title.equals(" "))||
                         (apt_note.equals(""))|| (apt_note.equals(" "))||
                         (apt_location.equals(""))||(apt_location.equals(" "))){
                     Toast.makeText(getApplicationContext(),"Enter all details",Toast.LENGTH_SHORT).show();
                 }
                 else if(spinner.getSelectedItemPosition()<0)
                     Toast.makeText(getApplicationContext(),"Select clients name",Toast.LENGTH_SHORT).show();
                 else
                 {
                     Toast.makeText(getApplicationContext(),selected_date+" "+specific_d+" "+
                             start_time[0]+" "+title_aptment.getText().toString()
                             +" "+loc_aptment.getText().toString()+" "+note_aptment.getText().toString(),Toast.LENGTH_SHORT).show();
                     Log.d("weeksetter",selected_date+" "+specific_d+" "+
                             tt.getHour()+" "+tt.getMinute()+" "+title_aptment.getText().toString()
                             +" "+loc_aptment.getText().toString()+" "+note_aptment.getText().toString());

                     queue = Volley.newRequestQueue(getApplicationContext());
                     Log.d("weeksetter","before");
                     StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
                         if (!response.equals("failure")){
                             Log.d("weeksetter","success");
                             Log.d("response weeksetter",response);

                             try {

                             } catch (Exception e) {
                                 e.printStackTrace();
                             }

                         }
                         else if (response.equals("failure")){
                             Log.d("weeksetter","failure");
                             Toast.makeText(getApplicationContext(), "Couldn't save the appointment", Toast.LENGTH_SHORT).show();
                         }
                     },error -> {
                         Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();})
                     {
                         @Nullable
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
                             //2022-05-25 11:35:31
                             //2022-4-31 23:9:00
                             Log.d("weeksetter dateandtime",dateandtime);
                            String status = "pending";
                            String duration = String.valueOf(specific_d);
                            String loc = loc_aptment.getText().toString();
                            String title =  title_aptment.getText().toString();
                            String note = note_aptment.getText().toString();
                            String notifyMe = "Y";
                             data.put("dietitianuserID", DataFromDatabase.dietitianuserID);
                             Log.d("weeksetter clientID",client_names.get(spinner.getSelectedItemPosition()));
                             data.put("clientuserID", client_names.get(spinner.getSelectedItemPosition()));
                             data.put("dateandtime",dateandtime);
                             data.put("status",status);
                             data.put("duration",duration);
                             data.put("location",loc);
                             data.put("title",title);
                             data.put("note",note);
                             data.put("notifyme",notifyMe);
                             return data;
                         }
                     };
                     RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                     requestQueue.add(stringRequest);
                     Log.d("weeksetter","at end");
                 }
             }
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