package com.ultimate.infits;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class Dashboard_profile_adapter extends RecyclerView.Adapter<Dashboard_profile_adapter.Dashboard_profileViewHolder> {

        Context ct;
// private String date,time,image,name;
    private List<Dashboard_profile_pics> list2;
        Dashboard_profile_adapter(Context ct, List<Dashboard_profile_pics> list1){
        this.ct = ct;
        this.list2=list1;
        }

@NonNull
@Override
public Dashboard_profileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view=inflater.inflate(R.layout.dashboard_profile_recycler,parent,false);
        return new Dashboard_profileViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull Dashboard_profileViewHolder holder, int position) {
        Dashboard_profile_pics pos=list2.get(position);

        holder.pimg.setImageBitmap(pos.getDashboard_photos());
//        String img=pos.getDashboard_photos();
//
//        File imgFile = new File(img);
//
//        if(imgFile.exists()){
//
//        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//        holder.pimg.setImageBitmap(myBitmap);
//
//        }
        }

@Override
public int getItemCount() {
        return list2.size();
        }

class Dashboard_profileViewHolder extends RecyclerView.ViewHolder{

    ImageView pimg;
    public Dashboard_profileViewHolder(@NonNull View itemView) {
        super(itemView);
        pimg=itemView.findViewById(R.id.dashboard_photos);
    }
}
}
