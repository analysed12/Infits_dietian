package com.ultimate.infits;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AcceptRejectClients extends AppCompatActivity implements AcceptRejectListAdapter.Selecteditem {


    DataFromDatabase dataFromDatabase;
    RequestQueue queue;
    String url = "http://192.168.9.1/allClients.php";


//    String urldietchart;
//    String urlpremium;
//    String url101;
    String all_clients_img[]={"app/src/main/res/drawable/doctor_blue_border.png"
            ,"app/src/main/res/drawable/doctor_blue_border.png", "app/src/main/res/drawable/doctor_blue_border.png",
            "app/src/main/res/drawable/doctor_blue_border.png"};
//    String all_client_name[]={"Michael Simpson","Michael Simpson","Michael Simpson","Michael Simpson"};
//    String all_clients_plan[]={"diet plan","diet plan","diet plan","diet plan"};
//    String particular_plan_client_name[]={"Michael Simpson","Michael Simpson","Michael Simpson","Michael Simpson"};
//    String particular_plan_clients_img[]={"app/src/main/res/drawable/doctor_blue_border.png"
//            ,"app/src/main/res/drawable/doctor_blue_border.png", "app/src/main/res/drawable/doctor_blue_border.png",
//            "app/src/main/res/drawable/doctor_blue_border.png"};
//    String particular_clients_plan[]={"diet plan","diet plan","diet plan","diet plan"};
    String dialog_name;
    AcceptRejectClients particular_plan_adapter;
    int adapter_flag=0;
    //List<AcceptRejectList> plan_diet= new ArrayList<>();
    //List<AcceptRejectList> plan_premium= new ArrayList<>();
   // List<AcceptRejectList> plan_1to1= new ArrayList<>();
    List<AcceptRejectList> all_plans=new ArrayList<>();
    List<AcceptRejectList> particular_plan= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_reject_clients);
        ImageView back=findViewById(R.id.accept_reject_back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        RecyclerView recyclerView2=findViewById(R.id.recycler_view_for_all_clients);
        RecyclerView recyclerView1=findViewById(R.id.recycler_view_for_plans);
        Button diet_btn=findViewById(R.id.plan_diet);
        Button premium_btn=findViewById(R.id.plan_premium);
        Button one_to_one_btn= findViewById(R.id.plan_1to1);

        queue = Volley.newRequestQueue(getApplicationContext());
        Log.d("allClients","before");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
            if (!response.equals("failure")){
                Log.d("allClients","success");
                Log.d("response",response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray.length()<1)
                        Toast.makeText(getApplicationContext(),"No client is in the pending list",Toast.LENGTH_SHORT).show();
                    else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String clientid = object.getString("clientID");
                            String plan = object.getString("plan");
                            String dieticianID = object.getString("dietitianID");
                            if (dieticianID == "null") {
                                AcceptRejectList obj = new AcceptRejectList(all_clients_img[i], clientid, plan);
                                Log.d("plan_x", plan.toString());
                                all_plans.add(obj);
                            }
                        }
                        Log.d("all_plans", String.valueOf(all_plans));
                        AcceptRejectListAdapter adapx = new AcceptRejectListAdapter(getApplicationContext(), all_plans, (AcceptRejectListAdapter.Selecteditem) this);
                        recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        recyclerView2.setAdapter(adapx);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "allClients success", Toast.LENGTH_SHORT).show();
            }
            else if (response.equals("failure")){
                Log.d("allClients","failure");
                Toast.makeText(getApplicationContext(), "allClients failed", Toast.LENGTH_SHORT).show();
            }
        },error -> {
            Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();});

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
        Log.d("allClients","at end");
        EmptyLayoutAdapter adempty= new EmptyLayoutAdapter(getApplicationContext());
        recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView1.setAdapter(adempty);

        diet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diet_btn.setBackgroundColor(Color.parseColor("#1D8BF1"));
                diet_btn.setTextColor(Color.parseColor("#FFFFFF"));
                premium_btn.setBackgroundResource(R.drawable.plan_button_background);
                premium_btn.setTextColor(Color.parseColor("#1D8BF1"));
                one_to_one_btn.setBackgroundResource(R.drawable.plan_button_background);
                one_to_one_btn.setTextColor(Color.parseColor("#1D8BF1"));
                //fetch data from database and store in particular_plan_x arrays
                Log.d("diet_plan","before");
                particular_plan= null;
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
                    if (!response.equals("failure")){
                        Log.d("diet_plan","success");
                        Log.d("response",response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if(jsonArray.length()<1)
                                adapter_flag=0;
                            else {
                                adapter_flag=1;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String clientid = object.getString("clientID");
                                    String plan = object.getString("plan");
                                    String dieticianID = object.getString("dietitianID");
                                    if (dieticianID == "null" && plan.equals("diet chart")) {
                                        {
                                            AcceptRejectList obj1 = new AcceptRejectList(all_clients_img[i], clientid, plan);
                                            particular_plan.add(obj1);
                                        }
                                    }
                                }
                                Log.d("plan_diet", String.valueOf(particular_plan));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Diet Plan success", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.equals("failure")){
                        Log.d("diet_plan","failure");
                        Toast.makeText(getApplicationContext(), "Diet Plan failed", Toast.LENGTH_SHORT).show();
                    }
                },error -> {
                    Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();});

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
                Log.d("diet_plan","at end");

            }
        });
        premium_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                premium_btn.setBackgroundColor(Color.parseColor("#1D8BF1"));
                premium_btn.setTextColor(Color.parseColor("#FFFFFF"));
                diet_btn.setBackgroundResource(R.drawable.plan_button_background);
                diet_btn.setTextColor(Color.parseColor("#1D8BF1"));
                one_to_one_btn.setBackgroundResource(R.drawable.plan_button_background);
                one_to_one_btn.setTextColor(Color.parseColor("#1D8BF1"));
                //fetch data from database and store in particular_plan_x arrays
                Log.d("premium_plan","before");
                particular_plan= null;
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
                    if (!response.equals("failure")){
                        Log.d("premium","success");
                        Log.d("response",response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if(jsonArray.length()<1)
                                adapter_flag=0;
                            else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String clientid = object.getString("clientID");
                                    String plan = object.getString("plan");
                                    String dieticianID = object.getString("dietitianID");
                                    if (dieticianID == "null" && plan.equals("premium")) {
                                        {
                                            AcceptRejectList obj1 = new AcceptRejectList(all_clients_img[i], clientid, plan);
                                            particular_plan.add(obj1);
                                        }
                                    }
                                }
                                Log.d("premium", String.valueOf(particular_plan));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "premium success", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.equals("failure")){
                        Log.d("Premium","failure");
                        Toast.makeText(getApplicationContext(), "Premium failed", Toast.LENGTH_SHORT).show();
                    }
                },error -> {
                    Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();});

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
                Log.d("premium","at end");


            }
        });
        one_to_one_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one_to_one_btn.setBackgroundColor(Color.parseColor("#1D8BF1"));
                one_to_one_btn.setTextColor(Color.parseColor("#FFFFFF"));
                premium_btn.setBackgroundResource(R.drawable.plan_button_background);
                premium_btn.setTextColor(Color.parseColor("#1D8BF1"));
                diet_btn.setBackgroundResource(R.drawable.plan_button_background);
                diet_btn.setTextColor(Color.parseColor("#1D8BF1"));
                //fetch data from database and store in particular_plan_x arrays

                Log.d("1 to 1","before");
                particular_plan= null;
                StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
                    if (!response.equals("failure")){
                        Log.d("1 to 1","success");
                        Log.d("response",response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if(jsonArray.length()<1)
                                adapter_flag=0;
                            else {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String clientid = object.getString("clientID");
                                    String plan = object.getString("plan");
                                    String dieticianID = object.getString("dietitianID");
                                    if (dieticianID == "null" && plan.equals("1to1")) {
                                        {
                                            AcceptRejectList obj1 = new AcceptRejectList(all_clients_img[i], clientid, plan);
                                            particular_plan.add(obj1);
                                        }
                                    }
                                }
                                Log.d("1 to 1", String.valueOf(particular_plan));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "1 to 1 success", Toast.LENGTH_SHORT).show();
                    }
                    else if (response.equals("failure")){
                        Log.d("1 to 1","failure");
                        Toast.makeText(getApplicationContext(), "1 to 1 failed", Toast.LENGTH_SHORT).show();
                    }
                },error -> {
                    Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();});

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
                Log.d("1 to 1","at end");


            }
        });
        if(particular_plan.size()>0) {
            AcceptRejectListAdapter adap = new AcceptRejectListAdapter(getApplicationContext(), particular_plan, (AcceptRejectListAdapter.Selecteditem) this);
            recyclerView1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView1.setAdapter(adap);
        }
        else if(adapter_flag==0)
            Toast.makeText(getApplicationContext(),"No pending clients in the selected plan type",Toast.LENGTH_SHORT).show();

    }
    @Override
    public void selecteditem(AcceptRejectList list_n) {
        dialog_name = list_n.getClient_name();
        Toast.makeText(getApplicationContext(), list_n.getClient_name(), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder ad = new AlertDialog.Builder(AcceptRejectClients.this);
        ad.setTitle("Info!");
        ad.setMessage("Do you want to accept " + dialog_name + " as your client?");
        ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override

            public void onClick(DialogInterface dialog, int arg1) {
                //write code to update subscribed client and refresh the page to reload set of clients by removing the last accepted client


                Toast.makeText(getApplicationContext(), "Refreshing client list", Toast.LENGTH_SHORT).show();
                onBackPressed();
                Intent i = new Intent(getApplicationContext(), AcceptRejectClients.class);
                //i.putExtra("dietitian_id",dietitian_id);
                startActivity(i);
            }

        });
        ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = ad.create();
        dialog.show();
    }

}