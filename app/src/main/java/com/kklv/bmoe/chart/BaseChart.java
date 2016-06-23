package com.kklv.bmoe.chart;

import com.kklv.bmoe.data.DataHelper;

import java.util.List;

/**
 * Chart的基类
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/23 11:28
 */
public abstract class BaseChart implements DataHelper.DataHelperCallBack {
    protected ChartCallBack mCallBack;

    @Override
    public <T> void onSuccess(List<T> result) {

    }

    @Override
    public void onFailure(Exception error) {

    }

    public interface ChartCallBack {
        /**
         * 加载数据完成
         */
        void onLoadCompleted();
    }

    /**
     * 注册一个Chart的监听器
     *
     * @param callBack
     */
    public void registerChartCallBack(ChartCallBack callBack) {
        this.mCallBack = callBack;
    }

    /**
     * 取消网络请求
     */
    public abstract void cancelRequest();
}
