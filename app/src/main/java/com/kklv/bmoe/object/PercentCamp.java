package com.kklv.bmoe.object;

import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *计算过百分比的Camp信息
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/14 11:07
 */
public class PercentCamp {
    private static final String TAG = "PercentCamp";
    private Camp camp;
    private double percentSuc;
    private double percentWait;
    private double percentFail;

    public double getPercentFail() {
        return percentFail;
    }

    public void setPercentFail(double percentFail) {
        this.percentFail = formatPercent(percentFail);
    }

    public Camp getCamp() {
        return camp;
    }

    public void setCamp(Camp camp) {
        this.camp = camp;
    }

    public double getPercentSuc() {
        return percentSuc;
    }

    public void setPercentSuc(double percentSuc) {
        this.percentSuc = formatPercent(percentSuc);
    }

    public double getPercentWait() {
        return percentWait;
    }

    public void setPercentWait(double percentWait) {
        this.percentWait = formatPercent(percentWait);
    }

    /**
     * 保留2位小数
     * @param value
     * @return
     */
    private double formatPercent(double value){
        BigDecimal bigDecimal=new BigDecimal(value);
        return bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
