package com.ultimate.infits;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientMetrics#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientMetrics extends Fragment {

    String urlm = String.format("%smetrics.php",DataFromDatabase.ipConfig);
    TextView stepstv,glassestv,glassesGoaltv,sleeptv,sleepGoaltv,weighttv,weightGoaltv,calorietv,
            calorieGoaltv,bpmtv,bpmUptv,bpmDowntv;
    String date_to_display_trackers;
    DataFromDatabase dataFromDatabase;
    RequestQueue queue;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ClientMetrics() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Metrics.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientMetrics newInstance(String param1, String param2) {
        ClientMetrics fragment = new ClientMetrics();
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
        View view = inflater.inflate(R.layout.fragment_client_metrics, container, false);
        stepstv = view.findViewById(R.id.steps);
        glassestv = view.findViewById(R.id.glasses);
        glassesGoaltv  = view.findViewById(R.id.glassesGoal);
        sleeptv = view.findViewById(R.id.sleep);
        sleepGoaltv = view.findViewById(R.id.Goalsleep);
        weighttv = view.findViewById(R.id.weight);
        weightGoaltv = view.findViewById(R.id.GoalWeight);
        calorietv = view.findViewById(R.id.calorie);
        calorieGoaltv = view.findViewById(R.id.calorieGoal);
        bpmtv = view.findViewById(R.id.bpm);
        bpmUptv = view.findViewById(R.id.bpmUp);
        bpmDowntv = view.findViewById(R.id.bpmDown);

        RadioButton today= view.findViewById(R.id.active_btn);
        RadioButton yesterday= view.findViewById(R.id.yesterday_btn);
        RadioButton date_picker= view.findViewById(R.id.date_picker_btn);
        Date dateobj=new Date();
        date_to_display_trackers=new SimpleDateFormat("yyyy-MM-dd").format(dateobj);

        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_to_display_trackers=new SimpleDateFormat("yyyy-MM-dd").format(dateobj);
                Toast.makeText(getContext(),"Selected date= "+date_to_display_trackers,Toast.LENGTH_SHORT).show();
                vollyFunc(date_to_display_trackers);
            }
        });
        yesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                cal.add(Calendar.DATE, -1);
                date_to_display_trackers= dateFormat.format(cal.getTime());
                Toast.makeText(getContext(),"Selected date= "+date_to_display_trackers,Toast.LENGTH_SHORT).show();
                vollyFunc(date_to_display_trackers);
            }
        });
        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_calendar);
                CalendarView calendarView = dialog.findViewById(R.id.calendarView);
                dialog.show();

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                                String curDay = String.valueOf(dayOfMonth);
                                String curMonth = String.valueOf(month+1);
                                String curYear = String.valueOf(year);
                                date_to_display_trackers = curYear+"-"+curMonth+"-"+curDay;
                                dialog.cancel();
                        Toast.makeText(getContext(),"Selected date= "+date_to_display_trackers,Toast.LENGTH_SHORT).show();
                        vollyFunc(date_to_display_trackers);
                            }
                        });
            }
        });

        vollyFunc(date_to_display_trackers);
        return view;
    }
     public void vollyFunc(String datex){
         queue = Volley.newRequestQueue(getContext());
         Log.d("ClientMetrics","before");
         StringRequest stringRequest = new StringRequest(Request.Method.POST,urlm, response -> {
             if (!response.equals("failure")){
                 Log.d("ClientMetrics","success");
                 Log.d("response",response);

                 try {
                     JSONArray jsonArray = new JSONArray(response);
                     JSONObject object = jsonArray.getJSONObject(0);
                     String stepsStr = object.getString("steps");
                     String stepsGoal = object.getString("stepsgoal");
                     String waterStr = object.getString("water");
                     String waterGoal = object.getString("watergoal");
                     String sleephrsStr = object.getString("sleephrs");
                     String sleepminsStr = object.getString("sleepmins");
                     String sleepGoal = object.getString("sleepgoal");
                     String weightStr = object.getString("weight");
                     String weightGoal = object.getString("weightgoal");

                     stepstv.setText(stepsStr+" steps");
                     glassestv.setText(waterStr+" glasses");
                     glassesGoaltv.setText(waterGoal+" glasses");
                     sleeptv.setText(sleephrsStr+" hours"+sleepminsStr+" mins");
                     sleepGoaltv.setText(sleepGoal+" hours");
                     weighttv.setText(weightStr+" KiloGrams");
                     weightGoaltv.setText(weightGoal+" KG");
                     if (stepsStr=="null"){
                         stepstv.setText("no data available");
                     }if (waterStr=="null"){
                         glassestv.setText("no data available");
                     }if (waterGoal=="null"){
                         glassesGoaltv.setText("no data available");
                     }if (sleephrsStr=="null"){
                         sleeptv.setText("no data available");
                     }if (sleepGoal=="null"){
                         sleepGoaltv.setText("no data available");
                     }if (weightStr=="null"){
                         weighttv.setText("no data available");
                     }if (weightGoal=="null"){
                         weightGoaltv.setText("no data available");
                     }
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             }
             else if (response.equals("failure")){
                 Log.d("clientMetrics","failure");
                 Toast.makeText(getContext(), "ClientMetrics failed", Toast.LENGTH_SHORT).show();
             }
         },error -> {

         try {
             Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
         }catch (NullPointerException exception){
             System.out.println("Null");
         }
         })
         {
             @Nullable
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String, String> data = new HashMap<>();
                 Log.d("ClientMetrics","clientuserID = "+ClientDetails.clientID);
                 Log.d("Client metrics","date= "+date_to_display_trackers);
                 data.put("userID", ClientDetails.clientID);
                 data.put("date",datex);
                 return data;
             }
         };
         RequestQueue requestQueue = Volley.newRequestQueue(getContext());
         requestQueue.add(stringRequest);
         Log.d("ClientMetrics","at end");
     }
}