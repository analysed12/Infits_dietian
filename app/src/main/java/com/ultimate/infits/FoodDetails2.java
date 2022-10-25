package com.ultimate.infits;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageOptions;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.ultimate.infits.adapter.NewRecipeAdapter;
import com.ultimate.infits.interfaces.FragmentCallbacks;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodDetails2 extends AppCompatActivity implements com.ultimate.infits.interfaces.FragmentCallbacks {


    public static Bitmap bitmap;
//    private static int serverResponseCode;
//    public static ArrayList<String> ingName = new ArrayList<>();
//    public static ArrayList<String> ingUnits = new ArrayList<>();
//    public static ArrayList<String> ingAmount = new ArrayList<>();
//    Spinner category, cookingTime;
//    String fileName, path;
//    File file;
//    Bitmap photoBit;
//    ImageButton back;
//    ActivityResultLauncher<String> photo;
//    RecyclerView ingredients_food;
//    ImageView add_ingredients, pic, upload_link, upload_file;
//    EditText title, cooking_time, calorie, fats, proteins, carbs, fibres, writeProcess,serving;
//    TextView nutritions,upload;
//    List<add_new> ingred = new ArrayList<>();
//    List<add_new> nutrit = new ArrayList<>();
//    List<add_new> utensils = new ArrayList<>();
//    ArrayList<String> ingredient = new ArrayList<>();
//    private String url = String.format("%screateRecipies.php",DataFromDatabase.ipConfig);
//    private String link;
//    private String files = "null";
//    private String servingStr;
//    ArrayList<String> duration = new ArrayList<>();
//    ArrayList<String> time = new ArrayList<>();

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    CardView cardNav;
    NewRecipeAdapter newrecipeAdapter;
    RadioGroup rg_1,rg_2,rg_3,courseRadioGroup ;
    CardView rl_uploadImage;
    ImageView new_img,upload_jpg;

    private static final int GALLERY_REQUEST_CODE = 2123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details2);

        //IDS
        ids();

        tabPlay();

        rl_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

    }

    private void setImage(Uri uri) {
        new_img.setVisibility(View.VISIBLE);
        upload_jpg.setVisibility(View.INVISIBLE);
        Glide.with(this).load(uri).into(new_img);
    }


  //  Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

    private void launchImageCrop(Uri uri) {
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
//                .setScaleType(CropImageView.ScaleType.F)
                .setAspectRatio(300,260)
                .setFixAspectRatio(true)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case GALLERY_REQUEST_CODE:
                if(resultCode == Activity.RESULT_OK){
                    assert data != null;
                    Uri uri = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    launchImageCrop(uri);
                }else{
                    Toast.makeText(this, "OnActivityResultError", Toast.LENGTH_SHORT).show();
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode==Activity.RESULT_OK){
                    assert result != null;
                    Uri uri = result.getUri();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    setImage(uri);
                }
                break;
            default:
                if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Toast.makeText(this, "OnActivityResultError 2", Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }

    public static String getStringOfImage(Bitmap bm){
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,50,bo);
        byte[] imageByte = bo.toByteArray();
        String imageEncode = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return imageEncode;
    }


    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }


    @Override
    public void goTo(int pos){
        viewPager2.setCurrentItem(pos);
    }

    private void tabPlay() {
        tabLayout.addTab(tabLayout.newTab().setText("Recipe Details"));
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Directions"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        newrecipeAdapter =new NewRecipeAdapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(newrecipeAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }


    private void ids() {
        tabLayout =findViewById(R.id.tabLayout);
        viewPager2 =findViewById(R.id.viewPager2);
        cardNav =findViewById(R.id.cardNav);

        courseRadioGroup=findViewById(R.id.courseRadioGroup);

        rl_uploadImage=findViewById(R.id.rl_uploadImage);
        upload_jpg=findViewById(R.id.upload_jpg);
        new_img=findViewById(R.id.new_img);

    }
    
}