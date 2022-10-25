package com.ultimate.infits;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Calender#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Calender extends Fragment {

    DataFromDatabase dataFromDatabase;
    RecyclerView event_list;
    String date_to_display_trackers;
    RequestQueue queue;

    String url = String.format("%sCalenderAppointment.php",DataFromDatabase.ipConfig);
    ImageView addevent;
    List<EventList> obj= new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Calender() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Calender.
     */
    // TODO: Rename and change types and number of parameters
    public static Calender newInstance(String param1, String param2) {
        Calender fragment = new Calender();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calender, container, false);
        event_list = view.findViewById(R.id.event_list);


        addevent=view.findViewById(R.id.add_app);

        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),WeekSetter.class);
                i.putExtra("dietitian_name",dataFromDatabase.dietitianuserID);
                startActivity(i);
            }
        });

        Date dateobj=new Date();
        date_to_display_trackers=new SimpleDateFormat("yyyy-MM-dd").format(dateobj);
        Button search_appointments= view.findViewById(R.id.appointment_done);
        CalendarView calendarView = view.findViewById(R.id.appointment_calendar);

        queue = Volley.newRequestQueue(getContext());
        Log.d("Calender","before");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
            if (!response.equals("failure")){
                Log.d("Calender","success");
                Log.d("response Calender",response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    obj.clear();
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
//                            String type = object.getString("type");
                            String title = object.getString("Title");
                            String client = object.getString("clientID");
                            String datetime = object.getString("dateAndTime");
                            String duration = object.getString("duration");
                            String location = object.getString("consultation_location");
                            String note = object.getString("Note");
                            String notifyMe = object.getString("NotifyMe");
                            Log.d("time", "a" + datetime.substring(11, 16));
                            Log.d("duration",duration);
                            EventList a = new EventList(title, client, datetime.substring(11, 16) +"(" + "duration:" + duration + ")", "(" + "duration:" + duration + ")", location, note, title, datetime.substring(5, 7), datetime.substring(8, 10), "9034*****", notifyMe);
                            obj.add(a);
                        }
                        EventListAdapter ea = new EventListAdapter(getContext(), obj);
                        event_list.setAdapter(ea);
                        event_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    }
                    else
                        Toast.makeText(getContext(),"No appointments of this date where found",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else if (response.equals("failure")){
                Log.d("Calender","failure");
                Toast.makeText(getContext(), "Calender failed", Toast.LENGTH_SHORT).show();
            }
        },error -> {
            Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();})
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                Log.d("Calender","dieticianuserID = "+ DataFromDatabase.dietitianuserID);
                Log.d("Calender","date= "+date_to_display_trackers);
                data.put("userID", DataFromDatabase.dietitianuserID);
                data.put("date",date_to_display_trackers);

                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        Log.d("Calender","at end");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String curDay = String.valueOf(dayOfMonth);
                String curMonth = String.valueOf(month+1);
                String curYear = String.valueOf(year);
                date_to_display_trackers = curYear+"-"+curMonth+"-"+curDay;
                Toast.makeText(getContext(),"Selected date= "+date_to_display_trackers,Toast.LENGTH_SHORT).show();
            }
        });
        search_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Searching appointments on "+date_to_display_trackers,Toast.LENGTH_SHORT).show();
                obj.removeAll(obj);
                obj.clear();
                event_list.setAdapter(null);
              //  vollyFunc(date_to_display_trackers);
                queue = Volley.newRequestQueue(getContext());
                Log.d("Calender","before");
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
                    if (!response.equals("failure")){
                        Log.d("Calender","success");
                        Log.d("response Calender",response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            obj.clear();
                            if(jsonArray.length()>0){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String title = object.getString("Title");
                                String client = object.getString("clientID");
                                String datetime = object.getString("dateAndTime");
                                String duration = object.getString("duration");
                                String location = object.getString("consultation_location");
                                String note = object.getString("Note");
                                String notifyMe = object.getString("NotifyMe");
                                Log.d("time", "a" + datetime.substring(11, 16));
                                EventList a = new EventList(title, client, datetime.substring(11, 16)+" (" + "duration: " + duration +" minutes"+ ")",  duration, location, note, title, datetime.substring(5, 7), datetime.substring(8, 10), "9034*****", notifyMe);
                                obj.add(a);
                                System.out.println(client+" "+datetime+" "+duration+" "+note);
                            }
                            EventListAdapter ea = new EventListAdapter(getContext(), obj);
                            event_list.setAdapter(ea);
                            event_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                        }
                        else
                            Toast.makeText(getContext(),"No appointments of this date where found",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else if (response.equals("failure")){
                        Log.d("Calender","failure");
                        Toast.makeText(getContext(), "Calender failed", Toast.LENGTH_SHORT).show();
                    }
                },error -> {
                    Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();})
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        Log.d("Calender","dieticianuserID = "+ DataFromDatabase.dietitianuserID);
                        Log.d("Calender","date= "+date_to_display_trackers);
                        data.put("userID", DataFromDatabase.dietitianuserID);
                        data.put("date",date_to_display_trackers);

                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
                Log.d("Calender","at end");
            }
        });


        //vollyFunc(date_to_display_trackers);

        return view;
    }

   /* public void vollyFunc(String datex) {
        queue = Volley.newRequestQueue(getContext());
        Log.d("Calender","before");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
            if (!response.equals("failure")){
                Log.d("Calender","success");
                Log.d("response Calender",response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    obj.clear();
                    for(int i=0;i< jsonArray.length();i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String title = object.getString("Title");
                        String client = object.getString("clientID");
                        String datetime = object.getString("dateAndTime");
                        String duration = object.getString("duration");
                        String location = object.getString("consultation_location");
                        String note = object.getString("Note");
                        String notifyMe = object.getString("NotifyMe");
                        Log.d("time","a"+datetime.substring(11,16));
                        EventList a=new EventList("Video Consultation",client,datetime.substring(11,16),"("+"duration:"+duration+")",location,note,title,datetime.substring(5,7),datetime.substring(8,10),"9034*****",notifyMe);
                        obj.add(a);
                    }
                    EventListAdapter ea = new EventListAdapter(getContext(),obj);
                    event_list.setAdapter(ea);
                    event_list.setLayoutManager(new LinearLayoutManager(getContext()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else if (response.equals("failure")){
                Log.d("Calender","failure");
                Toast.makeText(getContext(), "Calender failed", Toast.LENGTH_SHORT).show();
            }
        },error -> {
            Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();})
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                Log.d("Calender","dieticianuserID = "+dataFromDatabase.dietitianuserID);
                Log.d("Calender","date= "+date_to_display_trackers);
                data.put("userID", dataFromDatabase.dietitianuserID);
                data.put("date",datex);

                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        Log.d("Calender","at end");
    }*/
}