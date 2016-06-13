package com.kklv.bmoe.chart;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.kklv.bmoe.R;
import com.kklv.bmoe.data.DataHelper;
import com.kklv.bmoe.object.Camp;
import com.kklv.bmoe.object.RoleIntradayCount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/7 11:45
 */
public class Chart implements DataHelper.DataHelperCallBack{
    private static final String TAG = "Chart";
    private LineChart mLineChart;
    private DataHelper mDataHelper;
    private Context mContext;

    public Chart(Context context,LineChart lineChart){
        this.mContext=context;
        this.mLineChart=lineChart;
        mDataHelper=DataHelper.getInstance(mContext);
        mDataHelper.registerCallBack(this);

        initLineChart();
    }
    public void showData(){
//        mDataHelper.getAllCamps();
//        mDataHelper.getCampRank("Fate/stay night [UBW]");
        Map<String,String> map=new HashMap<>();
        map.put("date","15-12-18");
//        map.put("sex","0");
        map.put("group","G1"); //有一段时间是A1后面变成1-A
        mDataHelper.getRoleIntradayCount(map);
    }

    private void initLineChart(){
        mLineChart.setDescription(mContext.getString(R.string.count_line_chart));
        mLineChart.setNoDataText(mContext.getString(R.string.data_loading));
        mLineChart.setDescriptionTextSize(20.0f);
        mLineChart.getLegend().setWordWrapEnabled(true);    //label自动换行
        mLineChart.animateY(2000);
        YAxis rightAxis=mLineChart.getAxisRight();
        rightAxis.setEnabled(false);    //右边Y轴不显示

        YAxis leftAxis=mLineChart.getAxisLeft();
        leftAxis.setAxisMinValue(0.0f); //Y轴从0开始

        XAxis xAxis=mLineChart.getXAxis();
    }
    /**
     * 设置图表数据
     * @param list
     */
    private void setData(ArrayList<RoleIntradayCount> list) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        Log.i(TAG,"list.size:"+list.size());
        int[] colors=mContext.getResources().getIntArray(R.array.lineChart);
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
    /**
     * 生成一条折线
     * @param roleIntradayCount
     * @param color 折线颜色
     * @return
     */
    private LineDataSet createLineDataSet(RoleIntradayCount roleIntradayCount, int color){
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

    /**
     * 根据一条折线数据生成X轴
     * @param one 一个角色当天的数据
     * @return
     */
    private ArrayList<String> getXVals(RoleIntradayCount one){
        ArrayList<RoleIntradayCount.DataBean> list=one.getData();
        ArrayList<String> xVals = new ArrayList<String>();
        for(int i=0;i<list.size();i++){
            xVals.add(list.get(i).getTime());
        }
        return xVals;
    }

    @Override
    public <T> void onSuccess(ArrayList<T> result) {
        if(result != null){
            setData((ArrayList<RoleIntradayCount>) result);
        }else {
            Toast.makeText(mContext, R.string.no_data,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Exception error) {
        Toast.makeText(mContext, R.string.net_error,Toast.LENGTH_SHORT).show();
    }

}
