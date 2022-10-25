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

public class Diet_plan_main_screen extends AppCompatActivity {

    JSONObject diet_plan = new JSONObject();

    String url = String.format("%supdate_diet_chart.php", DataFromDatabase.ipConfig);

    String urlSaveTemplate = String.format("%stemplate.php", DataFromDatabase.ipConfig);

    RadioGroup days;

    RadioButton sun, mon, tue, wed, thur, fri, sat;

    //data models for every recycler

    public static List<ModelForFood> breakfastList;
    public static List<ModelForFood> breakfastListExpand;

    public static List<ModelForFood> breakfastListMon;
    public static List<ModelForFood> breakfastListExpandMon;

    public static List<ModelForFood> breakfastListTue;
    public static List<ModelForFood> breakfastListExpandTue;

    public static List<ModelForFood> breakfastListWed;
    public static List<ModelForFood> breakfastListExpandWed;

    public static List<ModelForFood> breakfastListThur;
    public static List<ModelForFood> breakfastListExpandThur;

    public static List<ModelForFood> breakfastListFri;
    public static List<ModelForFood> breakfastListExpandFri;

    public static List<ModelForFood> breakfastListSat;
    public static List<ModelForFood> breakfastListExpandSat;

    public static List<ModelForFood> lunchList;
    public static List<ModelForFood> lunchListExpand;

    public static List<ModelForFood> lunchListMon;
    public static List<ModelForFood> lunchListExpandMon;

    public static List<ModelForFood> lunchListTue;
    public static List<ModelForFood> lunchListExpandTue;

    public static List<ModelForFood> lunchListWed;
    public static List<ModelForFood> lunchListExpandWed;

    public static List<ModelForFood> lunchListThur;
    public static List<ModelForFood> lunchListExpandThur;

    public static List<ModelForFood> lunchListFri;
    public static List<ModelForFood> lunchListExpandFri;

    public static List<ModelForFood> lunchListSat;
    public static List<ModelForFood> lunchListExpandSat;

    public static List<ModelForFood> snackList;
    public static List<ModelForFood> snackListExpand;

    public static List<ModelForFood> snackListMon;
    public static List<ModelForFood> snackListExpandMon;

    public static List<ModelForFood> snackListTue;
    public static List<ModelForFood> snackListExpandTue;

    public static List<ModelForFood> snackListWed;
    public static List<ModelForFood> snackListExpandWed;

    public static List<ModelForFood> snackListThur;
    public static List<ModelForFood> snackListExpandThur;

    public static List<ModelForFood> snackListFri;
    public static List<ModelForFood> snackListExpandFri;

    public static List<ModelForFood> snackListSat;
    public static List<ModelForFood> snackListExpandSat;

    public static List<ModelForFood> dinnerList;
    public static List<ModelForFood> dinnerListExpand;

    public static List<ModelForFood> dinnerListMon;
    public static List<ModelForFood> dinnerListExpandMon;

    public static List<ModelForFood> dinnerListTue;
    public static List<ModelForFood> dinnerListExpandTue;

    public static List<ModelForFood> dinnerListWed;
    public static List<ModelForFood> dinnerListExpandWed;

    public static List<ModelForFood> dinnerListThur;
    public static List<ModelForFood> dinnerListExpandThur;

    public static List<ModelForFood> dinnerListFri;
    public static List<ModelForFood> dinnerListExpandFri;

    public static List<ModelForFood> dinnerListSat;
    public static List<ModelForFood> dinnerListExpandSat;

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
                    String response = result.getData().getStringExtra("json");
                    try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("breakfast");
                    System.out.println(jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject food = jsonArray.getJSONObject(i);
                        String name = food.getString("name");
                        String calorie = food.getString("calorie");
                        String description = food.getString("description");
                        String nutrients = food.getString("nutrients");
                        String ingredients = food.getString("ingredients");
                        String photoString = food.getString("photo");
                        byte[] photoByte = Base64.decode(photoString, 0);
                        Drawable photoDraw = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length));
                        String preparation = food.getString("preparation");
                        String quantity = food.getString("quantity");

                        breakfastList.get(i).setName(name);
                        breakfastList.get(i).setCalorie(calorie);
                        breakfastList.get(i).setPhoto(photoDraw);

                        System.out.println(name);
                        try {
                            breakfastListExpand.set(i, new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, "", preparation, quantity));
                        } catch (Exception ex) {
                            breakfastListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, "", preparation, quantity));
                        }
                        if (breakfastListExpand.size() >= 4) {
                            breakfastList.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                        }
                    }
                        add_breakfast.setAdapter(new AddBreakfast(breakfastList, getApplicationContext(), addButtonListenerChart));
                        add_breakfast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                        breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpand, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                        breakfast_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("lunch");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject food = jsonArray.getJSONObject(i);
                        String name = food.getString("name");
                        String calorie = food.getString("calorie");
                        String description = food.getString("description");
                        String nutrients = food.getString("nutrients");
                        String ingredients = food.getString("ingredients");
                        String photoString = food.getString("photo");
                        byte[] photoByte = Base64.decode(photoString, 0);
                        Drawable photoDraw = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length));
                        String preparation = food.getString("preparation");
                        String quantity = food.getString("quantity");

                        lunchList.get(i).setName(name);
                        lunchList.get(i).setCalorie(calorie);
                        lunchList.get(i).setPhoto(photoDraw);
                        try {
                            lunchListExpand.set(i, new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, "", preparation, quantity));
                        } catch (Exception ex) {
                            Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
                            lunchListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, "", preparation, quantity));
                        }
                        if (lunchListExpand.size() >= 4) {
                            lunchList.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                        }
                        add_lunch.setAdapter(new AddLunch(lunchList, getApplicationContext(), addButtonListenerChart));
                        add_lunch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                        lunch_list.setAdapter(new AddLunchList(lunchListExpand, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                        lunch_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("snack");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject food = jsonArray.getJSONObject(i);
                        String name = food.getString("name");
                        String calorie = food.getString("calorie");
                        String description = food.getString("description");
                        String nutrients = food.getString("nutrients");
                        String ingredients = food.getString("ingredients");
                        String photoString = food.getString("photo");
                        byte[] photoByte = Base64.decode(photoString, 0);
                        Drawable photoDraw = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length));
                        String preparation = food.getString("preparation");
                        String quantity = food.getString("quantity");

                        snackList.get(i).setName(name);
                        snackList.get(i).setCalorie(calorie);
                        snackList.get(i).setPhoto(photoDraw);
                        try {
                            snackListExpand.set(i, new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, "", preparation, quantity));
                        } catch (Exception ex) {
                            Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
                            snackListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, "", preparation, quantity));
                        }
                        if (snackListExpand.size() >= 4) {
                            snackList.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                        }
                        add_snack.setAdapter(new AddSnack(snackList, getApplicationContext(), addButtonListenerChart));
                        add_snack.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                        snack_list.setAdapter(new AddSnackList(snackListExpand, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                        snack_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }
            try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("dinner");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject food = jsonArray.getJSONObject(i);
                        String name = food.getString("name");
                        String calorie = food.getString("calorie");
                        String description = food.getString("description");
                        String nutrients = food.getString("nutrients");
                        String ingredients = food.getString("ingredients");
                        String photoString = food.getString("photo");
                        byte[] photoByte = Base64.decode(photoString, 0);
                        Drawable photoDraw = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length));
                        String preparation = food.getString("preparation");
                        String quantity = food.getString("quantity");

                        dinnerList.get(i).setName(name);
                        dinnerList.get(i).setCalorie(calorie);
                        dinnerList.get(i).setPhoto(photoDraw);
                        try {
                            dinnerListExpand.set(i, new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, "", preparation, quantity));
                        } catch (Exception ex) {
                            Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
                            dinnerListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, "", preparation, quantity));
                        }
                        if (dinnerListExpand.size() >= 4) {
                            dinnerList.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                        }
                        add_dinner.setAdapter(new AddDinner(dinnerList, getApplicationContext(), addButtonListenerChart));
                        add_dinner.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                        dinner_list.setAdapter(new AddDinnerList(dinnerListExpand, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                        dinner_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                }

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
                        if (sun.isChecked()) {
                            if (meal.equals("breakfast")) {
                                breakfastList.get(Integer.parseInt(position)).setName(name);
                                breakfastList.get(Integer.parseInt(position)).setCalorie(calorie);
                                breakfastList.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    breakfastListExpand.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
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

                                lunchList.get(Integer.parseInt(position)).setName(name);
                                lunchList.get(Integer.parseInt(position)).setCalorie(calorie);
                                lunchList.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    lunchListExpand.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Diet_plan_main_screen.this, "This for Morning", Toast.LENGTH_SHORT).show();
                                snackList.get(Integer.parseInt(position)).setName(name);
                                snackList.get(Integer.parseInt(position)).setCalorie(calorie);
                                snackList.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    snackListExpand.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Diet_plan_main_screen.this, "This for Morning", Toast.LENGTH_SHORT).show();
                                dinnerList.get(Integer.parseInt(position)).setName(name);
                                dinnerList.get(Integer.parseInt(position)).setCalorie(calorie);
                                dinnerList.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    dinnerListExpand.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
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
                        if (mon.isChecked()) {
                            if (meal.equals("breakfast")) {
                                breakfastListMon.get(Integer.parseInt(position)).setName(name);
                                breakfastListMon.get(Integer.parseInt(position)).setCalorie(calorie);
                                breakfastListMon.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    breakfastListExpandMon.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    breakfastListExpandMon.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (breakfastListExpandMon.size() >= 4) {
                                    breakfastListMon.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_breakfast.setAdapter(new AddBreakfast(breakfastListMon, getApplicationContext(), addButtonListenerChart));
                                add_breakfast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpandMon, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                breakfast_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("lunch")) {
                                lunchListMon.get(Integer.parseInt(position)).setName(name);
                                lunchListMon.get(Integer.parseInt(position)).setCalorie(calorie);
                                lunchListMon.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    lunchListExpandMon.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    lunchListExpandMon.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (lunchListExpandMon.size() >= 4) {
                                    lunchListMon.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_lunch.setAdapter(new AddLunch(lunchListMon, getApplicationContext(), addButtonListenerChart));
                                add_lunch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                lunch_list.setAdapter(new AddLunchList(lunchListExpandMon, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                lunch_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("snack")) {
                                snackListMon.get(Integer.parseInt(position)).setName(name);
                                snackListMon.get(Integer.parseInt(position)).setCalorie(calorie);
                                snackListMon.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    snackListExpandMon.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    snackListExpandMon.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (snackListExpandMon.size() >= 4) {
                                    snackListMon.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_snack.setAdapter(new AddSnack(snackListMon, getApplicationContext(), addButtonListenerChart));
                                add_snack.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                snack_list.setAdapter(new AddSnackList(snackListExpandMon, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                snack_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("dinner")) {
                                dinnerListMon.get(Integer.parseInt(position)).setName(name);
                                dinnerListMon.get(Integer.parseInt(position)).setCalorie(calorie);
                                dinnerListMon.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    dinnerListExpandMon.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    dinnerListExpandMon.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (dinnerListExpandMon.size() >= 4) {
                                    dinnerListMon.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_dinner.setAdapter(new AddDinner(dinnerListMon, getApplicationContext(), addButtonListenerChart));
                                add_dinner.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                dinner_list.setAdapter(new AddDinnerList(dinnerListExpandMon, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                dinner_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                        }
                        if (tue.isChecked()) {
                            if (meal.equals("breakfast")) {
                                breakfastListTue.get(Integer.parseInt(position)).setName(name);
                                breakfastListTue.get(Integer.parseInt(position)).setCalorie(calorie);
                                breakfastListTue.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    breakfastListExpandTue.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    breakfastListExpandTue.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (breakfastListExpandTue.size() >= 4) {
                                    breakfastListTue.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_breakfast.setAdapter(new AddBreakfast(breakfastListTue, getApplicationContext(), addButtonListenerChart));
                                add_breakfast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpandTue, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                breakfast_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("lunch")) {
                                lunchListTue.get(Integer.parseInt(position)).setName(name);
                                lunchListTue.get(Integer.parseInt(position)).setCalorie(calorie);
                                lunchListTue.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    lunchListExpandTue.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    lunchListExpandTue.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (lunchListExpandTue.size() >= 4) {
                                    lunchListTue.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_lunch.setAdapter(new AddLunch(lunchListTue, getApplicationContext(), addButtonListenerChart));
                                add_lunch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                lunch_list.setAdapter(new AddLunchList(lunchListExpandTue, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                lunch_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("snack")) {
                                snackListTue.get(Integer.parseInt(position)).setName(name);
                                snackListTue.get(Integer.parseInt(position)).setCalorie(calorie);
                                snackListTue.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    snackListExpandTue.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    snackListExpandTue.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (snackListExpandTue.size() >= 4) {
                                    snackList.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_snack.setAdapter(new AddSnack(snackListTue, getApplicationContext(), addButtonListenerChart));
                                add_snack.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                snack_list.setAdapter(new AddSnackList(snackListExpandTue, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                snack_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("dinner")) {
                                Toast.makeText(Diet_plan_main_screen.this, "This for Morning", Toast.LENGTH_SHORT).show();
                                dinnerListTue.get(Integer.parseInt(position)).setName(name);
                                dinnerListTue.get(Integer.parseInt(position)).setCalorie(calorie);
                                dinnerListTue.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    dinnerListExpandTue.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    dinnerListExpandTue.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (dinnerListExpandTue.size() >= 4) {
                                    dinnerListTue.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_dinner.setAdapter(new AddDinner(dinnerListTue, getApplicationContext(), addButtonListenerChart));
                                add_dinner.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                dinner_list.setAdapter(new AddDinnerList(dinnerListExpandTue, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                dinner_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                        }
                        if (wed.isChecked()) {
                            if (meal.equals("breakfast")) {
                                breakfastListWed.get(Integer.parseInt(position)).setName(name);
                                breakfastListWed.get(Integer.parseInt(position)).setCalorie(calorie);
                                breakfastListWed.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    breakfastListExpandWed.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    breakfastListExpandWed.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (breakfastListExpandWed.size() >= 4) {
                                    breakfastListWed.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_breakfast.setAdapter(new AddBreakfast(breakfastListWed, getApplicationContext(), addButtonListenerChart));
                                add_breakfast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpandWed, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                breakfast_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("lunch")) {
                                lunchListWed.get(Integer.parseInt(position)).setName(name);
                                lunchListWed.get(Integer.parseInt(position)).setCalorie(calorie);
                                lunchListWed.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    lunchListExpandWed.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    lunchListExpandWed.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (lunchListExpandWed.size() >= 4) {
                                    lunchListWed.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_lunch.setAdapter(new AddLunch(lunchListWed, getApplicationContext(), addButtonListenerChart));
                                add_lunch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                lunch_list.setAdapter(new AddLunchList(lunchListExpandWed, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                lunch_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("snack")) {
                                snackListWed.get(Integer.parseInt(position)).setName(name);
                                snackListWed.get(Integer.parseInt(position)).setCalorie(calorie);
                                snackListWed.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    snackListExpandWed.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    snackListExpandWed.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (snackListExpandWed.size() >= 4) {
                                    snackListWed.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_snack.setAdapter(new AddSnack(snackListWed, getApplicationContext(), addButtonListenerChart));
                                add_snack.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                snack_list.setAdapter(new AddSnackList(snackListExpandWed, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                snack_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("dinner")) {
                                dinnerListWed.get(Integer.parseInt(position)).setName(name);
                                dinnerListWed.get(Integer.parseInt(position)).setCalorie(calorie);
                                dinnerListWed.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    dinnerListExpandWed.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    dinnerListExpandWed.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (dinnerListExpandWed.size() >= 4) {
                                    dinnerListWed.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_dinner.setAdapter(new AddDinner(dinnerListWed, getApplicationContext(), addButtonListenerChart));
                                add_dinner.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                dinner_list.setAdapter(new AddDinnerList(dinnerListExpandWed, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                dinner_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                        }
                        if (thur.isChecked()) {
                            if (meal.equals("breakfast")) {
                                breakfastListThur.get(Integer.parseInt(position)).setName(name);
                                breakfastListThur.get(Integer.parseInt(position)).setCalorie(calorie);
                                breakfastListThur.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    breakfastListExpandThur.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    breakfastListExpandThur.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (breakfastListExpandThur.size() >= 4) {
                                    breakfastListThur.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_breakfast.setAdapter(new AddBreakfast(breakfastListThur, getApplicationContext(), addButtonListenerChart));
                                add_breakfast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpandThur, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                breakfast_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("lunch")) {
                                lunchListThur.get(Integer.parseInt(position)).setName(name);
                                lunchListThur.get(Integer.parseInt(position)).setCalorie(calorie);
                                lunchListThur.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    lunchListExpandThur.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    lunchListExpandThur.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (lunchListExpandThur.size() >= 4) {
                                    lunchListThur.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_lunch.setAdapter(new AddLunch(lunchListThur, getApplicationContext(), addButtonListenerChart));
                                add_lunch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                lunch_list.setAdapter(new AddLunchList(lunchListExpandThur, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                lunch_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("snack")) {
                                snackListThur.get(Integer.parseInt(position)).setName(name);
                                snackListThur.get(Integer.parseInt(position)).setCalorie(calorie);
                                snackListThur.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    snackListExpandThur.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    snackListExpandThur.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (snackListExpandThur.size() >= 4) {
                                    snackListThur.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_snack.setAdapter(new AddSnack(snackListThur, getApplicationContext(), addButtonListenerChart));
                                add_snack.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                snack_list.setAdapter(new AddSnackList(snackListExpandThur, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                snack_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("dinner")) {
                                dinnerListThur.get(Integer.parseInt(position)).setName(name);
                                dinnerListThur.get(Integer.parseInt(position)).setCalorie(calorie);
                                dinnerListThur.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    dinnerListExpandThur.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    dinnerListExpandThur.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (dinnerListExpandThur.size() >= 4) {
                                    dinnerListThur.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_dinner.setAdapter(new AddDinner(dinnerListThur, getApplicationContext(), addButtonListenerChart));
                                add_dinner.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                dinner_list.setAdapter(new AddDinnerList(dinnerListExpandThur, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                dinner_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                        }
                        if (fri.isChecked()) {
                            if (meal.equals("breakfast")) {
                                breakfastListFri.get(Integer.parseInt(position)).setName(name);
                                breakfastListFri.get(Integer.parseInt(position)).setCalorie(calorie);
                                breakfastListFri.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    breakfastListExpandFri.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    breakfastListExpandFri.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (breakfastListExpandFri.size() >= 4) {
                                    breakfastListFri.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_breakfast.setAdapter(new AddBreakfast(breakfastListFri, getApplicationContext(), addButtonListenerChart));
                                add_breakfast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpandFri, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                breakfast_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("lunch")) {
                                lunchListFri.get(Integer.parseInt(position)).setName(name);
                                lunchListFri.get(Integer.parseInt(position)).setCalorie(calorie);
                                lunchListFri.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    lunchListExpandFri.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    lunchListExpandFri.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (lunchListExpandFri.size() >= 4) {
                                    lunchListFri.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_lunch.setAdapter(new AddLunch(lunchListFri, getApplicationContext(), addButtonListenerChart));
                                add_lunch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                lunch_list.setAdapter(new AddLunchList(lunchListExpandFri, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                lunch_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("snack")) {
                                Toast.makeText(Diet_plan_main_screen.this, "This for Morning", Toast.LENGTH_SHORT).show();
                                snackListFri.get(Integer.parseInt(position)).setName(name);
                                snackListFri.get(Integer.parseInt(position)).setCalorie(calorie);
                                snackListFri.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    snackListExpandFri.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
                                    snackListExpandFri.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (snackListExpandFri.size() >= 4) {
                                    snackListFri.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_snack.setAdapter(new AddSnack(snackListFri, getApplicationContext(), addButtonListenerChart));
                                add_snack.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                snack_list.setAdapter(new AddSnackList(snackListExpandFri, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                snack_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("dinner")) {
                                Toast.makeText(Diet_plan_main_screen.this, "This for Morning", Toast.LENGTH_SHORT).show();
                                dinnerListFri.get(Integer.parseInt(position)).setName(name);
                                dinnerListFri.get(Integer.parseInt(position)).setCalorie(calorie);
                                dinnerListFri.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    dinnerListExpandFri.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
                                    dinnerListExpandFri.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (dinnerListExpandFri.size() >= 4) {
                                    dinnerListSat.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_dinner.setAdapter(new AddDinner(dinnerListFri, getApplicationContext(), addButtonListenerChart));
                                add_dinner.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                dinner_list.setAdapter(new AddDinnerList(dinnerListExpandFri, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                dinner_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                        }
                        if (sat.isChecked()) {
                            if (meal.equals("breakfast")) {
                                Toast.makeText(Diet_plan_main_screen.this, "This for Morning", Toast.LENGTH_SHORT).show();
                                breakfastListSat.get(Integer.parseInt(position)).setName(name);
                                breakfastListSat.get(Integer.parseInt(position)).setCalorie(calorie);
                                breakfastListSat.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    breakfastListExpandSat.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
                                    breakfastListExpandSat.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (breakfastListExpandSat.size() >= 4) {
                                    breakfastListSat.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_breakfast.setAdapter(new AddBreakfast(breakfastListSat, getApplicationContext(), addButtonListenerChart));
                                add_breakfast.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpandSat, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                breakfast_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("lunch")) {
                                Toast.makeText(Diet_plan_main_screen.this, "This for Morning", Toast.LENGTH_SHORT).show();
                                lunchListSat.get(Integer.parseInt(position)).setName(name);
                                lunchListSat.get(Integer.parseInt(position)).setCalorie(calorie);
                                lunchListSat.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    lunchListExpandSat.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
                                    lunchListExpandSat.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (lunchListExpandSat.size() >= 4) {
                                    lunchListSat.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_lunch.setAdapter(new AddLunch(lunchListSat, getApplicationContext(), addButtonListenerChart));
                                add_lunch.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                lunch_list.setAdapter(new AddLunchList(lunchListExpandSat, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                lunch_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("snack")) {
                                Toast.makeText(Diet_plan_main_screen.this, "This for Morning", Toast.LENGTH_SHORT).show();
                                snackListSat.get(Integer.parseInt(position)).setName(name);
                                snackListSat.get(Integer.parseInt(position)).setCalorie(calorie);
                                snackListSat.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    snackListExpandSat.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
                                    snackListExpandSat.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (snackListExpandSat.size() >= 4) {
                                    snackListSat.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_snack.setAdapter(new AddSnack(snackListSat, getApplicationContext(), addButtonListenerChart));
                                add_snack.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                snack_list.setAdapter(new AddSnackList(snackListExpandSat, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                snack_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                            if (meal.equals("dinner")) {
                                Toast.makeText(Diet_plan_main_screen.this, "This for Morning", Toast.LENGTH_SHORT).show();
                                dinnerListSat.get(Integer.parseInt(position)).setName(name);
                                dinnerListSat.get(Integer.parseInt(position)).setCalorie(calorie);
                                dinnerListSat.get(Integer.parseInt(position)).setPhoto(photo);
                                try {
                                    dinnerListExpandSat.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                } catch (Exception ex) {
                                    Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
                                    dinnerListExpandSat.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
                                }
                                if (dinnerListExpandSat.size() >= 4) {
                                    dinnerListSat.add(new ModelForFood("", "", getApplicationContext().getResources().getDrawable(R.drawable.add_food)));
                                }
                                add_dinner.setAdapter(new AddDinner(dinnerListSat, getApplicationContext(), addButtonListenerChart));
                                add_dinner.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

                                dinner_list.setAdapter(new AddDinnerList(dinnerListExpandSat, getApplicationContext(), addButtonListenerChart, foodDetailsListener));
                                dinner_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            }
                        }
                    }
                }
            });

    @SuppressLint("UseSupportActionBar")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plan_main_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.diet_chart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        breakfastList = new ArrayList<>();
        breakfastListExpand = new ArrayList<>();

        breakfastListMon = new ArrayList<>();
        breakfastListExpandMon = new ArrayList<>();

        breakfastListTue = new ArrayList<>();
        breakfastListExpandTue = new ArrayList<>();

        breakfastListWed = new ArrayList<>();
        breakfastListExpandWed = new ArrayList<>();

        breakfastListThur = new ArrayList<>();
        breakfastListExpandThur = new ArrayList<>();

        breakfastListFri = new ArrayList<>();
        breakfastListExpandFri = new ArrayList<>();

        breakfastListSat = new ArrayList<>();
        breakfastListExpandSat = new ArrayList<>();


        lunchList = new ArrayList<>();
        lunchListExpand = new ArrayList<>();

        lunchListMon = new ArrayList<>();
        lunchListExpandMon = new ArrayList<>();

        lunchListTue = new ArrayList<>();
        lunchListExpandTue = new ArrayList<>();

        lunchListWed = new ArrayList<>();
        lunchListExpandWed = new ArrayList<>();

        lunchListThur = new ArrayList<>();
        lunchListExpandThur = new ArrayList<>();

        lunchListFri = new ArrayList<>();
        lunchListExpandFri = new ArrayList<>();

        lunchListSat = new ArrayList<>();
        lunchListExpandSat = new ArrayList<>();


        snackList = new ArrayList<>();
        snackListExpand = new ArrayList<>();

        snackListMon = new ArrayList<>();
        snackListExpandMon = new ArrayList<>();

        snackListTue = new ArrayList<>();
        snackListExpandTue = new ArrayList<>();

        snackListWed = new ArrayList<>();
        snackListExpandWed = new ArrayList<>();

        snackListThur = new ArrayList<>();
        snackListExpandThur = new ArrayList<>();

        snackListFri = new ArrayList<>();
        snackListExpandFri = new ArrayList<>();

        snackListSat = new ArrayList<>();
        snackListExpandSat = new ArrayList<>();

        dinnerList = new ArrayList<>();
        dinnerListExpand = new ArrayList<>();

        dinnerListMon = new ArrayList<>();
        dinnerListExpandMon = new ArrayList<>();

        dinnerListTue = new ArrayList<>();
        dinnerListExpandTue = new ArrayList<>();

        dinnerListWed = new ArrayList<>();
        dinnerListExpandWed = new ArrayList<>();

        dinnerListThur = new ArrayList<>();
        dinnerListExpandThur = new ArrayList<>();

        dinnerListFri = new ArrayList<>();
        dinnerListExpandFri = new ArrayList<>();

        dinnerListSat = new ArrayList<>();
        dinnerListExpandSat = new ArrayList<>();


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.add_food);
        Drawable photo = getApplicationContext().getResources().getDrawable(R.drawable.add_food);

        for (int i = 0; i < 4; i++) {

            breakfastList.add(new ModelForFood("", "", photo));
            breakfastListMon.add(new ModelForFood("", "", photo));
            breakfastListTue.add(new ModelForFood("", "", photo));
            breakfastListWed.add(new ModelForFood("", "", photo));
            breakfastListThur.add(new ModelForFood("", "", photo));
            breakfastListFri.add(new ModelForFood("", "", photo));
            breakfastListSat.add(new ModelForFood("", "", photo));

            lunchList.add(new ModelForFood("", "", photo));
            lunchListMon.add(new ModelForFood("", "", photo));
            lunchListTue.add(new ModelForFood("", "", photo));
            lunchListWed.add(new ModelForFood("", "", photo));
            lunchListThur.add(new ModelForFood("", "", photo));
            lunchListFri.add(new ModelForFood("", "", photo));
            lunchListSat.add(new ModelForFood("", "", photo));

            snackList.add(new ModelForFood("", "", photo));
            snackListMon.add(new ModelForFood("", "", photo));
            snackListTue.add(new ModelForFood("", "", photo));
            snackListWed.add(new ModelForFood("", "", photo));
            snackListThur.add(new ModelForFood("", "", photo));
            snackListFri.add(new ModelForFood("", "", photo));
            snackListSat.add(new ModelForFood("", "", photo));

            dinnerList.add(new ModelForFood("", "", photo));
            dinnerListMon.add(new ModelForFood("", "", photo));
            dinnerListTue.add(new ModelForFood("", "", photo));
            dinnerListWed.add(new ModelForFood("", "", photo));
            dinnerListThur.add(new ModelForFood("", "", photo));
            dinnerListFri.add(new ModelForFood("", "", photo));
            dinnerListSat.add(new ModelForFood("", "", photo));

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

        sun = findViewById(R.id.sun);
        mon = findViewById(R.id.mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        thur = findViewById(R.id.thur);
        fri = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);

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

        days = findViewById(R.id.days);

        days.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.sun) {
                sun.setBackgroundColor(Color.parseColor("#1D8BF1"));
                sun.setTextColor(getResources().getColor(R.color.white));
                mon.setBackgroundColor(getResources().getColor(R.color.white));
                mon.setTextColor(getResources().getColor(R.color.blue));
                tue.setBackgroundColor(Color.WHITE);
                tue.setTextColor(Color.parseColor("#1D8BF1"));
                wed.setBackgroundColor(Color.WHITE);
                wed.setTextColor(Color.parseColor("#1D8BF1"));
                thur.setBackgroundColor(Color.WHITE);
                thur.setTextColor(Color.parseColor("#1D8BF1"));
                fri.setBackgroundColor(Color.WHITE);
                fri.setTextColor(Color.parseColor("#1D8BF1"));
                sat.setBackgroundColor(Color.WHITE);
                sat.setTextColor(Color.parseColor("#1D8BF1"));

                setItemOverview(add_breakfast, add_lunch, add_snack, add_dinner, breakfastList, lunchList, snackList, dinnerList);

                setItemList(breakfast_list, lunch_list, snack_list, dinner_list, breakfastListExpand, lunchListExpand, snackListExpand, dinnerListExpand);

            }
            if (checkedId == R.id.mon) {
                sun.setBackgroundColor(Color.WHITE);
                sun.setTextColor(Color.parseColor("#1D8BF1"));
                mon.setBackgroundColor(Color.parseColor("#1D8BF1"));
                mon.setTextColor(Color.WHITE);
                tue.setBackgroundColor(Color.WHITE);
                tue.setTextColor(Color.parseColor("#1D8BF1"));
                wed.setBackgroundColor(Color.WHITE);
                wed.setTextColor(Color.parseColor("#1D8BF1"));
                thur.setBackgroundColor(Color.WHITE);
                thur.setTextColor(Color.parseColor("#1D8BF1"));
                fri.setBackgroundColor(Color.WHITE);
                fri.setTextColor(Color.parseColor("#1D8BF1"));
                sat.setBackgroundColor(Color.WHITE);
                sat.setTextColor(Color.parseColor("#1D8BF1"));

                setItemOverview(add_breakfast, add_lunch, add_snack, add_dinner, breakfastListMon, lunchListMon, snackListMon, dinnerListMon);
                setItemList(breakfast_list, lunch_list, snack_list, dinner_list, breakfastListExpandMon, lunchListExpandMon, snackListExpandMon, dinnerListExpandMon);
            }
            if (checkedId == R.id.tue) {
                sun.setBackgroundColor(Color.WHITE);
                sun.setTextColor(Color.parseColor("#1D8BF1"));
                mon.setBackgroundColor(Color.WHITE);
                mon.setTextColor(getResources().getColor(R.color.blue));
                tue.setBackgroundColor(Color.parseColor("#1D8BF1"));
                tue.setTextColor(Color.WHITE);
                wed.setBackgroundColor(Color.WHITE);
                wed.setTextColor(Color.parseColor("#1D8BF1"));
                thur.setBackgroundColor(Color.WHITE);
                thur.setTextColor(Color.parseColor("#1D8BF1"));
                fri.setBackgroundColor(Color.WHITE);
                fri.setTextColor(Color.parseColor("#1D8BF1"));
                sat.setBackgroundColor(Color.WHITE);
                sat.setTextColor(Color.parseColor("#1D8BF1"));

                setItemOverview(add_breakfast, add_lunch, add_snack, add_dinner, breakfastListTue, lunchListTue, snackListTue, dinnerListTue);
                setItemList(breakfast_list, lunch_list, snack_list, dinner_list, breakfastListExpand, lunchListExpandTue, snackListExpandTue, dinnerListExpandTue);

            }
            if (checkedId == R.id.wed) {
                sun.setBackgroundColor(Color.WHITE);
                sun.setTextColor(Color.parseColor("#1D8BF1"));
                mon.setBackgroundColor(Color.WHITE);
                mon.setTextColor(getResources().getColor(R.color.blue));
                tue.setBackgroundColor(Color.WHITE);
                tue.setTextColor(Color.parseColor("#1D8BF1"));
                wed.setBackgroundColor(Color.parseColor("#1D8BF1"));
                wed.setTextColor(Color.WHITE);
                thur.setBackgroundColor(Color.WHITE);
                thur.setTextColor(Color.parseColor("#1D8BF1"));
                fri.setBackgroundColor(Color.WHITE);
                fri.setTextColor(Color.parseColor("#1D8BF1"));
                sat.setBackgroundColor(Color.WHITE);
                sat.setTextColor(Color.parseColor("#1D8BF1"));

                setItemOverview(add_breakfast, add_lunch, add_snack, add_dinner, breakfastListWed, lunchListWed, snackListWed, dinnerListWed);
                setItemList(breakfast_list, lunch_list, snack_list, dinner_list, breakfastListExpandWed, lunchListExpandWed, snackListExpandWed, dinnerListExpandWed);

            }
            if (checkedId == R.id.thur) {
                sun.setBackgroundColor(Color.WHITE);
                sun.setTextColor(Color.parseColor("#1D8BF1"));
                mon.setBackgroundColor(Color.WHITE);
                mon.setTextColor(getResources().getColor(R.color.blue));
                tue.setBackgroundColor(Color.WHITE);
                tue.setTextColor(Color.parseColor("#1D8BF1"));
                wed.setBackgroundColor(Color.WHITE);
                wed.setTextColor(Color.parseColor("#1D8BF1"));
                thur.setBackgroundColor(Color.parseColor("#1D8BF1"));
                thur.setTextColor(Color.WHITE);
                fri.setBackgroundColor(Color.WHITE);
                fri.setTextColor(Color.parseColor("#1D8BF1"));
                sat.setBackgroundColor(Color.WHITE);
                sat.setTextColor(Color.parseColor("#1D8BF1"));

                setItemOverview(add_breakfast, add_lunch, add_snack, add_dinner, breakfastListThur, lunchListThur, snackListThur, dinnerListThur);
                setItemList(breakfast_list, lunch_list, snack_list, dinner_list, breakfastListExpandThur, lunchListExpandThur, snackListExpandThur, dinnerListExpandThur);

            }
            if (checkedId == R.id.fri) {
                sun.setBackgroundColor(Color.WHITE);
                sun.setTextColor(Color.parseColor("#1D8BF1"));
                mon.setBackgroundColor(Color.WHITE);
                mon.setTextColor(getResources().getColor(R.color.blue));
                tue.setBackgroundColor(Color.WHITE);
                tue.setTextColor(Color.parseColor("#1D8BF1"));
                wed.setBackgroundColor(Color.WHITE);
                wed.setTextColor(Color.parseColor("#1D8BF1"));
                thur.setBackgroundColor(Color.WHITE);
                thur.setTextColor(Color.parseColor("#1D8BF1"));
                fri.setBackgroundColor(Color.parseColor("#1D8BF1"));
                fri.setTextColor(Color.WHITE);
                sat.setBackgroundColor(Color.WHITE);
                sat.setTextColor(Color.parseColor("#1D8BF1"));

                setItemOverview(add_breakfast, add_lunch, add_snack, add_dinner, breakfastListFri, lunchListFri, snackListFri, dinnerListFri);
                setItemList(breakfast_list, lunch_list, snack_list, dinner_list, breakfastListExpandFri, lunchListExpandFri, snackListExpandFri, dinnerListExpandFri);

            }
            if (checkedId == R.id.sat) {
                sun.setBackgroundColor(Color.WHITE);
                sun.setTextColor(Color.parseColor("#1D8BF1"));
                mon.setBackgroundColor(Color.WHITE);
                mon.setTextColor(getResources().getColor(R.color.blue));
                tue.setBackgroundColor(Color.WHITE);
                tue.setTextColor(Color.parseColor("#1D8BF1"));
                wed.setBackgroundColor(Color.WHITE);
                wed.setTextColor(Color.parseColor("#1D8BF1"));
                thur.setBackgroundColor(Color.WHITE);
                thur.setTextColor(Color.parseColor("#1D8BF1"));
                fri.setBackgroundColor(Color.WHITE);
                fri.setTextColor(Color.parseColor("#1D8BF1"));
                sat.setBackgroundColor(Color.parseColor("#1D8BF1"));
                sat.setTextColor(Color.WHITE);

                setItemOverview(add_breakfast, add_lunch, add_snack, add_dinner, breakfastListSat, lunchListSat, snackListSat, dinnerListSat);
                setItemList(breakfast_list, lunch_list, snack_list, dinner_list, breakfastListExpandSat, lunchListExpandSat, snackListExpandSat, dinnerListExpandSat);

            }
        });


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
            Dialog dialog = new Dialog(Diet_plan_main_screen.this);
            dialog.setContentView(R.layout.confirm_diet_plan);
            Button yes = dialog.findViewById(R.id.yes);
            Button no = dialog.findViewById(R.id.no);

            yes.setOnClickListener(view -> {

                Dialog tempDialog = new Dialog(Diet_plan_main_screen.this);
                tempDialog.setContentView(R.layout.template_question);

                Button saveAsTemplate = tempDialog.findViewById(R.id.yes);
                Button noAsTemplate = tempDialog.findViewById(R.id.no);

                saveAsTemplate.setOnClickListener(viewAsTemplate -> {

                    Dialog tempSave = new Dialog(Diet_plan_main_screen.this);
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
                dialog.dismiss();
            });
            no.setOnClickListener(view -> {
                dialog.dismiss();
            });
            dialog.show();
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

    void sendToDB(String day, String json) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            System.out.println(response);
            if (response.equals("updated")) {
                Toast.makeText(getApplicationContext(), "Saved successfully", Toast.LENGTH_SHORT);
            }
        }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("dietitianuserID", DataFromDatabase.dietitianuserID);
                data.put("clientID", ClientDetails.clientID);
                data.put("json", json);
                data.put("day", day);
                return data;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
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
                data.put("clientID", ClientDetails.clientID);
                data.put("json", json);
                data.put("template_name", templateName);
                return data;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diet_chart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.load_template) {
            Intent intent = new Intent(Diet_plan_main_screen.this, LoadTemplate.class);
            loadTemplate.launch(intent);
            return true;
        }
        if (item.getItemId() == R.id.new_template) {
            Intent intent = new Intent(Diet_plan_main_screen.this, NewTemplate.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void sendJson() {
        if (sun.isChecked()) {
            diet_plan = new JSONObject();
            addFoodToJSON(breakfastListExpand, "breakfast");
            addFoodToJSON(lunchListExpand, "lunch");
            addFoodToJSON(snackListExpand, "snack");
            addFoodToJSON(dinnerListExpand, "dinner");
            sendToDB("sunday", diet_plan.toString());
        }
        if (mon.isChecked()) {
                diet_plan = new JSONObject();
                addFoodToJSON(breakfastListExpandMon, "breakfast");
                addFoodToJSON(lunchListExpandMon, "lunch");
                addFoodToJSON(snackListExpandMon, "snack");
                addFoodToJSON(dinnerListExpandMon, "dinner");
                sendToDB("monday", diet_plan.toString());
        }
        if (tue.isChecked()) {
            diet_plan = new JSONObject();
            addFoodToJSON(breakfastListExpandTue, "breakfast");
            addFoodToJSON(lunchListExpandTue, "lunch");
            addFoodToJSON(snackListExpandTue, "snack");
            addFoodToJSON(dinnerListExpandTue, "dinner");
            sendToDB("tuesday", diet_plan.toString());
        }
        if (wed.isChecked()) {
            diet_plan = new JSONObject();
            addFoodToJSON(breakfastListExpandWed, "breakfast");
            addFoodToJSON(lunchListExpandWed, "lunch");
            addFoodToJSON(snackListExpandWed, "snack");
            addFoodToJSON(dinnerListExpandWed, "dinner");
            sendToDB("wednesday", diet_plan.toString());
        }
        if (thur.isChecked()) {
            diet_plan = new JSONObject();
            addFoodToJSON(breakfastListExpandThur, "breakfast");
            addFoodToJSON(lunchListExpandThur, "lunch");
            addFoodToJSON(snackListExpandThur, "snack");
            addFoodToJSON(dinnerListExpandThur, "dinner");
            sendToDB("thursday", diet_plan.toString());
        }
        if (fri.isChecked()) {
            diet_plan = new JSONObject();
            addFoodToJSON(breakfastListExpandFri, "breakfast");
            addFoodToJSON(lunchListExpandFri, "lunch");
            addFoodToJSON(snackListExpandFri, "snack");
            addFoodToJSON(dinnerListExpandFri, "dinner");
            sendToDB("friday", diet_plan.toString());
        }
        if (sat.isChecked()) {
            diet_plan = new JSONObject();
            addFoodToJSON(breakfastListExpandMon, "breakfast");
            addFoodToJSON(lunchListExpandSat, "lunch");
            addFoodToJSON(snackListExpandSat, "snack");
            addFoodToJSON(dinnerListExpandSat, "dinner");
            sendToDB("saturday", diet_plan.toString());
        }
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
    void afterAddingFood(RecyclerView add_breakfast,RecyclerView breakfast_list, List<ModelForFood> breakfastList,List<ModelForFood> breakfastListExpand,String position,String name,String calorie,String description,String nutrients,String ingredients,Drawable photo,String preparation,String quantity){
        breakfastList.get(Integer.parseInt(position)).setName(name);
        breakfastList.get(Integer.parseInt(position)).setCalorie(calorie);
        breakfastList.get(Integer.parseInt(position)).setPhoto(photo);
        try {
            breakfastListExpand.set(Integer.parseInt(position), new ModelForFood(name, calorie, description, nutrients, ingredients, photo, "", preparation, quantity));
        } catch (Exception ex) {
            Toast.makeText(Diet_plan_main_screen.this, "Hi", Toast.LENGTH_SHORT).show();
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
// 2100