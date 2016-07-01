package com.kklv.bmoe.chart;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.kklv.bmoe.R;
import com.kklv.bmoe.data.DataHelper;
import com.kklv.bmoe.object.BaseCount;
import com.kklv.bmoe.object.RoleDailyCount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Chart的基类
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/23 11:28
 */
public abstract class BaseChart implements DataHelper.DataHelperCallBack {
    protected static final int ANIMATEY_TIME = 2000;
    /**
     * 将List<RoleDailyCount>分割为 SPLIT_LENGTH 长的 List<List<RoleDailyCount>> 集合；
     * 即：chart每次最多显示 SPLIT_LENGTH 条曲线；
     * 注意：现在最大设置16，因为我只设置了16种颜色
     */
    protected static final int SPLIT_LENGTH = 16;

    protected Context mContext;
    protected DataHelper mDataHelper;
    /**
     * 回调Fragment
     */
    protected ChartCallBack mCallBack;

//    protected List<List<BaseCount>> mSplitLists;
    protected int mShowingSplitListId = 0;
    protected String mSexChecked = RoleDailyCount.SEX_ALL;
    protected String mGroupChecked = RoleDailyCount.GROUP_ALL;

    public BaseChart(Context context){
        mContext = context;
        mDataHelper = new DataHelper(mContext);
        mDataHelper.registerCallBack(this);
    }
    /**
     * 取消网络请求
     */
    public abstract void cancelRequest();

    @Override
    public abstract  <T> void onSuccess(List<T> result);


    /**
     * 设置图表数据
     */
    public abstract void setData();
    /**
     * 设置基本数据
     * @param dataList
     * @param <T>
     */
    public abstract  <T> void setBasicList(List<T> dataList);

    public interface ChartCallBack {

        /**
         * 加载数据完成
         * @param result true:成功；false:失败
         */
        void onLoadCompleted(boolean result);

        /**
         * 用于显示分组
         * @param list
         */
        void showGroup(List<String> list);

        void resetSexRG();
        void resetGroupRG();
    }

    /**
     * 注册一个Chart的监听器
     *
     * @param callBack
     */
    public final void registerChartCallBack(ChartCallBack callBack) {
        this.mCallBack = callBack;
    }

    /**
     * 得到当天的分组名称
     * @param list
     * @return
     */
    protected final List<String> getGroups(List<RoleDailyCount> list) {

        TreeSet<String> groups = new TreeSet<>();
        for (RoleDailyCount item : list) {
            if (!TextUtils.isEmpty(item.getGroup())) {
                groups.add(item.getGroup());
            }
        }

        if(groups != null && groups.size() > 0){
            return new ArrayList(groups);
        }else{
            return null;
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

    @Override
    public void onFailure(Exception error){
        mCallBack.onLoadCompleted(false);
        Toast.makeText(mContext, R.string.net_error, Toast.LENGTH_SHORT).show();
    }

    public final void getData(Map<String, String> map) {
        if (map == null) {
            return;
        }
        mDataHelper.getRoleDailyCount(map);
    }
}
