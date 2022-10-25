package com.ultimate.infits;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackerFragment extends Fragment {

    private String url = String.format("%sgetMeal.php",DataFromDatabase.ipConfig);

    RadioGroup days;

    Drawable photo;

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

    RecyclerView re;
    String date_to_display_trackers;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrackerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DietChartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackerFragment newInstance(String param1, String param2) {
        TrackerFragment fragment = new TrackerFragment();
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
        View view = inflater.inflate(R.layout.fragment_tracker, container, false);
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
        photo = getContext().getResources().getDrawable(R.drawable.black_outline);

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

        setItemOverview(add_breakfast, add_lunch, add_snack, add_dinner, breakfastList, lunchList, snackList, dinnerList);

        RadioButton today= view.findViewById(R.id.active_btn);
        RadioButton yesterday= view.findViewById(R.id.yesterday_btn);
        RadioButton date_picker= view.findViewById(R.id.date_picker_btn);
        Date dateobj=new Date();
        date_to_display_trackers=new SimpleDateFormat("d MMM yyyy").format(dateobj);

        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_to_display_trackers=new SimpleDateFormat("d MMM yyyy").format(dateobj);
                Toast.makeText(getContext(),"Selected date= "+date_to_display_trackers,Toast.LENGTH_SHORT).show();
//                vollyFunc(date_to_display_trackers);
            }
        });
        yesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
                cal.add(Calendar.DATE, -1);
                date_to_display_trackers= dateFormat.format(cal.getTime());
                Toast.makeText(getContext(),"Selected date= "+date_to_display_trackers,Toast.LENGTH_SHORT).show();
//                vollyFunc(date_to_display_trackers);
            }
        });
        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_calendar);
                CalendarView calendarView = dialog.findViewById(R.id.calendarView);
                dialog.show();

                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        String curDay = String.valueOf(dayOfMonth);
                        String curMonth = new DateFormatSymbols().getShortMonths()[month];
                        String curYear = String.valueOf(year);
                        date_to_display_trackers = curDay+" "+curMonth+" "+curYear;
                        dialog.cancel();
                        Toast.makeText(getContext(),"Selected date= "+date_to_display_trackers,Toast.LENGTH_SHORT).show();
                        Toast.makeText(getContext(),"Selected date= "+month,Toast.LENGTH_SHORT).show();
                        vollyFunc(date_to_display_trackers);
                    }
                });
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
        return view;
    }

    private void vollyFunc(String date_to_display_trackers) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

            System.out.println(response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("meal");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject meal = jsonArray.getJSONObject(i);
                    String name = meal.getString("name");
                    String description = meal.getString("description");
                    String mealString = meal.getString("meal");
                    String photoStr = meal.getString("image");
                    int position = meal.getInt("position");
                    System.out.println(name);
                    System.out.println(photoStr);
                    byte[] photoArr = Base64.decode(photoStr, Base64.DEFAULT);
                    Bitmap photoBit = BitmapFactory.decodeByteArray(photoArr, 0, photoArr.length);
                    Drawable photoDraw = new BitmapDrawable(getResources(), photoBit);
                    if (mealString.equals("breakfast")) {
                        System.out.println("inside breakfast");
                        breakfastList.get(position).setName(name);
                        breakfastList.get(position).setPhoto(photoDraw);
                        try {
                            System.out.println("inside try");
                            breakfastListExpand.set(position, new ModelForFood(name, description, photo));
                        } catch (Exception ex) {
                            Toast.makeText(getActivity(), "Hi", Toast.LENGTH_SHORT).show();
                            breakfastListExpand.add(new ModelForFood(name, description, photoDraw));
                        }
                        if (breakfastListExpand.size() >= 4) {
                            breakfastList.add(new ModelForFood("", "", getContext().getResources().getDrawable(R.drawable.add_food)));
                        }
                        add_breakfast.setAdapter(new AddBreakfastTracker(breakfastList, getContext(), addButtonListenerChart));
                        add_breakfast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                        breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpand, getContext(), addButtonListenerChart, foodDetailsListener));
                        breakfast_list.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                    if (mealString.equals("lunch")) {

                        Toast.makeText(getActivity(), "This for Morning", Toast.LENGTH_SHORT).show();
                        lunchList.get(position).setName(name);
                        lunchList.get(position).setPhoto(photoDraw);
                        try {
                            System.out.println("inside try");
                            lunchListExpand.set(position, new ModelForFood(name, description, photo));
                        } catch (Exception ex) {
                            Toast.makeText(getActivity(), "Hi", Toast.LENGTH_SHORT).show();
                            lunchListExpand.add(new ModelForFood(name, description, photoDraw));
                        }
                        if (lunchListExpand.size() >= 4) {
                            lunchList.add(new ModelForFood("", "", getContext().getResources().getDrawable(R.drawable.add_food)));
                        }
                        add_lunch.setAdapter(new AddLunch(lunchList, getContext(), addButtonListenerChart));
                        add_lunch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                        lunch_list.setAdapter(new AddLunchList(lunchListExpand, getContext(), addButtonListenerChart, foodDetailsListener));
                        lunch_list.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                    if (meal.equals("snack")) {
                        Toast.makeText(getActivity(), "This for Morning", Toast.LENGTH_SHORT).show();
                        snackList.get(position).setName(name);
//                            snackList.get(i).setCalorie(calorie);
                        snackList.get(position).setPhoto(photo);
                        try {
                            snackListExpand.set(i, new ModelForFood(name, description, photo));
                        } catch (Exception ex) {
                            Toast.makeText(getActivity(), "Hi", Toast.LENGTH_SHORT).show();
                            snackListExpand.add(new ModelForFood(name, description, photo));
                        }
                        if (snackListExpand.size() >= 4) {
                            snackList.add(new ModelForFood("", "", getContext().getResources().getDrawable(R.drawable.add_food)));
                        }
                        add_snack.setAdapter(new AddSnack(snackList, getContext(), addButtonListenerChart));
                        add_snack.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                        snack_list.setAdapter(new AddSnackList(snackListExpand, getContext(), addButtonListenerChart, foodDetailsListener));
                        snack_list.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                    if (meal.equals("dinner")) {
                        Toast.makeText(getActivity(), "This for Morning", Toast.LENGTH_SHORT).show();
                        dinnerList.get(i).setName(name);
                        dinnerList.get(i).setPhoto(photo);
                        try {
                            dinnerListExpand.set(i, new ModelForFood(name, description, photo));
                        } catch (Exception ex) {
                            Toast.makeText(getActivity(), "Hi", Toast.LENGTH_SHORT).show();
                            dinnerListExpand.add(new ModelForFood(name, description, photo));
                        }
                        if (dinnerListExpand.size() >= 4) {
                            dinnerList.add(new ModelForFood("", "", getContext().getResources().getDrawable(R.drawable.add_food)));
                        }
                        add_dinner.setAdapter(new AddDinnerTracker(dinnerList, getContext(), addButtonListenerChart));
                        add_dinner.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

                        dinner_list.setAdapter(new AddDinnerList(dinnerListExpand, getContext(), addButtonListenerChart, foodDetailsListener));
                        dinner_list.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            } catch (Exception exception) {
                Toast.makeText(getActivity(), "please wait", Toast.LENGTH_SHORT).show();
            }
        }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> data = new HashMap<>();

                data.put("clientID",ClientDetails.clientID);
                data.put("date",date_to_display_trackers);

                return data;
            }
        };

        Volley.newRequestQueue(getContext()).add(stringRequest);


    }

    private void setBreakfast(RecyclerView add_breakfast, List<ModelForFood> breakfastList) {
        add_breakfast.setAdapter(new AddBreakfastTracker(breakfastList, getContext(), addButtonListenerChart));
        add_breakfast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setLunch(RecyclerView add_lunch, List<ModelForFood> lunchList) {
        add_lunch.setAdapter(new AddLunch(lunchList, getContext(), addButtonListenerChart));
        add_lunch.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setSnack(RecyclerView add_snack, List<ModelForFood> snackList) {
        add_snack.setAdapter(new AddSnack(snackList, getContext(), addButtonListenerChart));
        add_snack.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setDinner(RecyclerView add_dinner, List<ModelForFood> dinnerList) {
        add_dinner.setAdapter(new AddDinner(dinnerList, getContext(), addButtonListenerChart));
        add_dinner.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setItemOverview(RecyclerView add_breakfast, RecyclerView add_lunch, RecyclerView add_snack, RecyclerView add_dinner, List<ModelForFood> breakfastList, List<ModelForFood> lunchList, List<ModelForFood> snackList, List<ModelForFood> dinnerList) {
        setBreakfast(add_breakfast, breakfastList);
        setLunch(add_lunch, lunchList);
        setSnack(add_snack, snackList);
        setDinner(add_dinner, dinnerList);
    }

    private void setItemList(RecyclerView breakfast_list, RecyclerView lunch_list, RecyclerView snack_list, RecyclerView dinner_list, List<ModelForFood> breakfastListExpand, List<ModelForFood> lunchListExpand, List<ModelForFood> snackListExpand, List<ModelForFood> dinnerListExpand) {

        breakfast_list.setAdapter(new AddBreakfastList(breakfastListExpand, getContext(), addButtonListenerChart, foodDetailsListener));
        breakfast_list.setLayoutManager(new LinearLayoutManager(getContext()));

        lunch_list.setAdapter(new AddLunch(lunchListExpand, getContext(), addButtonListenerChart));
        lunch_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        snack_list.setAdapter(new AddSnack(snackListExpand, getContext(), addButtonListenerChart));
        snack_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        snack_list.setAdapter(new AddBreakfastTracker(dinnerListExpand, getContext(), addButtonListenerChart));
        snack_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }
}