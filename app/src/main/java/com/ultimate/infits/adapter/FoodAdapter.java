package com.ultimate.infits.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ultimate.infits.FinalRecipes;
import com.ultimate.infits.R;
import com.ultimate.infits.Recipies;
import com.ultimate.infits.model.FoodItems;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {

    Context context;
    ArrayList<FoodItems> foodItems;

    public FoodAdapter(Context context,ArrayList<FoodItems> foodItems) {
        this.foodItems=foodItems;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FoodItems fi =foodItems.get(position);
        holder.name.setText(fi.getName());
        holder.cookTime.setText(fi.getCookingTime());
        holder.calories.setText(fi.getCalories());
        holder.steps.setText(fi.getSteps());
//        Glide.with(context).load(fi.getImage()).into(holder.image);
        holder.image.setImageBitmap(fi.getImage());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent inte = new Intent(context.getApplicationContext(),FinalRecipes.class);
                inte.putExtra("name",fi.getName());
                inte.putExtra("cal",fi.getCalories());
                inte.putExtra("carbs",fi.getCarbs());
                inte.putExtra("proteins",fi.getProteins());
                inte.putExtra("fats",fi.getFats());
                inte.putExtra("ingredArray",fi.getIngredientname());
                inte.putExtra("direnArray",fi.getDirections());
                inte.putExtra("img",fi.getImageByte());
                context.startActivity(inte);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,calories,rating,cookTime,steps;
        ImageView image;
            
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.food_name);
            cookTime =itemView.findViewById(R.id.time_to_cook);
            calories =itemView.findViewById(R.id.calories);
            rating =itemView.findViewById(R.id.rating);
            steps =itemView.findViewById(R.id.dirsteps);
            image =itemView.findViewById(R.id.food_image);
        }
    }
}
