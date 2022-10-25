package com.ultimate.infits;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class breakfast extends Fragment {

    RecyclerView re;
    List<List_Food> food_list =new ArrayList<>();

    String url = String.format("%srecipiesDisplay.php",DataFromDatabase.ipConfig);
    RequestQueue queue;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public breakfast() {

    }

    public static breakfast newInstance(String param1, String param2) {
        breakfast fragment = new breakfast();
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
        View view= inflater.inflate(R.layout.fragment_breakfast, container, false);
        re = view.findViewById(R.id.breakfast_list);
        re.setAdapter(null);

        queue = Volley.newRequestQueue(getContext());
        Log.d("breakfast","before");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
            if (!response.equals("failure")){
                Log.d("breakfast","success");
                Log.d("response",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i< jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        String name = object.getString("name");
                        String time = object.getString("time");
                        String serving = object.getString("serving");
                        String link = object.getString("link");
                        String calories = object.getString("calories");
                        String proteins = object.getString("proteins");
                        String fats = object.getString("fats");
                        String carbs = object.getString("carbs");
                        String fibres = object.getString("fibres");
                        String ingredient = object.getString("ingredient");
                        String category = object.getString("category");
                        String image = object.getString("image");
                        byte[] image64 = Base64.decode(image,0);
                        String instruction = object.getString("instruction");
                        Bitmap photo = BitmapFactory.decodeByteArray(image64,0,image64.length);
                        if (category.toLowerCase().equals("breakfast")){
                            List_Food obj = new List_Food(name, time, photo, serving,link,calories,proteins,fats,carbs,fibres,ingredient,category,image64,instruction);
                            food_list.add(obj);
                        }
                    }
                    BreakfastAdapter da = new BreakfastAdapter(food_list,getContext(), Color.parseColor("#F6E7FA"));
                    re.setAdapter(da);
                    re.setLayoutManager(new LinearLayoutManager(getContext()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Toast.makeText(getContext(), "breakfast success", Toast.LENGTH_SHORT).show();
                }catch (NullPointerException exception){
                    System.out.println("nullex");
                }
            }
            else if (response.equals("failure")){
                food_list.clear();
                BreakfastAdapter da = new BreakfastAdapter(food_list,getContext(), Color.parseColor("#F6E7FA"));
                re.setAdapter(da);
                re.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        },error -> {
            Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("category", "breakfast");
                data.put("userId",DataFromDatabase.dietitianuserID);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        Log.d("breakfast","at end");
        BreakfastAdapter da = new BreakfastAdapter(food_list,getContext(), Color.parseColor("#F6E7FA"));
        re.setAdapter(da);
        re.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
}