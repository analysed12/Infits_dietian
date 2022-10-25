package com.ultimate.infits;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatLogAdapter extends RecyclerView.Adapter<ChatLogAdapter.ChatLogHolder> {

    Context con;
    List<ChatLogList> l;
    RequestQueue queue;
    String url2 = "http://192.168.141.91/infits/messages2.php";
    DataFromDatabase dataFromDatabase;

    ChatLogAdapter(Context con,List<ChatLogList> l){
        this.con = con;
        this.l=l;
    }

    @NonNull
    @Override
    public ChatLogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(con);
        View view = inflater.inflate(R.layout.chat_log_layout,parent,false);
        return new ChatLogHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatLogHolder holder, int position) {
        ChatLogList  pos= l.get(position);
        holder.profile_pic.setImageBitmap(pos.getProfile_pic());
        if ((l.get(position).getRead() == "u") && (l.get(position).getMsg_by().equals("client"))){
            holder.unread.setVisibility(View.VISIBLE);
        }
        if(l.get(position).getClient_msg().length()>20){
            String m1=l.get(position).getClient_msg().substring(0,21)+".....";
            holder.msg.setText(m1);
        }
        else
        holder.msg.setText(l.get(position).getClient_msg());
            holder.msg_time.setText(l.get(position).getClient_time());
           // holder.profile_pic.setImageBitmap(l.get(position).getProfile_pic());
        holder.name.setText(l.get(position).getClient_name());
            holder.chat_log_view.setOnClickListener(v ->{
                //save to database the message is read
                queue = Volley.newRequestQueue(con);
                StringRequest stringRequest2 = new StringRequest(Request.Method.POST,url2, response -> {
                    if (response.equals("success")){
                        Log.d("ChatArea2","success");
                        Log.d("response ChatArea2",response);
                    }
                    else if (response.equals("failure")){
                        Log.d("ChatArea2","failure");
                    }
                },error -> {
                    Toast.makeText(con,error.toString().trim(),Toast.LENGTH_SHORT).show();})
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("duserID", dataFromDatabase.dietitianuserID);
                        data.put("cuserID",dataFromDatabase.clientuserID);
                        return data;
                    }
                };
                RequestQueue requestQueue2 = Volley.newRequestQueue(con);
                requestQueue2.add(stringRequest2);
                holder.unread.setVisibility(View.GONE);
                l.get(position).setRead("r");
                Intent i=new Intent(con.getApplicationContext(), ChatArea.class);
                DataFromDatabase.clientuserID=l.get(position).getClient_name();
                i.putExtra("client_name",l.get(position).getClient_name());
                con.startActivity(i);
            });
    }

    @Override
    public int getItemCount() {
        return l.size();
    }



    public class ChatLogHolder extends RecyclerView.ViewHolder{
        TextView msg,unread,msg_time,name;
        LinearLayout chat_log_view;
        ImageView profile_pic;
        public ChatLogHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.chat_log_msg);
            unread = itemView.findViewById(R.id.unread);
            msg_time=itemView.findViewById(R.id.chat_log_time);
            name=itemView.findViewById(R.id.chat_log_client_name);
            profile_pic=itemView.findViewById(R.id.chat_log_profile_pic);
            chat_log_view = itemView.findViewById(R.id.chat_log_view);
        }
    }
}
