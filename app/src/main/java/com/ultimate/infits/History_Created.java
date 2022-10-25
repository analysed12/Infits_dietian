package com.ultimate.infits;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class History_Created extends AppCompatActivity {

    String url = String.format("%ssee_all_clients.php",DataFromDatabase.ipConfig);

    ArrayList<String> name;

    ArrayList<String> code;

    RecyclerView nameRv;

    RecyclerView codeRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_created);


        name = new ArrayList<>();

        code = new ArrayList<>();

        codeRv = findViewById(R.id.code);

        nameRv = findViewById(R.id.name);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,response -> {
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i=0;i< jsonArray.length();i++){
                    System.out.println(jsonArray.length());
                    JSONObject object = jsonArray.getJSONObject(i);
                    String clientName = object.getString("clientName");
                    System.out.println(clientName);
                    String gender = object.getString("gender");
                    String age = object.getString("age");
                    String height = object.getString("height");
                    String about = object.getString("about");
                    String title = object.getString("title");
                    String plantitle = object.getString("plantitle");
//                    String image = object.getString("image");
                    String plandescription = object.getString("plandescription");
                    String referalcode = object.getString("referalcode");
                    String description = object.getString("description");
//                    byte[] image64 = Base64.decode(image,0);
//                    String instruction = object.getString("instruction");
//                    Bitmap photo = BitmapFactory.decodeByteArray(image64,0,image64.length);
                    name.add(clientName);
                    code.add(referalcode);
                }
                SeeAllAdapter da = new SeeAllAdapter(name);
                nameRv.setAdapter(da);
                nameRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                SeeAllAdapter daCode = new SeeAllAdapter(code);
                codeRv.setAdapter(daCode);
                codeRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },error -> {

        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

    }
}