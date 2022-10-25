package com.ultimate.infits;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment
//        implements UpcomingConsultationAdapter.Selecteditem
{

    TextView name,no_consultations,messages_read;
    DataFromDatabase dataFromDatabase;
    String url = String.format("%supcomingConsultations.php",DataFromDatabase.ipConfig);
    String url2 = String.format("%sdashboard2.php",DataFromDatabase.ipConfig);
    RequestQueue queue;
    RecyclerView recyclerView1, recyclerView2, recyclerview3;
    String consultation_patient[]={"John Wayne","John Wayne","John Wayne","John Wayne"};
    String consultation_patient_image[]={"app/src/main/res/drawable/doctor_blue_border.png"
            ,"app/src/main/res/drawable/doctor_blue_border.png", "app/src/main/res/drawable/doctor_blue_border.png",
            "app/src/main/res/drawable/doctor_blue_border.png","app/src/main/res/drawable/doctor_blue_border.png"
            ,"app/src/main/res/drawable/doctor_blue_border.png", "app/src/main/res/drawable/doctor_blue_border.png",
            "app/src/main/res/drawable/doctor_blue_border.png","app/src/main/res/drawable/doctor_blue_border.png"
    ,"app/src/main/res/drawable/doctor_blue_border.png", "app/src/main/res/drawable/doctor_blue_border.png",
    "app/src/main/res/drawable/doctor_blue_border.png","app/src/main/res/drawable/doctor_blue_border.png"
            ,"app/src/main/res/drawable/doctor_blue_border.png", "app/src/main/res/drawable/doctor_blue_border.png",
            "app/src/main/res/drawable/doctor_blue_border.png"};
    String consultation_type[]={"Video consultation","Video consultation","Audio consultation","Video consultation"};

    List<UpcomingConsultations> obj3= new ArrayList<>();
    List<DashboardMessages> obj1=new ArrayList<>();
    List<Dashboard_profile_pics> obj2=new ArrayList<>();

//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    private String mParam1;
//    private String mParam2;
//
//    public DashboardFragment() {
//
//    }
//
//    public static DashboardFragment newInstance(String param1, String param2) {
//        DashboardFragment fragment = new DashboardFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View v= inflater.inflate(R.layout.fragment_dashboard,container,false);
//        recyclerView1 = v.findViewById(R.id.upcoming_consultation_recycler);
//
//        name = v.findViewById(R.id.name);
//        no_consultations = v.findViewById(R.id.no_consultations);
//        messages_read = v.findViewById(R.id.messages_read);
//        ImageView dietician = v.findViewById(R.id.dietician_img);
//        dietician.setImageBitmap(DataFromDatabase.profile);
//        ImageView consultaton_next=v.findViewById(R.id.upcoming_consultation_next);
//        ImageView patients_next= v.findViewById(R.id.patients_profile_next);
//        ImageView messages_next= v.findViewById(R.id.messages_next);
//        name.setText(DataFromDatabase.name);
//        consultaton_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.calender2);
//            }
//        });
//        patients_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.clientList4);
//            }
//        });
//        messages_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate(R.id.action_dashboardFragment2_to_allMessages2);
//            }
//        });
//
//        queue = Volley.newRequestQueue(getContext());
//        Log.d("Dashboard","before");
////        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
////            if (!response.equals("failure")){
////                Log.d("dashboard","success");
////                Log.d("response",response);
////                try {
////                    obj3.clear();
////                    recyclerView1.setAdapter(null);
////                    recyclerView1.setLayoutManager(null);
////                    JSONArray jsonArray = new JSONArray(response);
////                    if (jsonArray.length() != 0){
////                        for (int i=0;i<jsonArray.length();i++){
////                            JSONObject object = jsonArray.getJSONObject(i);
////                            String dateandtime = object.getString("dateandtime");
////                            String mobile = object.getString("mobile");
////                            Log.d("mobile",mobile);
////                            String date = dateandtime.substring(0,10);
////                            String time = dateandtime.substring(11,16);
////                            byte[] qrimage = Base64.decode(object.getString("profilePhoto"),0);
////                            Bitmap img = BitmapFactory.decodeByteArray(qrimage,0,qrimage.length);
////
////                            Date dateDate = new SimpleDateFormat("y-MM-dd").parse(date);
////
////
////                            Date time1 = new Date();
////                            Calendar calendar1 = Calendar.getInstance();
////                            calendar1.setTime(time1);
////                            calendar1.add(Calendar.DATE, 1);
////
////
////                            Date todayDate = new SimpleDateFormat("y-MM-dd").parse(new SimpleDateFormat("y-MM-dd").format(time1.getTime()));
////
////                            if(dateDate.compareTo(todayDate) >0){
////                                UpcomingConsultations obj=new UpcomingConsultations(date,time,img,
////                                        object.getString("clientID"),mobile);
////
////                                Log.d("Dashboard UC",date+" "+time+" "+object.getString("clientID"));
////                                obj3.add(obj);
////                            }
////                            UpcomingConsultationAdapter adap= new UpcomingConsultationAdapter(getContext(),obj3, (UpcomingConsultationAdapter.Selecteditem) this);
////                            recyclerView1.setAdapter(adap);
////                            recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
////                            }
////                    }
////                    else{
////                        no_consultations.setVisibility(View.VISIBLE);
////                    }
////                } catch (JSONException | ParseException e) {
////                    e.printStackTrace();
////                }
////                try {
////                    Toast.makeText(getContext(), "Dashboard success", Toast.LENGTH_SHORT).show();
////                }
////                catch (NullPointerException exception){
////                    System.out.println(exception);
////                }
////            }
////            else if (response.equals("failure")){
////                Log.d("Dashboard","failure");
////                Toast.makeText(getContext(), "Dashboard failed", Toast.LENGTH_SHORT).show();
////            }
////        },error -> {
////            Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();})
////        {
////            @Nullable
////            @Override
////            protected Map<String, String> getParams() throws AuthFailureError {
////                Map<String, String> data = new HashMap<>();
////                String userid = DataFromDatabase.dietitianuserID;
////                Log.d("dashboard","dietitianid = "+userid);
////                data.put("userID", userid);
////                return data;
////            }
////        };
////        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
////        requestQueue.add(stringRequest);
////        Log.d("Dashboard","at end");
//
//        recyclerView2=v.findViewById(R.id.enquiries_reports_recycler);
////        for(int i=0;i<consultation_patient.length;i++)
////        {
////            DashboardMessages object=new DashboardMessages(consultation_patient_image[i],consultation_patient[i],consultation_type[i]);
////            obj1.add(object);
////        }
////        DashboardMessagesAdapter dfadap=new DashboardMessagesAdapter(getContext(),obj1);
////        recyclerView2.setAdapter(dfadap);
////        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
////        recyclerView2.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
//
//        recyclerview3= v.findViewById(R.id.add_profile_recycler);
//
//        queue = Volley.newRequestQueue(getContext());
//        Log.d("Dashboard2","before");
//        StringRequest stringRequest2 = new StringRequest(Request.Method.POST,url2, response -> {
//            if (!response.equals("failure")){
//                Log.d("dashboard2","success");
//                Log.d("response2",response);
//                obj2.clear();
//                recyclerview3.setAdapter(null);
//                recyclerview3.setLayoutManager(null);
//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//                    for (int i=0;i<jsonArray.length();i++){
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        String clientID = object.getString("clientID");
//                        byte[] qrimage2 = Base64.decode(object.getString("profilePhoto"),0);
//                        Log.d("profilePhoto",object.getString("profilePhoto"));
//                        Bitmap img2 = BitmapFactory.decodeByteArray(qrimage2,0,qrimage2.length);
//                        Dashboard_profile_pics objectx= new Dashboard_profile_pics(img2);
//                          obj2.add(objectx);
//                    }
//                    Dashboard_profile_adapter padapx= new Dashboard_profile_adapter(getContext(),obj2);
//                    recyclerview3.setAdapter(padapx);
//                    recyclerview3.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    Toast.makeText(getContext(), "Dashboard2 success", Toast.LENGTH_SHORT).show();
//                }catch (NullPointerException nullPointerException){
//                    System.out.println("nullexception");
//                }
//            }
//            else if (response.equals("failure")){
//                Log.d("Dashboard2","failure");
//                Toast.makeText(getContext(), "Dashboard2 failed", Toast.LENGTH_SHORT).show();
//            }
//        },error -> {
//            Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();})
//        {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> data = new HashMap<>();
//                String userid = dataFromDatabase.dietitianuserID;
//                Log.d("dashboard","dietitianid = "+userid);
//                data.put("userID", userid);
//                return data;
//            }
//        };
//        RequestQueue requestQueue2 = Volley.newRequestQueue(getContext());
//        requestQueue2.add(stringRequest2);
//        Log.d("Dashboard2","at end");
//
//        return v;
//    }

//    @Override
//    public void selecteditem(String client_n, String time_n) {
//        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
//        ad.setTitle("Info!");
//        ad.setMessage("Status of the appointment with " + client_n + " at time "+ time_n);
//        ad.setPositiveButton("Completed", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int arg1) {
//                //write code to update the appointment status
//
//                Toast.makeText(getContext(), "Appointment status updated", Toast.LENGTH_SHORT).show();
//            }
//
//        });
//        ad.setNegativeButton("Pending", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int arg1) {
//                dialog.cancel();
//            }
//        });
//        AlertDialog dialog = ad.create();
//        dialog.show();
//    }
}