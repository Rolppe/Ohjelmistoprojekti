package com.example.ohjelmistoprojekti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.*;


public class MainActivity extends AppCompatActivity {

    //Strings
    String CHANNEL_ID = "NotificationChannelOne";
    String CHANNEL_NAME = "BasicNotification";
    String description = "This is a Demo Notification with no proper functionality";

    //NavView
    BottomNavigationView bottomNavigationView;

    //Fragments
    HomeFragment homeFragment = new HomeFragment();
    HappyHourFragment happy_hourFragment = new HappyHourFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Calling NotifChannel function
        createNotificationChannel();

        //Replacing view with homefrag on startup
        replaceFragment(homeFragment);
        //Initializing the nav bar
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        //Listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.home:
                    replaceFragment(homeFragment);
                    break;
                case R.id.happy_hour:
                    replaceFragment(happy_hourFragment);
                    break;
                case R.id.settings:
                    replaceFragment(settingsFragment);
                    break;
            }
            return true;
        });
    }
    //Fragment replace function
    private void replaceFragment(Fragment fragment) {
        //New frag manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //Begin transaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Replace current frag with new frag
        fragmentTransaction.replace(R.id.flFragment,fragment);
        //Commit the change
        fragmentTransaction.commit();
    }
    //NotifChannel function
    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            //New notif channel
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            //Add channel description
            channel.setDescription(description);
            //New channel manager
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            //Create the channel
            notificationManager.createNotificationChannel(channel);
        }
    }
}
