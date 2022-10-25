package com.ultimate.infits;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
 * Use the {@link dietchart_plan#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dietchart_plan extends Fragment {

    String url = String.format("%sdiet_chart_client_side.php", DataFromDatabase.ipConfig);

    RadioGroup days;

    RadioButton sun, mon, tue, wed, thur, fri, sat;

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

    RecyclerView re;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public dietchart_plan() {

    }

    public static dietchart_plan newInstance(String param1, String param2) {
        dietchart_plan fragment = new dietchart_plan();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dietchart_plan, container, false);
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

        Button editPlan = view.findViewById(R.id.edit_diet_cart);

        editPlan.setOnClickListener(v->{
            Intent intent = new Intent(getContext(),Diet_plan_main_screen.class);
            startActivity(intent);
        });

        Drawable photo = getContext().getResources().getDrawable(R.drawable.add_food);

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

        breakfast_card = view.findViewById(R.id.breakfast_card);
        lunch_card = view.findViewById(R.id.lunch_card);
        dinner_card = view.findViewById(R.id.dinner_card);
        snack_card = view.findViewById(R.id.snack_card);
        breakfast = view.findViewById(R.id.breakfast);
        lunch = view.findViewById(R.id.lunch);
        dinner = view.findViewById(R.id.dinner);
        snack = view.findViewById(R.id.snacks);

        breakfast_list = view.findViewById(R.id.breakfast_list);
        lunch_list = view.findViewById(R.id.lunch_list);
        snack_list = view.findViewById(R.id.snack_list);
        dinner_list = view.findViewById(R.id.dinner_list);

        add_breakfast = view.findViewById(R.id.add_breakfast);
        add_lunch = view.findViewById(R.id.add_lunch);
        add_dinner = view.findViewById(R.id.add_dinner);
        add_snack = view.findViewById(R.id.add_snack);

        sun = view.findViewById(R.id.sun);
        mon = view.findViewById(R.id.mon);
        tue = view.findViewById(R.id.tue);
        wed = view.findViewById(R.id.wed);
        thur = view.findViewById(R.id.thur);
        fri = view.findViewById(R.id.fri);
        sat = view.findViewById(R.id.sat);

        Bitmap add = BitmapFactory.decodeResource(getResources(), R.drawable.add_food);

        addButtonListenerChart = new AddButtonListenerChart() {
            @Override
            public void addButtonOnClick(String name, String calorie, String description, String nutrients, String ingredients, Drawable photo, String category, String preparation, String meal) {

            }

            @Override
            public void addButtonOnClick(String position, String meal) {

            }
        };

        foodDetailsListener = new FoodDetailsListener() {
            @Override
            public void setDetails(int position) {

            }

            @Override
            public void setDetails(String name, String calorie, String description, String nutrients, String ingredients, Drawable photo, String category, String preparation) {
                FoodDetailsDialog foodDetailsDialog = new FoodDetailsDialog(name, calorie, description, nutrients, ingredients, photo, category, preparation);
                foodDetailsDialog.show(getActivity().getSupportFragmentManager(), "FoodDetails");
            }
        };

        item_overview_breakfast = view.findViewById(R.id.item_overview_breakfast);
        item_overview_lunch = view.findViewById(R.id.item_overview_lunch);
        item_overview_snack = view.findViewById(R.id.item_overview_snacks);
        item_overview_dinner = view.findViewById(R.id.item_overview_dinner);

        days = view.findViewById(R.id.days);

        getDietChart("sunday");

        days.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.sun) {

                setNullAdapter();
                getDietChart("sunday");

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
            }
            if (checkedId == R.id.mon) {

                setNullAdapter();
                getDietChart("monday");

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
            }
            if (checkedId == R.id.tue) {

                setNullAdapter();
                getDietChart("tuesday");

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
            }
            if (checkedId == R.id.wed) {
                setNullAdapter();
                getDietChart("wednesday");
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

            }
            if (checkedId == R.id.thur) {

                setNullAdapter();
                getDietChart("thursday");

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

                setNullAdapter();
                getDietChart("friday");
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
                setNullAdapter();
                getDietChart("saturday");
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
            item_overview_breakfast.setVisibility(View.VISIBLE);
            item_overview_lunch.setVisibility(View.VISIBLE);
            item_overview_snack.setVisibility(View.GONE);
            item_overview_dinner.setVisibility(View.VISIBLE);

            breakfast_list.setVisibility(View.GONE);
            lunch_list.setVisibility(View.GONE);
            snack_list.setVisibility(View.VISIBLE);
            dinner_list.setVisibility(View.GONE);
        });

        breakfast_card.setOnClickListener(v -> {
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
        lunch_card.setOnClickListener(v -> {
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
        dinner_card.setOnClickListener(v -> {
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
        snack_card.setOnClickListener(v -> {
            breakfast.setChecked(false);
            lunch.setChecked(false);
            dinner.setChecked(false);
            snack.setChecked(true);
            item_overview_breakfast.setVisibility(View.VISIBLE);
            item_overview_lunch.setVisibility(View.VISIBLE);
            item_overview_snack.setVisibility(View.GONE);
            item_overview_dinner.setVisibility(View.VISIBLE);

            breakfast_list.setVisibility(View.GONE);
            lunch_list.setVisibility(View.GONE);
            snack_list.setVisibility(View.VISIBLE);
            dinner_list.setVisibility(View.GONE);
        });
        return view;
    }

    private void setNullAdapter() {
        add_breakfast.setAdapter(null);
        breakfast_list.setAdapter(null);

        add_lunch.setAdapter(null);
        lunch_list.setAdapter(null);

        add_snack.setAdapter(null);
        snack_list.setAdapter(null);

        add_dinner.setAdapter(null);
        dinner_list.setAdapter(null);
    }

    private void getDietChart(String day) {
        StringRequest sunday = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("breakfast");
                breakfastList.removeAll(breakfastList);
                breakfastListExpand.removeAll(breakfastListExpand);
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

                    System.out.println(name);

                    breakfastList.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, "", preparation, quantity));
                    breakfastListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, "", preparation, quantity));

                }
                add_breakfast.setAdapter(new AddBreakfast(breakfastList, getContext(), addButtonListenerChart));
                add_breakfast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpand, getContext(), addButtonListenerChart, foodDetailsListener));
                breakfast_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("lunch");
                lunchList.removeAll(lunchList);
                lunchListExpand.removeAll(lunchListExpand);
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

                    lunchList.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, "", preparation, quantity));
                    lunchListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, "", preparation, quantity));

                    add_lunch.setAdapter(new AddLunch(lunchList, getContext(), addButtonListenerChart));
                    add_lunch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                    lunch_list.setAdapter(new AddLunchList(lunchListExpand, getContext(), addButtonListenerChart, foodDetailsListener));
                    lunch_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
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

                    snackList.removeAll(snackList);
                    snackListExpand.removeAll(snackListExpand);

                    snackList.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, preparation, "quantity"));
                    snackListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, preparation, "quantity"));

                    add_snack.setAdapter(new AddSnack(snackList, getContext(), addButtonListenerChart));
                    add_snack.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                    snack_list.setAdapter(new AddSnackList(snackListExpand, getContext(), addButtonListenerChart, foodDetailsListener));
                    snack_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
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

                    dinnerList.removeAll(dinnerList);
                    dinnerListExpand.removeAll(dinnerListExpand);

                    dinnerList.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, preparation, "quantity"));
                    dinnerListExpand.add(new ModelForFood(name, calorie, description, nutrients, ingredients, photoDraw, preparation, "quantity"));

                    add_dinner.setAdapter(new AddDinner(dinnerList, getContext(), addButtonListenerChart));
                    add_dinner.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                    dinner_list.setAdapter(new AddDinnerList(dinnerListExpand, getContext(), addButtonListenerChart, foodDetailsListener));
                    dinner_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        }, error -> {
            System.out.println(error);
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("dietitianID", DataFromDatabase.dietitianuserID);
                data.put("clientID", ClientDetails.clientID);
                data.put("day", day);
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(sunday);
    }
}