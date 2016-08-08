package com.kklv.bmoe.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleDailyCount;
import com.kklv.bmoe.utils.L;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LvZhenDong
 * created at 2016/6/22 10:30
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "BMoe.db";
    private static final int DB_VERSION = 1;


    private Map<String, Dao> mDaos = new HashMap<>();
    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database,
                         ConnectionSource connectionSource) {
        //创建表
        try {
            TableUtils.createTable(connectionSource, RoleDailyCount.class);
            TableUtils.createTable(connectionSource, DataBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          ConnectionSource connectionSource, int oldVersion, int newVersion) {
        //更新表
        try {
            TableUtils.dropTable(connectionSource, RoleDailyCount.class, true);
            TableUtils.dropTable(connectionSource, DataBean.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            L.i("TAG", "onCreate:e2");
            e.printStackTrace();
        }
    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }

        return instance;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (mDaos.containsKey(className)) {
            dao = mDaos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            mDaos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();

        for (String key : mDaos.keySet()) {
            Dao dao = mDaos.get(key);
            dao = null;
        }
    }

}