package com.ultimate.infits.fragments;

import static com.ultimate.infits.DataFromDatabase.dietitianuserID;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ultimate.infits.R;
import com.ultimate.infits.interfaces.FragmentCallbacks;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RecipeDetails extends Fragment {

    TextView nextBtn, tv_course, tv_category;
    BottomSheetDialog btmdialog;

    public static String url = "http://192.168.1.5/infits/createRecipies.php";

    //C:\xampp\htdocs\infits\Ankit\mycreateRecipies.php
    //C:\xampp\htdocs\infits\createRecipies.php

    static EditText et_recipeName,et_calories,et_Carbs,et_fats,et_proteins,et_prepTime,et_cookingTime,et_servings,et_fibre;

    public static String chooseCourse,chooseCategory;

    private FragmentCallbacks callBack;

    public static String recipeName,fibreName,cookingTime,prepTime,serving,calories,proteins,fats,Carbs,userId,imageRes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        ids(view);

        OnClickListener();

        return view;
    }


    private void OnClickListener() {
        et_recipeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_recipeName.setText(et_recipeName.getText().toString());
            }
        });
        et_fibre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fibreName = et_fibre.getText().toString();
                et_fibre.setText(fibreName);
            }
        });
        tv_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCourse(view);
            }
        });
        tv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategory(view);
                tv_category.setText(chooseCategory);
            }
        });
        et_cookingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cookingTime = et_cookingTime.getText().toString();
                et_cookingTime.setText(cookingTime);
            }
        });
        et_prepTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepTime = et_prepTime.getText().toString();
                et_prepTime.setText(prepTime);
            }
        });
        et_servings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serving = et_servings.getText().toString();
                et_servings.setText(serving);
            }
        });
        et_calories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calories = et_calories.getText().toString();
                et_calories.setText(calories);
            }
        });
        et_proteins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proteins = et_proteins.getText().toString();
                et_proteins.setText(proteins);
            }
        });
        et_fats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fats = et_fats.getText().toString();
                et_fats.setText(fats);
            }
        });
        et_Carbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carbs = et_Carbs.getText().toString();
                et_Carbs.setText(Carbs);
            }
        });
        //    private void getDetails(){
//
//        if (true){
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        JSONObject object =new JSONObject();
//                        String dietitianuserID= object.getString("dietitianuserID");
//                        String name=object.getString("name");
//                        String qualification=object.getString("qualification");
//                        String email=object.getString("email");
//                        String mobile=object.getString("mobile");
//                        String profilePhoto=object.getString("profilePhoto");
//                        String location=object.getString("location");
//                        String age= object.getString("age");
//                        String gender = object.getString("gender");
//
//                        map.put("userID",dietitianuserID);
//                        map.put("name",name);
//                        map.put("qualification",qualification);
//                        map.put("email",email);
//                        map.put("mobile",mobile);
//                        map.put("profilePhoto",profilePhoto);
//                        map.put("location",location);
//                        map.put("age",age);
//                        map.put("gender",gender);
//                        Log.i("data",dietitianuserID+" "+name+" "+qualification+" "+email+" "+
//                                mobile+" "+profilePhoto+" "+location+" "+age+" "+gender);
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
    //                    Toast.makeText(null,error.toString(),Toast.LENGTH_SHORT).show();
//                }
//            });
//            Volley.newRequestQueue(null).add(stringRequest);
//        }
//        else {
//            Log.i("Flag token", String.valueOf(flag));
//        }
//
//    }
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                db(view);
                nextPage();

            }

        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callBack = (FragmentCallbacks) context;
        } catch (ClassCastException e) {
           e.printStackTrace();
        }
    }

    public void nextPage() {
        if (callBack != null) {
            callBack.goTo(1);
        }
    }

    private void showCategory(View view) {
        btmdialog = new BottomSheetDialog(view.getContext());
        btmdialog.setContentView(R.layout.category_bottomsheet);

        RadioGroup rcg1 = btmdialog.findViewById(R.id.rcg1);
        RadioGroup rcg2 = btmdialog.findViewById(R.id.rcg2);
        RadioGroup rcg3 = btmdialog.findViewById(R.id.rcg3);
        RadioGroup rcg4 = btmdialog.findViewById(R.id.rcg4);
        RadioGroup rcg5 = btmdialog.findViewById(R.id.rcg5);


        rcg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rg1) {
                    chooseCategory = "Pancake";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else if (checkedId == R.id.rg2) {
                    chooseCategory ="Croissants";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else if (checkedId == R.id.rg3) {
                    chooseCategory ="Butter bread";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                }

            }
        });
        rcg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rg4) {
                    chooseCategory ="Poha";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else if (checkedId == R.id.rg5) {
                    chooseCategory ="Omellete";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else if (checkedId == R.id.rg6) {
                    chooseCategory ="Sandwich";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                }
            }
        });
        rcg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rg7) {
                    chooseCategory ="Waffles";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else if (checkedId == R.id.rg8) {
                    chooseCategory ="Crepes";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else if (checkedId == R.id.rg9) {
                    chooseCategory ="Juice";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                }
            }
        });
        rcg4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rg10) {
                    chooseCategory ="Paratha";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else if (checkedId == R.id.rg11) {
                    chooseCategory ="Oatmeal";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else if (checkedId == R.id.rg12) {
                    chooseCategory ="Coffee";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                }
            }
        });
        rcg5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rg13) {
                    chooseCategory ="Upma";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else if (checkedId == R.id.rg8) {
                    chooseCategory ="+ Add more";
                    tv_category.setText(chooseCategory);
                    tv_category.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                }
            }
        });

        btmdialog.show();
    }
    private void showCourse(View view) {
        btmdialog = new BottomSheetDialog(view.getContext());
        btmdialog.setContentView(R.layout.course_bottomsheet);

        RadioGroup rg1 = btmdialog.findViewById(R.id.rg1);
        RadioGroup rg2 = btmdialog.findViewById(R.id.rg2);
        RadioGroup rg3 = btmdialog.findViewById(R.id.rg3);


        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.r1) {
                    chooseCourse = "Breakfast";
                    tv_course.setText(chooseCourse);
                    tv_course.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else if (checkedId == R.id.r2) {
                    chooseCourse ="Elevenses(11am)";
                    tv_course.setText(chooseCourse);
                    tv_course.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else{
                    chooseCourse ="Lunch";
                    tv_course.setText(chooseCourse);
                    tv_course.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                }
            }
        });
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.r4) {
                    chooseCourse ="Snack";
                    tv_course.setText(chooseCourse);
                    tv_course.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else if (checkedId == R.id.r5) {
                    chooseCourse ="Tea";
                    tv_course.setText(chooseCourse);
                    tv_course.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                } else if(checkedId == R.id.r6){
                    chooseCourse ="Dinner";
                    tv_course.setText(chooseCourse);
                    tv_course.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                }
            }
        });
        rg3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.r7) {
                    chooseCourse ="Others";
                    tv_course.setText(chooseCourse);
                    tv_course.setTextColor(Color.parseColor("#000000"));
                    btmdialog.cancel();
                }
            }
        });

        btmdialog.show();
    }
    private void ids(View view) {

        et_recipeName =view.findViewById(R.id.et_recipeName);
        et_calories =view.findViewById(R.id.et_calories);
        et_Carbs =view.findViewById(R.id.et_Carbs);
        et_fats =view.findViewById(R.id.et_fats);
        et_proteins =view.findViewById(R.id.et_proteins);
        et_fibre =view.findViewById(R.id.et_fibre);

        tv_course = view.findViewById(R.id.tv_course);
        tv_category = view.findViewById(R.id.tv_category);

        et_prepTime = view.findViewById(R.id.et_prepTime);
        et_cookingTime = view.findViewById(R.id.et_cookingTime);
        et_servings = view.findViewById(R.id.et_servings);

        nextBtn =view.findViewById(R.id.nextBtn);

    }

}