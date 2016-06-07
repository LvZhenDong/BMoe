package com.kklv.bmoe;

import android.app.Activity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.kklv.bmoe.chart.Chart;

public class MainActivity extends Activity{
    private static final String TAG = "MainActivity";
    private LineChart mLineChart;
    private Chart mChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindId();
        initView();

    }

    private void bindId() {
        mLineChart = (LineChart) findViewById(R.id.lineChart);
    }

    private void initView() {
        mChart=new Chart(this,mLineChart);
        mChart.showData();
    }

}
