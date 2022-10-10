package com.example.ohjelmistoprojekti;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;


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
                if(isChecked){ ((MainActivity) getActivity()).buildNotification();  //If switch is on, do function. //APP notification at 2:30 pm - 3 pm
                } else {                                                            //Else do nothing
                }

            }
        });

        priceAlertSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                           //price change at 2pm, APP reads backend price                                              //If switch is on, and price is equal or lower than input price do function.
                        ((MainActivity) getActivity()).buildNotification();

                } else {                                                //Else do nothing
                }

            }
        });
        return view;

    }


}