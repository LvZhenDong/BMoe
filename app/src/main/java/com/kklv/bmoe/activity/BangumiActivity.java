package com.kklv.bmoe.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kklv.bmoe.R;
import com.kklv.bmoe.data.DataHelper;

import java.util.List;

/**
 * 角色数据表格
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/13 17:43
 */
public class BangumiActivity extends BaseActivity implements DataHelper.DataHelperCallBack{
    public static final String BANGUMI = "bangumi";
    private ActionBar mActionBar;
    private String mBangumi;

    //调试
    EditText mEditText;
    Button mButton;
    TextView mTextView;

    DataHelper mDataHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bangumi);

        bindId();
        initView();

        mDataHelper =new DataHelper(this);
        mDataHelper.registerCallBack(this);
        mDataHelper.getImageUrl(mBangumi);
//        mDataHelper.getImageUrl("安卓");
    }


    private void bindId() {
        mEditText= (EditText) findViewById(R.id.et_keywords);
        mButton= (Button) findViewById(R.id.btn_search);
        mTextView= (TextView) findViewById(R.id.tv_url);
    }

    private void initView() {
        mBangumi = getIntent().getStringExtra(BANGUMI);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(mBangumi);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataHelper.getImageUrl(mEditText.getText()+"");
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
        mTextView.setText((String)result.get(0));
    }

    @Override
    public void onFailure(Exception error) {
        mTextView.setText("失败");
    }
}
