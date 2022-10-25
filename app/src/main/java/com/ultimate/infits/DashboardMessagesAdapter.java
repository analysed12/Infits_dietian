package com.ultimate.infits;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class DashboardMessagesAdapter extends RecyclerView.Adapter<DashboardMessagesAdapter.DashboardMessagesViewHolder> {
    Context ct;
    List<DashboardMessages> list2;
    DashboardMessagesAdapter(Context ct, List<DashboardMessages> lst){
        this.ct = ct;
        this.list2=lst;
    }

    @NonNull
    @Override
    public DashboardMessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.dashboard_messages,parent,false);
        Button enq_message= view.findViewById(R.id.enquiry_message);
        enq_message.setOnClickListener(this::return_message_Area);
        return new DashboardMessagesViewHolder(view);
    }

        public void return_message_Area(View v) {
        Bundle b=new Bundle();
        b.putString("client_name","ronald richard");
        ct.startActivity(new Intent(ct.getApplicationContext(),ChatArea.class));
        }
    @Override
    public void onBindViewHolder(@NonNull DashboardMessagesViewHolder holder, int position) {
        DashboardMessages pos=list2.get(position);

        String img=pos.getConsultation_patient_image();
        String n=pos.getConsultation_patient_name();
        String type=pos.getConsultation_type();

        File imgFile = new File(img);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.pimg.setImageBitmap(myBitmap);

        }
        holder.ptype.setText(type);
        // holder.pimg.setImageDrawable(img);
        holder.pname.setText(n);

    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    class DashboardMessagesViewHolder extends RecyclerView.ViewHolder{
        ImageView pimg;
        TextView pname,ptype;
        public DashboardMessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            ptype= itemView.findViewById(R.id.enquiry_consultation_type);
            pimg=itemView.findViewById(R.id.enquiry_profile_photo);
            pname=itemView.findViewById(R.id.enquiry_profile_name);

        }
    }

}
