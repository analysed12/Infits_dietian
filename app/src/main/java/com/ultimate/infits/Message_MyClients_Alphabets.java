package com.ultimate.infits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Message_MyClients_Alphabets extends RecyclerView.Adapter<Message_MyClients_Alphabets.Message_MyClients_AlphabetsViewHolder> {
    private Context c;

    public Message_MyClients_Alphabets(Context c) {
        this.c = c;
    }

    @NonNull
    @Override
    public Message_MyClients_Alphabets.Message_MyClients_AlphabetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View view =  layoutInflater.inflate(R.layout.messages_allclients_view,parent,false);
        return new Message_MyClients_AlphabetsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Message_MyClients_Alphabets.Message_MyClients_AlphabetsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Message_MyClients_AlphabetsViewHolder extends RecyclerView.ViewHolder {
        public Message_MyClients_AlphabetsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
