package com.kklv.bmoe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.kklv.bmoe.data.DataHelper;
import com.kklv.bmoe.object.RoleIntradayCount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        Map<String,String> map=new HashMap<>();
        map.put("date","15-12-18");
//        map.put("sex","0");
        map.put("group","G1"); //有一段时间是A1后面变成1-A
        dataHelper.getRoleIntradayCount(map);

        bindId();
        initView();
    }

    private void bindId() {
        mLineChart = (LineChart) findViewById(R.id.lineChart);
    }

    private void initView() {
        mLineChart.setDescription("总票数折线图");
        mLineChart.setNoDataText("数据加载中...");
        mLineChart.setDescriptionTextSize(20.0f);
        mLineChart.getLegend().setWordWrapEnabled(true);    //label自动换行
        YAxis rightAxis=mLineChart.getAxisRight();
        rightAxis.setEnabled(false);    //右边Y轴不显示

        YAxis leftAxis=mLineChart.getAxisLeft();
        leftAxis.setAxisMinValue(0.0f); //Y轴从0开始

    }

    private void setData(ArrayList<RoleIntradayCount> list) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        Log.i(TAG,"list.size:"+list.size());
        int[] colors=getResources().getIntArray(R.array.lineChart);
        for (RoleIntradayCount item:list) {
            int i=list.indexOf(item);
            dataSets.add(createLineDataSet(item,colors[i]));
        }
        LineData data = new LineData(getXVals(list.get(0)), dataSets);
        // set data
        mLineChart.setData(data);
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
    }

    private ArrayList<String> getXVals(RoleIntradayCount one){
        ArrayList<RoleIntradayCount.DataBean> list=one.getData();
        ArrayList<String> xVals = new ArrayList<String>();
        for(int i=0;i<list.size();i++){
            xVals.add(list.get(i).getTime());
        }
        return xVals;
    }

    private LineDataSet createLineDataSet(RoleIntradayCount roleIntradayCount,int color){
        LineDataSet set;
        ArrayList<RoleIntradayCount.DataBean> list=roleIntradayCount.getData();
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < list.size(); i++) {
            yVals.add(new Entry(new Float(list.get(i).getCount()),i));
        }

        set = new LineDataSet(yVals, roleIntradayCount.getName());
//            set.enableDashedLine(10f, 5f, 0f);       //设置虚线
//            set.enableDashedHighlightLine(10f, 5f, 0f);
        set.setColor(color);
        set.setCircleColor(color);
        set.setValueTextColor(color);
        set.setLineWidth(1f);
        set.setCircleRadius(3f);
        set.setDrawCircleHole(false);  //点是实心的
        set.setValueTextSize(9f);
        set.setDrawFilled(false);  //单纯的line，line下面不覆盖颜色
        return set;
    }
    @Override
    public void onSuccess(ArrayList<RoleIntradayCount> list) {
        if(list != null){
            setData(list);
        }else {
            Toast.makeText(this,"没有数据",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFailure(Exception error) {

    }
}
