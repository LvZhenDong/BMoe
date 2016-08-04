package com.kklv.bmoe;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kklv.bmoe.activity.BangumiActivity;
import com.kklv.bmoe.activity.BaseActivity;
import com.kklv.bmoe.fragment.CampFragment;
import com.kklv.bmoe.fragment.LineChartFragment;
import com.kklv.bmoe.fragment.ThemeFragment;
import com.kklv.bmoe.utils.L;
import com.kklv.bmoe.utils.ThemeHelper;
import com.pgyersdk.update.PgyUpdateManager;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBar mActionBar;

    private FragmentManager mFragmentManager;
    private LineChartFragment mLineChartFragment;
    private CampFragment mCampFragment;
    private ThemeFragment mThemeFragment;

    private int[][] states = new int[][]{new int[]{-android.R.attr.state_checked},
            new int[]{android.R.attr.state_checked}};
    int[] colors = new int[]{0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO 自定义更新对话框
//        PgyUpdateManager.register(this);
        bindId();
        initView();

    }

    private void bindId() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void initView() {
        setNavItemColor();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mActionBar.setDisplayHomeAsUpEnabled(true);


        setupDrawerContent(mNavigationView);
        setDefaultFragment();

    }

    /**
     * 更新NavigationView菜单icon和title的颜色
     */
    public void setNavItemColor() {
        BMoeApplication application = (BMoeApplication) getApplication();
        colors[0] = getResources().getColor(R.color.gray_default);
        colors[1] = getResources().getColor(application.getThemeColor(this));
        ColorStateList csl = new ColorStateList(states, colors);
        mNavigationView.setItemTextColor(csl);
        mNavigationView.setItemIconTintList(csl);
    }

    private void setDefaultFragment() {
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        mLineChartFragment = new LineChartFragment();
        transaction.replace(R.id.fl_fragment, mLineChartFragment);
        transaction.commit();
        mActionBar.setTitle(R.string.line_chart);
    }

    /**
     * 设置侧滑栏
     *
     * @param navigationView
     */
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (!item.isChecked()) {
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    hideAllFragments(transaction);
                    switch (item.getItemId()) {
                        case R.id.nav_line_chart:
                            if (mLineChartFragment == null) {
                                mLineChartFragment = new LineChartFragment();
                                transaction.add(R.id.fl_fragment, mLineChartFragment);
                            } else {
                                transaction.show(mLineChartFragment);
                            }
                            break;
                        case R.id.nav_camp:
                            if (mCampFragment == null) {
                                mCampFragment = new CampFragment();
                                transaction.add(R.id.fl_fragment, mCampFragment);
                            } else {
                                transaction.show(mCampFragment);
                            }
                            break;
                        case R.id.nav_theme:
                            if (mThemeFragment == null) {
                                mThemeFragment = new ThemeFragment();
                                transaction.add(R.id.fl_fragment, mThemeFragment);
                            } else {
                                transaction.show(mThemeFragment);
                            }
                            break;
                    }
                    transaction.commit();
                    item.setChecked(true);
                    mActionBar.setTitle(item.getTitle());
                }

                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void hideAllFragments(FragmentTransaction transaction) {
        //TODO 考虑用更好的方式来实现
        if (mLineChartFragment != null) {
            transaction.hide(mLineChartFragment);
        }
        if (mCampFragment != null) {
            transaction.hide(mCampFragment);
        }
        if (mThemeFragment != null) {
            transaction.hide(mThemeFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_debug_null_point_exception:

                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
