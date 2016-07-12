package com.kklv.bmoe.chart;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.kklv.bmoe.R;
import com.kklv.bmoe.data.DataHelper;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleDailyCount;
import com.kklv.bmoe.utils.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Chart的基类
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/23 11:28
 */
public class BaseChart implements DataHelper.DataHelperCallBack {
    private static final String TAG = "BaseChart";
    /**
     * 总票数折线图
     */
    public static final int CREATOR_TOTAL_TICKETS_COUNT = 0;
    /**
     * 每小时票数折线图
     */
    public static final int CREATOR_ONE_HOUR_TICKETS_COUNT = 1;
    /**
     * 每小时得票率
     */
    public static final int CREATOR_ONE_HOUR_TICKETS_PERCENT = 2;
    /**
     * 总得票率
     */
    public static final int CREATOR_TOTAL_TICKETS_PERCENT = 3;

    private LineDataSetCreator mLineDataSetCreator;
    private int mCreatorType = CREATOR_TOTAL_TICKETS_COUNT;
    /**
     * Y轴上的动画时间
     */
    private static final int ANIMATEY_TIME = 2000;
    /**
     * 将List<RoleDailyCount>分割为 SPLIT_LENGTH 长的 List<List<RoleDailyCount>> 集合；
     * 即：chart每次最多显示 SPLIT_LENGTH 条曲线；
     * 注意：现在最大设置16，因为我只设置了16种颜色
     */
    private static final int SPLIT_LENGTH = 16;

    private LineChart mLineChart;
    private Context mContext;
    private DataHelper mDataHelper;
    /**
     * 回调Fragment
     */
    private ChartCallBack mCallBack;

    /**
     * 当天的所以RoleDailyCount数据
     */
    private List<RoleDailyCount> mRoleDailyCountList;
    /**
     * 按SPLIT_LENGTH分割后的list
     */
    private List<List<RoleDailyCount>> mSplitLists;
    private int mShowingSplitListId = 0;
    private String mSexChecked = RoleDailyCount.SEX_ALL;
    private String mGroupChecked = RoleDailyCount.GROUP_ALL;

    public BaseChart(Context context, LineChart lineChart, int creator) {
        mContext = context;
        mDataHelper = new DataHelper(mContext);
        mDataHelper.registerCallBack(this);
        this.mLineChart = lineChart;
        setChartType(creator);
        initLineChart();
    }

    public int getCreatorType() {
        return mCreatorType;
    }

    /**
     * 切换Chart
     *
     * @param creator
     */
    public void setChartTypeAndShow(int creator) {
        setChartType(creator);
        if(!ListUtils.isEmpty(mSplitLists)){
            drawChart(mSplitLists.get(mShowingSplitListId));
        }

    }

    private void setChartType(int creator) {
        mCreatorType = creator;
        switch (creator) {
            case CREATOR_TOTAL_TICKETS_COUNT:
                mLineDataSetCreator = new TotalTicketsCountSetCreator();
                break;
            case CREATOR_ONE_HOUR_TICKETS_COUNT:
                mLineDataSetCreator = new OneHourTicketsCountSetCreator();
                break;
            case CREATOR_ONE_HOUR_TICKETS_PERCENT:
                mLineDataSetCreator = new OneHourTicketsPercentSetCreator();
                break;
            case CREATOR_TOTAL_TICKETS_PERCENT:
                mLineDataSetCreator = new TotalTicketsPercentSetCreator();
                break;
        }
    }

    /**
     * 开始从数据库读取数据
     *
     * @param map
     */
    public final void getData(Map<String, String> map) {
        if (map == null) {
            return;
        }
        mDataHelper.getRoleDailyCount(map);
    }

    /**
     * 设置图表数据
     */
    public void setData() {

        if (ListUtils.isEmpty(mRoleDailyCountList)) {
            return;
        }
        List<RoleDailyCount> sexAndGroupList = selectRoleDailyCounts(mSexChecked, mGroupChecked);
        if (ListUtils.isEmpty(sexAndGroupList)) {
            return;
        }
        mSplitLists = ListUtils.split(sexAndGroupList, SPLIT_LENGTH);
        mShowingSplitListId = 0;//必须清0

        drawChart(mSplitLists.get(mShowingSplitListId));
    }

    /**************************set或者get数据---开始****************************/
    /**
     * 设置基本数据
     *
     * @param dataList
     * @param <T>
     */
    public <T> void setBasicList(List<T> dataList) {
        if (ListUtils.isEmpty(dataList)) {
            return;
        }

        if (dataList.get(0) instanceof RoleDailyCount)
            mRoleDailyCountList = (List<RoleDailyCount>) dataList;
    }

    /**
     * 得到当前正在显示的数据
     *
     * @return
     */
    public List<RoleDailyCount> getSplitList() {
        if (!ListUtils.isEmpty(mSplitLists)) {
            return mSplitLists.get(mShowingSplitListId);
        }
        return null;
    }
    /**************************set或者get数据---结束****************************/

    /**************************工具---开始****************************/
    /**
     * 根据sex和group得到List<RoleDailyCount>
     *
     * @param sex
     * @param group
     * @return
     */
    private List<RoleDailyCount> selectRoleDailyCounts(String sex, String group) {
        List<RoleDailyCount> sexList = new ArrayList<>();
        if (RoleDailyCount.SEX_ALL.equals(sex)) {
            sexList = mRoleDailyCountList;
        } else {
            for (RoleDailyCount item : mRoleDailyCountList) {
                if (sex.equals(item.getSex())) sexList.add(item);
            }
        }

        List<RoleDailyCount> sexAndGroupList = new ArrayList<>();
        if (RoleDailyCount.GROUP_ALL.equals(group)) {
            sexAndGroupList = sexList;
        } else {
            for (RoleDailyCount item : sexList) {
                if (group.equals(item.getGroup())) sexAndGroupList.add(item);
            }
        }

        return sexAndGroupList;
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

    /**
     * 得到当天的分组名称
     *
     * @param list
     * @return
     */
    private static List<String> getGroups(List<RoleDailyCount> list) {

        TreeSet<String> groups = new TreeSet<>();
        for (RoleDailyCount item : list) {
            if (!TextUtils.isEmpty(item.getGroup())) {
                groups.add(item.getGroup());
            }
        }

        if (groups != null && groups.size() > 0) {
            return new ArrayList(groups);
        } else {
            return null;
        }
    }

    private void setSexAndGroupsMap() {
        Map<String, List<Integer>> oneHourSexAndGroupsMap = new HashMap<>();
        Map<String, List<Integer>> totalSexAndGroupsMap = new HashMap<>();
        List<String> groupsNames = getGroups(mRoleDailyCountList);
        if (ListUtils.isEmpty(groupsNames)) {  //未分组
            //获取萌组总每小时总票数
            List<RoleDailyCount> moeList = selectRoleDailyCounts(RoleDailyCount.SEX_MOE,
                    RoleDailyCount.GROUP_ALL);
            if (!ListUtils.isEmpty(moeList)) {
                oneHourSexAndGroupsMap.put(RoleDailyCount.SEX_MOE + "", getOneHourCountsList(moeList));
                totalSexAndGroupsMap.put(RoleDailyCount.SEX_MOE + "", getTotalCountsList(moeList));
            }

            //获取燃组每小时总票数
            List<RoleDailyCount> lightList = selectRoleDailyCounts(RoleDailyCount.SEX_LIGHT,
                    RoleDailyCount.GROUP_ALL);
            if (!ListUtils.isEmpty(lightList)) {
                oneHourSexAndGroupsMap.put(RoleDailyCount.SEX_LIGHT + "", getOneHourCountsList(lightList));
                totalSexAndGroupsMap.put(RoleDailyCount.SEX_LIGHT + "", getTotalCountsList(lightList));
            }
        } else {  //已经分组，每小时总票数要在组内单独算，而且要分sex

            //萌组的group：每小时总票数
            for (String item : groupsNames) {
                List<RoleDailyCount> moeList = selectRoleDailyCounts(RoleDailyCount.SEX_MOE,
                        item);
                if (!ListUtils.isEmpty(moeList)) {
                    oneHourSexAndGroupsMap.put(RoleDailyCount.SEX_MOE + item,
                            getOneHourCountsList(moeList));
                    totalSexAndGroupsMap.put(RoleDailyCount.SEX_MOE + item,
                            getTotalCountsList(moeList));
                }
            }
            //燃组的group：每小时总票数
            for (String item : groupsNames) {
                List<RoleDailyCount> lightList = selectRoleDailyCounts(RoleDailyCount.SEX_LIGHT,
                        item);
                if (!ListUtils.isEmpty(lightList)) {
                    oneHourSexAndGroupsMap.put(RoleDailyCount.SEX_LIGHT + item,
                            getOneHourCountsList(lightList));
                    totalSexAndGroupsMap.put(RoleDailyCount.SEX_LIGHT + item,
                            getTotalCountsList(lightList));
                }
            }

        }
        mLineDataSetCreator.setOneHourSexAndGroupsMap(oneHourSexAndGroupsMap);
        mLineDataSetCreator.setTotalSexAndGroupsMap(totalSexAndGroupsMap);
    }

    /**
     * 得到一个每小时的票数的list
     *
     * @param roleDailyCounts
     * @return
     */
    private static List<Integer> getOneHourCountsList(List<RoleDailyCount> roleDailyCounts) {

        Integer[] total = new Integer[24];
        for (int i = 0; i < roleDailyCounts.size(); i++) {
            RoleDailyCount item = roleDailyCounts.get(i);

            List<DataBean> list = new ArrayList<>();
            list.addAll(item.getData());
            for (int j = 1; j < list.size(); j++) {
                if (i == 0) total[j - 1] = 0;//初始化数组
                total[j - 1] += list.get(j).getCount() - list.get(j - 1).getCount();
            }
        }
        List<Integer> totalList = Arrays.asList(total);
        return totalList;
    }

    private static List<Integer> getTotalCountsList(List<RoleDailyCount> roleDailyCounts) {
        Integer[] total = new Integer[24];
        for (int i = 0; i < roleDailyCounts.size(); i++) {
            RoleDailyCount item = roleDailyCounts.get(i);

            List<DataBean> list = new ArrayList<>();
            list.addAll(item.getData());
            for (int j = 1; j < list.size(); j++) {
                if (i == 0) total[j - 1] = 0;//初始化数组
                total[j - 1] += list.get(j).getCount();
            }
        }
        List<Integer> totalList = Arrays.asList(total);
        return totalList;
    }
    /**************************工具---结束****************************/

    /**************************UI控制---开始****************************/
    /**
     * 查看上一组排名的数据
     */
    public void goLeftSplitLists() {
        if (!ListUtils.isEmpty(mSplitLists) &&
                mShowingSplitListId > 0) {
            mShowingSplitListId--;
            drawChart(mSplitLists.get(mShowingSplitListId));
        }
    }

    /**
     * 查看下一组排名的数据
     */
    public void goRightSplitLists() {

        if (!ListUtils.isEmpty(mSplitLists) &&
                (mShowingSplitListId < mSplitLists.size() - 1)) {
            mShowingSplitListId++;
            drawChart(mSplitLists.get(mShowingSplitListId));
        }
    }

    /**
     * 选择萌、燃、萌燃
     *
     * @param sex "":萌燃;"1":萌;"2":燃
     */
    public final void showMoe(String sex) {
        mSexChecked = sex;
        setData();
    }

    /**
     * 选择分组
     *
     * @param group
     */
    public final void showGroup(String group) {
        mGroupChecked = group;
        setData();
    }
    /**************************UI控制---结束****************************/

    /**************************
     * Chart相关---开始
     ****************************/
    private void initLineChart() {
        mLineChart.setNoDataText(mContext.getString(R.string.data_loading));
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
     * 根据一条折线数据生成X轴
     *
     * @param one 一个角色当天的数据
     * @return
     */
    private List<String> getXVals(RoleDailyCount one, int xStartIndex) {
        List<DataBean> list = new ArrayList<>();
        list.addAll(one.getData());
        List<String> xVals = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            xVals.add(list.get(i).getTime() + "");
        }
        for (int i = 0; i < xStartIndex; i++) {
            xVals.remove(0);
        }
        return xVals;
    }

    /**
     * 绘制Chart
     *
     * @param list
     */
    private void drawChart(List<RoleDailyCount> list) {
        if (ListUtils.isEmpty(list)) {
            return;
        }

        if(mCallBack != null){
            mCallBack.setDescription(mLineDataSetCreator.getDescription() +
                    getRankString(mShowingSplitListId));
        }
        List<ILineDataSet> dataSets = new ArrayList<>();
        int[] colors = mContext.getResources().getIntArray(R.array.lineChart);
        for (RoleDailyCount item : list) {
            int i = list.indexOf(item);
            LineDataSet set = mLineDataSetCreator.createLineDataSet(item);
            setSetType(set, colors[i]);
            dataSets.add(set);
        }
        LineData data = new LineData(getXVals(list.get(0), mLineDataSetCreator.getXStartIndex()),
                dataSets);
        // set data
        mLineChart.setData(data);
        mLineChart.animateY(ANIMATEY_TIME);
        mLineChart.notifyDataSetChanged();
        mLineChart.invalidate();
    }


    /**
     * 设置折线颜色和样式
     *
     * @param set
     * @param color
     */
    private void setSetType(LineDataSet set, int color) {
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
    }

    /**************************
     * Chart相关---结束
     ****************************/

    public interface ChartCallBack {

        /**
         * 加载数据完成
         *
         * @param result true:成功；false:失败
         */
        void onLoadCompleted(boolean result);

        /**
         * 用于显示分组
         *
         * @param list
         */
        void showGroup(List<String> list);

        /**
         * 初始化RadioGroup
         */
        void resetRG();

        void setDescription(String description);
    }

    /**
     * 注册一个Chart的监听器
     *
     * @param callBack
     */
    public final void registerChartCallBack(ChartCallBack callBack) {
        this.mCallBack = callBack;
    }

    @Override
    public <T> void onSuccess(List<T> result) {
        if (!ListUtils.isEmpty(result)) {
            mRoleDailyCountList = (List<RoleDailyCount>) result;
            if(mCallBack != null){
                mCallBack.showGroup(getGroups(mRoleDailyCountList));
                mCallBack.onLoadCompleted(true);
                mCallBack.resetRG();
            }
            mSexChecked = RoleDailyCount.SEX_ALL;
            mGroupChecked = RoleDailyCount.GROUP_ALL;

            setSexAndGroupsMap();
            setData();
        } else {
            if(mCallBack != null){
                mCallBack.onLoadCompleted(false);
            }

            Toast.makeText(mContext, R.string.no_data, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Exception error) {
        if(mCallBack != null){
            mCallBack.onLoadCompleted(false);
        }

        Toast.makeText(mContext, R.string.net_error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 取消网络请求
     */
    public void cancelRequest() {
        mDataHelper.mRoleDailyCountRequest.cancel();
    }

}
