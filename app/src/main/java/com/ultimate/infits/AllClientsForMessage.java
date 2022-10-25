package com.ultimate.infits;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AllClientsForMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clients_for_message);
        ImageView search= findViewById(R.id.search_client_icon_message);
        EditText searchtext= findViewById(R.id.search_bar_text_message);
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (searchtext.getVisibility() == v.VISIBLE) {
//                    String client_search_name = searchtext.getText().toString();
//                    searchtext.setVisibility(v.INVISIBLE);
//                    if(!(client_search_name.equals("")) && !(client_search_name.equals(" "))){
//                        Toast.makeText(getApplicationContext(),"Searching for the client "+client_search_name,Toast.LENGTH_SHORT).show();
//                        //query database for the searched username
//                    }
//                }
//                else
//                    searchtext.setVisibility(v.VISIBLE);
//            }
//        });
    }
}