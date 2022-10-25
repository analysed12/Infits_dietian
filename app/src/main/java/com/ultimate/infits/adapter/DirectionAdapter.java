package com.ultimate.infits.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ultimate.infits.R;
import com.ultimate.infits.model.MyDirections;

import java.util.ArrayList;

 public class DirectionAdapter extends RecyclerView.Adapter<DirectionAdapter.MyViewHolder> {

    Context context;
    public static ArrayList<MyDirections> directionList;

   public DirectionAdapter(Context context, ArrayList<MyDirections> directionList){
       this.context=context;
       this.directionList=directionList;
   }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_directions,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyDirections dataobj =directionList.get(position);
        holder.itemName.setText(dataobj.getCookingDirn());
    }

    @Override
    public int getItemCount() {
        return directionList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView itemName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName =itemView.findViewById(R.id.itemMethod);
        }
    }

}
