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
        super(context, lineChart);
        mChartDescription = context.getString(R.string.one_hour_tickets_count_chart);
    }

    /**
     * 生成一条每小时票数折线
     *
     * @param roleDailyCount
     * @param color
     * @return
     */
    @Override
    protected LineDataSet createLineDataSet(RoleDailyCount roleDailyCount, int color) {
        LineDataSet set;
        List<DataBean> list = new ArrayList<>();
        list.addAll(roleDailyCount.getData());
        List<Entry> yVals = new ArrayList<>();

        int lastTimeCount = 0;
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                yVals.add(new Entry(new Float(list.get(0).getCount()), i));
                lastTimeCount = Integer.parseInt(list.get(0).getCount());
            } else {
                yVals.add(new Entry(new Float(list.get(i).getCount()) - lastTimeCount, i));
                lastTimeCount = Integer.parseInt(list.get(i).getCount());
            }

        }
        set = new LineDataSet(yVals, roleDailyCount.getName());
        setSetType(set, color);

        return set;
    }


}
