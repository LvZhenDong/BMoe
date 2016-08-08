package com.kklv.bmoe.object;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 时间-票数
 *
 * @author LvZhenDong
 * created at 2016/6/22 10:38
 */
@DatabaseTable
public class DataBean implements Serializable {

    @DatabaseField(id = true)
    private int id; //手动设置id，roleDailyCount的id*100+(DataBean的time)
    @DatabaseField
    private int time;
    @DatabaseField
    private int count;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private RoleDailyCount roleDailyCount;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RoleDailyCount getRoleDailyCount() {
        return roleDailyCount;
    }

    public void setRoleDailyCount(RoleDailyCount roleDailyCount) {
        this.roleDailyCount = roleDailyCount;
    }
}
