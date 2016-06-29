package com.kklv.bmoe.database;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleDailyCount;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/17 11:17
 */
public class RoleDailyCountDao {
    private static final String TAG = "RoleDailyCountDao";
    private Context mContext;
    private Dao<RoleDailyCount, Integer> mRoleDailyCountDaoOpe;
    private DatabaseHelper mDatabaseHelper;

    public RoleDailyCountDao(Context context) {
        this.mContext = context;
        mDatabaseHelper = DatabaseHelper.getHelper(mContext);
        try {
            mRoleDailyCountDaoOpe = mDatabaseHelper.getDao(RoleDailyCount.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个RoleDailyCount
     *
     * @param roleDailyCount
     */
    private void addOrUpdateRoleDailyCount(RoleDailyCount roleDailyCount) {
        try {
            mRoleDailyCountDaoOpe.createOrUpdate(roleDailyCount);
//            Collection<DataBean> list=mRoleDailyCountDaoOpe.getEmptyForeignCollection("data");//方法二
            for (DataBean item : roleDailyCount.getData()
                    ) {
                item.setRoleDailyCount(roleDailyCount);
//                list.add(item);//方法二
                new DataBeanDao(mContext).add(item);//方法一
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addOrUpdateRoleDailyCounts(List<RoleDailyCount> list) {
        Log.i(TAG,"begin addOrUpdateRoleDailyCount");
        for (RoleDailyCount itme : list) {
            addOrUpdateRoleDailyCount(itme);
        }
        Log.i(TAG,"addOrUpdateRoleDailyCount over");
    }

    /**
     * 查询所有的RoleDailyCount
     *
     * @return
     */
    public List<RoleDailyCount> getAllRoleDailyCounts() {
        List<RoleDailyCount> list = null;
        try {
            list = mRoleDailyCountDaoOpe.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取对应的数据，并按macCount降序排列
     *
     * @param date
     * @param sex
     * @return
     */
    public List<RoleDailyCount> getRoleDailyCounts(String date, String sex) {

        QueryBuilder<RoleDailyCount, Integer> queryBuilder = mRoleDailyCountDaoOpe.queryBuilder();
        Where<RoleDailyCount, Integer> where = queryBuilder.where();
        try {

            //TODO 为什么把maxCount改为主键就成功了？ 现在不用主键也可以了，莫名其妙就好了，无语
            List<RoleDailyCount> list = null;
            if (TextUtils.isEmpty(sex)) {
                list = queryBuilder.orderBy(RoleDailyCount.MAX_COUNT, false).where().eq(RoleDailyCount.DATE, date).query();
            } else if (RoleDailyCount.SEX_MOE.equals(sex) || RoleDailyCount.SEX_LIGHT.equals(sex)) {
                list = queryBuilder.orderBy(RoleDailyCount.MAX_COUNT, false).where().eq(RoleDailyCount.DATE, date).
                        and().eq(RoleDailyCount.SEX, sex).query();
            }
            if (list != null)
                for (RoleDailyCount item : list
                        ) {
                    Log.i("kklv", "name:" + item.getName() + ";id:" + item.getId() + ";maxCount:" + item.getMaxCount());
                }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
