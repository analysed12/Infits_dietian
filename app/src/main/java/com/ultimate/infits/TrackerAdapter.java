package com.ultimate.infits;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class TrackerAdapter extends RecyclerView.Adapter<TrackerAdapter.TrackerViewHolder> {

    Context con;
    //    OnFoodItemClickListener onFoodItemClickListener;
    String[] foodTiming;
    TrackerAdapter(String[] foodTiming, Context con){
        this.foodTiming = foodTiming;
        this.con = con;
//        this.onFoodItemClickListener = onFoodItemClickListener;
    }

    @NonNull
    @Override
    public TrackerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(con);
        View view = inflater.inflate(R.layout.dietchart_layout,parent,false);
        return new TrackerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerViewHolder holder, int position) {
//        holder.time.setText(foodTiming[position]);
        holder.card.setOnClickListener(v->{
            Log.d("Click", "onBindViewHolder: ");
            if (holder.foodList.getVisibility() == View.VISIBLE){
                holder.foodList.setVisibility(View.GONE);
                holder.imgOverview.setVisibility(View.VISIBLE);
            }
            else{
                holder.foodList.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodTiming.length;
    }

//    public interface OnFoodItemClickListener{
//        void onFoodItemClick(int position);
//    }

    public class TrackerViewHolder extends RecyclerView.ViewHolder{
        TextView time,cal;
        CardView card;
        CardView foodList,imgOverview;
        //        OnFoodItemClickListener onFoodItemClickListener;
        public TrackerViewHolder(@NonNull View itemView) {
            super(itemView);
//            this.onFoodItemClickListener = onFoodItemClickListener;
//            time = itemView.findViewById(R.id.time);
            foodList = itemView.findViewById(R.id.food_list);
            imgOverview = itemView.findViewById(R.id.img_list_overview);
            card = itemView.findViewById(R.id.card);
//            itemView.setOnClickListener(this);
        }

//        @\Override
//        public void onClick(View v) {
//            onFoodItemClickListener.onFoodItemClick(getLayoutPosition());
//        }
    }
}
