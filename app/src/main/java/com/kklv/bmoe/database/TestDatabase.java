package com.kklv.bmoe.database;

import android.content.Context;
import android.util.Log;

import com.kklv.bmoe.object.RoleIntradayCount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public class TestDatabase {
    public List<RoleIntradayCount> testAddUser(Context context, ArrayList<RoleIntradayCount> list) {
        Log.i("kklv", "list.size:" + list.size());
        RoleIntradayCountDao roleIntradayCountDao = new RoleIntradayCountDao(context);
        roleIntradayCountDao.addOrUpdateRoleIntradayCounts(list);
        Log.i("TestDatabase", "SIZE:" + roleIntradayCountDao.getAllRoleIntradayCounts().size());
        return roleIntradayCountDao.getAllRoleIntradayCounts();
    }


}
