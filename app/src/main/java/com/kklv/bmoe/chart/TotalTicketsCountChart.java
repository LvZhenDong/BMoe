package com.kklv.bmoe.chart;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.kklv.bmoe.R;
import com.kklv.bmoe.data.DataHelper;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleDailyCount;
import com.kklv.bmoe.utils.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
        super(context, lineChart);
        mChartDescription = context.getString(R.string.total_tickets_count_chart);
    }

    /**
     * 生成一条总票数折线
     *
     * @param roleDailyCount
     * @param color          折线颜色
     * @return
     */
    @Override
    protected LineDataSet createLineDataSet(RoleDailyCount roleDailyCount, int color) {
        LineDataSet set;
        List<DataBean> list = new ArrayList<>();
        list.addAll(roleDailyCount.getData());
        List<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            yVals.add(new Entry(new Float(list.get(i).getCount()), i));
        }

        set = new LineDataSet(yVals, roleDailyCount.getName());
        setSetType(set, color);

        return set;
    }

}
