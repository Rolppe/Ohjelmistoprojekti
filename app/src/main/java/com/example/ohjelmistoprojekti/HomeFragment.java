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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    ArrayList BarToday = new ArrayList();
    ArrayList BarTomorrow = new ArrayList();
    double[] pricesToday;
    double[] pricesTomorrow;
    int size = 24;
    boolean state = false;
    Handler handler = new Handler();
    BarChart barChartToday;
    BarChart barChartTomorrow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //initializations and some configuration of graphs
        barChartToday = (BarChart) view.findViewById(R.id.Chart1);
        barChartToday.setFitBars(true);
        barChartToday.setDrawGridBackground(false);
        barChartToday.getDescription().setEnabled(false);

        barChartTomorrow = (BarChart) view.findViewById(R.id.Chart2);
        barChartTomorrow.setFitBars(true);
        barChartTomorrow.setDrawGridBackground(false);
        barChartTomorrow.getDescription().setEnabled(false);
        //if async data has not been gotten yet, get data
        if(state == false) {
            getPrices();
            //small delay to allow for the async function to run its course
            handler.postDelayed(new Runnable() {
                public void run() {
                    //creating a graph using async data
                    try {
                        BarDataSet barDataSet1 = new BarDataSet(BarToday, "Today");
                        BarData barData = new BarData(barDataSet1);
                        barDataSet1.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet1.setValueTextColor(Color.parseColor("#ceccd2"));
                        barDataSet1.setValueTextSize(8f);
                        barChartToday.setData(barData);
                        barChartToday.setDrawGridBackground(false);
                        barChartToday.getDescription().setEnabled(false);
                        barChartToday.notifyDataSetChanged();
                        barChartToday.invalidate();
                    } catch (NegativeArraySizeException negativeArraySizeException) {
                        negativeArraySizeException.printStackTrace();
                        Toast.makeText(getContext(),"Negative Array Size Exception.", Toast.LENGTH_SHORT);
                    }
                    //creating a graph using async data
                    try {
                        BarDataSet barDataSet2 = new BarDataSet(BarTomorrow, "Tomorrow");
                        BarData barData = new BarData(barDataSet2);
                        barDataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
                        barDataSet2.setValueTextColor(Color.parseColor("#ceccd2"));
                        barDataSet2.setValueTextSize(8f);
                        barChartTomorrow.setData(barData);
                        barChartTomorrow.setDrawGridBackground(false);
                        barChartTomorrow.getDescription().setEnabled(false);
                        barChartTomorrow.notifyDataSetChanged();
                        barChartTomorrow.invalidate();
                    } catch (NegativeArraySizeException negativeArraySizeException) {
                        negativeArraySizeException.printStackTrace();
                        Toast.makeText(getContext(),"Negative Array Size Exception.", Toast.LENGTH_SHORT);
                    }
                }
            }, 1000); }
        //creating a graph using local data
        try {
            BarDataSet barDataSet1 = new BarDataSet(BarToday, "Today");
            BarData barData = new BarData(barDataSet1);
            barDataSet1.setColors(ColorTemplate.MATERIAL_COLORS);
            barDataSet1.setValueTextColor(Color.parseColor("#ceccd2"));
            barDataSet1.setValueTextSize(8f);
            barChartToday.setData(barData);
            barChartToday.setDrawGridBackground(false);
            barChartToday.getDescription().setEnabled(false);
            barChartToday.notifyDataSetChanged();
            barChartToday.invalidate();
        } catch (NegativeArraySizeException negativeArraySizeException) {
            negativeArraySizeException.printStackTrace();
            Toast.makeText(getContext(),"Negative Array Size Exception.", Toast.LENGTH_SHORT);
        }
        //creating a graph using local data
        try {
            BarDataSet barDataSet2 = new BarDataSet(BarTomorrow, "Tomorrow");
            BarData barData = new BarData(barDataSet2);
            barDataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
            barDataSet2.setValueTextColor(Color.parseColor("#ceccd2"));
            barDataSet2.setValueTextSize(8f);
            barChartTomorrow.setData(barData);
            barChartTomorrow.setDrawGridBackground(false);
            barChartTomorrow.getDescription().setEnabled(false);
            barChartTomorrow.notifyDataSetChanged();
            barChartTomorrow.invalidate();
        } catch (NegativeArraySizeException negativeArraySizeException) {
            negativeArraySizeException.printStackTrace();
            Toast.makeText(getContext(),"Negative Array Size Exception.", Toast.LENGTH_SHORT);
        }
        return view;
    }
    //setting data for today's graph
    private ArrayList getBarToday(int count, double[] arr) {
        //converting double arrays into float arrays, some precision is lost due to this
        float[] temp = new float[arr.length];
        for (int j = 0 ; j < arr.length; j++)
            temp[j] = (float) arr[j];
        //adding data into local arraylist
        for( int i = 0; i < count; i++)
            BarToday.add(new BarEntry(i, temp[i]));
        return BarToday;
    }
    //setting data for tommorow's graph
    private ArrayList getBarTomorrow(int count, double[] arr) {
        //converting double arrays into float arrays, some precision is lost due to this
        float[] temp = new float[arr.length];
        for (int j = 0 ; j < arr.length; j++)
            temp[j] = (float) arr[j];
        //adding data into local arraylist
        for( int i = 0; i < count; i++)
            BarTomorrow.add(new BarEntry(i, temp[i]));
        return BarTomorrow;
    }
    //singleton function to get prices
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

                //toastPrices(pricesToday,"HomeFragment pricesToday: ", dateToday);
                //toastPrices(pricesTomorrow,"HomeFragment pricesTomorrow: ",dateTomorrow);

                getBarToday(size, pricesToday);
                getBarTomorrow(size, pricesTomorrow);
                state = true;
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