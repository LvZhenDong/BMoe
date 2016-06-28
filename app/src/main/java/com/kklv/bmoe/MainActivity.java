package com.kklv.bmoe;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kklv.bmoe.activity.BaseActivity;
import com.kklv.bmoe.chart.Chart;
import com.kklv.bmoe.fragment.CampFragment;
import com.kklv.bmoe.fragment.LineChartFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBar mActionBar;

    private FragmentManager mFragmentManager;
    private LineChartFragment mLineChartFragment;
    private CampFragment mCampFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindId();
        initView();

    }

    private void bindId() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mActionBar.setDisplayHomeAsUpEnabled(true);


        setupDrawerContent(mNavigationView);
        setDefaultFragment();

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
//                Chart debug=null;   //调试蒲公英
//                debug.showData("SFSD");
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
