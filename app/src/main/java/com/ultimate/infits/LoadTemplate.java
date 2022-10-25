package com.ultimate.infits;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LoadTemplate extends AppCompatActivity {

    String url = String.format("%sload_template.php",DataFromDatabase.ipConfig);

    ArrayList<String> nameList = new ArrayList<>();

    ArrayList<String> jsonList = new ArrayList<>();

    LoadTemplateInterface loadTemplateInterface;

    RecyclerView templateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_template);

        templateList = findViewById(R.id.template_list);

        loadTemplateInterface = new LoadTemplateInterface() {
            @Override
            public void loadTemplateInterface(int json) {
                Intent intent = new Intent(getApplicationContext(),Diet_plan_main_screen.class);
                String jsonElement = jsonList.get(json);
                intent.putExtra("json",jsonElement);
                setResult(RESULT_OK,intent);
                finish();
                System.out.println("Hi2");
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,response->{
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("temp");
                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject temp = jsonArray.getJSONObject(i);
                    String template = temp.getString("template_name");
                    String json = temp.getString("diet_chart");
                    nameList.add(template);
                    jsonList.add(json);
                }
                templateList.setAdapter(new LoadTemplateAdapter(nameList,getApplicationContext(),loadTemplateInterface));
                templateList.setLayoutManager(new LinearLayoutManager(this));
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        },error -> {

        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>  data = new HashMap<>();
                return data;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }
}