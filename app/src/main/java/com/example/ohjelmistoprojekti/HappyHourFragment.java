package com.example.ohjelmistoprojekti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class HappyHourFragment extends Fragment {

    ArrayList<HappyHourItem> happyHourList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button addProg_btn;

    private String title = "1";
    private String from = "2";
    private String to = "3";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_happy_hour, container, false);

        // initializing variables
        mRecyclerView = view.findViewById(R.id.hhRecyclerView);
        addProg_btn = (Button) view.findViewById(R.id.addProgramButton);
        happyHourList = new ArrayList<>();

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        // adding list to adapter
        mAdapter = new HH_RecyclerViewAdapter(getActivity(), happyHourList);
        // setting adapter to recycler view
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

        addProg_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });

        return view;
    }

    public void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Add New Program");

        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog, (ViewGroup) getView(), false);

        final EditText inputTitle = (EditText) view.findViewById(R.id.enterTitle);
        final TimePicker inputFrom = (TimePicker) view.findViewById(R.id.enterFrom);
        inputFrom.setIs24HourView(true);
        final TimePicker inputTo = (TimePicker) view.findViewById(R.id.enterTo);
        inputTo.setIs24HourView(true);

         builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //get data from dialog
                        title = inputTitle.getText().toString();
                        int fHour = inputFrom.getHour();
                        int fMinute = inputFrom.getMinute();
                        from = (fHour + ":" + fMinute);
                        int iHour = inputTo.getHour();
                        int iMinute = inputTo.getMinute();
                        to = (iHour + ":" + iMinute);

                        //update RecyclerView
                        HappyHourItem happyHourItem = new HappyHourItem(title, from, to);
                        happyHourList.add(happyHourItem);
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setCancelable(true);

        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}

