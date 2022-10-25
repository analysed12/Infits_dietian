package com.ultimate.infits;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class NewTemplate extends AppCompatActivity {
    JSONObject diet_plan = new JSONObject();

    String urlSaveTemplate = String.format("%stemplate.php", DataFromDatabase.ipConfig);

    //data models for every recycler

    public static List<ModelForFood> breakfastList;
    public static List<ModelForFood> breakfastListExpand;

    public static List<ModelForFood> lunchList;
    public static List<ModelForFood> lunchListExpand;

    public static List<ModelForFood> snackList;
    public static List<ModelForFood> snackListExpand;

    public static List<ModelForFood> dinnerList;
    public static List<ModelForFood> dinnerListExpand;

    RadioButton breakfast, lunch, dinner, snack;
    CardView breakfast_card, dinner_card, lunch_card, snack_card;
    LinearLayout item_overview_breakfast, item_overview_lunch, item_overview_snack, item_overview_dinner;

    RecyclerView breakfast_list, lunch_list, snack_list, dinner_list, add_breakfast, add_lunch, add_dinner, add_snack;

    AddButtonListenerChart addButtonListenerChart;

    public static FoodDetailsListener foodDetailsListener;

    Button save;

    ActivityResultLauncher<Intent> loadTemplate = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );

    ActivityResultLauncher<Intent> addFood = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String position = data.getStringExtra("position");
                        String name = data.getStringExtra("name");
                        String calorie = data.getStringExtra("calorie");
                        String nutrients = data.getStringExtra("nutrients");
                        String description = data.getStringExtra("description");
                        String ingredients = data.getStringExtra("ingredients");
                        String preparation = data.getStringExtra("preparation");
                        String quantity = data.getStringExtra("quantity");
                        String meal = data.getStringExtra("meal");
                        byte[] photoArr = data.getByteArrayExtra("photo");
                        Bitmap bitmap = BitmapFactory.decodeByteArray(photoArr, 0, photoArr.length);
                        Drawable photo = new BitmapDrawable(getResources(), bitmap);
                        if (meal.equals("breakfast")) {
                            breakfastList.get(Integer.parseInt(position)).setName(name);
                            breakfastList.get(Integer.parseInt(position)).setCalorie(calorie);
                            breakfastList.get(Integer.parseInt(position)).setPhoto(photo);
                            try {
                                breakfastListExpand.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                            } catch (Exception ex) {
                                Toast.makeText(NewTemplate.this, "Hi", Toast.LENGTH_SHORT).show();
                                breakfastListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                            }
                            if (breakfastListExpand.size() >= 4) {
                                breakfastList.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                            }
                            add_breakfast.setAdapter(new AddBreakfast(breakfastList, getApplicationContext(), addButtonListenerChart));
                            add_breakfast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                            breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpand, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                            breakfast_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        }
                        if (meal.equals("lunch")) {
                            Toast.makeText(NewTemplate.this, "This for Morning", Toast.LENGTH_SHORT).show();
                            lunchList.get(Integer.parseInt(position)).setName(name);
                            lunchList.get(Integer.parseInt(position)).setCalorie(calorie);
                            lunchList.get(Integer.parseInt(position)).setPhoto(photo);
                            try {
                                lunchListExpand.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                            } catch (Exception ex) {
                                Toast.makeText(NewTemplate.this, "Hi", Toast.LENGTH_SHORT).show();
                                lunchListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                            }
                            if (lunchListExpand.size() >= 4) {
                                lunchList.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                            }
                            add_lunch.setAdapter(new AddLunch(lunchList, getApplicationContext(), addButtonListenerChart));
                            add_lunch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                            lunch_list.setAdapter(new AddLunchList(lunchListExpand, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                            lunch_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        }
                        if (meal.equals("snack")) {
                            Toast.makeText(NewTemplate.this, "This for Morning", Toast.LENGTH_SHORT).show();
                            snackList.get(Integer.parseInt(position)).setName(name);
                            snackList.get(Integer.parseInt(position)).setCalorie(calorie);
                            snackList.get(Integer.parseInt(position)).setPhoto(photo);
                            try {
                                snackListExpand.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                            } catch (Exception ex) {
                                Toast.makeText(NewTemplate.this, "Hi", Toast.LENGTH_SHORT).show();
                                snackListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                            }
                            if (snackListExpand.size() >= 4) {
                                snackList.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                            }
                            add_snack.setAdapter(new AddSnack(snackList, getApplicationContext(), addButtonListenerChart));
                            add_snack.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                            snack_list.setAdapter(new AddSnackList(snackListExpand, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                            snack_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        }
                        if (meal.equals("dinner")) {
                            Toast.makeText(NewTemplate.this, "This for Morning", Toast.LENGTH_SHORT).show();
                            dinnerList.get(Integer.parseInt(position)).setName(name);
                            dinnerList.get(Integer.parseInt(position)).setCalorie(calorie);
                            dinnerList.get(Integer.parseInt(position)).setPhoto(photo);
                            try {
                                dinnerListExpand.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                            } catch (Exception ex) {
                                Toast.makeText(NewTemplate.this, "Hi", Toast.LENGTH_SHORT).show();
                                dinnerListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                            }
                            if (dinnerListExpand.size() >= 4) {
                                dinnerList.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                            }
                            add_dinner.setAdapter(new AddDinner(dinnerList, getApplicationContext(), addButtonListenerChart));
                            add_dinner.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                            dinner_list.setAdapter(new AddDinnerList(dinnerListExpand, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                            dinner_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        }
                    }
                }
            });

    @SuppressLint("UseSupportActionBar")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_template);

        breakfastList = new ArrayList<>();
        breakfastListExpand = new ArrayList<>();

        lunchList = new ArrayList<>();
        lunchListExpand = new ArrayList<>();

        snackList = new ArrayList<>();
        snackListExpand = new ArrayList<>();

        dinnerList = new ArrayList<>();
        dinnerListExpand = new ArrayList<>();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.add_food);
        Drawable photo = getApplicationContext().getResources().getDrawable(R.drawable.add_food);

        for (int i = 0; i < 4; i++) {

            breakfastList.add(new ModelForFood("", "", photo));

            lunchList.add(new ModelForFood("", "", photo));

            snackList.add(new ModelForFood("", "", photo));

            dinnerList.add(new ModelForFood("", "", photo));
        }

        breakfast_card = findViewById(R.id.breakfast_card);
        lunch_card = findViewById(R.id.lunch_card);
        dinner_card = findViewById(R.id.dinner_card);
        snack_card = findViewById(R.id.snack_card);
        breakfast = findViewById(R.id.breakfast);
        lunch = findViewById(R.id.lunch);
        dinner = findViewById(R.id.dinner);
        snack = findViewById(R.id.snacks);

        save = findViewById(R.id.save_diet_cart);

        breakfast_list = findViewById(R.id.breakfast_list);
        lunch_list = findViewById(R.id.lunch_list);
        snack_list = findViewById(R.id.snack_list);
        dinner_list = findViewById(R.id.dinner_list);

        add_breakfast = findViewById(R.id.add_breakfast);
        add_lunch = findViewById(R.id.add_lunch);
        add_dinner = findViewById(R.id.add_dinner);
        add_snack = findViewById(R.id.add_snack);

        Bitmap add = BitmapFactory.decodeResource(getResources(), R.drawable.add_food);

        addButtonListenerChart = new AddButtonListenerChart() {
            @Override
            public void addButtonOnClick(String name, String calorie, String description, String nutrients, String ingredients, Drawable photo, String category, String preparation, String meal) {

            }

            @Override
            public void addButtonOnClick(String position, String meal) {
                Intent intent = new Intent(getApplicationContext(), Add_Food_Diet_Chart.class);
                intent.putExtra("position", position);
                intent.putExtra("meal", meal);
                addFood.launch(intent);
            }
        };

        foodDetailsListener = new FoodDetailsListener() {
            @Override
            public void setDetails(int position) {

            }

            @Override
            public void setDetails(String name, String calorie, String description, String nutrients, String ingredients, Drawable photo, String category, String preparation) {
                FoodDetailsDialog foodDetailsDialog = new FoodDetailsDialog(name, calorie, description, nutrients, ingredients, photo, category, preparation);
                foodDetailsDialog.show(getSupportFragmentManager(), "FoodDetails");
            }
        };

        item_overview_breakfast = findViewById(R.id.item_overview_breakfast);
        item_overview_lunch = findViewById(R.id.item_overview_lunch);
        item_overview_snack = findViewById(R.id.item_overview_snacks);
        item_overview_dinner = findViewById(R.id.item_overview_dinner);

        setItemOverview(add_breakfast, add_lunch, add_snack, add_dinner, breakfastList, lunchList, snackList, dinnerList);

        breakfast.setOnClickListener(v -> {

            breakfast.setChecked(true);
            lunch.setChecked(false);
            dinner.setChecked(false);
            snack.setChecked(false);

            item_overview_breakfast.setVisibility(View.GONE);
            item_overview_lunch.setVisibility(View.VISIBLE);
            item_overview_snack.setVisibility(View.VISIBLE);
            item_overview_dinner.setVisibility(View.VISIBLE);

            breakfast_list.setVisibility(View.VISIBLE);
            lunch_list.setVisibility(View.GONE);
            snack_list.setVisibility(View.GONE);
            dinner_list.setVisibility(View.GONE);
        });

        lunch.setOnClickListener(v -> {
            breakfast.setChecked(false);
            lunch.setChecked(true);
            dinner.setChecked(false);
            snack.setChecked(false);
            item_overview_breakfast.setVisibility(View.VISIBLE);
            item_overview_lunch.setVisibility(View.GONE);
            item_overview_snack.setVisibility(View.VISIBLE);
            item_overview_dinner.setVisibility(View.VISIBLE);

            breakfast_list.setVisibility(View.GONE);
            lunch_list.setVisibility(View.VISIBLE);
            snack_list.setVisibility(View.GONE);
            dinner_list.setVisibility(View.GONE);
        });

        dinner.setOnClickListener(v -> {
            breakfast.setChecked(false);
            lunch.setChecked(false);
            dinner.setChecked(true);
            snack.setChecked(false);
            item_overview_breakfast.setVisibility(View.VISIBLE);
            item_overview_lunch.setVisibility(View.VISIBLE);
            item_overview_snack.setVisibility(View.VISIBLE);
            item_overview_dinner.setVisibility(View.GONE);

            breakfast_list.setVisibility(View.GONE);
            lunch_list.setVisibility(View.GONE);
            snack_list.setVisibility(View.GONE);
            dinner_list.setVisibility(View.VISIBLE);
        });
        snack.setOnClickListener(v -> {
            breakfast.setChecked(false);
            lunch.setChecked(false);
            dinner.setChecked(false);
            snack.setChecked(true);
            item_overview_breakfast.setVisibility(View.GONE);
            item_overview_lunch.setVisibility(View.GONE);
            item_overview_snack.setVisibility(View.VISIBLE);
            item_overview_dinner.setVisibility(View.GONE);

            breakfast_list.setVisibility(View.VISIBLE);
            lunch_list.setVisibility(View.GONE);
            snack_list.setVisibility(View.VISIBLE);
            dinner_list.setVisibility(View.GONE);
        });

        save.setOnClickListener(v -> {
                Dialog tempDialog = new Dialog(NewTemplate.this);
                tempDialog.setContentView(R.layout.template_question);

                Button saveAsTemplate = tempDialog.findViewById(R.id.yes);
                Button noAsTemplate = tempDialog.findViewById(R.id.no);

                saveAsTemplate.setOnClickListener(viewAsTemplate -> {

                    Dialog tempSave = new Dialog(NewTemplate.this);
                    tempSave.setContentView(R.layout.template);

                    Button saveTemplate = tempSave.findViewById(R.id.done);
                    EditText templateName = tempSave.findViewById(R.id.template_name);

                    saveTemplate.setOnClickListener(viewSaveTemplate -> {
                        sendJson();
                        saveTemplate(templateName.getText().toString(), diet_plan.toString());
                        tempDialog.dismiss();
                    });
                    tempSave.show();
                    tempDialog.dismiss();
                });

                noAsTemplate.setOnClickListener(viewAsTemplate -> {
                    sendJson();
                    tempDialog.dismiss();
                });

                tempDialog.show();
        });
    }

    private void setBreakfast(RecyclerView add_breakfast, List<ModelForFood> breakfastList) {
        add_breakfast.setAdapter(new AddBreakfast(breakfastList, getApplicationContext(), addButtonListenerChart));
        add_breakfast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setLunch(RecyclerView add_lunch, List<ModelForFood> lunchList) {
        add_lunch.setAdapter(new AddLunch(lunchList, getApplicationContext(), addButtonListenerChart));
        add_lunch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setSnack(RecyclerView add_snack, List<ModelForFood> snackList) {
        add_snack.setAdapter(new AddSnack(snackList, getApplicationContext(), addButtonListenerChart));
        add_snack.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setDinner(RecyclerView add_dinner, List<ModelForFood> dinnerList) {
        add_dinner.setAdapter(new AddDinner(dinnerList, getApplicationContext(), addButtonListenerChart));
        add_dinner.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    void saveTemplate(String json, String templateName) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSaveTemplate, response -> {
            System.out.println(response);
            if (response.equals("updated")) {
                Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Use different name", Toast.LENGTH_SHORT).show();
            }
        }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("dietitianuserID", "Rahul");
                data.put("clientID", "Azarudeen");
                data.put("json", json);
                data.put("template_name", templateName);
                return data;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    void sendJson() {
            diet_plan = new JSONObject();
            addFoodToJSON(breakfastListExpand, "breakfast");
            addFoodToJSON(lunchListExpand, "lunch");
            addFoodToJSON(snackListExpand, "snack");
            addFoodToJSON(dinnerListExpand, "dinner");
//            sendToDB("sunday", diet_plan.toString());
    }

    private void setItemOverview(RecyclerView add_breakfast, RecyclerView add_lunch, RecyclerView add_snack, RecyclerView add_dinner, List<ModelForFood> breakfastList, List<ModelForFood> lunchList, List<ModelForFood> snackList, List<ModelForFood> dinnerList) {
        setBreakfast(add_breakfast, breakfastList);
        setLunch(add_lunch, lunchList);
        setSnack(add_snack, snackList);
        setDinner(add_dinner, dinnerList);
    }

    private void setItemList(RecyclerView breakfast_list, RecyclerView lunch_list, RecyclerView snack_list, RecyclerView dinner_list, List<ModelForFood> breakfastListExpand, List<ModelForFood> lunchListExpand, List<ModelForFood> snackListExpand, List<ModelForFood> dinnerListExpand) {

        breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpand, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
        breakfast_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        lunch_list.setAdapter(new AddLunch(lunchListExpand, getApplicationContext(), addButtonListenerChart));
        lunch_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        snack_list.setAdapter(new AddSnack(snackListExpand, getApplicationContext(), addButtonListenerChart));
        snack_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        snack_list.setAdapter(new AddBreakfast(dinnerListExpand, getApplicationContext(), addButtonListenerChart));
        snack_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    void addFoodToJSON(List<ModelForFood> breakfastListExpand, String meal) {
        try {
            JSONArray breakfast = new JSONArray();
            for (int i = 0; i < breakfastListExpand.size(); i++) {
                JSONObject food = new JSONObject();
                food.put("name", breakfastListExpand.get(i).getName());
                food.put("calorie", breakfastListExpand.get(i).getCalorie());
                food.put("description", breakfastListExpand.get(i).getDescription());
                food.put("nutrients", breakfastListExpand.get(i).getNutrients());
                food.put("ingredients", breakfastListExpand.get(i).getIngredients());
                food.put("quantity", breakfastListExpand.get(i).getQuantity());
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Bitmap dir = ((BitmapDrawable) breakfastListExpand.get(i).getPhoto()).getBitmap();
                dir.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                String base64String = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                food.put("photo", base64String);
                food.put("preparation", breakfastListExpand.get(i).getPreparation());
                breakfast.put(food);
                System.out.println(breakfastListExpand.get(i).getName() + " ");
                diet_plan.put(meal, breakfast);
            }
            System.out.println(diet_plan.toString());
        } catch (JSONException jsonException) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    void afterAddingFood(RecyclerView add_breakfast, RecyclerView breakfast_list, List<ModelForFood> breakfastList, List<ModelForFood> breakfastListExpand, String position, String name, String calorie, String description, String nutrients, String ingredients, Drawable photo, String preparation, String quantity) {
        breakfastList.get(Integer.parseInt(position)).setName(name);
        breakfastList.get(Integer.parseInt(position)).setCalorie(calorie);
        breakfastList.get(Integer.parseInt(position)).setPhoto(photo);
        try {
            breakfastListExpand.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
        } catch (Exception ex) {
            Toast.makeText(NewTemplate.this, "Hi", Toast.LENGTH_SHORT).show();
            breakfastListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
        }
        if (breakfastListExpand.size() >= 4) {
            breakfastList.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
        }
        add_breakfast.setAdapter(new AddBreakfast(breakfastList, getApplicationContext(), addButtonListenerChart));
        add_breakfast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpand, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
        breakfast_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}