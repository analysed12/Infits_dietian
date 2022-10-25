package com.ultimate.infits;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LoadTemplateAdapter extends RecyclerView.Adapter<LoadTemplateAdapter.LoadTemplateViewHolder> {

    LoadTemplateInterface loadTemplateInterface;

    ArrayList<String> name;
    Context ct;

    LoadTemplateAdapter(ArrayList<String> name,Context ct,LoadTemplateInterface loadTemplateInterface){
        this.name = name;
        this.ct = ct;
        this.loadTemplateInterface = loadTemplateInterface;
    }

    @NonNull
    @Override
    public LoadTemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LoadTemplateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.load_template_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull LoadTemplateViewHolder holder, int position) {
        holder.name.setText(name.get(position));
        holder.load.setOnClickListener(v->{
            loadTemplateInterface.loadTemplateInterface(position);
            System.out.println("HI");
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class LoadTemplateViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView load;

        public LoadTemplateViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);

            load = itemView.findViewById(R.id.load);

        }
    }
}