package com.kklv.bmoe.object;

/**
 * 代表一个阵营(即一部动画)
 * 包含了该阵营总参与人数、晋级人数、复活人数、淘汰人数
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/1 16:31
 */
public class Camp {
    /**
     * 阵营名称
     */
    private String bangumi;
    private int total;
    private int suc;
    private int wait;
    private int fail;

    /**
     * 阵营名称
     */
    public String getBangumi() {
        return bangumi;
    }

    /**
     * 阵营名称
     */
    public void setBangumi(String bangumi) {
        this.bangumi = bangumi;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        if (total < 0) {
            total = 0;
        }
        this.total = total;
    }

    public int getSuc() {
        return suc;
    }

    public void setSuc(int suc) {
        if (suc < 0) {
            suc = 0;
        }
        this.suc = suc;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        if (wait < 0) {
            wait = 0;
        }
        this.wait = wait;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        if (fail < 0) {
            fail = 0;
        }
        this.fail = fail;
    }
}
