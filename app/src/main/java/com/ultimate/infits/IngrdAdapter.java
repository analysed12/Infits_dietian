package com.ultimate.infits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IngrdAdapter extends RecyclerView.Adapter<IngrdAdapter.IngrdViewHolder> {

    List<String> ingrd;
    Context con;
    public IngrdAdapter(List<String> ingrd, Context context) {
        this.ingrd=ingrd;
        this.con=context;
    }

    @NonNull
    @Override
    public IngrdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(con);
        View view = inflater.inflate(R.layout.ingrd,parent,false);
        return new IngrdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngrdViewHolder holder, int position) {
//            Ingredients pos = ingrd.get(position);
            holder.ingrdtxt.setText(ingrd.get(position));
    }

    @Override
    public int getItemCount() {
        return ingrd.size();
    }

    protected class IngrdViewHolder extends RecyclerView.ViewHolder {
        TextView ingrdtxt;
        public IngrdViewHolder(@NonNull View itemView) {
            super(itemView);
            ingrdtxt = itemView.findViewById(R.id.ingrdtxt);
        }
    }
}
