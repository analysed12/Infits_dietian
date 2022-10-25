package com.ultimate.infits;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClientHealthFileAdpater extends RecyclerView.Adapter<ClientHealthFileAdpater.ClientHealthFileViewHolder> {

    Context ct;

    ArrayList<String> upload;

    ArrayList<String> filename;

    ArrayList<String> icon;

    FileDialogInterface openDialog;

    ClientHealthFileAdpater(Context ct,ArrayList<String> filename,ArrayList<String> upload,ArrayList<String> icon,FileDialogInterface openDialog){
        this.ct = ct;
        this.upload = upload;
        this.filename = filename;
        this.icon = icon;
        this.openDialog = openDialog;
    }

    @NonNull
    @Override
    public ClientHealthFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View view = inflater.inflate(R.layout.health_report_files_layout,parent,false);
        return new ClientHealthFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientHealthFileViewHolder holder, int position) {

        holder.filename.setText(filename.get(position));
        holder.upload_date.setText(upload.get(position));

        holder.more_icon.setOnClickListener(v->{
            openDialog.openDialog(position);
        });

        if (icon.get(position).equals("pdf")){
            holder.type.setImageDrawable(ct.getDrawable(R.drawable.pdf_icon));
        }
        if (icon.get(position).equals("docx")){
            holder.type.setImageDrawable(ct.getDrawable(R.drawable.word_icon));
        }
        if (icon.get(position).equals("png")|icon.get(position).equals("jpg")|icon.get(position).equals("jpeg")){
            holder.type.setImageDrawable(ct.getDrawable(R.drawable.png_icon));
        }
    }

    @Override
    public int getItemCount() {
        return filename.size();
    }

    class ClientHealthFileViewHolder extends RecyclerView.ViewHolder{

        ImageView more_icon,type;
        TextView filename,upload_date;

        public ClientHealthFileViewHolder(@NonNull View itemView) {
            super(itemView);
            more_icon = itemView.findViewById(R.id.more_health);
            filename = itemView.findViewById(R.id.filename);
            upload_date = itemView.findViewById(R.id.upload_date);
            type = itemView.findViewById(R.id.file_type_icon);
        }
    }
}