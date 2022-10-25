package com.ultimate.infits.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.ultimate.infits.ChatArea;
import com.ultimate.infits.DataFromDatabase;
import com.ultimate.infits.DownloadFileForMessages;
import com.ultimate.infits.Messages_Recycler;
import com.ultimate.infits.R;
import com.ultimate.infits.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {


    private static List<ChatMessage> data =null;
    Messages_Recycler context;
    static ArrayList<ChatMessage> selectList ;
    static boolean isEnabled =false;
    static boolean isSelectAll =false;
    public static ChatMessage model;
    private String senderID="";
    static ClipboardManager clipboardManager;
    ClipData myClip;
    String copiedMsg;
    static DownloadFileForMessages downloadFileForMessages;
    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVE = 2;

    public ChatMessageAdapter(List<ChatMessage> data, String senderID,DownloadFileForMessages downloadFileForMessages) {
        this.data = data;
        this.senderID = senderID;
        this.downloadFileForMessages = downloadFileForMessages;
    }

    public ChatMessageAdapter(Messages_Recycler context, List<ChatMessage> data, String senderID) {
        this.context=context;
        this.senderID=senderID;
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatareadietitianmessage,parent,false);
//            model = ViewModelProviders.of(context).get(ChatMessage.class);
            return new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatareaclientmessage,parent,false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            setData(data.get(position),holder);
        }else{
//            setData(data.get(position),holder);
        }
    }

    private void setData(ChatMessage chatMessage, ViewHolder holder) {
        selectList =new ArrayList<>();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEnabled) {
                    ClickItem(holder);
                } else {
//                    Toast.makeText(context, "\nYou clicked" + data.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {

                if (!isEnabled) {
                    ActionMode.Callback callback = new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                            MenuInflater menuInflater = actionMode.getMenuInflater();
                            menuInflater.inflate(R.menu.menu_chat_option, menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                            isEnabled = true;
                            ClickItem(holder);
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                            int id = menuItem.getItemId();
                            switch (id) {
                                case R.id.menuDelete:
                                    for (ChatMessage s : selectList) {
                                        data.remove(s);
                                    }
                                    actionMode.finish();
                                    break;
                                case R.id.menuCopy:
                                    clipboardManager = (ClipboardManager) context.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                    copiedMsg = holder.dietitian_msg.getText().toString();
                                    myClip = ClipData.newPlainText("text", copiedMsg);
                                    clipboardManager.setPrimaryClip(myClip);
                                    Toast.makeText(context.getContext(), "Text Copied", Toast.LENGTH_SHORT).show();

//                                    if (selectList.size()==data.size()){
//                                        isSelectAll=false;
//                                        selectList.clear();
//                                    }else{
//                                        isSelectAll=true;
//                                        selectList.clear();
//                                        selectList.addAll(data);
//                                    }
                                    model.SetText(String.valueOf(selectList.size()));
                                    break;
                            }
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode actionMode) {
                            isEnabled = false;
                            isSelectAll = false;
                            selectList.clear();
                        }
                    };
                    ((AppCompatActivity) view.getContext()).startActionMode(callback);
                } else {
                    ClickItem(holder);
                }
                return true;
            }
        });

        if (isSelectAll) {
            holder.checkBoxD.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.BLUE);
        } else {
            holder.checkBoxD.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }


//            if (chatMessage.getType().equals("jpeg")) {
//                System.out.println("Hola");
//                holder.attachment.setVisibility(View.VISIBLE);
//                holder.files.setVisibility(View.GONE);
//                holder.dietitian_msg.setVisibility(View.GONE);
//                byte[] qrimage = Base64.decode(chatMessage.message, 0);
//                holder.attachment.setImageBitmap(BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length));
//            }
//            if (chatMessage.getType().equals("jpg")) {
//                holder.attachment.setVisibility(View.VISIBLE);
//                holder.files.setVisibility(View.GONE);
//                holder.dietitian_msg.setVisibility(View.GONE);
//                byte[] qrimage = Base64.decode(chatMessage.message, 0);
//                holder.attachment.setImageBitmap(BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length));
//            }
//            if (chatMessage.getType().equals("png")) {
//                holder.attachment.setVisibility(View.VISIBLE);
//                holder.files.setVisibility(View.GONE);
//                holder.dietitian_msg.setVisibility(View.GONE);
//                byte[] qrimage = Base64.decode(chatMessage.message, 0);
//                holder.attachment.setImageBitmap(BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length));
//            }
//            if (chatMessage.getType().equals("pdf")) {
//                holder.attachment.setVisibility(View.GONE);
//                holder.dietitian_msg.setVisibility(View.GONE);
//                holder.files.setVisibility(View.VISIBLE);
//                holder.filename.setText(chatMessage.filename);
//            }
//            if (chatMessage.getType().equals("text")) {
//                System.out.println("Hola");
//                holder.dietitian_msg.setText(chatMessage.message);
////                holder.dietitianMsgTime.setText(chatMessage.time);
//                holder.attachmentD.setVisibility(View.GONE);
//                holder.filesD.setVisibility(View.GONE);
//                holder.dietitian_msg.setVisibility(View.VISIBLE);
//            }
//                holder.dietitian_msg.setOnClickListener(v->{
//                System.out.println("Hola");
//            });
//                holder.filenameD.setOnClickListener(v->{
//                System.out.println("Hi   "+chatMessage.filename);
//                downloadFileForMessages.downloadFile(DataFromDatabase.dietitianuserID+ ChatArea.chat_area_client_name+chatMessage.filename);
//            });
//            }

    }

    private void ClickItem(ViewHolder holder) {
        ChatMessage s=data.get(holder.getAdapterPosition());
        if (holder.checkBoxD.getVisibility()==View.GONE){
            holder.checkBoxD.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(Color.BLUE);
            selectList.add(s);
        }else{
            holder.checkBoxD.setVisibility(View.GONE);
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            selectList.remove(s);
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//
//        if (getItemViewType(position) == VIEW_TYPE_SENT) {
//            ((SentMessageViewHolder) holder).setData(data.get(position),holder);
//            ((SentMessageViewHolder) holder).binding1.files.setOnClickListener(v -> {
//
//            });
//        } else {
//            ((ReceivedMessageViewHolder) holder).setData(data.get(position));
//        }
//    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (data.get(position).messageBy.equals("client"))
            return VIEW_TYPE_RECEIVE;
        else
            return VIEW_TYPE_SENT;
    }


//    class SentMessageViewHolder extends RecyclerView.ViewHolder {
////        private final ChatareadietitianmessageBinding binding1;
////        ChatMessage model = ViewModelProviders.of(context).get(ChatMessage.class);
////        SentMessageViewHolder(ChatareadietitianmessageBinding chatAreaDietitianMessageBinding) {
////            super(chatAreaDietitianMessageBinding.getRoot());
////            binding1 = chatAreaDietitianMessageBinding;
//////            model =  ViewModelProviders.of(context).get(ChatMessage.class);
////        }
//
//        private void ClickItem(RecyclerView.ViewHolder holder,ChatareadietitianmessageBinding binding1) {
//
//            ChatMessage s=data.get(holder.getAdapterPosition());
////            s =ViewModelProviders.of(context).get(ChatMessage.class);
//            if (binding1.checkBox.getVisibility()==View.GONE){
//                binding1.checkBox.setVisibility(View.VISIBLE);
//                holder.itemView.setBackgroundColor(Color.BLUE);
//                selectList.add(s);
//            }else{
//                binding1.checkBox.setVisibility(View.GONE);
//                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
//                selectList.remove(s);
//            }
//            model.SetText(String.valueOf(selectList.size()));
//
//        }

//        void setData(ChatMessage chatMessage,@NonNull RecyclerView.ViewHolder holder) {
//
//            binding1.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    if (!isEnabled){
//                        ActionMode.Callback callback =new ActionMode.Callback() {
//                            @Override
//                            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
//                                MenuInflater menuInflater =actionMode.getMenuInflater();
//                                menuInflater.inflate(R.menu.menu_chat_option,menu);
//                                return true;
//                            }
//
//                            @Override
//                            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
//                                isEnabled=true;
//                                ClickItem(holder,binding1);
//                                chatMessage.getText().observe( context, new Observer<String>() {
//                                    @Override
//                                    public void onChanged(String s) {
//                                        actionMode.setTitle(String.format("%s Selected",s));
//                                    }
//                                });
//                                return true;
//                            }
//
//                            @Override
//                            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
//                                int id =   menuItem.getItemId();
//                                switch (id){
//                                    case R.id.menuDelete:
//                                        for (ChatMessage s:selectList){
//                                            data.remove(s);
//                                        }
//                                        actionMode.finish();
//                                        break;
//                                    case  R.id.menuCopy:
//                                        clipboardManager= (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
//                                        copiedMsg= binding1.dietitianMsg.getText().toString();
//                                        myClip = ClipData.newPlainText("text",copiedMsg);
//                                        clipboardManager.setPrimaryClip(myClip);
//                                        Toast.makeText(context,"Text Copied",Toast.LENGTH_SHORT).show();
//
////                                    if (selectList.size()==data.size()){
////                                        isSelectAll=false;
////                                        selectList.clear();
////                                    }else{
////                                        isSelectAll=true;
////                                        selectList.clear();
////                                        selectList.addAll(data);
////                                    }
////                                    model.SetText(String.valueOf(selectList.size()));
//                                        break;
//                                }
//                                return true;
//                            }
//
//                            @Override
//                            public void onDestroyActionMode(ActionMode actionMode) {
//                                isEnabled=false;
//                                isSelectAll=false;
//                                selectList.clear();
//                            }
//                        };
//                        ((AppCompatActivity) view.getContext()).startActionMode(callback);
//                    }else{
//                        ClickItem(holder,binding1);
//                    }
//                    return true;
//                }
//            });
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (isEnabled){
//                        ClickItem(holder,binding1);
//                    }else{
//                        Toast.makeText(context, "\nYou clicked"+data.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//
//            if (isSelectAll){
//                binding1.checkBox.setVisibility(View.VISIBLE);
//                holder.itemView.setBackgroundColor(Color.BLUE);
//            }else{
//                binding1.checkBox.setVisibility(View.GONE);
//                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
//            }
//
//            if (chatMessage.getType().equals("jpeg")) {
//                System.out.println("Hola");
//                binding1.attachment.setVisibility(View.VISIBLE);
//                binding1.files.setVisibility(View.GONE);
//                binding1.dietitianMsg.setVisibility(View.GONE);
//                byte[] qrimage = Base64.decode(chatMessage.message, 0);
//                binding1.attachment.setImageBitmap(BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length));
//            }
//            if (chatMessage.getType().equals("jpg")) {
//                binding1.attachment.setVisibility(View.VISIBLE);
//                binding1.files.setVisibility(View.GONE);
//                binding1.dietitianMsg.setVisibility(View.GONE);
//                byte[] qrimage = Base64.decode(chatMessage.message, 0);
//                binding1.attachment.setImageBitmap(BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length));
//            }
//            if (chatMessage.getType().equals("png")) {
//                binding1.attachment.setVisibility(View.VISIBLE);
//                binding1.files.setVisibility(View.GONE);
//                binding1.dietitianMsg.setVisibility(View.GONE);
//                byte[] qrimage = Base64.decode(chatMessage.message, 0);
//                binding1.attachment.setImageBitmap(BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length));
//            }
//            if (chatMessage.getType().equals("pdf")) {
//                binding1.attachment.setVisibility(View.GONE);
//                binding1.dietitianMsg.setVisibility(View.GONE);
//                binding1.files.setVisibility(View.VISIBLE);
//                binding1.filename.setText(chatMessage.filename);
//            }
//            if (chatMessage.getType().equals("text")) {
//                System.out.println("Hola");
//                binding1.dietitianMsg.setText(chatMessage.message);
//                binding1.dietitianMsgTime.setText(chatMessage.time);
//                binding1.attachment.setVisibility(View.GONE);
//                binding1.files.setVisibility(View.GONE);
//                binding1.dietitianMsg.setVisibility(View.VISIBLE);
//            }
//            binding1.dietitianMsg.setOnClickListener(v->{
//                System.out.println("Hola");
//            });
//            binding1.filename.setOnClickListener(v->{
//                System.out.println("Hi   "+chatMessage.filename);
//                downloadFileForMessages.downloadFile(DataFromDatabase.dietitianuserID+ ChatArea.chat_area_client_name+chatMessage.filename);
//            });
//        }
//    }


//    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
//        private final ChatareaclientmessageBinding binding2;
//
//        ReceivedMessageViewHolder(ChatareaclientmessageBinding chatAreaClientMessageBinding) {
//            super(chatAreaClientMessageBinding.getRoot());
//            binding2 = chatAreaClientMessageBinding;
////            model = ViewModelProviders.of(context).get(ChatMessage.class);
//        }
//
//        void setData(ChatMessage chatMessage) {
//            if (chatMessage.getType().equals("jpeg")) {
//                binding2.attachment.setVisibility(View.VISIBLE);
//                binding2.clientMsg.setVisibility(View.GONE);
//                byte[] qrimage = Base64.decode(chatMessage.message, 0);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(qrimage, 0, qrimage.length);
//                binding2.attachment.setImageBitmap(bitmap);
//                binding2.attachment.setOnClickListener(v -> {
//                    System.out.println("Hi");
//                });
//            }
//            if (chatMessage.getType().equals("pdf")) {
//                binding2.attachment.setVisibility(View.GONE);
//                binding2.clientMsg.setVisibility(View.GONE);
//                binding2.files.setVisibility(View.VISIBLE);
//                binding2.files.setOnClickListener(v -> {
//                    System.out.println("Hi");
//                    downloadFileForMessages.downloadFile(DataFromDatabase.dietitianuserID+ChatArea.chat_area_client_name+chatMessage.filename);
//                });
//                binding2.filename.setText(chatMessage.filename);
//            }
//            if (chatMessage.getType().equals("text")) {
//                binding2.attachment.setVisibility(View.GONE);
//                binding2.clientMsg.setVisibility(View.VISIBLE);
//                binding2.files.setVisibility(View.GONE);
//                binding2.clientMsg.setText(chatMessage.message);
//                binding2.clientMsgTime.setText(chatMessage.time);
//            }
//        }
//    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView attachment,type,typeD,attachmentD;
        TextView client_msg,filename,client_msg_time,dietitian_msg_time,dietitian_msg,filenameD;
        LinearLayout files,filesD;
        CheckBox checkBoxD,checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            attachment =itemView.findViewById(R.id.attachment);
            attachmentD =itemView.findViewById(R.id.attachmentD);
            client_msg =itemView.findViewById(R.id.client_msg);
            files =itemView.findViewById(R.id.files);
            dietitian_msg =itemView.findViewById(R.id.dietitian_msg);
            type =itemView.findViewById(R.id.type);
            typeD =itemView.findViewById(R.id.typeD);
            filename =itemView.findViewById(R.id.filename);
            filenameD =itemView.findViewById(R.id.filenameD);
            checkBoxD =itemView.findViewById(R.id.checkBoxD);
            checkBox =itemView.findViewById(R.id.checkBox);
            filesD =itemView.findViewById(R.id.filesD);
            client_msg_time =itemView.findViewById(R.id.client_msg_time);
            dietitian_msg_time =itemView.findViewById(R.id.dietitian_msg_time);
        }
    }
}