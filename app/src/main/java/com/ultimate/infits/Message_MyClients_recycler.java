package com.ultimate.infits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Message_MyClients_recycler extends RecyclerView.Adapter<Message_MyClients_recycler.Message_MyClientsViewHolder> {
    private Context ct;

    public Message_MyClients_recycler(Context ct) {
        this.ct = ct;
    }

    @NonNull
    @Override
    public Message_MyClients_recycler.Message_MyClientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(ct);
        View v1 =  layoutInflater.inflate(R.layout.message_myclients_recycler,parent,false);
        return new Message_MyClientsViewHolder(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull Message_MyClients_recycler.Message_MyClientsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Message_MyClientsViewHolder extends RecyclerView.ViewHolder {

        public Message_MyClientsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
