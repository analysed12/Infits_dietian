package com.ultimate.infits.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ultimate.infits.R;
import com.ultimate.infits.model.MyIngredients;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyIngredients> Ingredlist;

    public IngredientAdapter(Context context, ArrayList<MyIngredients> Ingredlist){
        this.context=context;
        this.Ingredlist=Ingredlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ingredients,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyIngredients dataobj =Ingredlist.get(position);
        holder.itemName.setText(dataobj.getItemName());
        holder.itemQuantity.setText(dataobj.getItemQuant());
    }

    @Override
    public int getItemCount() {
        return Ingredlist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName =itemView.findViewById(R.id.itemName);
            itemQuantity =itemView.findViewById(R.id.itemQuant);
        }
    }

}
