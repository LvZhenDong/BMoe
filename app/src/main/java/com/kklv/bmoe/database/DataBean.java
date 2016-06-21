package com.kklv.bmoe.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.kklv.bmoe.object.RoleIntradayCount;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/17.
 */
@DatabaseTable
public class DataBean implements Serializable{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String time;
    @DatabaseField
    private String count;
    @DatabaseField(foreign = true,foreignAutoRefresh = true)
    private RoleIntradayCount roleIntradayCount;

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

    public void setId(int id) {
        this.id = id;
    }

    public RoleIntradayCount getRoleIntradayCount() {
        return roleIntradayCount;
    }

    public void setRoleIntradayCount(RoleIntradayCount roleIntradayCount) {
        this.roleIntradayCount = roleIntradayCount;
    }
}
