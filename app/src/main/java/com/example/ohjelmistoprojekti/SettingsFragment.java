package com.example.ohjelmistoprojekti;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class SettingsFragment extends Fragment {

    double[] pricesToday;
    double[] pricesTomorrow;
    Switch notificationSwitch;
    Switch priceAlertSwitch;
    EditText inputPrice;
    boolean found = false;
    String CHANNEL_ID = "Notif1";
    String priceString = "1";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        notificationSwitch = (Switch) view.findViewById(R.id.Notif_Switch);
        priceAlertSwitch = (Switch) view.findViewById(R.id.Price_Alert_Switch);
        inputPrice = (EditText) view.findViewById(R.id.etPrice);
        // Create the NotificationChannels, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel1();
            createNotificationChannel2();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 40);
        calendar.set(Calendar.SECOND, 41);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        getPrices();


        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //Create new intent
                    Intent genIntent = new Intent(getContext(), MainActivity.class);
                    //set intent flag
                    genIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pGenIntent = PendingIntent.getBroadcast(getContext(),0, genIntent, PendingIntent.FLAG_IMMUTABLE);
                    //make a notification builer and set constraints (icon, title, text etc..)
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "NotifCh1")
                            .setContentIntent(pGenIntent)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(getString(R.string.channel_name1))
                            .setContentText(getString(R.string.channel_description1))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true);
                    //new notification manager
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                    //build the notification when the builder is called
                    notificationManager.notify(1, builder.build());
                    //new alarm manager
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    // Send a notification once a day at the time specified in the calendar object.
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pGenIntent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pGenIntent);
                    }
                }
            }
        });
        priceAlertSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    //get input number (double) and if it's null then set it to 100
                    priceString = inputPrice.getText().toString();
                    if(priceString == null)
                        priceString = "100";
                    //parse string into double
                    double price = Double.valueOf(priceString);
                    //if price less than or equal to prices in the two arrays is found, then boolean found is true
                    for (int j = 0; j < pricesToday.length; j++) {
                        if (price <= pricesToday[j] || price <= pricesTomorrow[j])
                            found = true;
                    }

                    if (found) {
                        //check if true and send a notification if so
                        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(getString(R.string.channel_name2))
                                .setContentText(getString(R.string.channel_description2))
                                .setPriority(NotificationCompat.PRIORITY_LOW)
                                .setAutoCancel(true);
                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                        managerCompat.notify(2, builder2.build());
                    }
                }
            }
        });
        return view;
    }
    //getting prices via singleton function and setting the prices into local arrays
    private void getPrices(){
        Singleton singleton = Singleton.getInstance(getActivity().getApplicationContext());
        singleton.getPriceData(getActivity().getApplicationContext(), new Singleton.VolleyResponseListener() {
            @Override
            public void onError(String message) {
            }
            @Override
            public void onResponse(double[] aPricesToday, double[] aPricesTomorrow, String dateToday, String dateTomorrow) {
                pricesToday= aPricesToday;
                pricesTomorrow= aPricesTomorrow;

                //toastPrices(pricesToday,"HappyHourFragment pricesToday: ", dateToday);
                //toastPrices(pricesTomorrow,"HappyHourFragment pricesTomorrow: ",dateTomorrow);

            }
        });
    }
    //toast function for async data used for testing
    /*
    public void toastPrices(double[] pricesArray, String additionalText,String date) {
        StringBuilder builder = new StringBuilder();
        builder.append(" ").append(date).append(" ");
        for (double k : pricesArray) {
            builder.append(" ").append(k).append(" ");
        }
        Toast.makeText(getActivity().getApplicationContext(), additionalText + builder, Toast.LENGTH_LONG).show();
    }*/

    //creating notification channels
    private void createNotificationChannel1() {
            CharSequence name = getString(R.string.channel_name1);
            NotificationChannel channel = new NotificationChannel("Notif1", name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

    }
    private void createNotificationChannel2() {
        CharSequence name = getString(R.string.channel_name2);
        NotificationChannel channel2 = new NotificationChannel("Notif2", name, NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel2);
    }


}

