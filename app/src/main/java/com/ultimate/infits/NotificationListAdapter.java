package com.ultimate.infits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.NotificationListViewHolder> {
    Context con;
    NotificationListAdapter(Context con){
        this.con = con;
    }
    @NonNull
    @Override
    public NotificationListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(con);
        View view = inflater.inflate(R.layout.notification_list,parent,false);
        return new NotificationListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class NotificationListViewHolder extends RecyclerView.ViewHolder {
        CardView cd;
        public NotificationListViewHolder(@NonNull View itemView) {
            super(itemView);
//            cd = itemView.findViewById(R.id.notification_background);
        }
    }
}
