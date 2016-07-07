package com.kklv.bmoe.chart;

import android.content.Context;
import android.text.TextUtils;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.kklv.bmoe.R;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleDailyCount;

import java.util.ArrayList;
import java.util.List;

/**
 * 总票数折线图
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/7 11:45
 */
public class TotalTicketsCountChart extends BaseChart {
    private static final String TAG = "TotalTicketsCountChart";

    public TotalTicketsCountChart(Context context, LineChart lineChart) {
        super(context, lineChart,R.string.total_tickets_count_chart);
    }

    protected List<Entry> getYVals(List<DataBean> list) {
        List<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            yVals.add(new Entry(new Float(list.get(i).getCount()), i));
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
