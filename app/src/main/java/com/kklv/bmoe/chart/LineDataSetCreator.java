package com.kklv.bmoe.chart;

import com.github.mikephil.charting.data.LineDataSet;
import com.kklv.bmoe.object.RoleDailyCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LvZhenDong
 * created at 2016/7/8 9:42
 */
public abstract class LineDataSetCreator {
    private String mDescription;
    /**
     * X起始坐标
     */
    private int mXStartIndex;
    /**
     * 分组后每小时票数
     */
    protected static Map<String, List<Integer>> mOneHourSexAndGroupsMap = new HashMap<>();
    /**
     * 分组后总票数
     */
    protected static Map<String, List<Integer>> mTotalSexAndGroupsMap = new HashMap<>();

    public LineDataSetCreator(String description, int xStartIndex) {
        this.mDescription = description;
        this.mXStartIndex = xStartIndex;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getXStartIndex() {
        return mXStartIndex;
    }

    public void setOneHourSexAndGroupsMap(Map<String, List<Integer>> oneHourSexAndGroupsMap) {
        mOneHourSexAndGroupsMap = oneHourSexAndGroupsMap;
    }

    public void setTotalSexAndGroupsMap(Map<String, List<Integer>> totalSexAndGroupsMap) {
        mTotalSexAndGroupsMap = totalSexAndGroupsMap;
    }

    abstract LineDataSet createLineDataSet(RoleDailyCount roleDailyCount);
}
