package com.kklv.bmoe.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.kklv.bmoe.R;
import com.kklv.bmoe.chart.Chart;


public class LineChartFragment extends Fragment {
    private LineChart mLineChart;
    private Chart mChart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_line_chart, container, false);

        bindId(view);
        initView();

        return view;
    }

    private void bindId(View view){
        mLineChart= (LineChart) view.findViewById(R.id.lineChart);
    }

    private void initView(){
        mChart=new Chart(getActivity(),mLineChart);
        mChart.showData();
    }
}
