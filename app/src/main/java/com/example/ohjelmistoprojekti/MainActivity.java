package com.example.ohjelmistoprojekti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    String CHANNEL_ID = "NotificationChannelOne";                                       //Notifikaatio kanavan Id
    String CHANNEL_NAME = "BasicNotification";                                          //Notifikaatio kanavan nimi
    String description = "This is a Demo Notification with no proper functionality";    //Notifikaatio kanavan kuvaus

    BottomNavigationView bottomNavigationView;                                          //Navigointipalkin viite

    HomeFragment homeFragment = new HomeFragment();                                     //
    HappyHourFragment happy_hourFragment = new HappyHourFragment();                     //
    SettingsFragment settingsFragment = new SettingsFragment();                         //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

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

    private void replaceFragment(Fragment fragment){

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