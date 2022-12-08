package com.example.ohjelmistoprojekti;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.ArrayList;

public class HappyHourFragment extends Fragment {

    ArrayList<HappyHourItem> happyHourList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button addProg_btn;

    double[] pricesToday;
    double[] pricesTomorrow;

    private String title = "";
    private String from = "";
    private String to = "";
    private Integer pLength = 1;
    double[] temp = new double[]{};
    int size = 24;

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
        TextInputLayout til = (TextInputLayout) view.findViewById(R.id.text_input_layout);
        final TextInputEditText inputTitle = (TextInputEditText) view.findViewById(R.id.enterTitle);
        final TimePicker inputFrom = (TimePicker) view.findViewById(R.id.enterFrom);
        inputFrom.setIs24HourView(true);
        final TimePicker inputTo = (TimePicker) view.findViewById(R.id.enterTo);
        inputTo.setIs24HourView(true);
        final NumberPicker inputLength = (NumberPicker) view.findViewById(R.id.enterPrgrmLgth);
        //limiting user given program length
        inputLength.setMinValue(1);
        inputLength.setMaxValue(3);
        //building the dialog
         builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //get data from dialog
                        //getting title from user input
                        title = inputTitle.getText().toString();
                        //function was still in development and doesn't work at turn in time for project
                        //intended error message doesn't show if title left empty, and allows for data to be sent to card without title
                        inputTitle.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(inputTitle.getText().toString().length() <= 0) {
                                    inputTitle.setError("You need to enter a Title");
                                }
                                else {
                                    inputTitle.setError(null);
                                }
                            }
                        });
                        //getting  time from user input
                        int fHour = inputFrom.getHour();
                        int fMinute = inputFrom.getMinute();
                        from = (fHour + ":" + fMinute);
                        //getting  time from user input
                        int iHour = inputTo.getHour();
                        int iMinute = inputTo.getMinute();
                        to = (iHour + ":" + iMinute);
                        //getting program length
                        pLength = inputLength.getValue();

                        //transforming data into strings
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
        //showing dialog
        dialog.show();

    }

    // finding minimum sum of a subarray of size = length of program
        double[] minSum(double arr[], int arrLength, int pLength) {
            // Initialize result
            double min_sum = 1000;
            double returnIndex = 0;
            // Consider all blocks starting with i.
            for (int i = 0; i < arrLength - pLength + 1; i++) {
                int current_sum = 0;
                for (int j = 0; j < pLength; j++)
                    current_sum = (int) (current_sum + arr[i + j]);
                    // Update result if required.
                if (current_sum < min_sum) {
                    min_sum = current_sum;
                    returnIndex = Double.valueOf(i);
                }
            }
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
                temp = minSum(pricesToday, size, pLength);
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


