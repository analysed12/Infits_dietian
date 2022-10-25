package com.ultimate.infits;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddClients extends AppCompatActivity {

    ActivityResultLauncher<String> photo;

    Button addClient;

    ImageView male,female,pic;

    EditText name,age,height,weight,about,title,description,planTitle,planDescription;
    String url = String.format("%saddClient.php", DataFromDatabase.ipConfig);

    boolean maleSelected = true;

    boolean femaleSelected = false;

    String gender = "M";

    TextView seeAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clients);

        addClient = findViewById(R.id.addClient);

        name = findViewById(R.id.name_edt);
        age = findViewById(R.id.age_edt);
        description = findViewById(R.id.description_edt);
        height = findViewById(R.id.height_edt);
        weight = findViewById(R.id.weight_edt);
        about = findViewById(R.id.about_edt);
        description = findViewById(R.id.description_edt);
        title = findViewById(R.id.title_edt);
        planTitle = findViewById(R.id.plan_title_edt);
        planDescription = findViewById(R.id.plan_describption_edt);

        male = findViewById(R.id.gender_male_icon);
        female = findViewById(R.id.gender_female_icon);

        seeAll = findViewById(R.id.see_all);

        pic = findViewById(R.id.pic);

        pic.setOnClickListener(v -> {

            photo.launch("image/*");

        });

        photo = registerForActivityResult(
                new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        pic.setImageURI(result);

                    }
                }
        );

        male.setOnClickListener(v->{
            if (femaleSelected){
                gender = "M";
                femaleSelected = false;
                maleSelected = true;
                male.setImageDrawable(getDrawable(R.drawable.gender_male_selected));
                female.setImageDrawable(getDrawable(R.drawable.gender_female));
            }
        });

        female.setOnClickListener(v->{
            if (maleSelected){
                gender = "M";
                maleSelected = false;
                femaleSelected = true;
                female.setImageDrawable(getDrawable(R.drawable.gender_female_selected));
                male.setImageDrawable(getDrawable(R.drawable.gender_male));
            }
        });

        addClient.setOnClickListener(v->{

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dMYHms");

            Dialog dialog = new Dialog(AddClients.this);
            dialog.setContentView(R.layout.referralcodedialog);
            dialog.setCancelable(true);
            EditText referalCode = dialog.findViewById(R.id.referal);
            String code = shuffle(DataFromDatabase.dietitianuserID+name.getText().toString()+simpleDateFormat.format(new Date()));
            referalCode.setText(code);
            dialog.show();

            StringRequest addClientRequest = new StringRequest(Request.Method.POST,url,response -> {

                System.out.println(response);

                if (response.equals("success")){
                    Toast.makeText(this, "Client add", Toast.LENGTH_SHORT).show();
                }
            },error -> {

            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> data = new HashMap<>();

                    data.put("dietitianID",DataFromDatabase.dietitianuserID);
                    data.put("clientName",name.getText().toString());

                    data.put("gender",gender);

                    data.put("age",age.getText().toString());

                    data.put("height",height.getText().toString());

                    data.put("weight",weight.getText().toString());

                    data.put("about",about.getText().toString());

                    data.put("description",description.getText().toString());

                    data.put("title",title.getText().toString());

                    data.put("plantitle",planTitle.getText().toString());

                    data.put("plandescription",planDescription.getText().toString());

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dMYHms");

                    data.put("referalcode",code);
                    return data;
                }
            };
            Volley.newRequestQueue(getApplicationContext()).add(addClientRequest);
        });

        seeAll.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),History_Created.class);
            startActivity(intent);
        });

    }

    public String shuffle(String input){
        List<Character> characters = new ArrayList<Character>();
        for(char c:input.toCharArray()){
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while(characters.size()!=0){
            int randPicker = (int)(Math.random()*characters.size());
            output.append(characters.remove(randPicker));
        }
        System.out.println(output.toString());
        return output.toString();
    }
}