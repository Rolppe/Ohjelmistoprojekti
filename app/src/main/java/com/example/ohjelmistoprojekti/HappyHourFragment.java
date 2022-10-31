package com.example.ohjelmistoprojekti;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HappyHourFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_happy_hour, container, false);

        ListView listView = view.findViewById(R.id.HappHourList);
        List<String> list = new ArrayList<>();
        list.add("Dummy 1");
        list.add("Dummy 2");
        list.add("Dummy 3");
        list.add("Dummy 4");

        ArrayAdapter adapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, list );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View view, int position, long id) {
                if(position == 0) {
                    //Clicked on Dummy1
                } else if(position == 1) {
                    //Clicked on Dummy2
                } else if(position == 2) {
                    //Clicked on Dummy3
                } else if(position == 3) {
                    //Clicked on Dummy4
                }
            }
        });


        return view;
    }
}

