package com.ultimate.infits;

import static com.ultimate.infits.ChatArea.chat_area_client_name;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ultimate.infits.adapter.ChatMessageAdapter;
import com.ultimate.infits.model.ChatMessage;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Messages_Recycler#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Messages_Recycler extends Fragment {
    List<ChatMessage> cMessages = new ArrayList<>();
    ChatMessageAdapter chatMessageAdapter;
    DataFromDatabase dataFromDatabase;

    List<ChatMessage> msg=new ArrayList<>();
    ChatMessageAdapter ad1;
    String url = String.format("%smessages.php",DataFromDatabase.ipConfig);

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Messages_Recycler() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Messages_Recycler.
     */
    // TODO: Rename and change types and number of parameters
    public static Messages_Recycler newInstance(String param1, String param2) {
        Messages_Recycler fragment = new Messages_Recycler();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_messages__recycler, container, false);
        RecyclerView r1=v.findViewById(R.id.chat_area_message_recycler);
        ProgressBar p1=v.findViewById(R.id.chat_area_loading_status);
        // r1.setAdapter(chatMessageAdapter);
        //setMessages();
       /* for(int i=0;i<3;i++) {
            ChatMessage ch = new ChatMessage(ChatArea.chat_area_client_name,DataFromDatabase.dietitianuserID,"hello","14:00","dietitian","U");
            ch.senderId = chat_area_client_name;
            ch.receiverId = DataFromDatabase.dietitianuserID;
            ch.message ="hi";
            ch.time="14:00";
            cMessages.add(ch);
        }*/
        // chatMessageAdapter.notifyItemInserted(chatMessages.size());
       // chatMessageAdapter= new ChatMessageAdapter(cMessages,ChatArea.chat_area_client_name) ;//add constants.java from video 8

        Log.d("ChatArea", "before");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            if (!response.equals("failure")) {
                Log.d("ChatArea", "success");
                Log.d("response ChatArea", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    String messageby = null;
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String message = jsonObject.getString("message");
                            messageby = jsonObject.getString("messageBy");
                            String time = jsonObject.getString("time").substring(11,16);
//                            String readUnread = jsonObject.getString("read");
//                            ChatMessage obj = new ChatMessage("client_name", DataFromDatabase.dietitianuserID, message, time, messageby,"");
//                            msg.add(obj);
                        }
                        ad1 = new ChatMessageAdapter(this,msg,messageby);
                        r1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//                        r1.smoothScrollToPosition(cMessages.size()-1);
                        r1.setAdapter(ad1);
                        r1.setVisibility(View.VISIBLE);
                        p1.setVisibility(View.GONE);
                    }
                    else{
                        Toast.makeText(getContext(), "No messages", Toast.LENGTH_SHORT).show();
                        p1.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else if (response.equals("failure")) {
                Log.d("ChatArea", "failure");
                Toast.makeText(getContext(), "ChatArea failed", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("duserID", DataFromDatabase.dietitianuserID);
                data.put("cuserID", ClientDetails.clientID);
//                Log.d("clientID", DataFromDatabase.clientuserID);
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
        Log.d("ChatArea", "at end");

    return v;
    }
}