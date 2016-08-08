package com.kklv.bmoe.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.kklv.bmoe.BMoeApplication;
import com.kklv.bmoe.R;
import com.pgyersdk.crash.PgyCrashManager;

/**
 * @author LvZhenDong
 * created at 2016/6/12 17:24
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * 主题颜色id
     */
    public int mThemeColorId= R.color.pink;
    /**
     * 主题颜色名称
     */
    public String mThemeColorName="pink";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PgyCrashManager.register(this);
        setStatusBar();
        getThemeColor();
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= 19) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void getThemeColor(){
        BMoeApplication bMoeApplication= (BMoeApplication) getApplication();
        mThemeColorId=bMoeApplication.getThemeColor(this);
        mThemeColorName=bMoeApplication.getTheme(this);
    }
}
