package com.kklv.bmoe.chart;

import com.github.mikephil.charting.data.LineDataSet;
import com.kklv.bmoe.object.RoleDailyCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/8 9:42
 */
public abstract class LineDataSetCreator {
    private String mDescription;
    /**
     * X起始坐标
     */
    private int mXStartIndex;
    /**
     * 分组后每小时总票数
     */
    protected Map<String,List<Integer>> mSexAndGroupsMap=new HashMap<>();

    public LineDataSetCreator(String description,int xStartIndex){
        this.mDescription=description;
        this.mXStartIndex=xStartIndex;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getXStartIndex() {
        return mXStartIndex;
    }

    public void setSexAndGroupsMap(Map<String, List<Integer>> sexAndGroupsMap) {
        mSexAndGroupsMap = sexAndGroupsMap;
    }

    abstract LineDataSet createLineDataSet(RoleDailyCount roleDailyCount);
}
