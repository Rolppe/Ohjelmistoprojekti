package com.example.ohjelmistoprojekti;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    HappyHourFragment happy_hourFragment = new HappyHourFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(homeFragment);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
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


    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment,fragment);
        fragmentTransaction.commit();
    }
}