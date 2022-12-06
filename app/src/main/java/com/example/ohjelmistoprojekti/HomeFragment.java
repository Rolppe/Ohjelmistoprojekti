package com.example.ohjelmistoprojekti;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {

    ArrayList BarData = new ArrayList();
    ArrayList LineData = new ArrayList();
    double[] pricesToday;
    double[] pricesTomorrow;
    int size = 24;
    boolean state = false;
    Handler handler = new Handler();
    BarChart barChart;
    LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        barChart = (BarChart) view.findViewById(R.id.Chart1);
        barChart.setFitBars(true);
        barChart.setDrawGridBackground(false);
        barChart.getDescription().setEnabled(false);
        lineChart = (LineChart) view.findViewById(R.id.Chart2);
        if(state == false) {
            getPrices();
            handler.postDelayed(new Runnable() {
                public void run() {
                    try {
                        BarDataSet barDataSet = new BarDataSet(BarData, "Price Dummy");
                        BarData barData = new BarData(barDataSet);
                        barChart.setData(barData);
                        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        barDataSet.setValueTextColor(Color.RED);
                        barChart.setDrawGridBackground(false);
                        barChart.getDescription().setEnabled(false);
                        barChart.notifyDataSetChanged();
                        barChart.invalidate();
                    } catch (NegativeArraySizeException negativeArraySizeException) {
                        negativeArraySizeException.printStackTrace();
                        Toast.makeText(getContext(),"Negative Array Size Exception.", Toast.LENGTH_SHORT);
                    }
                    try {
                        LineDataSet lineDataSet = new LineDataSet(LineData, "Dummyline");
                        LineData lineData = new LineData((lineDataSet));
                        lineChart.setData(lineData);
                        lineData.setValueTextSize(10f);
                        lineDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
                        lineData.setValueTextColor(Color.RED);
                        lineDataSet.setLineWidth(5f);
                        lineDataSet.setValueTextSize(16f);
                        lineChart.setDrawGridBackground(false);
                        lineChart.getDescription().setEnabled(false);
                        lineChart.notifyDataSetChanged();
                        lineChart.invalidate();
                    } catch (NegativeArraySizeException negativeArraySizeException) {
                        negativeArraySizeException.printStackTrace();
                        Toast.makeText(getContext(),"Negative Array Size Exception.", Toast.LENGTH_SHORT);
                    }
                }
            }, 1000); }
        try {
            BarDataSet barDataSet = new BarDataSet(BarData, "Price Dummy");
            BarData barData = new BarData(barDataSet);
            barChart.setData(barData);
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet.setValueTextColor(Color.RED);
            barChart.setDrawGridBackground(false);
            barChart.getDescription().setEnabled(false);
            barChart.notifyDataSetChanged();
            barChart.invalidate();
        } catch (NegativeArraySizeException negativeArraySizeException) {
            negativeArraySizeException.printStackTrace();
            Toast.makeText(getContext(),"Negative Array Size Exception.", Toast.LENGTH_SHORT);
        }
        try {
            LineDataSet lineDataSet = new LineDataSet(LineData, "Dummyline");
            LineData lineData = new LineData((lineDataSet));
            lineChart.setData(lineData);
            lineData.setValueTextSize(10f);
            lineDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
            lineData.setValueTextColor(Color.RED);
            lineDataSet.setLineWidth(5f);
            lineDataSet.setValueTextSize(16f);
            lineChart.setDrawGridBackground(false);
            lineChart.getDescription().setEnabled(false);
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        } catch (NegativeArraySizeException negativeArraySizeException) {
            negativeArraySizeException.printStackTrace();
            Toast.makeText(getContext(),"Negative Array Size Exception.", Toast.LENGTH_SHORT);
        }
        return view;
    }

    private ArrayList getDataBar(int count, double[] arr) {
        float[] temp = new float[arr.length];
        for (int j = 0 ; j < arr.length; j++)
            temp[j] = (float) arr[j];

        for( int i = 0; i < count; i++)
            BarData.add(new BarEntry(i, temp[i]));
        return BarData;
    }

    private ArrayList getDataLine(int count, double[] arr) {

        float[] temp = new float[arr.length];
        for (int j = 0 ; j < arr.length; j++)
            temp[j] = (float) arr[j];

        for (int i=0; i<count; i++)
            LineData.add(new Entry(i, temp[i]));
        Collections.sort(LineData, new EntryXComparator());
        return LineData;
    }

    // Function to get prices from Singleton
    private void getPrices(){
        // Creating instance for singleton
        Singleton singleton = Singleton.getInstance(getActivity().getApplicationContext());
        // Getting data from singleton using Volley response listener
        singleton.getPriceData(getActivity().getApplicationContext(), new Singleton.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                // If bringing data creates error Toast the error
                Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onResponse(double[] aPricesToday, double[] aPricesTomorrow, String dateToday, String dateTomorrow) {
                // If get data. Saving pricesToday and Prices tomorrow
                pricesToday= aPricesToday;
                pricesTomorrow= aPricesTomorrow;

                // Toasting fetched data
                //toastPrices(pricesToday,"HomeFragment pricesToday: ", dateToday);
                //toastPrices(pricesTomorrow,"HomeFragment pricesTomorrow: ",dateTomorrow);

                getDataBar(size, pricesToday);
                getDataLine(size, pricesTomorrow);
                state = true;
            }
        });
    }

    // Function for toastin price arrays
    public void toastPrices(double[] pricesArray, String additionalText,String date) {
        // Create stringBuilder
        StringBuilder builder = new StringBuilder();
        // Append date to StringBuilder
        builder.append(" ").append(date).append(" ");
        // Append data from array to String builder
        for (double k : pricesArray) {
            builder.append(" ").append(k).append(" ");
        }

        // Toasting StringBuilder + additional text
        Toast.makeText(getActivity().getApplicationContext(), additionalText + builder, Toast.LENGTH_LONG).show();
    }
}