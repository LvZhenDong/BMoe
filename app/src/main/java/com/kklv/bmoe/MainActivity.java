package com.kklv.bmoe;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.kklv.bmoe.chart.Chart;
import com.kklv.bmoe.fragment.CampFragment;
import com.kklv.bmoe.fragment.LineChartFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private FragmentManager mFragmentManager;
    private LineChartFragment mLineChartFragment;
    private CampFragment mCampFragment;
//    private LineChart mLineChart;
//    private Chart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindId();
        initView();

    }

    private void bindId() {
//        mLineChart = (LineChart) findViewById(R.id.lineChart);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void initView() {
//        mChart=new Chart(this,mLineChart);
//        mChart.showData();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        setupDrawerContent(mNavigationView);
        setDefaultFragment();

    }
    private void setDefaultFragment(){
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        mLineChartFragment=new LineChartFragment();
        transaction.replace(R.id.fl_fragment,mLineChartFragment);
        transaction.commit();
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (!item.isChecked()){
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    hideAllFragments(transaction);
                    switch (item.getItemId()){
                        case R.id.nav_line_chart:
                            if (mLineChartFragment == null){
                                mLineChartFragment=new LineChartFragment();
                                transaction.add(R.id.fl_fragment,mLineChartFragment);
                            }else {
                                transaction.show(mLineChartFragment);
                            }
                            break;
                        case R.id.nav_camp:
                            if (mCampFragment == null){
                                mCampFragment= new CampFragment();
                                transaction.add(R.id.fl_fragment,mCampFragment);
                            }else {
                                transaction.show(mCampFragment);
                            }
                            break;
                    }
                    transaction.commit();
                    item.setChecked(true);
                }

                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }
    private void hideAllFragments(FragmentTransaction transaction){
        //TODO 考虑用更好的方式来实现
        if(mLineChartFragment != null){
            transaction.hide(mLineChartFragment);
        }
        if(mCampFragment != null){
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
        }
        return super.onOptionsItemSelected(item);
    }
}
