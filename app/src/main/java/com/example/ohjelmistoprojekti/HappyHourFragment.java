package com.example.ohjelmistoprojekti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

public class HappyHourFragment extends Fragment {

    ArrayList<HappyHourItem> happyHourList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button addProg_btn;

    private String title = "";
    private String from = "";
    private String to = "";
    private Integer pLength = 1;
    int arr[] = new int[]{1, 4, 5, 10, 2, 3, 1, 7, 20, 11, 44, 32, 0, 24, 17, 3, 47, 0, 0, 13, 17, 22, 24, 27};
                        //0  1  2  3   4  5  6  7  8   9   10  11  12  13 14 15  16  17 18 19  20  21  22  23

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_happy_hour, container, false);

        // initializing variables
        mRecyclerView = view.findViewById(R.id.hhRecyclerView);
        addProg_btn = (Button) view.findViewById(R.id.addProgramButton);

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
        final NumberPicker inputLength = (NumberPicker) view.findViewById(R.id.enterPrgrmLgth);
        inputLength.setMinValue(1);
        inputLength.setMaxValue(3);

         builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //get data from dialog

                        if(inputTitle.length() == 0) {
                            inputTitle.setError("Please input Title!");
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT);
                        }
                        title = inputTitle.getText().toString();
                        int fHour = inputFrom.getHour();
                        int fMinute = inputFrom.getMinute();
                        from = (fHour + ":" + fMinute);
                        int iHour = inputTo.getHour();
                        int iMinute = inputTo.getMinute();
                        to = (iHour + ":" + iMinute);

                        pLength = inputLength.getValue();
                        double[] temp = minSum(arr, arr.length, pLength);

                        from = (int) temp[1] + ":" + "00";
                        to = (int)temp[1] + pLength + ":" + "00";

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


    // finding minimum sum of a subarray of size = length of program
        double[] minSum(int arr[], int arrLength, int pLength) {
            // Initialize result
            double min_sum = 1000;
            double returnIndex = 0;
            // Consider all blocks starting with i.
            for (int i = 0; i < arrLength - pLength + 1; i++) {
                int current_sum = 0;
                for (int j = 0; j < pLength; j++)
                    current_sum = current_sum + arr[i + j];
                    // Update result if required.
                    if (current_sum < min_sum) {
                        min_sum = current_sum;
                        returnIndex = Double.valueOf(i);
                    }
            }
            double[] tempArray = {min_sum, returnIndex};
            return tempArray;
        }
}

