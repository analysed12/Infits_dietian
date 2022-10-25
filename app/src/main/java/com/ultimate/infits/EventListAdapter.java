package com.ultimate.infits;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventListViewHolder> {

    Context con;
    private List<EventList> lst1;

    EventListAdapter(Context con, List<EventList> lst1){
        this.con = con;
        this.lst1 = lst1;
    }

    @NonNull
    @Override
    public EventListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(con);
        View view =  layoutInflater.inflate(R.layout.events_in_profile_layout,parent,false);
        return new EventListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListViewHolder holder, int position) {
        EventList pos= lst1.get(position);
        holder.apt_type.setText(pos.getCal_apt_type());
        holder.apt_name.setText(pos.getCal_apt_clientname());
        holder.apt_time.setText(pos.getCal_apt_time());
        holder.itemView.setOnClickListener(v->{
            // here name refers to client ID
//            Log.d("AppointmentListAdapter",pos.getCal_apt_clientname());
            Intent in = new Intent(con,SelectedAppointment.class);
            in.putExtra("clientID",pos.getCal_apt_clientname());
            in.putExtra("appointment_type",pos.getCal_apt_type());
            in.putExtra("appointment_time",pos.getCal_apt_time());
            in.putExtra("appointment_note",pos.getCal_apt_note());
            in.putExtra("appointment_title",pos.getCal_apt_title());
            in.putExtra("appointment_month",pos.getCal_apt_date_month());
            in.putExtra("appointment_date",pos.getCal_apt_date_date());
            in.putExtra("appointment_duration",pos.getCal_apt_duration());
            in.putExtra("appointment_location",pos.getCal_apt_location());
            in.putExtra("appointment_link",pos.getCal_apt_link());
            if (!pos.getCal_apt_notifyme().isEmpty()){
                in.putExtra("appointment_notifyme",pos.getCal_apt_notifyme());
            }
            con.startActivity(in);
        });
    }

    @Override
    public int getItemCount() {
        return lst1.size();
    }


    public class EventListViewHolder extends RecyclerView.ViewHolder{
        TextView apt_name,apt_type,apt_time;
        public EventListViewHolder(@NonNull View itemView) {
            super(itemView);
            apt_name=itemView.findViewById(R.id.apt_name);
            apt_time=itemView.findViewById(R.id.apt_time);
            apt_type=itemView.findViewById(R.id.apt_type);

        }
    }
}
