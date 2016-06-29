package com.kklv.bmoe.database;

import android.content.Context;
import android.util.Log;

import com.kklv.bmoe.object.RoleDailyCount;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/16.
 */
public class TestDatabase {
    public List<RoleDailyCount> testAddUser(Context context, ArrayList<RoleDailyCount> list) {
        Log.i("kklv", "list.size:" + list.size());
        RoleDailyCountDao roleDailyCountDao = new RoleDailyCountDao(context);
        roleDailyCountDao.addOrUpdateRoleDailyCounts(list);
        Log.i("TestDatabase", "SIZE:" + roleDailyCountDao.getAllRoleDailyCounts().size());
        return roleDailyCountDao.getAllRoleDailyCounts();
    }


}
