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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link snacks#newInstance} factory method to
 * create an instance of this fragment.
 */
public class snacks extends Fragment {

    RecyclerView re;
    List<List_Food> food_list =new ArrayList<>();

    String url = "http://192.168.244.1/foodCategory.php";

    String urlSnacks = String.format("%srecipiesDisplay.php",DataFromDatabase.ipConfig);

    RequestQueue queue;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public snacks() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment snacks.
     */
    // TODO: Rename and change types and number of parameters
    public static snacks newInstance(String param1, String param2) {
        snacks fragment = new snacks();
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
        View view= inflater.inflate(R.layout.fragment_breakfast, container, false);
        re = view.findViewById(R.id.breakfast_list);
        re.setAdapter(null);
//        List_Food obj = new List_Food("BreadTruffle","20 min",DataFromDatabase.profile,"1 serving");
//        List_Food obj2 = new List_Food("BreadTruffle","20 min",DataFromDatabase.profile,"1 serving");
//        food_list.add(obj);
//        food_list.add(obj2);
//        BreakfastAdapter da = new BreakfastAdapter(food_list,getContext(), Color.parseColor("#FFEBEB"));
//        re.setAdapter(da);
//        re.setLayoutManager(new LinearLayoutManager(getContext()));

        queue = Volley.newRequestQueue(getContext());
        Log.d("snacks","before");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
            if (!response.equals("failure")){
                Log.d("snacks","success");
                Log.d("response",response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i< jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        List_Food obj = new List_Food(object.getString("name"), object.getString("time"), DataFromDatabase.profile, object.getString("serving"));
                        food_list.add(obj);
                    }
                    BreakfastAdapter da = new BreakfastAdapter(food_list,getContext(), Color.parseColor("#FFEBEB"));
                    re.setAdapter(da);
                    re.setLayoutManager(new LinearLayoutManager(getContext()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getContext(), "snacks success", Toast.LENGTH_SHORT).show();
            }
            else if (response.equals("failure")){
                food_list.clear();
                BreakfastAdapter da = new BreakfastAdapter(food_list,getContext(), Color.parseColor("#FFEBEB"));
                re.setAdapter(da);
                re.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        },error -> {
            try {
                Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
            }
            catch(NullPointerException nullPointerException) {
                System.out.println("Error");
            }
            })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("category", "snacks");
                return data;
            }
        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//        requestQueue.add(stringRequest);
        Log.d("snacks","at end");

        StringRequest stringRequestSnacks = new StringRequest(Request.Method.POST,urlSnacks, response -> {
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
                        if (category.toLowerCase().equals("snacks")){
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
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequestSnacks);


        return view;
    }
}