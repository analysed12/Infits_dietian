package com.ultimate.infits;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView name,qual;
    ImageView dieticianimg;
    DrawerLayout draw;
    NavigationView navigationView;
    LinearLayout nav_account;
    //public static List<String> stack_fragment= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Client List");
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        name = findViewById(R.id.nav_name);
        qual = findViewById(R.id.nav_designation);
        dieticianimg = findViewById(R.id.dietian_pic);
        name.setText(DataFromDatabase.name);
        qual.setText(DataFromDatabase.qualification);
        draw = findViewById(R.id.drawer_layout);
        dieticianimg.setImageBitmap(DataFromDatabase.profile);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,draw,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        draw.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(getDrawable(R.drawable.hamburger_icon));
        toggle.syncState();
        toggle.setToolbarNavigationClickListener(v -> {
            if (draw.isDrawerVisible(GravityCompat.START)) {
                draw.closeDrawer(GravityCompat.START);
            } else {
                draw.openDrawer(GravityCompat.START);
            }
        });
        if (savedInstanceState == null){

        }
        else{
            draw.open();
        }

        nav_account= (LinearLayout) findViewById(R.id.nav_account);
        nav_account.setOnClickListener(v -> {
            draw.closeDrawer(GravityCompat.START);
            Navigation.findNavController(MainActivity.this, R.id.FrameContainer).navigate(R.id.open_profile);
            //getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer,new Profile()).commit();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (draw.isDrawerOpen(GravityCompat.START)){
            draw.closeDrawer(GravityCompat.START);
        }
        else{
           // super.onBackPressed();
           /* if(getSupportFragmentManager().getBackStackEntryCount()>0)
            {

                //getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer,new BlankFragment()).commit();
                /*if(stack_fragment.size()>0) {
                    stack_fragment.remove(stack_fragment.get(stack_fragment.size() - 1));
                    System.out.println(stack_fragment);
                }*/
               // getSupportFragmentManager().popBackStackImmediate();
            /*}
           else {
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
                ad.setTitle("Warning!");
                ad.setMessage("Do you want to exit?");
                ad.setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        MainActivity.super.onBackPressed();
                    }

                });
                ad.setNegativeButton("No",  new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1)
                    {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = ad.create();
                dialog.show();
            }*/
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.recepies:
                startActivity(new Intent(getApplicationContext(),Recipies.class));
                draw.closeDrawer(GravityCompat.START);

            case R.id.dashboard:
//                Navigation.findNavController(MainActivity.this, R.id.FrameContainer).navigate(R.id.open_dashboard_fragment);
                /*FragmentTransaction ft2= getSupportFragmentManager().beginTransaction();
                ft2.replace(R.id.FrameContainer,new BlankFragment());
                ft2.add(R.id.FrameContainer,new DashboardFragment());
                if( (stack_fragment.size()>0) && (stack_fragment.get(stack_fragment.size()-1)!="dashboard"))
                {
                    ft2.addToBackStack("dashboard");
                    stack_fragment.add("dashboard");
                }
                ft2.commit();*/
                startActivity(new Intent(MainActivity.this,MyDashBoard.class));
                draw.closeDrawer(GravityCompat.START);
                break;
            case R.id.message:
                Navigation.findNavController(MainActivity.this, R.id.FrameContainer).navigate(R.id.message_nav);
                /*FragmentTransaction ft3= getSupportFragmentManager().beginTransaction();
                ft3.replace(R.id.FrameContainer,new BlankFragment());
                ft3.add(R.id.FrameContainer,new Messages());
                if( (stack_fragment.size()>0) && (stack_fragment.get(stack_fragment.size()-1)!="messages"))
                {
                    ft3.addToBackStack("messages");
                    stack_fragment.add("messages");
                }
                ft3.commit();*/
                //startActivity(new Intent(this,MessageActivity.class));
                draw.closeDrawer(GravityCompat.START);
                break;
            case R.id.settings:
                Navigation.findNavController(MainActivity.this, R.id.FrameContainer).navigate(R.id.settings_nav);
                /*FragmentTransaction ft4= getSupportFragmentManager().beginTransaction();
                ft4.replace(R.id.FrameContainer,new BlankFragment());
                ft4.add(R.id.FrameContainer,new SettingMain());
                if( (stack_fragment.size()>0) && (stack_fragment.get(stack_fragment.size()-1)!="setting"))
                {
                    ft4.addToBackStack("setting");
                    stack_fragment.add("setting");
                }
                ft4.commit();*/
                //startActivity(new Intent(this,Settings.class));
                draw.closeDrawer(GravityCompat.START);
                break;
            case R.id.Appointment:
                Navigation.findNavController(MainActivity.this, R.id.FrameContainer).navigate(R.id.open_appointment);
                //getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer,new Calender()).commit();
                draw.closeDrawer(GravityCompat.START);
                break;
            case R.id.live:
                startActivity(new Intent(this,LiveSchedule.class));
//                Toast.makeText(getApplicationContext(),"Text",Toast.LENGTH_SHORT).show();
                draw.closeDrawer(GravityCompat.START);
                break;
            case R.id.add_client:
                startActivity(new Intent(this,AddClients.class));
                draw.closeDrawer(GravityCompat.START);
        }
        return true;
    }
}