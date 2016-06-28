package com.kklv.bmoe.database;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleIntradayCount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
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
    public void addOrUpdateRoleIntradayCount(RoleIntradayCount roleIntradayCount) {
        try {
            mRoleIntradayCountDaoOpe.createOrUpdate(roleIntradayCount);
            for (DataBean item : roleIntradayCount.getData()
                    ) {
                item.setRoleIntradayCount(roleIntradayCount);
                new DataBeanDao(mContext).add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addOrUpdateRoleIntradayCounts(List<RoleIntradayCount> list) {
        for (RoleIntradayCount itme : list) {
            addOrUpdateRoleIntradayCount(itme);
        }
    }

    /**
     * 查询所有的RoleIntradayCount
     *
     * @return
     */
    public List<RoleIntradayCount> getAllRoleIntradayCounts() {
        List<RoleIntradayCount> list = null;
        try {
            list = mRoleIntradayCountDaoOpe.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取日期对应的数据，并按macCount降序排列
     *
     * @param date
     * @return
     */
    public List<RoleIntradayCount> getRoleIntradayCounts(String date, String sex) {

        QueryBuilder<RoleIntradayCount, Integer> queryBuilder = mRoleIntradayCountDaoOpe.queryBuilder();
        Where<RoleIntradayCount, Integer> where = queryBuilder.where();
        try {

            //TODO 为什么把maxCount改为主键就成功了？ 现在不用主键也可以了，莫名其妙就好了，无语
            List<RoleIntradayCount> list;
            if (TextUtils.isEmpty(sex)) {
                list = queryBuilder.orderBy("maxCount", false).where().eq("date", date).query();
            } else {
                list = queryBuilder.orderBy("maxCount", false).where().eq("date", date).and().eq("sex", sex).query();
            }
            for (RoleIntradayCount item : list
                    ) {
                Log.i("kklv", "name:" + item.getName() + ";id:" + item.getId() + ";maxCount:" + item.getMaxCount());
            }
            //TODO 有没有不用写"date"，直接根据RoleIntradayCount里面名称改变的方法
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
