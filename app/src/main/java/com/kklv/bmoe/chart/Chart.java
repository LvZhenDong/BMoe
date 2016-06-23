package com.kklv.bmoe.chart;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
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
import com.kklv.bmoe.database.DatabaseHelper;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleIntradayCount;
import com.kklv.bmoe.utils.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/7 11:45
 */
public class Chart extends BaseChart {
    private static final String TAG = "Chart";
    private static final int ANIMATEY_TIME = 2000;
    private LineChart mLineChart;
    private DataHelper mDataHelper;
    private Context mContext;

    private HandlerThread mHandlerThread;
    private Handler mSubThreadHandler;
    private Handler mUIHandler = new Handler();

    private List<RoleIntradayCount> mCampList;
    private List<List<RoleIntradayCount>> mSplitedList;

    public List<RoleIntradayCount> getCampList() {
        return mCampList;
    }

    public Chart(Context context, LineChart lineChart) {
        this.mContext = context;
        this.mLineChart = lineChart;
        mDataHelper = new DataHelper(mContext);
        mDataHelper.registerCallBack(this);

        initHandler();
        initLineChart();
    }

    public void showData(String date) {
        if (TextUtils.isEmpty(date)) {
            //TODO 我在考虑要不要Toast下
            return;
        }
//        mDataHelper.getCampRank("Fate/stay night [UBW]");
        Map<String, String> map = new HashMap<>();
        map.put("date", date);
//        map.put("sex","0");
//        map.put("group","G1"); //有一段时间是A1后面变成1-A
//        mDataHelper.getRoleIntradayCount(map);
        Message msg = new Message();
        msg.obj = map;
        mSubThreadHandler.sendMessage(msg);
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
     *
     * @param list
     */
    public void setData(List<RoleIntradayCount> list) {
        if (list == null) {
            return;
        }
        mSplitedList = ListUtils.split(list, 16);
        drawChart(mSplitedList.get(0));
    }

    private void drawChart(List<RoleIntradayCount> list) {
        if (list == null) {
            return;
        }
        List<ILineDataSet> dataSets = new ArrayList<>();
        int[] colors = mContext.getResources().getIntArray(R.array.lineChart);
        for (RoleIntradayCount item : list) {
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
     * @param roleIntradayCount
     * @param color             折线颜色
     * @return
     */
    private LineDataSet createLineDataSet(RoleIntradayCount roleIntradayCount, int color) {
        LineDataSet set;
        List<DataBean> list = new ArrayList<>();
        list.addAll(roleIntradayCount.getData());
        List<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < list.size(); i++) {
            yVals.add(new Entry(new Float(list.get(i).getCount()), i));
        }


        set = new LineDataSet(yVals, roleIntradayCount.getName());
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
    private List<String> getXVals(RoleIntradayCount one) {
        List<DataBean> list = new ArrayList<>();
        list.addAll(one.getData());
        List<String> xVals = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            xVals.add(list.get(i).getTime());
        }
        return xVals;
    }

    @Override
    public <T> void onSuccess(final List<T> result) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallBack.onLoadCompleted();
                if (result != null && result.size() > 0) {
                    mCampList = (List<RoleIntradayCount>) result;
                    setData((List<RoleIntradayCount>) result);
                } else {
                    Toast.makeText(mContext, R.string.no_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onFailure(Exception error) {
        mUIHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallBack.onLoadCompleted();
                Toast.makeText(mContext, R.string.net_error, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void cancelRequest() {
        mDataHelper.mRoleIntradayCountRequest.cancel();
    }

    /**
     * 初始化HandlerThread，在子线程中进行数据库操作
     */
    private void initHandler() {
        mHandlerThread = new HandlerThread(DatabaseHelper.DB_HANDLER_THREAD_NAME);
        mHandlerThread.start();
        mSubThreadHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                mDataHelper.getRoleIntradayCount((Map<String, String>) msg.obj);
            }
        };
    }
}
