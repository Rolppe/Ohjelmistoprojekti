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

    String CHANNEL_ID = "NotificationChannelOne";                                       //Notifikaatio kanavan Id
    String CHANNEL_NAME = "BasicNotification";                                          //Notifikaatio kanavan nimi
    String description = "This is a Demo Notification with no proper functionality";    //Notifikaatio kanavan kuvaus

    BottomNavigationView bottomNavigationView;                                          //Navigointipalkin viite

    HomeFragment homeFragment = new HomeFragment();                                     //
    HappyHourFragment happy_hourFragment = new HappyHourFragment();                     //
    SettingsFragment settingsFragment = new SettingsFragment();

    private Singleton singleton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] prices = new String[24];
        Singleton singleton = Singleton.getInstance(getApplicationContext());
        prices = singleton.getPrices();

        createNotificationChannel();
        // sendAndRequestResponse();
        /*
        while (this.pricesArray[0] == null) {

            continue;
        }

        StringBuilder builder = new StringBuilder();

        for(String k : this.pricesArray) {

            builder.append("").append(k).append(" ");
        }
        */

        // Access the RequestQueue through your singleton class.
        // MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

        replaceFragment(homeFragment);                                                  // APPia avatessa se avaa koti fragmentin
        bottomNavigationView = findViewById(R.id.bottomNavigationView);                 // Tunnistaa navigointipalkin
        bottomNavigationView.setOnItemSelectedListener(item -> {                        // navigatiopalkin kuuntelija

            switch(item.getItemId()){                                                   //
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

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,fragment);
        fragmentTransaction.commit();
    }

    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
