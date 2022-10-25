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

import java.util.ArrayList;
import java.util.List;

public class SeeAllAdapter extends RecyclerView.Adapter<SeeAllAdapter.SeeAllViewHolder> {
    ArrayList<String> obj;
    public SeeAllAdapter(ArrayList<String> obj) {
        this.obj = obj;
    }

    @NonNull
    @Override
    public SeeAllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.see_all_card,parent,false);
        return new SeeAllViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeAllViewHolder holder, int position) {
//        List_Food pos= obj.get(position);
        holder.name.setText(obj.get(position));
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    public class SeeAllViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public SeeAllViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
        }
    }
}