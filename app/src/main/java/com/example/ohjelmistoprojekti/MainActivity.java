package com.example.ohjelmistoprojekti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    String CHANNEL_ID = "NotificationChannelOne";                                       //Notifikaatio kanavan Id
    String CHANNEL_NAME = "BasicNotification";                                          //Notifikaatio kanavan nimi
    String description = "This is a Demo Notification with no proper functionality";    //Notifikaatio kanavan kuvaus

    private RequestQueue queue;
    private Double[] priceArr = new Double[24];

    BottomNavigationView bottomNavigationView;                                          //Navigointipalkin viite

    HomeFragment homeFragment = new HomeFragment();                                     //
    HappyHourFragment happy_hourFragment = new HappyHourFragment();                     //
    SettingsFragment settingsFragment = new SettingsFragment();                         //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toteutetaan RequestQueue
        queue = Volley.newRequestQueue(this);

        createNotificationChannel();
        getPriceData();

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


    public void getPriceData() {

        Toast.makeText(this, "getPriceData -funktion sisällä!", Toast.LENGTH_SHORT).show();
        // Haetaan hintatiedot palvelimelta (JSON)
        String url = "https://ohjelmistoprojekti-production.up.railway.app/pricejson/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Näytetään response toastina ruudulla
                    Toast.makeText(this, response, Toast.LENGTH_LONG).show();

                    parseJsonAndUpdateUI(response);
                },
                error -> {
                    // virhe verkosta hakemisessa
                    Toast.makeText(this, "Verkkovirhe", Toast.LENGTH_SHORT).show();
                }
        );
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    private void parseJsonAndUpdateUI(String response) {
        try {
            JSONObject rootObject = new JSONObject(response);

            // Parsitaan JSONia eli kaivetaan haluttu data
            String id = rootObject.getJSONArray("Prices").getJSONObject(8).getString("id");
            Double H23 = rootObject.getJSONArray("Prices").getJSONObject(8).getDouble("H23");
            priceArr[0] = H23;
            String sH23 = String.valueOf(priceArr[0]);

            Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, sH23, Toast.LENGTH_SHORT).show();

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Virhe JSON parsinnassa", Toast.LENGTH_SHORT).show();
        }
    }
}
