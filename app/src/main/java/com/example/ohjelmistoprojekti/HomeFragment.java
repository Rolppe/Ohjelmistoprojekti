package com.example.ohjelmistoprojekti;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {

    ArrayList dummyDataArrayList = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getData();
        ((MainActivity) requireActivity()).createChart(dummyDataArrayList);
        return view;
    }

    public void getData() {
        dummyDataArrayList.add(new BarEntry(2f, 10));
        dummyDataArrayList.add(new BarEntry(3f, 8));
        dummyDataArrayList.add(new BarEntry(4f, 15));
        dummyDataArrayList.add(new BarEntry(5f, 17));
        dummyDataArrayList.add(new BarEntry(6f, 11));

    }
}
