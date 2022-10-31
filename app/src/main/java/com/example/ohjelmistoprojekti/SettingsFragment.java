package com.example.ohjelmistoprojekti;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class SettingsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        Switch notificationSwitch;
        Switch priceAlertSwitch;
        notificationSwitch = (Switch) view.findViewById(R.id.Notif_Switch);
        priceAlertSwitch = (Switch) view.findViewById(R.id.Price_Alert_Switch);

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(view.getContext(), "Notifications are ON!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), ReminderBroadcast.class);  //If switch is on, do function.
                    // APP notification at 2:30 pm - 3 pm
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(view.getContext(), 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                            AlarmManager.INTERVAL_DAY, pendingIntent);

                    priceAlertSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if(isChecked){
                                Toast.makeText(view.getContext(), "Price Alerts are ON!", Toast.LENGTH_SHORT).show();
                                //price change at 2pm, APP reads backend price
                                Intent intent = new Intent(view.getContext(), ReminderBroadcast.class);  //If switch is on, do function.
                                // APP notification at 2:30 pm - 3 pm
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(view.getContext(), 0, intent, 0);
                                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis(),
                                        AlarmManager.INTERVAL_DAY, pendingIntent);


                            } else {
                                //Else do nothing
                            }}});
                } else {
                    //Else do nothing
                }}});
        return view;

    }


}