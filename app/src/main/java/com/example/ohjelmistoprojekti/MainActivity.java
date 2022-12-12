package com.example.ohjelmistoprojekti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

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

        //Replacing view with HomeFragment on startup
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
}
