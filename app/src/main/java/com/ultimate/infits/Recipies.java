package com.ultimate.infits;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.ultimate.infits.adapter.FoodAdapter;
import com.ultimate.infits.model.FoodItems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Recipies extends AppCompatActivity {
    RecyclerView recyclerView;
    FoodAdapter adapter;
    ArrayList<FoodItems> foodItems=new ArrayList<>();
    RelativeLayout bottombar;
    ImageView mylunch,mybreakfast,mydinner,mysnacks;

    String url ="http://192.168.1.5/infits/recipiesDisplay.php";
    FoodItems foodItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);

        ids();
        onclick();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));

    }

    private void onclick() {
        mybreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveData("Breakfast");
            }
        });

        mylunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveData("Lunch");
            }
        });

        mysnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveData("Snacks");
            }
        });

        mydinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveData("Dinner");
            }
        });

        bottombar.setOnClickListener(v ->{
            Intent in =new Intent(Recipies.this,FoodDetails2.class);
            startActivity(in);
        });

    }

    private void ids() {
        recyclerView = findViewById(R.id.food_items_recycler);
        bottombar=findViewById(R.id.bottombar);
        mybreakfast =findViewById(R.id.imgBreak);
        mylunch =findViewById(R.id.lunch);
        mydinner =findViewById(R.id.dinner);
        mysnacks =findViewById(R.id.Imgsnacks);
    }

    private void retriveData(String selectedCourse) {
        StringRequest req =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                foodItems.clear();
                System.out.println(response);
                try {
                    JSONArray item =new JSONArray(response);
                    for (int i=0;i<item.length();i++){

                        JSONObject jsonObj = item.getJSONObject(i);
                        String course =jsonObj.getString("course");
                        String name =jsonObj.getString("name");
                        String category =jsonObj.getString("category");
                        String preparationTime =jsonObj.getString("preparationTime");
                        String cookingTime =jsonObj.getString("cookingTime");
                        String servings =jsonObj.getString("servings");
                        String protein =jsonObj.getString("protein");
                        String fats =jsonObj.getString("fats");
                        String calories =jsonObj.getString("calories");
                        String carbs =jsonObj.getString("carbs");
                        String fibres =jsonObj.getString("fibres");
//                        String ingredientname =jsonObj.getString("ingredientname");
//                        String ingredientquantity =jsonObj.getString("ingredientquantity");
//                        String directions =jsonObj.getString("directions");
                        String steps =jsonObj.getString("steps");
                        String image =jsonObj.getString("image");
                        String ingrstr = jsonObj.getString("ingredientname").replace("[","");
                        ingrstr = ingrstr.replace("[","");
                        ingrstr = ingrstr.replace("]","");
                        ingrstr = ingrstr.replace("]","");

                        String[] ingredientname= new String[] {ingrstr};
                        String[] ingredientquantity= new String[] {jsonObj.getString("ingredientquantity")};
                        String[] directions= new String[] {jsonObj.getString("directions")};
                        ingrstr = ingrstr.replace("[","");
                        ingrstr = ingrstr.replace("]","");
                        ingrstr = ingrstr.replace("]","");
//
//                        Toast.makeText(Recipies.this, str, Toast.LENGTH_SHORT).show();

                        byte[] qrimage = Base64.decode(image,0);
                        Bitmap myImage = BitmapFactory.decodeByteArray(qrimage,0,qrimage.length);


                        if(course.equals(selectedCourse)){
                            FoodItems FI =new FoodItems(name,"",calories,myImage,steps,
                                    fats,protein,carbs,course,category,preparationTime,cookingTime,
                                    servings,fibres,ingredientname,ingredientquantity,directions,qrimage);
                            foodItems.add(FI);
                        }
                    }
                    adapter =new FoodAdapter(Recipies.this,foodItems);
                    recyclerView.setAdapter(adapter);
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Recipies.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map =new HashMap<>();
                map.put("userId",DataFromDatabase.dietitianuserID);
                return map;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(req);
    }

}