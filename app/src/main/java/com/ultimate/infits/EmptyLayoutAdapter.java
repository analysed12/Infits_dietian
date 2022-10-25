package com.ultimate.infits;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class EmptyLayoutAdapter extends RecyclerView.Adapter<EmptyLayoutAdapter.EmptyLayoutViewHolder> {

    Context ct;
    EmptyLayoutAdapter(Context ct){
        this.ct = ct;
    }

    @NonNull
    @Override
    public EmptyLayoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.empty_layout_for_accept_reject_client,parent,false);
        return new EmptyLayoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmptyLayoutViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class EmptyLayoutViewHolder extends RecyclerView.ViewHolder{

        public EmptyLayoutViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }}

