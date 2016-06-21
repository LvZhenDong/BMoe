package com.kklv.bmoe.database;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/17.
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

    public void getAll() {

        try {
            List<DataBean> list = null;
//            list=mDataBeenOpe.queryForAll();
            list = mDataBeenOpe.queryBuilder().where().eq("time", "sd4").query();
            for (DataBean data : list
                    ) {
                Log.i("TAG", "\nd");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
