package com.kklv.bmoe;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;
import com.squareup.leakcanary.LeakCanary;

/**
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/15 17:17
 */
public class BMoeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PgyCrashManager.register(this);
        LeakCanary.install(this);
    }
}
