package com.ultimate.infits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EndStreamingLive extends RecyclerView.Adapter<EndStreamingLive.EndStreamingLiveViewHolder> {
    Context context;

    ArrayList<String> endTitle = new ArrayList<>();
    ArrayList<String> endDate = new ArrayList<>();
    ArrayList<String> endTime = new ArrayList<>();
    ArrayList<String> endNote = new ArrayList<>();

    EndStreamingLive(Context context, ArrayList<String> endTitle, ArrayList<String> endDate, ArrayList<String> endTime, ArrayList<String> endNote){
        this.context = context;
        this.endTitle = endTitle;
        this.endDate = endDate;
        this.endNote = endNote;
        this.endTime = endTime;
    }
    @NonNull
    @Override
    public EndStreamingLiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ending_list_item,parent,false);
        return new EndStreamingLiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EndStreamingLiveViewHolder holder, int position) {
        holder.endTitle.setText(endTitle.get(position));
        holder.endNotes.setText(endNote.get(position));
        holder.endTime.setText(endTime.get(position));
        holder.endDate.setText(endDate.get(position));
        holder.doctor.setImageBitmap(DataFromDatabase.profile);
    }

    @Override
    public int getItemCount() {
        return endTitle.size();
    }

    public class EndStreamingLiveViewHolder extends RecyclerView.ViewHolder {
        TextView endTitle,endNotes,endDate,endTime;
        ImageView doctor;
        public EndStreamingLiveViewHolder(@NonNull View itemView) {
            super(itemView);
            endNotes = itemView.findViewById(R.id.notes);
            endTitle = itemView.findViewById(R.id.title);
            endDate = itemView.findViewById(R.id.date);
            endTime = itemView.findViewById(R.id.clock);
            doctor = itemView.findViewById(R.id.dietian);
        }
    }
}

