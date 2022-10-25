
package com.ultimate.infits;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientList extends Fragment {

    StringRequest stringRequest;
    RecyclerView clientList;
    RadioButton active,pending;
    String url = String.format("%sclientsList.php",DataFromDatabase.ipConfig);
    RequestQueue queue;

    List<List_Clients> client_list_active =new ArrayList<>();
    List<List_Clients> client_list_pending =new ArrayList<>();

    public ClientList() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_list, container, false);
        clientList = view.findViewById(R.id.client_list);
        active = view.findViewById(R.id.active_btn);
        pending = view.findViewById(R.id.pending_btn);
        DataFromDatabase.clientsID.clear();
        clientList.setAdapter(null);
        client_list_active.removeAll(client_list_active);
        client_list_pending.removeAll(client_list_pending);
        queue = Volley.newRequestQueue(getContext());
        Log.d("ClientList","before");
        stringRequest = new StringRequest(Request.Method.POST,url,response -> {
            Log.d("ClientList",response);
            if (!response.equals("failure")){
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i< jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        byte[] qrimage = Base64.decode(object.getString("profilePhoto"),0);
                        DataFromDatabase.profileClient = BitmapFactory.decodeByteArray(qrimage,0,qrimage.length);

                        List_Clients obj = new List_Clients(object.getString("plan"), object.getString("clientID"),
                                DataFromDatabase.profileClient);
                        DataFromDatabase.clientsID.add(object.getString("clientID"));
                        String dietchart = object.getString("plan");
                        System.out.println(object.getString("clientID"));
                        if(dietchart.equals("null")){
                            client_list_pending.add(obj);
                        }else {
                            client_list_active.add(obj);
                        }
                        ClientListAdapter cd = new ClientListAdapter(getContext(), client_list_active);
                        clientList.setAdapter(cd);
                        clientList.setLayoutManager(new LinearLayoutManager(getContext()));
                        active.setOnClickListener(v -> {
                            ClientListAdapter cd1 = new ClientListAdapter(getContext(), client_list_active);
                            clientList.setAdapter(cd1);
                            clientList.setLayoutManager(new LinearLayoutManager(getContext()));
                            active.setTextColor(Color.parseColor("#FFFFFF"));
                            active.setBackgroundColor(Color.parseColor("#8780F8"));
                            pending.setTextColor(Color.BLACK);
                            pending.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        });
                        pending.setOnClickListener(v -> {
                            ClientListAdapter cd2 = new ClientListAdapter(getContext(), client_list_pending);
                            clientList.setAdapter(cd2);
                            clientList.setLayoutManager(new LinearLayoutManager(getContext()));
                            pending.setTextColor(Color.parseColor("#FFFFFF"));
                            pending.setBackgroundColor(Color.parseColor("#8780F8"));
                            active.setTextColor(Color.BLACK);
                            active.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    Toast.makeText(getContext(), "ClientList success", Toast.LENGTH_SHORT).show();
                }catch (NullPointerException nullPointerException){
                    System.out.println("nullpointerexception");
                }
            }
            else if (response.equals("failure")){
                Log.d("clientList","failure");
                try {
                    Toast.makeText(getContext(), "ClientList failed", Toast.LENGTH_SHORT).show();
                }catch (NullPointerException nullPointerException){
                    System.out.println("nullpointerexception");
                }
            }
        },error -> {
            try {
                Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
            }catch (NullPointerException nullPointerException){
            System.out.println("nullpointerexception");
        }
    })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                Log.d("ClientList","dietitianid = "+ DataFromDatabase.dietitianuserID);
                data.put("userID", DataFromDatabase.dietitianuserID);
                return data;
            }
        };
        Volley.newRequestQueue(getContext()).add(stringRequest);
        Log.d("ClientList","at end");
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        queue.cancelAll(this);
    }
}