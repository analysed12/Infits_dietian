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

public class AcceptRejectListAdapter extends RecyclerView.Adapter<AcceptRejectListAdapter.AcceptRejectListViewHolder> {


    Context ct;
    List<AcceptRejectList> list2;
    private Selecteditem selectedItem;
    ImageView list_accept;
    AcceptRejectListAdapter(Context ct, List<AcceptRejectList> lst,Selecteditem selecteditem){
        this.ct = ct;
        this.list2=lst;
        this.selectedItem=selecteditem;
    }

    @NonNull
    @Override
    public AcceptRejectListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.accept_reject_recyclerview,parent,false);
        list_accept=view.findViewById(R.id.request_accept);
        return new AcceptRejectListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptRejectListViewHolder holder, int position) {
        AcceptRejectList pos=list2.get(position);

        String img=pos.getClient_image();
        String n=pos.getClient_name();
        String type=pos.getPlan_type();



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

    public interface Selecteditem{
        void selecteditem(AcceptRejectList list_n);
    }

    class AcceptRejectListViewHolder extends RecyclerView.ViewHolder{
        ImageView pimg;
        TextView pname,ptype;
        public AcceptRejectListViewHolder(@NonNull View itemView) {
            super(itemView);
            ptype = itemView.findViewById(R.id.accept_reject_plan_type);
            pimg = itemView.findViewById(R.id.accept_reject_profile_photo);
            pname = itemView.findViewById(R.id.accept_reject_profile_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedItem.selecteditem(list2.get(getAdapterPosition()));

                }
            });

        }
    }}
