package com.kklv.bmoe.object;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 时间-票数
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/22 10:38
 */
@DatabaseTable
public class DataBean implements Serializable {
    /**
     * 手动设置id时候的增量，防止出现下面这种情况
     * roleDailyCount的id为1000，dataBean的time为10，所以现在它的id为100010；
     * 如果另一个roleDailyCount的id为10000，dataBean的time为00，所以现在它的id也为100010
     */
    public static final int idIncrement = 0XFFF;
    @DatabaseField(id = true)
    private int id; //手动设置id，roleDailyCount的id+""+(DataBean的time+idIncrement)
    @DatabaseField
    private String time;
    @DatabaseField
    private String count;
    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private RoleDailyCount roleDailyCount;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public RoleDailyCount getRoleDailyCount() {
        return roleDailyCount;
    }

    public void setRoleDailyCount(RoleDailyCount roleDailyCount) {
        this.roleDailyCount = roleDailyCount;
    }
}
