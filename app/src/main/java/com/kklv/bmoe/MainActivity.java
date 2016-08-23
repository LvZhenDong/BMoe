package com.kklv.bmoe;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.kklv.bmoe.activity.BaseActivity;
import com.kklv.bmoe.fragment.CampFragment;
import com.kklv.bmoe.fragment.LineChartFragment;
import com.kklv.bmoe.fragment.ThemeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private static final int LINE_CHART_FRAGMENT = 0;
    private static final int CAMP_FRAGMENT = 1;
    private static final int THEME_FRAGMENT = 2;
    int[] colors = new int[]{0, 0};

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    private ActionBar mActionBar;
    private FragmentManager mFragmentManager;
    private LineChartFragment mLineChartFragment;
    private CampFragment mCampFragment;
    private ThemeFragment mThemeFragment;
    private int[][] states = new int[][]{new int[]{-android.R.attr.state_checked}, new
            int[]{android.R.attr.state_checked}};
    /**
     * 当前显示的Fragment
     */
    private int showingFragment = LINE_CHART_FRAGMENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注解
        ButterKnife.bind(this);
        //TODO 自定义更新对话框
//        PgyUpdateManager.register(this);
        initView();

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
        colors[0] = getResources().getColor(R.color.text_primary_color);
        colors[1] = getResources().getColor(mThemeColorId);
        ColorStateList csl = new ColorStateList(states, colors);
        mNavigationView.setItemTextColor(csl);
        mNavigationView.setItemIconTintList(csl);
    }

    private void setDefaultFragment() {
        mFragmentManager = getFragmentManager();
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
        navigationView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (!item.isChecked()) {
                    switch (item.getItemId()) {
                        case R.id.nav_line_chart:
                            showFragment(LINE_CHART_FRAGMENT);
                            break;
                        case R.id.nav_camp:
                            showFragment(CAMP_FRAGMENT);
                            break;
                        case R.id.nav_theme:
                            showFragment(THEME_FRAGMENT);
                            break;
                    }

                    item.setChecked(true);
                    mActionBar.setTitle(item.getTitle());
                }

                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    /**
     * 显示制定的Fragment
     *
     * @param fragmentType
     */
    private void showFragment(int fragmentType) {
        if (showingFragment == fragmentType) return;

        showingFragment = fragmentType;

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        hideAllFragments(transaction);
        switch (fragmentType) {
            case LINE_CHART_FRAGMENT:
                if (mLineChartFragment == null) {
                    mLineChartFragment = new LineChartFragment();
                    transaction.add(R.id.fl_fragment, mLineChartFragment);
                } else {
                    transaction.show(mLineChartFragment);
                }
                break;
            case CAMP_FRAGMENT:
                if (mCampFragment == null) {
                    mCampFragment = new CampFragment();
                    transaction.add(R.id.fl_fragment, mCampFragment);
                } else {
                    transaction.show(mCampFragment);
                }
                break;
            case THEME_FRAGMENT:
                if (mThemeFragment == null) {
                    mThemeFragment = new ThemeFragment();
                    transaction.add(R.id.fl_fragment, mThemeFragment);
                } else {
                    transaction.show(mThemeFragment);
                }
                break;
        }
        transaction.commit();
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

    @Override
    public void onBackPressed() {
        //如果DrawerLayout是打开的话就关闭DrawerLayout
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        } else if (showingFragment != LINE_CHART_FRAGMENT) {//回到主界面“曲线图”
            showFragment(LINE_CHART_FRAGMENT);
            mNavigationView.setCheckedItem(R.id.nav_line_chart);
            //不会触发OnNavigationItemSelectedListener
            mActionBar.setTitle(R.string.line_chart);
            return;
        }
        super.onBackPressed();
    }
}
