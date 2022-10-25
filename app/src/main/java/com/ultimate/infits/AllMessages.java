package com.ultimate.infits;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllMessages extends Fragment {

    RadioButton all,unread;
    RecyclerView r11;
    ImageView i1;
    List<ChatLogList> all_chats= new ArrayList<>();
    List<ChatLogList> unread_chats=new ArrayList<>();
    String url1= String.format("%sfetch_names_for_messages.php",DataFromDatabase.ipConfig); //php file to fetch all chats- name of all clients
    String url2= String.format("%sfetch_top_messages.php",DataFromDatabase.ipConfig); //php file to fetch unread chats- top message and time fetched from list obtained from url1

    String all_chats_profile[];
    String img="app/src/main/res/drawable/doctor_blue_border.png";

    String month,year,date;
    String all_chats_messages;
    String all_chats_time;
    String read_unread;
    String all_chats_messageby;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllMessages() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllMessages newInstance(String param1, String param2) {
        AllMessages fragment = new AllMessages();
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
        ArrayList<String> all_chats_names=new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Integer monthint = cal.get(Calendar.MONTH)+1;
        month = String.valueOf(cal.get(Calendar.MONTH)+1);
        if(monthint<10){
            month = String.valueOf(0)+month;
        }
        year = String.valueOf(cal.get(Calendar.YEAR));
        date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        Integer yearint = cal.get(Calendar.YEAR);
        Integer dateint = cal.get(Calendar.DAY_OF_MONTH);
        Log.d("date",String.valueOf(date)+" "+String.valueOf(month)+" "+String.valueOf(year));
        View v= inflater.inflate(R.layout.fragment_all_messages, container, false);
        all=v.findViewById(R.id.all_chat_btn);
        unread=v.findViewById(R.id.unread_btn);
        r11=v.findViewById(R.id.chat_log);
        i1=v.findViewById(R.id.startmessage);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url1, response -> {
            if (!response.equals("failure")){
                Log.d("allmessages response", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(jsonArray.length()>0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            all_chats_names.add(object.getString("clientID"));
                        }
                        for ( int j = 0; j < all_chats_names.size(); j++) {
                            int finalJ = j;
                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url2, response1 -> {
                                if (!response1.equals("failure")) {
                                    Log.d("allmessages url2",response1);
                                    try {
                                        JSONArray jsonArray1 = new JSONArray(response1);
                                        if (jsonArray1.length() > 0) {
                                            for (int k = 0; k < jsonArray1.length(); k++) {
                                                JSONObject object = jsonArray1.getJSONObject(k);
                                                all_chats_messages = object.getString("message");
                                                String resyear = object.getString("time").substring(0,4);
                                                String resmonth = object.getString("time").substring(5,7);
                                                String resdate = object.getString("time").substring(8,10);
                                                Log.d("date response",resdate+" "+resmonth+" "+resyear);
                                                if (resyear.equals(year) && resmonth.equals(month) && resdate.equals(date)){
                                                    all_chats_time = object.getString("time").substring(11,16);
                                                }else{
                                                    all_chats_time = resdate+"/"+resmonth;
                                                }
                                                byte[] qrimage = Base64.decode(object.getString("profilePhoto"),0);
                                                Bitmap pic = BitmapFactory.decodeByteArray(qrimage,0,qrimage.length);
                                                all_chats_messageby=object.getString("messageBy");
                                                read_unread = "object.getString(\"unread\")";

                                                ChatLogList o12 = new ChatLogList(pic, all_chats_names.get(finalJ), all_chats_messages,all_chats_messageby, all_chats_time, read_unread);
                                                all_chats.add(o12);
                                                if (read_unread.equals("U")) {
                                                    ChatLogList o13 = new ChatLogList(pic, all_chats_names.get(finalJ), all_chats_messages, all_chats_messageby, all_chats_time, read_unread);
                                                    unread_chats.add(o13);
                                                }
                                            }
                                            ChatLogAdapter adap = new ChatLogAdapter(getContext(), all_chats);
                                            r11.setAdapter(adap);
                                            r11.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else if (response1.equals("failure")) {
                                }
                            }, error -> {
                                Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                            }) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> data = new HashMap<>();
                                    data.put("dietitianID", DataFromDatabase.dietitianuserID);
                                    Log.d("allmessages j",finalJ+" "+all_chats_names.get(finalJ));
                                    data.put("clientID",all_chats_names.get(finalJ));
                                    return data;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            requestQueue.add(stringRequest1);
                        }
                    }
                }catch(JSONException e){
                        e.printStackTrace();
                    }

                Toast.makeText(getContext(), "Login success", Toast.LENGTH_SHORT).show();
            }
            else if (response.equals("failure")){
            }
        },error -> {
            Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();}){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> data = new HashMap<>();
                data.put("userID",DataFromDatabase.dietitianuserID);
                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatLogAdapter adap= new ChatLogAdapter(getContext(),all_chats);
                r11.setAdapter(adap);
                r11.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            }
        });
        unread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatLogAdapter adap= new ChatLogAdapter(getContext(),unread_chats);
                r11.setAdapter(adap);
                r11.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            }
        });
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),AllClientsForMessage.class);
                startActivity(i);
            }
        });
        return v;
    }
}