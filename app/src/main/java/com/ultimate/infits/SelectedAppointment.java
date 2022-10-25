package com.ultimate.infits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SelectedAppointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);
        ImageView calback= findViewById(R.id.calendar_back);
        calback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedAppointment.super.onBackPressed();
            }
        });

        Log.e("One","One");

//        getSupportFragmentManager().beginTransaction().replace(R.id.selected_client_appt,new selectedAppointment_details()).commit();

        Log.e("Two","Two");

        TextView sel_client_appt_month=findViewById(R.id.selected_client_appt_month);
        TextView sel_client_appt_date=findViewById(R.id.selected_client_appt_date);
        TextView sel_client_appt_type=findViewById(R.id.selected_client_appt_type);
        TextView duration = findViewById(R.id.duration);
        TextView meet_link = findViewById(R.id.meet_link);
        TextView description = findViewById(R.id.description);
        TextView clientName = findViewById(R.id.client);

//        Button d=(Button) findViewById(R.id.active_btn);
//        Button n=(Button) findViewById(R.id.pending_btn);

        Intent intent = getIntent();

        String type = intent.getStringExtra("appointment_type");

//        sel_client_appt_type.setText(intent.getStringExtra("appointment_type"));
        sel_client_appt_type.setText(type);
//        sel_client_appt_type.setText(getIntent().getExtras().get("appointment_type").toString());
        sel_client_appt_date.setText(getIntent().getExtras().get("appointment_date").toString());
        meet_link.setText(intent.getStringExtra("appointment_location"));
        duration.setText(intent.getStringExtra("appointment_time"));
        description.setText(intent.getStringExtra("appointment_note"));
        clientName.setText(intent.getStringExtra("clientID"));
        int month= Integer.parseInt(intent.getStringExtra("appointment_month"));
        String month1="JAN";
        switch (month)
        {
            case 1:month1="JAN";
                break;
            case 2:month1="FEB";
                break;
            case 3:month1="MAR";
                break;
            case 4: month1="APR";
                break;
            case 5:month1="MAY";
                break;
            case 6:month1="JUN";
                break;
            case 7:month1="JUL";
                break;
            case 8:month1="AUG";
                break;
            case 9:month1="SEP";
                break;
            case 10:month1="OCT";
                break;
            case 11:month1="NOV";
                break;
            case 12:month1="DEC";
                break;
        }
        sel_client_appt_month.setText(month1);
//        d.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle b=new Bundle();
//                b.putString("appointment_time",getIntent().getExtras().getString("appointment_time"));
//                b.putString("appointment_location",getIntent().getExtras().getString("appointment_location"));
//                b.putString("appointment_title",getIntent().getExtras().getString("appointment_title"));
//                b.putString("appointment _link",getIntent().getExtras().getString("appointment_time"));
//                b.putString("appointment_client_name",getIntent().getExtras().getString("clientID"));
//                b.putString("appointment_duration",getIntent().getExtras().getString("appointment_duration"));
//                selectedAppointment_details frag= new selectedAppointment_details();
//                frag.setArguments(b);
                //b.putString("appointment_client_photo",getIntent().getExtras().getString("appointment_photo"));
//                Navigation.findNavController(v).navigate(R.id.appointment_details,b);
                //selectedAppointment_details fragobj=new selectedAppointment_details();
                //fragobj.setArguments(b);
                // getSupportFragmentManager().beginTransaction().replace(R.id.selected_client_appt,fragobj).commit();

//            }
//        });
//        n.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle b1=new Bundle();
////                b1.putString("appointment_note",getIntent().getExtras().getString("appointment_note"));
////                Navigation.findNavController(SelectedAppointment.this,R.id.selected_client_appt).navigate(R.id.appointment_notes,b1);
//                // selectedAppointment_notes fragobj1=new selectedAppointment_notes();
//                //fragobj1.setArguments(b1);
//                //getSupportFragmentManager().beginTransaction().replace(R.id.selected_client_appt,fragobj1).commit();
//            }
//        });
        ToggleButton btn=findViewById(R.id.toggleButtonNotify);
//        if(!getIntent().getExtras().getString("appointment_notifyme").isEmpty()){
//            if(getIntent().getExtras().getString("appointment_notifyme").equals("Y"))
//                btn.setChecked(true);
//            else
//                btn.setChecked(false);
//        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Notification alert set", Toast.LENGTH_SHORT).show();

                    //code to push notification
                }
                //write volley to store the updated notifyme
            }
        });
    }
}