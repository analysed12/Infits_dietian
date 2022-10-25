package com.ultimate.infits;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
 * Use the {@link lunch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class lunch extends Fragment {
    RecyclerView re;
    List<List_Food> food_list =new ArrayList<>();

    String url = "http://192.168.244.1/foodCategory.php";
    RequestQueue queue;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public lunch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment lunch.
     */
    // TODO: Rename and change types and number of parameters
    public static lunch newInstance(String param1, String param2) {
        lunch fragment = new lunch();
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
        View view= inflater.inflate(R.layout.fragment_lunch, container, false);
        re = view.findViewById(R.id.breakfast_list);
        re.setAdapter(null);
//        List_Food obj = new List_Food("BreadTruffle","20 min",DataFromDatabase.profile,"1 serving");
//        List_Food obj2 = new List_Food("BreadTruffle","20 min",DataFromDatabase.profile,"1 serving");
//        food_list.add(obj);
//        food_list.add(obj2);
//        BreakfastAdapter da = new BreakfastAdapter(food_list,getContext(), Color.parseColor("#DDF4ED"));
//        re.setAdapter(da);
//        re.setLayoutManager(new LinearLayoutManager(getContext()));

        queue = Volley.newRequestQueue(getContext());
        Log.d("lunch","before");
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
            if (!response.equals("failure")){
                Log.d("lunch","success");
                Log.d("response",response);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i< jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        List_Food obj = new List_Food(object.getString("name"), object.getString("time"), DataFromDatabase.profile, object.getString("serving"));
                        food_list.add(obj);
                    }
                    BreakfastAdapter da = new BreakfastAdapter(food_list,getContext(), Color.parseColor("#DDF4ED"));
                    re.setAdapter(da);
                    re.setLayoutManager(new LinearLayoutManager(getContext()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getContext(), "lunch success", Toast.LENGTH_SHORT).show();
            }
            else if (response.equals("failure")){
                food_list.clear();
                BreakfastAdapter da = new BreakfastAdapter(food_list,getContext(), Color.parseColor("#DDF4ED"));
                re.setAdapter(da);
                re.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        },error -> {

        try {
            Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
        }catch (NullPointerException exception){
            System.out.println("error");
        }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("category", "lunch");
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        Log.d("lunch","at end");

        return view;
    }
}