package com.example.ohjelmistoprojekti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;

import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class HappyHourFragment extends Fragment {

    ArrayList<HappyHourItem> happyHourList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button addProg_btn;
    double[] pricesToday;
    double[] pricesTomorrow;
    private String timeframe ="";
    private String title = "";
    private String from = "";
    private String to = "";
    private String result ="";
    private Integer pLength = 1;
    private String price="";
    double[] temp = new double[]{};

    private static final DecimalFormat df = new DecimalFormat("00.00");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_happy_hour, container, false);
        //Async get prices calling on singleton
        getPrices();
        // initializing variables
        mRecyclerView = view.findViewById(R.id.hhRecyclerView);
        //Button init
        addProg_btn = (Button) view.findViewById(R.id.addProgramButton);
        //Giving RV fixed size
        mRecyclerView.setHasFixedSize(true);
        //New layoutmanager
        mLayoutManager = new LinearLayoutManager(getActivity());
        //adding list to adapter
        mAdapter = new HH_RecyclerViewAdapter(getActivity(), happyHourList);
        //setting adapter to recycler view
        mRecyclerView.setAdapter(mAdapter);
        //Changeing RV based on layoutmanager
        mRecyclerView.setLayoutManager(mLayoutManager);
        //button listener
        addProg_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //calling on creteDialog
                createDialog();
            }
        });

        return view;
    }

    public void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("");
        //new view based on dialog layout
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog, (ViewGroup) getView(), false);
        //Initializations
        final TextInputLayout til = (TextInputLayout) view.findViewById(R.id.text_input_layout);
        final TextInputEditText inputTitle = (TextInputEditText) view.findViewById(R.id.enterTitle);
        final NumberPicker inputFrom = (NumberPicker) view.findViewById(R.id.enterFrom);
        //limiting user given time frame to a 24 hour period
        inputFrom.setMinValue(0);
        inputFrom.setMaxValue(23);
        final NumberPicker inputTo = (NumberPicker) view.findViewById(R.id.enterTo);
        //limiting user given time frame to a 24 hour period
        inputTo.setMinValue(0);
        inputTo.setMaxValue(23);
        final NumberPicker inputLength = (NumberPicker) view.findViewById(R.id.enterPrgrmLgth);
        //limiting user given program length
        inputLength.setMinValue(1);
        inputLength.setMaxValue(3);

        //building the dialog
         builder.setView(view)
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setCancelable(true);
        final AlertDialog d = builder.create();

        d.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialog) {
                                    Button b = d.getButton(AlertDialog.BUTTON_POSITIVE);
                                    b.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            //if title input field is empty show error
                                            if (inputTitle.length() == 0) {
                                                inputTitle.setError("You need to enter a Title");
                                            } else {
                                                //if title input field is not empty, dismiss error
                                                inputTitle.setError(null);
                                                //getting title from user input
                                                title = inputTitle.getText().toString();
                                                //getting start time from user input
                                                int fHour = inputFrom.getValue();
                                                //getting goal time from user input
                                                int tHour = inputTo.getValue();
                                                pLength = inputLength.getValue();
                                                //user error conditions
                                                //if goal time is before start time send error toast
                                                if(tHour < fHour && fHour != 23) {
                                                    Toast.makeText(getActivity().getApplicationContext(), "Please set start time to be lower than end time", Toast.LENGTH_SHORT).show();
                                                }
                                                //if program length is higher than the total amount of hours in the time frame send error
                                                else if(pLength > tHour - fHour && fHour != 23) {
                                                    Toast.makeText(getActivity().getApplicationContext(), "Program duration must be lower than the timeframe", Toast.LENGTH_SHORT).show();
                                                }
                                                else if (pLength == 1 && tHour == 0){
                                                    //set from, to and timeframe value as strings
                                                    from = (fHour + ":" + "00");
                                                    to = (tHour + ":" + "00");
                                                    timeframe = from + " - " + to;
                                                    //calc minimum sum
                                                    temp = minSum(pricesToday, pLength, fHour, tHour);
                                                    //reassign from and to into minimum sum answers
                                                    from = (int) temp[1] + ":" + "00";
                                                    //if calculated index is 23 then result is the same as the timeframe
                                                    if(temp[1] > 22) {
                                                        result = timeframe;
                                                    } else {
                                                        //otherwise set values normally
                                                        to = (int) temp[1] + pLength + ":" + "00";
                                                        result = from + " - " + to;
                                                    }
                                                    //set price as minsum result in correct format
                                                    price = df.format(temp[0]);

                                                    //update RecyclerView
                                                    HappyHourItem happyHourItem = new HappyHourItem(title, timeframe, result, price);
                                                    happyHourList.add(happyHourItem);
                                                    mAdapter.notifyDataSetChanged();
                                                    d.dismiss();
                                                } else {
                                                    //set from, to and timeframe value as strings
                                                    from = (fHour + ":" + "00");
                                                    to = (tHour + ":" + "00");
                                                    timeframe = from + " - " + to;
                                                    //calc minimum sum
                                                    temp = minSum(pricesToday, pLength, fHour, tHour);
                                                    //reassign from and to into minimum sum answers
                                                    from = (int) temp[1] + ":" + "00";
                                                    to = (int) temp[1] + pLength + ":" + "00";
                                                    //set the time result
                                                    result = from + " - " + to;
                                                    //set price as minsum result in correct format
                                                    price = df.format(temp[0]);

                                                //update RecyclerView
                                                HappyHourItem happyHourItem = new HappyHourItem(title, timeframe, result, price);
                                                happyHourList.add(happyHourItem);
                                                mAdapter.notifyDataSetChanged();
                                                d.dismiss();
                                                }
                                        }
                                    }
                                    });
                                }
                            });
        //showing dialog
        d.show();
    }

    // finding minimum sum of a subarray of size = length of program
        double[] minSum(double arr[], int pLength, int start, int end) {
            // Initialize result
            double min_sum = 1000.0;
            double returnIndex = 0;
            double current_sum;
            String fuck ="";
            int diff = end - start;
            //if user input time is higher than 23 + user input program length
            //sum is value at arr[23]
            //and index is start
            if(start + pLength > 23 && pLength < 2) {
                min_sum = arr[23];
                returnIndex = start;
            }
            //if the difference between the end and start is teh same as the program length
            //do the minimum sum sort
            //this is a very ghetto solution, but I've been toiling away at this for almost 6 hours now
            if (diff == pLength) {
                for (int a = start; a < end; a++) {
                    current_sum = 0.0;
                    for (int b = 0; b < pLength; b++)
                        current_sum = (current_sum + arr[a + b]);
                    // Update result if required.
                    if (current_sum < min_sum) {
                        min_sum = current_sum;
                        returnIndex = Double.valueOf(start);
                    }
                }
            }
            //do the normal minimum sum sort
            else {
                // Consider all blocks starting from i.
                for (int i = start; i < end - pLength; i++) {
                    current_sum = 0.0;
                    for (int j = 0; j < pLength; j++)
                        current_sum = (current_sum + arr[i + j]);
                    // Update result if required.
                    if (current_sum < min_sum) {
                        min_sum = current_sum;
                        returnIndex = Double.valueOf(i);
                    }
                }
            }
            min_sum = min_sum/pLength;
            double[] tempArray = {min_sum, returnIndex};
            return tempArray;
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
}


