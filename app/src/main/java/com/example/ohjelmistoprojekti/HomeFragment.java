package com.example.ohjelmistoprojekti;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    ArrayList dummyData = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getData();
        BarChart barChart = (BarChart) view.findViewById(R.id.Chart1);
        BarDataSet barDataSet = new BarDataSet(dummyData, "Price Dummy");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);
        return view;
    }

    private void getData() {
        dummyData.add(new BarEntry(2f, 10));
        dummyData.add(new BarEntry(3f, 8));
        dummyData.add(new BarEntry(4f, 15));
        dummyData.add(new BarEntry(5f, 17));
        dummyData.add(new BarEntry(6f, 11));

    }


}