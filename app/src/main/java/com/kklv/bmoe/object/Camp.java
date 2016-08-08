package com.kklv.bmoe.object;

/**
 * 代表一个阵营(即一部动画)
 * 包含了该阵营总参与人数、晋级人数、复活人数、淘汰人数、存活数
 *
 * @author LvZhenDong
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
    private int alive;
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
        this.total = total;
    }

    public int getSuc() {
        return suc;
    }

    public void setSuc(int suc) {
        this.suc = suc;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }
}
