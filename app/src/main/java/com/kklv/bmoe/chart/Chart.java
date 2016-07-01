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
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/7 11:45
 */
public class Chart extends BaseChart {
    private static final String TAG = "Chart";
    /**
     * 将List<RoleDailyCount>分割为 SPLIT_LENGTH 长的 List<List<RoleDailyCount>> 集合；
     * 即：chart每次最多显示 SPLIT_LENGTH 条曲线；
     * 注意：现在最大设置16，因为我只设置了16种颜色
     */
    private static final int SPLIT_LENGTH = 16;
    private LineChart mLineChart;
    private DataHelper mDataHelper;
    private Context mContext;

    private List<RoleDailyCount> mRoleDailyCountList;
    private List<List<RoleDailyCount>> mSplitLists;
    private int mShowingSplitListId = 0;

    private String mSexChecked = RoleDailyCount.SEX_ALL;
    private String mGroupChecked = RoleDailyCount.GROUP_ALL;

    public List<RoleDailyCount> getSplitList() {
        if (mSplitLists != null && mSplitLists.size() > 0) {
            return mSplitLists.get(mShowingSplitListId);
        }
        return null;
    }

    public Chart(Context context, LineChart lineChart) {
        this.mContext = context;
        this.mLineChart = lineChart;
        mDataHelper = new DataHelper(mContext);
        mDataHelper.registerCallBack(this);

        initLineChart();
    }

    public void getData(Map<String, String> map) {
        if (map == null) {
            return;
        }
        mDataHelper.getRoleDailyCount(map);
    }

    private void initLineChart() {
        mLineChart.setDescription(mContext.getString(R.string.count_line_chart));
//        mLineChart.setDescriptionPosition(440,100);
        mLineChart.setNoDataText(mContext.getString(R.string.data_loading));
        mLineChart.setDescriptionTextSize(20.0f);
        mLineChart.getLegend().setWordWrapEnabled(true);    //label自动换行
        mLineChart.animateY(ANIMATEY_TIME);
        mLineChart.setMaxVisibleValueCount(Integer.MAX_VALUE);
        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setEnabled(false);    //右边Y轴不显示

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setAxisMinValue(0.0f); //Y轴从0开始

        XAxis xAxis = mLineChart.getXAxis();
    }

    /**
     * 设置图表数据
     */
    public void setData() {
        if (mRoleDailyCountList == null || mRoleDailyCountList.size() <= 0) {
            return;
        }
        List<RoleDailyCount> sexAndGroupList = getSexAndGroupList();
        if (sexAndGroupList == null || sexAndGroupList.size() <= 0) {
            return;
        }
        mSplitLists = ListUtils.split(sexAndGroupList, SPLIT_LENGTH);
        mShowingSplitListId = 0;//必须清0

        drawChart(mSplitLists.get(mShowingSplitListId));
    }

    private List<RoleDailyCount> getSexAndGroupList() {
        List<RoleDailyCount> sexList = new ArrayList<>();
        if (RoleDailyCount.SEX_ALL.equals(mSexChecked)) {
            sexList = mRoleDailyCountList;
        } else {
            for (RoleDailyCount item : mRoleDailyCountList) {
                if (mSexChecked.equals(item.getSex())) sexList.add(item);
            }
        }

        List<RoleDailyCount> sexAndGroupList = new ArrayList<>();
        if (RoleDailyCount.GROUP_ALL.equals(mGroupChecked)) {
            sexAndGroupList = sexList;
        } else {
            for (RoleDailyCount item : sexList) {
                if (mGroupChecked.equals(item.getGroup())) sexAndGroupList.add(item);
            }
        }

        return sexAndGroupList;
    }

    /**
     * 查看上一组排名的数据
     */
    public void goLeftSplitLists() {
        if ((mSplitLists != null && mSplitLists.size() > 0) &&
                mShowingSplitListId > 0) {
            mShowingSplitListId--;
            drawChart(mSplitLists.get(mShowingSplitListId));
        }
    }

    /**
     * 查看下一组排名的数据
     */
    public void goRightSplitLists() {

        if ((mSplitLists != null && mSplitLists.size() > 0) &&
                mShowingSplitListId < mSplitLists.size() - 1) {
            mShowingSplitListId++;
            drawChart(mSplitLists.get(mShowingSplitListId));
        }

    }

    /**
     * 选择萌、燃、萌燃
     *
     * @param sex "":萌燃;"1":萌;"2":燃
     */
    public void showMoe(String sex) {
        mSexChecked = sex;
        setData();
    }

    /**
     * 选择分组
     *
     * @param group
     */
    public void showGroup(String group) {
        mGroupChecked = group;
        setData();
    }

    /**
     * 根据组数得到这一组开始和结束的排名字符串
     *
     * @param showingSplitListId
     * @return
     */
    private String getRankString(int showingSplitListId) {
        int start = showingSplitListId * SPLIT_LENGTH + 1;
        int length = mSplitLists.get(showingSplitListId).size();

        return "(" + start + "-" + (length + start - 1) + ")";
    }

    private void drawChart(List<RoleDailyCount> list) {
        if (list == null) {
            return;
        }
        mLineChart.setDescription(mContext.getString(R.string.count_line_chart) +
                getRankString(mShowingSplitListId));
        List<ILineDataSet> dataSets = new ArrayList<>();
        int[] colors = mContext.getResources().getIntArray(R.array.lineChart);
        for (RoleDailyCount item : list) {
            int i = list.indexOf(item);
            dataSets.add(createLineDataSet(item, colors[i]));
        }
        LineData data = new LineData(getXVals(list.get(0)), dataSets);
        // set data
        mLineChart.setData(data);
        mLineChart.animateY(ANIMATEY_TIME);
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
    }

    /**
     * 生成一条折线
     *
     * @param roleDailyCount
     * @param color          折线颜色
     * @return
     */
    private LineDataSet createLineDataSet(RoleDailyCount roleDailyCount, int color) {
        LineDataSet set;
        List<DataBean> list = new ArrayList<>();
        list.addAll(roleDailyCount.getData());
        List<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < list.size(); i++) {
            yVals.add(new Entry(new Float(list.get(i).getCount()), i));
        }


        set = new LineDataSet(yVals, roleDailyCount.getName());
//            set.enableDashedLine(10f, 5f, 0f);       //设置虚线
//            set.enableDashedHighlightLine(10f, 5f, 0f);
        set.setColor(color);
        set.setCircleColor(color);
        set.setValueTextColor(color);
        set.setLineWidth(1f);
        set.setCircleRadius(3f);
        set.setDrawCircleHole(false);  //点是实心的
        set.setValueTextSize(9f);
        set.setDrawFilled(false);  //单纯的line，line下面不覆盖颜色

        return set;
    }

    /**
     * 根据一条折线数据生成X轴
     *
     * @param one 一个角色当天的数据
     * @return
     */
    private List<String> getXVals(RoleDailyCount one) {
        List<DataBean> list = new ArrayList<>();
        list.addAll(one.getData());
        List<String> xVals = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            xVals.add(list.get(i).getTime());
        }
        return xVals;
    }


    private Set<String> getGroups(List<RoleDailyCount> list) {

        TreeSet<String> groups = new TreeSet<>();
        for (RoleDailyCount item : list) {
            if (!TextUtils.isEmpty(item.getGroup())) {
                groups.add(item.getGroup());
            }
        }

        return groups;
    }

    @Override
    public <T> void onSuccess(final List<T> result) {

        if (result != null && result.size() > 0) {
            mRoleDailyCountList = (List<RoleDailyCount>) result;

            Set<String> set = getGroups(mRoleDailyCountList);
            if (set != null && set.size() > 0) {
                mCallBack.showGroup(new ArrayList(set));
            } else {
                mCallBack.showGroup(null);
            }
            mCallBack.onLoadCompleted(true);
            mCallBack.resetSexRG();
            mCallBack.resetGroupRG();
            mSexChecked = RoleDailyCount.SEX_ALL;
            mGroupChecked = RoleDailyCount.GROUP_ALL;
            setData();
        } else {
            mCallBack.onLoadCompleted(false);
            Toast.makeText(mContext, R.string.no_data, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Exception error) {
        mCallBack.onLoadCompleted(false);
        Toast.makeText(mContext, R.string.net_error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void cancelRequest() {
        mDataHelper.mRoleDailyCountRequest.cancel();
    }

    public void setRoleDailyCountList(List<RoleDailyCount> roleDailyCountList) {
        if (roleDailyCountList == null || roleDailyCountList.size() <= 0){
            return;
        }
        mRoleDailyCountList = roleDailyCountList;
    }
}
