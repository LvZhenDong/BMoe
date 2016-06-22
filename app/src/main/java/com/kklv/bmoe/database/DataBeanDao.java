package com.kklv.bmoe.database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.kklv.bmoe.object.DataBean;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/22 11:09
 */
public class DataBeanDao {
    private Dao<DataBean, Integer> mDataBeenOpe;
    private DatabaseHelper mDatabaseHelper;

    public DataBeanDao(Context context) {
        mDatabaseHelper = DatabaseHelper.getHelper(context);
        try {
            mDataBeenOpe = mDatabaseHelper.getDao(DataBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(DataBean dataBean) {
        try {
            mDataBeenOpe.create(dataBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DataBean> getAll() {
        List<DataBean> list=null;
        try {
            list = mDataBeenOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
