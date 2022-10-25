package com.ultimate.infits.fragments;

import static com.ultimate.infits.DataFromDatabase.dietitianuserID;
import static com.ultimate.infits.FoodDetails2.bitmap;
import static com.ultimate.infits.FoodDetails2.getStringOfImage;
import static com.ultimate.infits.fragments.Ingredients.allIngredsName;
import static com.ultimate.infits.fragments.Ingredients.allIngredsQuant;
import static com.ultimate.infits.fragments.RecipeDetails.Carbs;
import static com.ultimate.infits.fragments.RecipeDetails.calories;
import static com.ultimate.infits.fragments.RecipeDetails.chooseCategory;
import static com.ultimate.infits.fragments.RecipeDetails.chooseCourse;
import static com.ultimate.infits.fragments.RecipeDetails.cookingTime;
import static com.ultimate.infits.fragments.RecipeDetails.et_Carbs;
import static com.ultimate.infits.fragments.RecipeDetails.et_calories;
import static com.ultimate.infits.fragments.RecipeDetails.et_cookingTime;
import static com.ultimate.infits.fragments.RecipeDetails.et_fats;
import static com.ultimate.infits.fragments.RecipeDetails.et_fibre;
import static com.ultimate.infits.fragments.RecipeDetails.et_prepTime;
import static com.ultimate.infits.fragments.RecipeDetails.et_proteins;
import static com.ultimate.infits.fragments.RecipeDetails.et_recipeName;
import static com.ultimate.infits.fragments.RecipeDetails.et_servings;
import static com.ultimate.infits.fragments.RecipeDetails.fats;
import static com.ultimate.infits.fragments.RecipeDetails.fibreName;
import static com.ultimate.infits.fragments.RecipeDetails.prepTime;
import static com.ultimate.infits.fragments.RecipeDetails.proteins;
import static com.ultimate.infits.fragments.RecipeDetails.recipeName;
import static com.ultimate.infits.fragments.RecipeDetails.serving;
import static com.ultimate.infits.fragments.RecipeDetails.url;
import static com.ultimate.infits.fragments.RecipeDetails.userId;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ultimate.infits.FoodDetails2;
import com.ultimate.infits.R;
import com.ultimate.infits.Recipies;
import com.ultimate.infits.adapter.DirectionAdapter;
import com.ultimate.infits.model.MyDirections;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Directions extends Fragment {

    LinearLayout ll_addDirection;
    RecyclerView recyclerDirn;
    ArrayList<MyDirections> myDirn;
    DirectionAdapter directionAdapter;
    RequestQueue requestQueue;
    String dirnText;
    LinearLayout ll_upload;
    ImageView link_img;
    TextView finalUpload;
    String mysteps;

    ArrayList<String> addAllDirections=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_directions, container, false);

        ids(view);
        AddDirections();
        RecyclerControl();

        finalUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db(view);
                Intent intent = new Intent(getActivity(), Recipies.class);
                startActivity(intent);
            }
        });

        return view;
    }


    private void db(View view) {
        if (view.getId() == R.id.finalUpload) {
            StringRequest strreq = new StringRequest(Request.Method.POST, url, response -> {
                Toast.makeText(getActivity(), "Database Updated successfully", Toast.LENGTH_SHORT).show();
                System.out.println(response);

            }, error -> Toast.makeText(getActivity(), "Failed to update the database", Toast.LENGTH_SHORT).show()){
                @NotNull
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String> map = new HashMap<>();
                    userId=dietitianuserID;
                    recipeName = et_recipeName.getText().toString();
                    fibreName = et_fibre.getText().toString();
                    cookingTime = et_cookingTime.getText().toString();
                    prepTime = et_prepTime.getText().toString();
                    Carbs = et_Carbs.getText().toString();
                    fats = et_fats.getText().toString();
                    proteins = et_proteins.getText().toString();
                    serving = et_servings.getText().toString();
                    calories = et_calories.getText().toString();

                    map.put("userId",userId);
                    map.put("name", recipeName);
                    map.put("course",chooseCourse);
                    map.put("category", chooseCategory);
                    map.put("preparationTime", prepTime);
                    map.put("cookingTime", cookingTime);
                    map.put("servings", serving);
                    map.put("calories", calories);
                    map.put("protein", proteins);
                    map.put("fats", fats);
                    map.put("carbs", Carbs);
                    map.put("fibres", fibreName);
                    map.put("ingredientquantity",allIngredsQuant.toString());
                    map.put("ingredientname",allIngredsName.toString());
                    map.put("directions",addAllDirections.toString());
                    map.put("image",getStringOfImage(bitmap));
                    map.put("steps",String.valueOf(addAllDirections.size()));

                    return map;
                }
            };

            requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(strreq);
        }
        else{
            Toast.makeText(getActivity(), "myfail", Toast.LENGTH_SHORT).show();
        }
    }

    private void RecyclerControl() {
        myDirn = new ArrayList<>();
        recyclerDirn.setHasFixedSize(true);
        recyclerDirn.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

    }

    private void AddDirections() {
        ll_addDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.add_direction_dialog);
                dialog.getWindow().setBackgroundDrawable(getActivity().getDrawable(R.drawable.alert_bg));
                dialog.getWindow().setLayout(800,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(true);
                dialog.findViewById(R.id.doneBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dirnText ="";
                        TextView recipeMethods =dialog.findViewById(R.id.dirnMethods);
                        if(recipeMethods.getText()==null){
                            Toast.makeText(getActivity(),"Please enter ingredient's name",Toast.LENGTH_LONG).show();
                            return;
                        }else{
                            dirnText =recipeMethods.getText().toString();
                            addAllDirections.add(recipeMethods.getText().toString());
                        }
                        passData(dirnText);
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }

    private void passData(String dirnText) {
        link_img.setVisibility(View.VISIBLE);
        ll_upload.setVisibility(View.VISIBLE);
        recyclerDirn.setVisibility(View.VISIBLE);
        MyDirections obj =new MyDirections();
        obj.setCookingDirn(dirnText);
        myDirn.add(obj);
        directionAdapter =new DirectionAdapter(this.getActivity(),myDirn);
        recyclerDirn.setAdapter(directionAdapter);
    }

    private void ids(View view) {
        ll_addDirection =view.findViewById(R.id.ll_addDirection);
        recyclerDirn =view.findViewById(R.id.recyclerDirn);
        finalUpload =view.findViewById(R.id.finalUpload);
        ll_upload =view.findViewById(R.id.ll_upload);
        link_img =view.findViewById(R.id.link_img);
    }

}