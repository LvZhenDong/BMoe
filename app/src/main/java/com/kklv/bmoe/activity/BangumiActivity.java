package com.kklv.bmoe.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kklv.bmoe.R;

/**
 *角色数据表格
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/13 17:43
 */
public class BangumiActivity extends BaseActivity {
    public static final String BANGUMI="bangumi";
    private ActionBar mActionBar;
    private String mBangumi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangumi);

        bindId();
        initView();
    }

    private void bindId(){

    }

    private void initView(){
        mBangumi=getIntent().getStringExtra(BANGUMI);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar=getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(mBangumi);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
