package com.kklv.bmoe.chart;

import android.content.Context;

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
 * 每小时票数折线图
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/2 1:53
 */
public class OneHourTicketsCountChart extends BaseChart {

    public OneHourTicketsCountChart(Context context, LineChart lineChart) {
        super(context, lineChart,R.string.one_hour_tickets_count_chart);
    }

    @Override
    protected List<String> getXVals(RoleDailyCount one) {

        List<String> xVals = super.getXVals(one);
        if (!ListUtils.isEmpty(xVals)) {
            xVals.remove(0);
        }

        return xVals;
    }

    protected List<Entry> getYVals(List<DataBean> list) {
        List<Entry> yVals = new ArrayList<>();

        int lastTimeCount = 0;
        for (int i = 1; i < list.size(); i++) {
            yVals.add(new Entry(new Float(list.get(i).getCount()) - lastTimeCount, i-1));
            lastTimeCount = Integer.parseInt(list.get(i).getCount());
        }
        return yVals;
    }

    @Override
    protected LineDataSet createLineDataSet(RoleDailyCount roleDailyCount) {
        List<DataBean> list = new ArrayList<>();
        list.addAll(roleDailyCount.getData());
        List<Entry> yVals = getYVals(list);

        return new LineDataSet(yVals, roleDailyCount.getName());
    }
}
