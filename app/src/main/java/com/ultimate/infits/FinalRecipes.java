package com.ultimate.infits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.ultimate.infits.adapter.FinalRecipeAdapter;
import com.ultimate.infits.adapter.FoodAdapter;
import com.ultimate.infits.model.FoodItems;

import java.util.ArrayList;
import java.util.Arrays;

public class FinalRecipes extends AppCompatActivity {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    FinalRecipeAdapter finalRecipeAdapter;
    TextView tv_newRecipe, calTxt, carbsTxt, proteinsTxt, fatsTxt,tv_cookingTime;
    FoodAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<FoodItems> foodItems = new ArrayList<>();
    ImageView finalImage;
    public static ArrayList<String> directionLists;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_recipes);

        ids();
        tabPlay();
        String name = getIntent().getExtras().getString("name");
        String calories = getIntent().getExtras().getString("cal");
        String carbs = getIntent().getExtras().getString("carbs");
        String proteins = getIntent().getExtras().getString("proteins");
        String fats = getIntent().getExtras().getString("fats");
        String cookingTime = getIntent().getExtras().getString("cooktym");
        String []ingreArray = getIntent().getExtras().getStringArray("ingredArray");
        String []direnArray = getIntent().getExtras().getStringArray("direnArray");
        byte []imgArr = getIntent().getExtras().getByteArray("img");
        Bitmap myImage = BitmapFactory.decodeByteArray(imgArr,0,imgArr.length);
        Log.e("yy",Arrays.toString(ingreArray));
//        Toast.makeText(this, Arrays.toString(ingreArray), Toast.LENGTH_SHORT).show();
        tv_newRecipe.setText(name);
        calTxt.setText(calories);
        carbsTxt.setText(carbs);
        proteinsTxt.setText(proteins);
        fatsTxt.setText(fats);
        tv_cookingTime.setText(cookingTime);
        finalImage.setImageBitmap(myImage);
        directionLists = new ArrayList<>();
        directionLists.add(Arrays.toString(direnArray));

    }

    private void tabPlay() {
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Directions"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        finalRecipeAdapter =new FinalRecipeAdapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(finalRecipeAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }

    private void ids() {
        tabLayout =findViewById(R.id.final_recipe_tab_layout);
        viewPager2 =findViewById(R.id.final_recipe_view_pager);
        tv_newRecipe =findViewById(R.id.tv_newRecipe);
        calTxt =findViewById(R.id.calTxt);
        fatsTxt =findViewById(R.id.fatsTxt);
        proteinsTxt =findViewById(R.id.proteinsTxt);
        carbsTxt =findViewById(R.id.carbsTxt);
        tv_cookingTime=findViewById(R.id.tv_cookingTime);
        finalImage=findViewById(R.id.finalImage);
    }
}