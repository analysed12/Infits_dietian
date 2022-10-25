package com.ultimate.infits;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account extends Fragment {

    ActivityResultLauncher<String> photo;

    File file;

    String fileName, path;

    Bitmap photoBit;

    DataFromDatabase dataFromDatabase;
    ImageView male, female,profile_pic;
    String url = "http://192.168.141.91/upload.php";
    RequestQueue queue;
    Button logout,save;
    String dietitian_acc_gender;
    String dieititian_acc_name, dietitiamn_acc_age, dietitian_acc_email,dietitian_acc_phoneno,dietitian_acc_userID,
            dietitian_about_me,dietitian_location,dietitian_experience;

    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Account() {
        // Required empty public constructor
    }

    public static Account newInstance(String param1, String param2) {
        Account fragment = new Account();
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
        View view= inflater.inflate(R.layout.fragment_account, container, false);
        male = view.findViewById(R.id.gender_male_icon);
        female=view.findViewById(R.id.gender_female_icon);
        EditText name=view.findViewById(R.id.name_edt);
        name.setText(dataFromDatabase.name);
        EditText age=view.findViewById(R.id.age_edt);
        age.setText(dataFromDatabase.age);
        EditText email=view.findViewById(R.id.email_edt);
        email.setText(dataFromDatabase.email);
        EditText phone=view.findViewById(R.id.phone_edt);
        phone.setText(dataFromDatabase.mobile);
        profile_pic=view.findViewById(R.id.dp);
        ImageView select_pic= view.findViewById(R.id.select_dp);
        save=view.findViewById(R.id.button_save);
        logout=view.findViewById(R.id.button_logout);
        male.setImageResource(R.drawable.gender_male);
        female.setImageResource(R.drawable.gender_female);
        EditText about_me=view.findViewById(R.id.about_me_edt);
        about_me.setText(dataFromDatabase.about_me);
        EditText location=view.findViewById(R.id.location_edt);
        location.setText(dataFromDatabase.location);
        EditText experience= view.findViewById(R.id.experience_edt);
        experience.setText(dataFromDatabase.experience);

        ImageView name_btn=view.findViewById(R.id.name_edt_button);
        ImageView age_btn=view.findViewById(R.id.age_edt_button);
        ImageView email_btn=view.findViewById(R.id.email_edt_button);
        ImageView phone_btn=view.findViewById(R.id.phone_edt_button);
        ImageView about_me_btn=view.findViewById(R.id.about_me_edt_button);
        ImageView location_btn=view.findViewById(R.id.location_edt_button);
        ImageView experience_btn=view.findViewById(R.id.experience_edt_button);

        profile_pic.setImageBitmap(dataFromDatabase.profile);

        profile_pic.setOnClickListener(v->{
            photo.launch("image/*");
        });

        photo = registerForActivityResult(
                new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        profile_pic.setImageURI(result);
                        path = result.getPath();
                        file = new File(result.toString());
                        String filename = path.substring(path.lastIndexOf("/")+1);
                        if (filename.indexOf(".") > 0) {
                            fileName = filename.substring(0, filename.lastIndexOf("."));
                        } else {
                            fileName =  filename;
                        }
                        Log.d("MainClass", "Real Path: " + path);
                        Log.d("MainClass", "Filename With Extension: " + filename);
                        Log.d("MainClass", "File Without Extension: " + fileName);
                        try {
                            photoBit = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver() , result);
                            DataFromDatabase.profile = photoBit;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"name edit enabled",Toast.LENGTH_SHORT).show();
                name.setCursorVisible(true);
                name.setFocusableInTouchMode(true);
                name.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        age_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"age edit enabled",Toast.LENGTH_SHORT).show();
                age.setCursorVisible(true);
                age.setFocusableInTouchMode(true);
                age.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
        });
        email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"email edit enabled",Toast.LENGTH_SHORT).show();
                email.setCursorVisible(true);
                email.setFocusableInTouchMode(true);
                email.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"phone number edit enabled",Toast.LENGTH_SHORT).show();
                phone.setCursorVisible(true);
                phone.setFocusableInTouchMode(true);
                phone.setInputType(InputType.TYPE_CLASS_PHONE);
            }
        });
        location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"location edit enabled",Toast.LENGTH_SHORT).show();
                location.setCursorVisible(true);
                location.setFocusableInTouchMode(true);
                location.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        experience_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"experience edit enabled",Toast.LENGTH_SHORT).show();
                experience.setCursorVisible(true);
                experience.setFocusableInTouchMode(true);
            }
        });
        about_me_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"about me edit enabled",Toast.LENGTH_SHORT).show();
                about_me.setCursorVisible(true);
                about_me.setFocusableInTouchMode(true);
                about_me.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        });
        if(dataFromDatabase.gender=="M"){
            male.setImageResource(R.drawable.gender_male_selected);
            female.setImageResource(R.drawable.gender_female);
        }else if(dataFromDatabase.gender=="F"){
            male.setImageResource(R.drawable.gender_male);
            female.setImageResource(R.drawable.gender_female_selected);
        }
//        select_pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectImage();
//            }
//        });


        dietitian_acc_gender=dataFromDatabase.gender;
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setImageResource(R.drawable.gender_male_selected);
                female.setImageResource(R.drawable.gender_female);
                dietitian_acc_gender="M";
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setImageResource(R.drawable.gender_male);
                female.setImageResource(R.drawable.gender_female_selected);
                dietitian_acc_gender="F";
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(),LoginScreen.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        queue = Volley.newRequestQueue(getContext());
        save.setOnClickListener(v-> {
            dieititian_acc_name=name.getText().toString().trim();
            dietitiamn_acc_age=age.getText().toString().trim();
            dietitian_acc_email=email.getText().toString().trim();
            dietitian_acc_phoneno=phone.getText().toString().trim();
            dietitian_acc_userID=dataFromDatabase.dietitianuserID;
            dietitian_location = location.getText().toString().trim();
            dietitian_experience= experience.getText().toString().trim();
            dietitian_about_me = about_me.getText().toString().trim();

            Log.d("account","before");
            StringRequest stringRequest = new StringRequest(Request.Method.POST,url, response -> {
                if (!response.equals("failure")){
                    Log.d("account","success");
                    Log.d("response account",response);
                    Toast.makeText(getContext(), "save success", Toast.LENGTH_SHORT).show();
                }
                else if (response.equals("failure")){
                    Log.d("account","failure");
                    Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            },error -> {
                Toast.makeText(getContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();}){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    String image = getStringOfImage(photoBit);
                    Map<String,String> data = new HashMap<>();
//                        data.put("userID",dietitian_acc_userID);
                    data.put("email","dietitian_acc_email");
                    data.put("gender","M");
                    data.put("age","90");
                    data.put("mobile","29302");
                    data.put("name","Aza");
                    data.put("img",image);
                    data.put("nameImg","Azarudeen");
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
            Log.d("account","at end");
        });

        return view;

    }


    public String getStringOfImage(Bitmap bm){
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,bo);
        byte[] imageByte = bo.toByteArray();
        String imageEncode = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return imageEncode;
    }


//    private void selectImage() {
//        try {
//            PackageManager pm = getContext().getPackageManager();
//            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getContext().getPackageName());
//            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
//                //final CharSequence[] options = {"Take Photo", "Choose From Gallery","Remove picture","Cancel"};
//                final CharSequence[] options = { "Choose From Gallery","Remove picture","Cancel"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Select Option");
//                builder.setItems(options, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int item) {
//                      /*  if (options[item].equals("Take Photo")) {
//                            dialog.cancel();
//                            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                            startActivityForResult(intent, PICK_IMAGE_CAMERA);
//                        } else*/
//                        if (options[item].equals("Choose From Gallery")) {
//                            dialog.cancel();
//                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                            startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
//                        } else if (options[item].equals("Cancel")) {
//                            dialog.cancel();
//                        }
//                        else if(options[item].equals("Remove picture")){
//                            dialog.dismiss();
//                            profile_pic.setImageResource(R.drawable.blankdp);
//                        }
//                        else
//                            dialog.cancel();
//                    }
//                });
//                builder.show();
//            } else {
//                Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), "Change app permission in your device settings", Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            Toast.makeText(getActivity(), "Camera Permission error", Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(), "Change app permission in your device settings", Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//    }
//        @Override
//        public void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//            inputStreamImg = null;
//            if (requestCode == PICK_IMAGE_GALLERY) {
//                     try {
//                         if( data.getData() != null) {
//                             Uri selectedImage = data.getData();
//                             bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
//                             //ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                            // bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
//                             profile_pic.setImageBitmap(bitmap);
//                  /*  imgPath = getRealPathFromURI(selectedImage);
//                    destination = new File(imgPath.toString());*/
//
//                         }
//                    } catch (Exception e) {
//                        Toast.makeText(getActivity(),"No picture selected",Toast.LENGTH_SHORT).show();
//                     }
//
//            }
//        }

//        public String getRealPathFromURI(Uri contentUri) {
//            String[] proj = {MediaStore.Audio.Media.DATA};
//            Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        }
}