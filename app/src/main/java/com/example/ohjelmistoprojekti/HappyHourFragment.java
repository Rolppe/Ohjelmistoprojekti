package com.example.ohjelmistoprojekti;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class HappyHourFragment extends Fragment implements HH_Dialog.HH_DialogListener{

    private ArrayList<HappyHourItem> happyHourList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_happy_hour, container, false);
        button = (Button) view.findViewById(R.id.addProgramButton);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        setExampleItems();

        mRecyclerView = view.findViewById(R.id.hhRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        // Adapter
        mAdapter = new HH_RecyclerViewAdapter(getActivity(), happyHourList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        return view;
    }

    public void openDialog(){
        HH_Dialog hh_dialog = new HH_Dialog();
        hh_dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }

    @Override
    public void applyTexts(String title, String from, String to) {

    }

    private void setExampleItems() {
        happyHourList = new ArrayList<>();
        happyHourList.add(new HappyHourItem("Title 1", "10:00", "12:00"));
        happyHourList.add(new HappyHourItem("Title 2", "12:00", "14:00"));
        happyHourList.add(new HappyHourItem("Title 3", "14:00", "16:00"));
        happyHourList.add(new HappyHourItem("Title 4", "11:00", "13:00"));
        happyHourList.add(new HappyHourItem("Title 5", "13:00", "15:00"));
        happyHourList.add(new HappyHourItem("Title 6", "15:00", "17:00"));
        happyHourList.add(new HappyHourItem("Title 7", "10:00", "12:00"));
        happyHourList.add(new HappyHourItem("Title 8", "12:00", "14:00"));
        happyHourList.add(new HappyHourItem("Title 9", "14:00", "16:00"));
        happyHourList.add(new HappyHourItem("Title 10", "11:00", "13:00"));
        happyHourList.add(new HappyHourItem("Title 11", "13:00", "15:00"));
        happyHourList.add(new HappyHourItem("Title 12", "15:00", "17:00"));
        //a
    }


}

