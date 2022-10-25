package com.ultimate.infits;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BreakfastAdapter extends RecyclerView.Adapter<BreakfastAdapter.BreakfastViewHolder> {
    Context con;
    //    OnFoodItemClickListener onFoodItemClickListener;
    List<List_Food> obj;
    int color;
    public BreakfastAdapter(List<List_Food> obj, Context context,int color) {
        this.obj = obj;
        this.con = context;
        this.color = color;
    }

    @NonNull
    @Override
    public BreakfastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(con);
        View view = inflater.inflate(R.layout.breakfast_recepies,parent,false);
        return new BreakfastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreakfastViewHolder holder, int position) {
//        List_Food pos= obj.get(position);
        holder.name.setText(obj.get(position).getName());
        holder.serving.setText(obj.get(position).getServing());
        holder.time.setText(obj.get(position).getTime());
        holder.image.setImageBitmap(obj.get(position).getImage());
        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(con,FoodDetails.class);
                in.putExtra("name",obj.get(position).getName());
                in.putExtra("serving",obj.get(position).getServing());
                in.putExtra("time",obj.get(position).getTime());
                in.putExtra("link",obj.get(position).getLink());
//                nutritions,ingredient,category
                in.putExtra("calories",obj.get(position).getCalories());
                in.putExtra("fats",obj.get(position).getFats());
                in.putExtra("proteins",obj.get(position).getProteins());
                in.putExtra("fibres",obj.get(position).getFibres());
                in.putExtra("carbs",obj.get(position).getCarbs());
                in.putExtra("nutritions",obj.get(position).getNutritions());
                in.putExtra("ingredient",obj.get(position).getIngredient());
                in.putExtra("category",obj.get(position).getCategory());
                in.putExtra("image64",obj.get(position).getImage64());
                in.putExtra("instruction",obj.get(position).getInstruction());
                con.startActivity(in);
            }
        });
        holder.br_card.setCardBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    protected class BreakfastViewHolder extends RecyclerView.ViewHolder {
        TextView name,time,serving;
        ImageView image;
        ImageButton next;
        CardView br_card;
        public BreakfastViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.br_food_name);
            time=view.findViewById(R.id.br_time);
            serving=view.findViewById(R.id.br_serving);
            image=view.findViewById(R.id.br_image);
            next=view.findViewById(R.id.br_next);
            br_card = view.findViewById(R.id.br_card);
        }
    }
}