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
    String url = "https://ohjelmistoprojekti-production.up.railway.app/pricejson/";
    String[] pricesArray = new String[24];
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        sendAndRequestResponse();
        while (this.pricesArray[0] == null) {

            continue;
        }
            
        StringBuilder builder = new StringBuilder();

        for(String k : this.pricesArray) {

            builder.append("").append(k).append(" ");
        }

        Toast.makeText(this, builder, Toast.LENGTH_LONG).show();

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

    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                // Kutsutaan JSON-parsintafunktiota
                // String[] pricesArray = new String[24];
                parseJsonAndUpdateUI(response, "2022-11-28");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    private void parseJsonAndUpdateUI(String response, String time) {
        // String[] pricesArray = new String[24];
        try {
            // String jsonString = response ; //assign your JSON String here
            JSONObject obj = new JSONObject(response);

            JSONArray arr = obj.getJSONArray("Prices"); // notice that `"posts": [...]`
            for (int i = 0; i < arr.length(); i++) {

                // pricesToday[0] = arr.getJSONObject(i).getString("H00");
                String date = arr.getJSONObject(i).getString("Date");
                // Toast.makeText(this, date, Toast.LENGTH_LONG).show();
                // Toast.makeText(this, arr.getJSONObject(i).getString("H00"), Toast.LENGTH_LONG).show();

                if (date.equals(time)) {

                    // Toast.makeText(this, "Inside IF", Toast.LENGTH_LONG).show();
                    this.pricesArray[0] = arr.getJSONObject(i).getString("H00");
                    this.pricesArray[1] = arr.getJSONObject(i).getString("H01");
                    this.pricesArray[2] = arr.getJSONObject(i).getString("H02");
                    this.pricesArray[3] = arr.getJSONObject(i).getString("H03");
                    this.pricesArray[4] = arr.getJSONObject(i).getString("H04");
                    this.pricesArray[5] = arr.getJSONObject(i).getString("H05");
                    this.pricesArray[6] = arr.getJSONObject(i).getString("H06");
                    this.pricesArray[7] = arr.getJSONObject(i).getString("H07");
                    this.pricesArray[8] = arr.getJSONObject(i).getString("H08");
                    this.pricesArray[9] = arr.getJSONObject(i).getString("H09");
                    this.pricesArray[10] = arr.getJSONObject(i).getString("H10");
                    this.pricesArray[11] = arr.getJSONObject(i).getString("H11");
                    this.pricesArray[12] = arr.getJSONObject(i).getString("H12");
                    this.pricesArray[13] = arr.getJSONObject(i).getString("H13");
                    this.pricesArray[14] = arr.getJSONObject(i).getString("H14");
                    this.pricesArray[15] = arr.getJSONObject(i).getString("H15");
                    this.pricesArray[16] = arr.getJSONObject(i).getString("H16");
                    this.pricesArray[17] = arr.getJSONObject(i).getString("H17");
                    this.pricesArray[18] = arr.getJSONObject(i).getString("H18");
                    this.pricesArray[19] = arr.getJSONObject(i).getString("H19");
                    this.pricesArray[20] = arr.getJSONObject(i).getString("H20");
                    this.pricesArray[21] = arr.getJSONObject(i).getString("H21");
                    this.pricesArray[22] = arr.getJSONObject(i).getString("H22");
                    this.pricesArray[23] = arr.getJSONObject(i).getString("H23");
                }
            }
            StringBuilder builder = new StringBuilder();

            for(String k : this.pricesArray) {

                builder.append("").append(k).append(" ");
            }

            Toast.makeText(this, builder, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Virhe JSON parsinnassa", Toast.LENGTH_SHORT).show();
        }
    }
}
