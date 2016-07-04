package com.kklv.bmoe.chart;

import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.kklv.bmoe.R;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleDailyCount;

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
    protected List<Entry> getYVals(List<DataBean> list) {
        List<Entry> yVals = new ArrayList<>();

        int lastTimeCount = 0;
        for (int i = 0; i < list.size(); i++) {
            yVals.add(new Entry(new Float(list.get(i).getCount()) - lastTimeCount, i));
            lastTimeCount = Integer.parseInt(list.get(i).getCount());
        }
        return yVals;
    }
}
