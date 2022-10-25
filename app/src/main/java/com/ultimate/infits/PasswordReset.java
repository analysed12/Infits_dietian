package com.ultimate.infits;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordReset#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordReset extends Fragment {
    ImageButton b1;
    Button reset;
    RequestQueue queue;
    DataFromDatabase dataFromDatabase;
    String url = "http://192.168.9.1/pwdReset.php";
    TextInputLayout old_pwd,new_pwd,new_pwd1;
    String old_pwd_str,new_pwd_str,new_pwd1_str;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PasswordReset() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PasswordReset.
     */
    // TODO: Rename and change types and number of parameters
    public static PasswordReset newInstance(String param1, String param2) {
        PasswordReset fragment = new PasswordReset();
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
        View view= inflater.inflate(R.layout.fragment_password_reset, container, false);
        b1=view.findViewById(R.id.newpwd_back);
        reset= view.findViewById(R.id.button3);
        old_pwd= view.findViewById(R.id.textcurrentpwd);
        new_pwd=view.findViewById(R.id.textnewpwd);
        new_pwd1=view.findViewById(R.id.textnewpwd1);
        MainActivity con=(MainActivity)getActivity();
        con.getSupportActionBar().hide();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                con.getSupportActionBar().show();
                Navigation.findNavController(v).navigateUp();

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_pwd_str = old_pwd.getEditText().getText().toString();
                new_pwd_str = new_pwd.getEditText().getText().toString();
                new_pwd1_str = new_pwd1.getEditText().getText().toString();
                Log.d("passwords",old_pwd_str+" "+new_pwd_str+" "+new_pwd1_str);
                //store to db
                if(new_pwd_str.equals(new_pwd1_str)){
                    if (old_pwd_str.equals(dataFromDatabase.password)){
                        Log.d("pwdReset","before");
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
                            if (response.equals("success")){
                                Log.d("pwdReset","success");
                                Log.d("pwdReset response",response);

                                Toast.makeText(getContext(), "pwdReset success", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getContext(),MainActivity.class));
                            }
                            else if (response.equals("failure")){
                                Log.d("pwdReset","failure");
                                Toast.makeText(getContext(), "pwdReset failed", Toast.LENGTH_SHORT).show();
                            }
                        },error -> {
                            Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();}){
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> data = new HashMap<>();
                                data.put("userID",dataFromDatabase.dietitianuserID);
                                data.put("password",new_pwd_str);
                                return data;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                        requestQueue.add(stringRequest);
                        Log.d("pwdReset","at end");
                    }else {
                        Toast.makeText(getContext(),"Incorrect Old Password",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(),"New password fields need to be same ",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}