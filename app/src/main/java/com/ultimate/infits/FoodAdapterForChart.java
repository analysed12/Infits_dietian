package com.ultimate.infits;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapterForChart extends RecyclerView.Adapter<FoodAdapterForChart.FoodAdapterViewHolder> {

    List<ModelForFood> listForFood;
    AddButtonListenerChart addButtonListenerChart;
    FoodDetailsListener foodDetailsListener;
    String meal;

    FoodAdapterForChart(List<ModelForFood> listForFood,AddButtonListenerChart addButtonListenerChart,FoodDetailsListener foodDetailsListener,String meal){
        this.listForFood = listForFood;
        this.addButtonListenerChart = addButtonListenerChart;
        this.foodDetailsListener = foodDetailsListener;
        this.meal  = meal;
    }
    @NonNull
    @Override
    public FoodAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new FoodAdapterViewHolder(inflater.inflate(R.layout.food_item_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapterViewHolder holder, int position) {
        holder.photo.setImageDrawable(listForFood.get(position).getPhoto());
        holder.name.setText(listForFood.get(position).getName());
        holder.calories.setText(listForFood.get(position).getCalorie() + " per 100g");
        holder.addFoodToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    addButtonListenerChart.addButtonOnClick(listForFood.get(position).getName(),listForFood.get(position).getCalorie(),listForFood.get(position).getDescription(),listForFood.get(position).getNutrients(),listForFood.get(position).getIngredients(),listForFood.get(position).getPhoto(),"",listForFood.get(position).getPreparation(),meal);
            }
        });
        holder.food_item.setOnClickListener(v->{
            //String name, String calorie, String description, String nutrients, String ingredients, Drawable photo, String category, String preparation
                foodDetailsListener.setDetails(listForFood.get(position).getName(),listForFood.get(position).getCalorie(),listForFood.get(position).getDescription(),listForFood.get(position).getNutrients(),listForFood.get(position).getIngredients(),listForFood.get(position).getPhoto(),"",listForFood.get(position).getPreparation());
        });
    }

    @Override
    public int getItemCount() {
        return listForFood.size();
    }

    public class FoodAdapterViewHolder extends RecyclerView.ViewHolder {
        LinearLayout food_item;
        ImageView photo;
        TextView name, calories;
        Button addFoodToList;
        public FoodAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            addFoodToList = itemView.findViewById(R.id.add_food);
            name = itemView.findViewById(R.id.name);
            calories = itemView.findViewById(R.id.calorie);
            photo = itemView.findViewById(R.id.photo);
            food_item = itemView.findViewById(R.id.food_item);
        }
    }
}