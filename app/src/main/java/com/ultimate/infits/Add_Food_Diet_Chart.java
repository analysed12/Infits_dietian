package com.ultimate.infits;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Add_Food_Diet_Chart extends AppCompatActivity {

    String position,meal;

    private List<ModelForFood> listForSouthIndianFood, listForNorthIndianFood, listForVegFood;
    LinearLayout north_indian, south_indian, veg, non_veg, snacks;

    AddFoodToList addFoodToList;

    AddButtonListenerChart addButtonListenerChart;

    public static FoodDetailsListener foodDetailsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_diet_chart);

        Intent intent = getIntent();
        position = intent.getStringExtra("position");

        meal = intent.getStringExtra("meal");

        Toast.makeText(getApplicationContext(), position, Toast.LENGTH_SHORT).show();

        north_indian = findViewById(R.id.north_indian);
        south_indian = findViewById(R.id.south_indian);
        veg = findViewById(R.id.veg);

        RecyclerView food_items = findViewById(R.id.food_items);

        listForSouthIndianFood = new ArrayList<>();
        listForNorthIndianFood = new ArrayList<>();

        String urlSouth = String.format("%ssouth_indian_food_diet_chart.php",DataFromDatabase.ipConfig);

        addFoodToList = new AddFoodToList() {
            @Override
            public void addFoodInList(String name, Bitmap photo, String calories) {

            }
        };

        foodDetailsListener = new FoodDetailsListener() {
            @Override
            public void setDetails(int position) {
//                final Dialog dialog = new Dialog(Add_Food_Diet_Chart.this);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setCancelable(true);
//                dialog.setContentView(R.layout.food_details_dialog);
//                TextView name = dialog.findViewById(R.id.food_name);
//                TextView nutrients = dialog.findViewById(R.id.nutrients);
//                dialog.show();
            }

            @Override
            public void setDetails(String name, String calorie, String description, String nutrients, String ingredients, Drawable photo, String category, String preparation) {
                FoodDetailsDialog foodDetailsDialog = new FoodDetailsDialog(name, calorie, description, nutrients, ingredients, photo, category, preparation);
                foodDetailsDialog.show(getSupportFragmentManager(), "FoodDetails");
            }
        };

        StringRequest southIndian = new StringRequest(Request.Method.POST, urlSouth, response -> {
            try {
                System.out.println(response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("food");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.getString("name");
                    String calorie = object.getString("calorie");
                    String description = object.getString("description");
                    String nutrients = object.getString("nutrients");
                    String ingredients = object.getString("ingredients");
                    String photoStr = object.getString("photo");
                    String category = object.getString("category");
                    String preparation = object.getString("preparation");
                    System.out.println(calorie);
                    byte[] qrimage = Base64.decode(photoStr, 0);
                    Bitmap photo = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
                    Drawable drawable = new BitmapDrawable(getResources(), photo);
                    listForSouthIndianFood.add(new ModelForFood(name, calorie, description, nutrients, ingredients, drawable, category, preparation));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            Log.d("Error", error.toString());
        });

        Volley.newRequestQueue(getApplicationContext()).add(southIndian);

        String urlNorth = String.format("%snorth_indian_food_diet_chart.php",DataFromDatabase.ipConfig);

        StringRequest northIndian = new StringRequest(Request.Method.POST, urlNorth, response -> {
            try {
                System.out.println(response);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("food");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String name = object.getString("name");
                    String calorie = object.getString("calorie");
                    String description = object.getString("description");
                    String nutrients = object.getString("nutrients");
                    String ingredients = object.getString("ingredients");
                    String photoStr = object.getString("photo");
                    String category = object.getString("category");
                    String preparation = object.getString("preparation");
                    System.out.println(calorie);
                    byte[] qrimage = Base64.decode(photoStr, 0);
                    Bitmap photo = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
                    Drawable drawable = new BitmapDrawable(getResources(), photo);
                    listForNorthIndianFood.add(new ModelForFood(name, calorie, description, nutrients, ingredients, drawable, category, preparation));
                    food_items.setAdapter(new FoodAdapterForChart(listForNorthIndianFood, addButtonListenerChart, foodDetailsListener,meal));
                    food_items.setLayoutManager(new GridLayoutManager(this, 2));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            Log.d("Error", error.toString());
        });

        Volley.newRequestQueue(getApplicationContext()).add(northIndian);

        addButtonListenerChart = new AddButtonListenerChart() {
            @Override
            public void addButtonOnClick(String name, String calorie, String description, String nutrients, String ingredients, Drawable photo, String category, String preparation,String meal) {
                //add_breakfast.setAdapter(new AddBreakfast(photo,getApplicationContext(),addButtonListenerChart));
                //add_breakfast.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,true));
//                startActivity(new Intent(getApplicationContext(),Add_Food_Diet_Chart.class));

                Dialog dialog = new Dialog(Add_Food_Diet_Chart.this);

                dialog.setContentView(R.layout.fragment_add_food_item_overlay);
                Button add = dialog.findViewById(R.id.food_item_done);
                EditText quantity = dialog.findViewById(R.id.enter_food_quantity);
                EditText unit = dialog.findViewById(R.id.enter_food_quantity_unit);

                add.setOnClickListener(v -> {
                    if (quantity.getText().toString().equals("") && unit.getText().toString().equals("")) {
                        Toast.makeText(Add_Food_Diet_Chart.this, "Please enter the details", Toast.LENGTH_SHORT).show();
                    } else {
                        Bitmap bitmap = ((BitmapDrawable) photo).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] bitmapdata = stream.toByteArray();
                        Intent intent = new Intent(getApplicationContext(), Diet_plan_main_screen.class);
                        intent.putExtra("position", position);
                        intent.putExtra("name", name);
                        intent.putExtra("calorie", calorie);
                        intent.putExtra("description", description);
                        intent.putExtra("nutrients", nutrients);
                        intent.putExtra("ingredients", ingredients);
                        intent.putExtra("preparation", preparation);
                        intent.putExtra("photo", bitmapdata);
                        intent.putExtra("quantity", quantity.getText().toString() + " " + unit.getText().toString());
                        intent.putExtra("meal",meal);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                dialog.show();
            }

        @Override
        public void addButtonOnClick (String position,String meal){

        }
    };

        south_indian.setOnClickListener(v->

    {
        south_indian.setBackground(getDrawable(R.drawable.black_outline));
        north_indian.setBackground(null);
        food_items.setAdapter(new FoodAdapterForChart(listForSouthIndianFood, addButtonListenerChart, foodDetailsListener,meal));
        food_items.setLayoutManager(new GridLayoutManager(this, 2));
    });
        north_indian.setOnClickListener(v->{
        south_indian.setBackground(null);
        north_indian.setBackground(getDrawable(R.drawable.black_outline));
        food_items.setAdapter(new FoodAdapterForChart(listForNorthIndianFood, addButtonListenerChart, foodDetailsListener,meal));
        food_items.setLayoutManager(new GridLayoutManager(this, 2));
    });
}
}