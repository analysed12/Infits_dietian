package com.ultimate.infits.adapter;

import static com.ultimate.infits.FinalRecipes.directionLists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ultimate.infits.R;
import com.ultimate.infits.fragments.FinalRecipeDirectionsFragment;
import com.ultimate.infits.model.MyDirections;

import java.util.ArrayList;

public class FinalDirectionAdapter extends RecyclerView.Adapter<FinalDirectionAdapter.myDirectionViewHolder> {

    FinalRecipeDirectionsFragment context;
    ArrayList<MyDirections> finalDirnList;

    public FinalDirectionAdapter(FinalRecipeDirectionsFragment context, ArrayList<MyDirections> finalDirnList) {
        this.context = context;
        this.finalDirnList = finalDirnList;
    }

    @NonNull
    @Override
    public myDirectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_directions,parent,false);
        return new myDirectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myDirectionViewHolder holder, int position) {
        holder.itemMethod.setText(directionLists.get(position));
    }


    @Override
    public int getItemCount() {
        return finalDirnList.size();
    }

    public class myDirectionViewHolder extends RecyclerView.ViewHolder{
        TextView itemMethod;
        public myDirectionViewHolder(@NonNull View itemView) {
            super(itemView);
            itemMethod =itemView.findViewById(R.id.itemMethod);
        }
    }
}
