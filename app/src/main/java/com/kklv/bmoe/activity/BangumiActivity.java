package com.kklv.bmoe.activity;

import android.content.res.ColorStateList;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kklv.bmoe.R;
import com.kklv.bmoe.adapter.BangumiRecycleViewAdapter;
import com.kklv.bmoe.data.DataHelper;
import com.kklv.bmoe.diskLruCache.DiskLruCacheHelper;
import com.kklv.bmoe.object.BingImageSearchResult;
import com.kklv.bmoe.object.RoleInfo;
import com.kklv.bmoe.utils.L;
import com.kklv.bmoe.utils.T;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色数据表格
 *
 * @author LvZhenDong
 *         created at 2016/6/13 17:43
 */
public class BangumiActivity extends BaseActivity implements DataHelper.DataHelperCallBack {
    public static final String BANGUMI = "bangumi";
    private static final String TAG = "BangumiActivity";

    private int mTotalError;

    private String mBangumi;

    private BingImageSearchResult mBingImageSearchResult;
    private DiskLruCacheHelper mDiskLruCacheHelper;
    private BangumiRecycleViewAdapter mBangumiRecycleViewAdapter;

    SimpleDraweeView mSimpleDraweeView;
    RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;

    DataHelper mDataHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangumi);

        bindId();
        getThemeColor();
        initView();




        mDataHelper = new DataHelper(this);
        mDataHelper.registerCallBack(this);
        mDiskLruCacheHelper = DiskLruCacheHelper.getInstance(this);
        mDataHelper.getImageUrl(mBangumi);
        mDataHelper.getCampRank(mBangumi);
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
        //设置折叠时颜色
        collapsingToolbarLayout.setContentScrimResource(mThemeColorId);

        //设置FloatingActionButton的颜色
        mFloatingActionButton.
                setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(mThemeColorId)));
        int rippleColorId = getResources().
                getIdentifier(mThemeColorName + "_trans", "color", getPackageName());
        mFloatingActionButton.setRippleColor(getResources().getColor(rippleColorId));
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBingImageSearchResult != null) {
                    showNext();
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
        if(result.get(0) instanceof BingImageSearchResult){
            mBingImageSearchResult = (BingImageSearchResult) result.get(0);
            showImage(mBingImageSearchResult.getIndexUrl());
        }else if(result.get(0) instanceof RoleInfo){
            if(mBangumiRecycleViewAdapter == null){
                mBangumiRecycleViewAdapter = new BangumiRecycleViewAdapter(this, (List<RoleInfo>) result);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                mRecyclerView.setAdapter(mBangumiRecycleViewAdapter);
            }else{
                mBangumiRecycleViewAdapter.setData((List<RoleInfo>)result);
            }
        }

    }

    @Override
    public void onFailure(String error) {
        T.showShort(this, error);
    }


    private void showNext() {
        showImage(mBingImageSearchResult.next());
        mDiskLruCacheHelper.writeBingImageSearchResult2Disk(null, mBingImageSearchResult);
    }

    private void showImage(String url) {
        L.i(TAG, "Fresco showImage uri:" + url);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setTapToRetryEnabled(true)
                .setOldController(mSimpleDraweeView.getController())
                .setControllerListener(mControllerListener)
                .build();
        mSimpleDraweeView.setController(controller);
    }

    ControllerListener mControllerListener = new BaseControllerListener() {

        @Override
        public void onFinalImageSet(String id, @javax.annotation.Nullable Object imageInfo,
                                    @javax.annotation.Nullable Animatable animatable) {
            mTotalError = 0;
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            if (mTotalError >= 10) {
                mTotalError = 0;
                T.showShort(BangumiActivity.this, getString(R.string.load_image_error));
            }
            mTotalError++;
            showNext();
        }
    };
}
