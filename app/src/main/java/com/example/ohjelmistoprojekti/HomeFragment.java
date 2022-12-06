package com.example.ohjelmistoprojekti;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class HomeFragment extends Fragment {

    ArrayList dummyBarData = new ArrayList();
    double[] pricesToday;
    double[] pricesTomorrow;
    List<ILineDataSet> dummyLineData = new ArrayList<>();
    Random random = new Random();
    //Context context;
    int max = 10;
    int min = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getPrices();
        dummyBarData.removeAll(dummyLineData);
        dummyLineData.removeAll(dummyLineData);
        try {
            getDataBar();
            BarChart barChart = (BarChart) view.findViewById(R.id.Chart1);
            BarDataSet barDataSet = new BarDataSet(dummyBarData, "Price Dummy");
            BarData barData = new BarData(barDataSet);
            barChart.setData(barData);
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet.setValueTextColor(Color.RED);
            barChart.setDrawGridBackground(false);
            barChart.getDescription().setEnabled(false);
        } catch (NegativeArraySizeException negativeArraySizeException) {
            negativeArraySizeException.printStackTrace();
        }

            ArrayList<Entry> values = new ArrayList<>();
            for (int i=0; i<10; i++){
                values.add(new Entry(i, random.nextInt(max - min + 1)+ min));
            }

        try {
            LineChart lineChart = (LineChart) view.findViewById(R.id.Chart2);
            LineDataSet lineDataSet = new LineDataSet(values, "Dummyline");
            LineData lineData = new LineData((lineDataSet));
            lineDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
            lineDataSet.setLineWidth(5f);
            lineDataSet.setValueTextSize(16f);

            dummyLineData.add(lineDataSet);

            LineData data = new LineData(dummyLineData);
            data.setValueTextColor(Color.RED);
            data.setValueTextSize(16f);
            lineChart.setData(data);
            lineChart.setDrawGridBackground(false);
            lineChart.getDescription().setEnabled(false);
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        } catch (NegativeArraySizeException negativeArraySizeException) {
            negativeArraySizeException.printStackTrace();
        }
        System.out.println("Negative Array Size Exception.");

        return view;
    }

    private void getDataBar() {
        dummyBarData.add(new BarEntry(1f, 9));
        dummyBarData.add(new BarEntry(2f, 10));
        dummyBarData.add(new BarEntry(3f, 8));
        dummyBarData.add(new BarEntry(4f, 15));
        dummyBarData.add(new BarEntry(5f, 17));
        dummyBarData.add(new BarEntry(6f, 11));
        dummyBarData.add(new BarEntry(7f, 10));
        dummyBarData.add(new BarEntry(8f, 13));
        dummyBarData.add(new BarEntry(9f, 14));
        dummyBarData.add(new BarEntry(10f, 23));
        dummyBarData.add(new BarEntry(11f, 11));
        dummyBarData.add(new BarEntry(12f, 18));
        dummyBarData.add(new BarEntry(13f, 8));
        dummyBarData.add(new BarEntry(14f, 9));
        dummyBarData.add(new BarEntry(15f, 14));
        dummyBarData.add(new BarEntry(16f, 11));
        dummyBarData.add(new BarEntry(17f, 16));
        dummyBarData.add(new BarEntry(18f, 21));
        dummyBarData.add(new BarEntry(19f, 17));
        dummyBarData.add(new BarEntry(20f, 11));
        dummyBarData.add(new BarEntry(21f, 8));
        dummyBarData.add(new BarEntry(22f, 12));
        dummyBarData.add(new BarEntry(23f, 17));
        dummyBarData.add(new BarEntry(24f, 17));
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
                toastPrices(pricesToday,"HomeFragment pricesToday: ", dateToday);
                toastPrices(pricesTomorrow,"HomeFragment pricesTomorrow: ",dateTomorrow);

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