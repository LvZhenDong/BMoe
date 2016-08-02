package com.kklv.bmoe.database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleDailyCount;
import com.kklv.bmoe.utils.ListUtils;

import java.sql.SQLException;
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
//            Collection<DataBean> list = mRoleDailyCountDaoOpe.getEmptyForeignCollection("data");//方法二
            for (DataBean item : roleDailyCount.getData()) {
                item.setRoleDailyCount(roleDailyCount);
                //手动设置id，roleDailyCount的id+""+(DataBean的time+idIncrement)
//                item.setId(Integer.parseInt(roleDailyCount.getId()+""
//                        + (item.getTime()+DataBean.idIncrement)));
                item.setId(roleDailyCount.getId() * 100 + item.getTime());
//                list.add(item);//方法二
                new DataBeanDao(mContext).add(item);//方法一
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addOrUpdateRoleDailyCounts(List<RoleDailyCount> list) {
        for (RoleDailyCount itme : list) {
            addOrUpdateRoleDailyCount(itme);
        }
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
     * @return
     */
    public List<RoleDailyCount> getRoleDailyCounts(String date) {

        QueryBuilder<RoleDailyCount, Integer> queryBuilder = mRoleDailyCountDaoOpe.queryBuilder();
        Where<RoleDailyCount, Integer> where = queryBuilder.where();
        try {
            List<RoleDailyCount> list = queryBuilder.orderBy(RoleDailyCount.MAX_COUNT, false).where().eq(RoleDailyCount.DATE, date).query();    //方法一：比方法二快0.25s左右
//            where.eq(RoleDailyCount.DATE, date);      //方法二
//            queryBuilder.orderBy(RoleDailyCount.MAX_COUNT, false);
//            List<RoleDailyCount> list=queryBuilder.query();
            if (!ListUtils.isEmpty(list))
                return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
