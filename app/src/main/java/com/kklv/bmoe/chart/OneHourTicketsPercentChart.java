package com.kklv.bmoe.chart;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.kklv.bmoe.R;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleDailyCount;
import com.kklv.bmoe.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 每小时得票率折线图
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/4 15:50
 */
public class OneHourTicketsPercentChart extends BaseChart {

    private static final String TAG = "HourTicketsPercentChart";

    public OneHourTicketsPercentChart(Context context, LineChart lineChart) {
        super(context, lineChart, R.string.one_hour_tickets_percent_chart);
    }

    @Override
    protected List<String> getXVals(RoleDailyCount one) {

        List<String> xVals = super.getXVals(one);
        if (!ListUtils.isEmpty(xVals)) {
            xVals.remove(0);
        }

        return xVals;
    }

    @Override
    protected LineDataSet createLineDataSet(RoleDailyCount roleDailyCount) {
        List<DataBean> list = new ArrayList<>();
        list.addAll(roleDailyCount.getData());
        String group =roleDailyCount.getGroup();
        List<Entry> yVals = getYVals(list,roleDailyCount.getSex()+(TextUtils.isEmpty(group) ? "" :group));//不然是"1null"或者"0null"

        return new LineDataSet(yVals, roleDailyCount.getName());
    }

    protected List<Entry> getYVals(List<DataBean> list,String tag) {
        List<Integer> totalList = mSexAndGroupsMap.get(tag);
        if (ListUtils.isEmpty(totalList)) return null;
        List<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < list.size() - 1; i++) {
            float percent = 0.0f;
            float den = totalList.get(i);
            if (den > 0) {
                float mem = Integer.parseInt(list.get(i + 1).getCount())
                        - Integer.parseInt(list.get(i).getCount());
                percent = mem / den;
            }
            yVals.add(new Entry(new Float(percent * 100), i));
        }

        return yVals;
    }

    @Override
    public void setData() {
        getTotalList();
        super.setData();
    }
}
