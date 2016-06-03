package com.kklv.bmoe;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.kklv.bmoe.data.DataHelper;
import com.kklv.bmoe.data.HttpUtil;
import com.kklv.bmoe.object.RoleIntradayCount;

import java.util.ArrayList;

public class MainActivity extends Activity implements DataHelper.DataHelperCallBack{
    private static final String TAG = "MainActivity";
    private LineChart mLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataHelper dataHelper = DataHelper.getInstance(this);
        dataHelper.registerCallBack(this);
        dataHelper.getAllCamps();
        dataHelper.getCampRank("Fate/stay night [UBW]");
        dataHelper.getRoleIntradayCount("Saber", "16-01-03");

        bindId();
        initView();
    }

    private void bindId() {
        mLineChart = (LineChart) findViewById(R.id.lineChart);
    }

    private void initView() {

    }

    private void setData(RoleIntradayCount roleIntradayCount) {
        ArrayList<RoleIntradayCount.DataBean> list=roleIntradayCount.getData();
        Log.i(TAG,"数据长度："+list.size());
        ArrayList<String> xVals = new ArrayList<String>();
        for(int i=0;i<list.size();i++){
            xVals.add(list.get(i).getTime());
        }

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < list.size(); i++) {
            yVals.add(new Entry(new Float(list.get(i).getCount()),i));
        }

        LineDataSet set1;

        if (mLineChart.getData() != null &&
                mLineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mLineChart.getData().getDataSetByIndex(0);
            set1.setYVals(yVals);
            mLineChart.getData().setXVals(xVals);
            mLineChart.getData().notifyDataChanged();
            mLineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");

            // set1.setFillAlpha(110);
            // set1.setFillColor(Color.RED);

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);

            set1.setFillColor(Color.BLACK);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(xVals, dataSets);

            // set data
            mLineChart.setData(data);
        }
    }

    @Override
    public void onSuccess(RoleIntradayCount result) {
        setData(result);
        mLineChart.invalidate();
    }

    @Override
    public void onFailure(Exception error) {

    }
}
