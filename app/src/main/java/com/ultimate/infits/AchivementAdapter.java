package com.ultimate.infits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AchivementAdapter extends RecyclerView.Adapter<AchivementAdapter.AchivementViewHolder> {

    Context con;
    AchivementAdapter(Context con){
        this.con = con;
    }

    @NonNull
    @Override
    public AchivementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(con);
        View view = inflater.inflate(R.layout.achivement_card_layout,parent,false);
        return new AchivementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AchivementViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class AchivementViewHolder extends RecyclerView.ViewHolder {
        public AchivementViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
