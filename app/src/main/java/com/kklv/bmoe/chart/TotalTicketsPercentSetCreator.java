package com.kklv.bmoe.chart;

import android.text.TextUtils;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleDailyCount;
import com.kklv.bmoe.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *总得票率
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/11 9:05
 */
public class TotalTicketsPercentSetCreator extends LineDataSetCreator {
    public TotalTicketsPercentSetCreator() {
        super("总得票率折线图", 1);
    }

    @Override
    LineDataSet createLineDataSet(RoleDailyCount roleDailyCount) {
        List<DataBean> list = new ArrayList<>();
        list.addAll(roleDailyCount.getData());
        String group =roleDailyCount.getGroup();
        List<Entry> yVals = getYVals(list,roleDailyCount.getSex()+(TextUtils.isEmpty(group) ? "" :group));//不然是"1null"或者"0null"

        return new LineDataSet(yVals, roleDailyCount.getName());
    }

    private List<Entry> getYVals(List<DataBean> list, String tag) {
        List<Integer> totalList = mTotalSexAndGroupsMap.get(tag);
        if (ListUtils.isEmpty(totalList)) return null;
        List<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < list.size() - 1; i++) {
            float percent = 0.0f;
            float den = totalList.get(i);
            if (den > 0) {
                float mem = list.get(i + 1).getCount();
                percent = mem / den;
            }
            yVals.add(new Entry(new Float(percent * 100), i));
        }

        return yVals;
    }
}
