package com.kklv.bmoe.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kklv.bmoe.BMoeApplication;
import com.kklv.bmoe.R;
import com.kklv.bmoe.adapter.BangumiRecycleViewAdapter;
import com.kklv.bmoe.data.DataHelper;
import com.kklv.bmoe.diskLruCache.DiskLruCacheHelper;
import com.kklv.bmoe.object.BingImageSearchResult;
import com.kklv.bmoe.utils.L;
import com.kklv.bmoe.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色数据表格
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/13 17:43
 */
public class BangumiActivity extends BaseActivity implements DataHelper.DataHelperCallBack {
    public static final String BANGUMI = "bangumi";
    private static final String TAG = "BangumiActivity";

    /**
     * 主题颜色
     */
    public static int THEME_COLOR_ID = R.color.pink;
    private String mBangumi;

    private FloatingActionButton mFloatingActionButton;

    private BingImageSearchResult mBingImageSearchResult;
    private DiskLruCacheHelper mDiskLruCacheHelper;
    //调试
    SimpleDraweeView mSimpleDraweeView;
    RecyclerView mRecyclerView;
    DataHelper mDataHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangumi);

        bindId();
        getColor();
        initView();

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            list.add("");
        }
        BangumiRecycleViewAdapter adapter = new BangumiRecycleViewAdapter(this, list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);


        mDataHelper = new DataHelper(this);
        mDataHelper.registerCallBack(this);
        mDiskLruCacheHelper = DiskLruCacheHelper.getInstance(this);
        mDataHelper.getImageUrl(mBangumi);
    }

    private void getColor(){
        BMoeApplication application= (BMoeApplication) getApplication();
        THEME_COLOR_ID=application.getThemeColor(this);
    }


    private void bindId() {
        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.sdv_image);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_bangumi);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
    }

    private void initView() {
        mBangumi = getIntent().getStringExtra(BANGUMI);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mBangumi);
        collapsingToolbarLayout.setContentScrimResource(THEME_COLOR_ID);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBingImageSearchResult != null) {
                    showImage(mBingImageSearchResult.next());
                    mDiskLruCacheHelper.writeBingImageSearchResult2Disk(null, mBingImageSearchResult);
                } else {
                    //没有该动画的数据就去取一次
                    mDataHelper.getImageUrl(mBangumi);
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        //TODO 取消所有网络请求

        super.onDestroy();
    }

    @Override
    public <T> void onSuccess(List<T> result) {
        mBingImageSearchResult = (BingImageSearchResult) result.get(0);
        showImage(mBingImageSearchResult.getIndexUrl());
    }

    @Override
    public void onFailure(String error) {
        T.showShort(this, error);
    }

    private void showImage(String url) {
        L.i(TAG, "Fresco showImage uri:" + url);
        Uri uri = Uri.parse(url);
        mSimpleDraweeView.setImageURI(uri);
    }
}
