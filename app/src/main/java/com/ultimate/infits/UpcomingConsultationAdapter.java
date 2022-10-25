package com.ultimate.infits;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class UpcomingConsultationAdapter extends RecyclerView.Adapter<UpcomingConsultationAdapter.UpcomingConsultationViewHolder> {

    Context ct;
    String mobile;
    private Selecteditem selectedItem;
   // private String date,time,image,name;
    private List<UpcomingConsultations> list1;
    UpcomingConsultationAdapter(Context ct, List<UpcomingConsultations> list1,Selecteditem selecteditem){
        this.ct = ct;
        this.list1=list1;
        this.selectedItem=selecteditem;
    }

    @NonNull
    @Override
    public UpcomingConsultationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view=inflater.inflate(R.layout.dashboard_upcoming_consultations_layout,parent,false);
//        call=view.findViewById(R.id.call);
        return new UpcomingConsultationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingConsultationViewHolder holder, int position) {
        UpcomingConsultations pos=list1.get(position);

        Integer posit= holder.getAbsoluteAdapterPosition();
        String d=pos.getConsultation_date();
        String t=pos.getConsultation_time();
//        String img=pos.getConsultation_patient_image();
        String n=pos.getConsultation_patient();
        mobile = pos.getConsultation_patient_mobile();
//        File imgFile = new File(img);

//        if(imgFile.exists()){
//
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            holder.pimg.setImageBitmap(myBitmap);
//
//        }
        holder.pimg.setImageBitmap(pos.getConsultation_patient_image());
        holder.pdate.setText(d);
        holder.ptime.setText(t);
       // holder.pimg.setImageDrawable(img);
        holder.pname.setText(n);

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("mobile",mobile);
                        holder.call.setBackgroundResource(R.drawable.overlay_corner);
                        // write logic to get phone number of client from the database of client and store it in call string
                        String call_no = list1.get(posit).getConsultation_patient_mobile();
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_DIAL);
                        i.setData(Uri.parse("tel:" + call_no));
                        ct.startActivity(i);
                        selectedItem.selecteditem(list1.get(posit).getConsultation_patient(),list1.get(posit).getConsultation_time());
                    }});
            }
        });
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public interface Selecteditem{
        void selecteditem(String client_n,String time_n);
    }


    class UpcomingConsultationViewHolder extends RecyclerView.ViewHolder{
        TextView pdate,pname,ptime;
        ImageView pimg;
        Button call;
        public UpcomingConsultationViewHolder(@NonNull View itemView) {
            super(itemView);
            pdate= itemView.findViewById(R.id.consultation_date);
            ptime=itemView.findViewById(R.id.consultation_time);
            pimg=itemView.findViewById(R.id.consultation_profile_photo);
            pname=itemView.findViewById(R.id.consultation_profile_name);
            call = itemView.findViewById(R.id.call);


    }
    }
}
