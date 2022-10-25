package com.ultimate.infits;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.security.acl.LastOwnerException;
import java.util.List;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientListHolder>{
    Context ct;
    List<List_Clients> obj;
   // Selecteditem selecteditem;
    ClientListAdapter(Context ct,List<List_Clients> obj){
        this.ct = ct;
        this.obj=obj;
        //this.selecteditem=selecteditem;
    }

    @NonNull
    @Override
    public ClientListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(ct);
        View view = layoutInflater.inflate(R.layout.client_list_layout,parent,false);
        return new ClientListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientListHolder holder, int position) {
        List_Clients pos= obj.get(position);

//        File imgFile = new File(String.valueOf(pos.getClient_list_image()));

        holder.img.setImageBitmap(obj.get(position).getClient_list_image());

//        if(imgFile.exists()){
//
////            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            holder.img.setImageBitmap(pos.getClient_list_image());
//
//        }
        holder.plan.setText(obj.get(position).getPlan_type());
        holder.name.setText(obj.get(position).getClient_list_name());
        holder.startdate.setText(pos.getClient_list_startdate());
        holder.enddate.setText(pos.getClient_list_enddate());
        holder.itemView.setOnClickListener(v->{
            // here name refers to client ID
                Log.d("ClientListAdapter",pos.getClient_list_name());
                Intent in = new Intent(ct,ClientDetails.class);
                in.putExtra("clientID",pos.getClient_list_name());
//                in.putExtra("startDate",pos.getClient_list_startdate());
//                in.putExtra("endDate",pos.getClient_list_enddate());
                ct.startActivity(in);
            ((Activity)ct).finish();
        });
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }
   // public interface Selecteditem{
     //   void selecteditem(List_Clients obj);
   // }

    public class ClientListHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name,plan,startdate,enddate;
        public ClientListHolder(@NonNull View itemView) {
            super(itemView);
            img= itemView.findViewById(R.id.client_list_profile_pic);
            name=itemView.findViewById(R.id.client_list_profile_name);
            startdate=itemView.findViewById(R.id.client_list_startdate);
            plan=itemView.findViewById(R.id.client_list_plan_name);
            enddate=itemView.findViewById(R.id.client_list_enddate);
           // itemView.setOnClickListener(new View.OnClickListener() {
            //    @Override
             //   public void onClick(View v) {
                //    selectedItem.selecteditem(obj.get(getAdapterPosition()));
             //   }
          //  });

        }
    }
}
