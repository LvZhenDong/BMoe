package com.kklv.bmoe.database;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.kklv.bmoe.object.RoleIntradayCount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/17 11:17
 */
public class RoleIntradayCountDao {
    private Context mContext;
    private Dao<RoleIntradayCount, Integer> mRoleIntradayCountDaoOpe;
    private DatabaseHelper mDatabaseHelper;

    public RoleIntradayCountDao(Context context) {
        this.mContext = context;
        mDatabaseHelper = DatabaseHelper.getHelper(mContext);
        try {
            mRoleIntradayCountDaoOpe = mDatabaseHelper.getDao(RoleIntradayCount.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个RoleIntradayCount
     *
     * @param roleIntradayCount
     */
    public void addRoleIntradayCount(RoleIntradayCount roleIntradayCount) {
        try {
            mRoleIntradayCountDaoOpe.create(roleIntradayCount);
            for (DataBean item:roleIntradayCount.getData()
                 ) {
                item.setRoleIntradayCount(roleIntradayCount);
                new DataBeanDao(mContext).add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addRoleIntradayCounts(ArrayList<RoleIntradayCount> list){
        for (RoleIntradayCount itme:list) {
            addRoleIntradayCount(itme);
        }
    }

    public List<RoleIntradayCount> getAllRoleIntradayCounts() {
        List<RoleIntradayCount> list = null ;
        try {
            list = mRoleIntradayCountDaoOpe.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
