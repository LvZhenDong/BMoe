package com.kklv.bmoe.chart;

import android.content.Context;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
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
    protected List<Entry> getYVals(List<DataBean> list) {
        //TODO 怎么来传入正确的List<RoleDailyCount>
        List<Integer> totalList = getOneHourTotalCountsList(mSplitLists.get(mShowingSplitListId));
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

    /**
     * 得到一个每小时的总票数的list
     *
     * @param roleDailyCounts
     * @return
     */
    private List<Integer> getOneHourTotalCountsList(List<RoleDailyCount> roleDailyCounts) {
        List<Integer> totalList = new ArrayList<>();
        for (int i = 1; i < roleDailyCounts.get(0).getData().size(); i++) {
            int total = 0;
            for (int j = 0; j < roleDailyCounts.size(); j++) {
                List<DataBean> list = new ArrayList<>();
                list.addAll(roleDailyCounts.get(j).getData());
                total += (Integer.parseInt(list.get(i).getCount())
                        - Integer.parseInt(list.get(i - 1).getCount()));
            }
            totalList.add(total);
            Log.i(TAG, "total:" + total);
        }
        return totalList;
    }


}
