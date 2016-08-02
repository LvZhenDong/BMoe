package com.kklv.bmoe.chart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleDailyCount;

import java.util.ArrayList;
import java.util.List;

/**
 * 每小时得票数
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/8 9:23
 */
public class OneHourTicketsCountSetCreator extends LineDataSetCreator {
    public OneHourTicketsCountSetCreator() {
        super("每小时票数", 1);
    }

    @Override
    LineDataSet createLineDataSet(RoleDailyCount roleDailyCount) {
        List<DataBean> list = new ArrayList<>();
        list.addAll(roleDailyCount.getData());
        List<Entry> yVals = getYVals(list);

        return new LineDataSet(yVals, roleDailyCount.getName());
    }

    private static List<Entry> getYVals(List<DataBean> list) {
        List<Entry> yVals = new ArrayList<>();

        int lastTimeCount = 0;
        for (int i = 1; i < list.size(); i++) {
            yVals.add(new Entry(new Float(list.get(i).getCount()) - lastTimeCount, i - 1));
            lastTimeCount = list.get(i).getCount();
        }
        return yVals;
    }
}
