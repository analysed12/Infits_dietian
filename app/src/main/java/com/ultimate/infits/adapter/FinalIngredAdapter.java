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

public class FinalIngredAdapter extends RecyclerView.Adapter<FinalIngredAdapter.MyIngredViewHolder> {

    ArrayList<MyIngredients> myIngredients ;
    Context context;

    FinalIngredAdapter(Context context , ArrayList<MyIngredients> myIngredients){
        this.context=context;
        this.myIngredients=myIngredients;
    }

    @NonNull
    @Override
    public MyIngredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ingredients,parent,false);
        return new FinalIngredAdapter.MyIngredViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyIngredViewHolder holder, int position) {
        MyIngredients dataobj =myIngredients.get(position);
        holder.itemName.setText(dataobj.getItemName());
        holder.itemQuantity.setText(dataobj.getItemQuant());
    }

    @Override
    public int getItemCount() {
        return myIngredients.size();
    }

    public class MyIngredViewHolder extends RecyclerView.ViewHolder {
        TextView itemName,itemQuantity;
        public MyIngredViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName =itemView.findViewById(R.id.itemName);
            itemQuantity =itemView.findViewById(R.id.itemQuant);
        }
    }
}
