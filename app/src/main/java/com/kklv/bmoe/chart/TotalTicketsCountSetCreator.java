package com.kklv.bmoe.chart;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleDailyCount;

import java.util.ArrayList;
import java.util.List;

/**
 *总票数折线图
 * 
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/8 8:52
 */
public class TotalTicketsCountSetCreator extends LineDataSetCreator {

    public TotalTicketsCountSetCreator() {
        super("总票数折线图",0 );
    }

    /**
     * 绘制一条Y轴
     *
     * @param roleDailyCount
     * @return
     */
    @Override
    public LineDataSet createLineDataSet(RoleDailyCount roleDailyCount) {
        List<DataBean> list = new ArrayList<>();
        list.addAll(roleDailyCount.getData());
        List<Entry> yVals = getYVals(list);

        return new LineDataSet(yVals, roleDailyCount.getName());
    }

    private List<Entry> getYVals(List<DataBean> list) {
        List<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            yVals.add(new Entry(new Float(list.get(i).getCount()), i));
        }

        return yVals;
    }
}
