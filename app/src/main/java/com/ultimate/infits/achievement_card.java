package com.ultimate.infits;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link achievement_card#newInstance} factory method to
 * create an instance of this fragment.
 */
public class achievement_card extends Fragment {

    String url = "http://192.168.141.91/achievement.php";
    TextView progress1, progress2,progress3,progress4,progress5;
    ProgressBar bar1,bar2,bar3,bar4,bar5;


    int client_count=0;
    DataFromDatabase dataFromDatabase;
    RequestQueue queue;
    int p=0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public achievement_card() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment achievement_card.
     */
    // TODO: Rename and change types and number of parameters
    public static achievement_card newInstance(String param1, String param2) {
        achievement_card fragment = new achievement_card();
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
        View view= inflater.inflate(R.layout.fragment_achievement_card, container, false);
        progress1=view.findViewById(R.id.achievementbartext1);
        progress2=view.findViewById(R.id.achievementbartext2);
        progress3=view.findViewById(R.id.achievementbartext3);
        progress4=view.findViewById(R.id.achievementbartext4);
        progress5=view.findViewById(R.id.achievementbartext5);

        bar1=view.findViewById(R.id.achievementbar1);
        bar2=view.findViewById(R.id.achievementbar2);
        bar3=view.findViewById(R.id.achievementbar3);
        bar4=view.findViewById(R.id.achievementbar4);
        bar5=view.findViewById(R.id.achievementbar5);

        queue = Volley.newRequestQueue(getContext());
        Log.d("Achievement","before");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
            if (!response.equals("failure")){
                Log.d("Achievement","success");
                Log.d("response Achievement",response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    client_count = Integer.parseInt(jsonObject.getString("count"));
                    Log.d("client", String.valueOf(client_count));
                    if(client_count<=10) {
                        p = client_count * 100 / 10;
                        progress1.setText(client_count+"/10");
                        bar1.setProgress(p);
                    }
                    else {
                        progress1.setText("10/10");
                        bar1.setProgress(100);
                    }
                    if(client_count<=25) {
                        p = client_count * 100 / 25;
                        progress2.setText(client_count+"/25");
                        bar2.setProgress(p);
                    }
                    else {
                        progress2.setText("25/25");
                        bar2.setProgress(100);
                    }
                    if(client_count<=50) {
                        p = client_count * 100 / 50;
                        progress3.setText(client_count+"/50");
                        bar3.setProgress(p);
                    }
                    else {
                        progress3.setText("50/50");
                        bar3.setProgress(100);
                    }
                    if(client_count<=75) {
                        p = client_count * 100 / 75;
                        progress4.setText(client_count+"/75");
                        bar4.setProgress(p);
                    }
                    else{
                        progress4.setText("75/75");
                        bar4.setProgress(100);
                    }
                    if(client_count<=100) {
                        p = client_count * 100 / 100;
                        progress5.setText(client_count+"/100");
                        bar5.setProgress(p);
                    }
                    else{
                        progress5.setText("100/100");
                        bar5.setProgress(100);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else if (response.equals("failure")){
                Log.d("Achievement","failure");
                Toast.makeText(getContext(), "Achievement failed", Toast.LENGTH_SHORT).show();
            }
        },error -> {
//            Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("userID", dataFromDatabase.dietitianuserID);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        Log.d("Achievement","at end");
        //write code for volley connection and add the following lines in the try block

        Log.d("client count2", String.valueOf(client_count));

        ImageView i1=view.findViewById(R.id.imageviewachievement);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (client_count==100)
                    startActivity(new Intent(getActivity(),Congratulate.class));
                else
                    Toast.makeText(getContext(),"Locked until you reach 100 clients",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}