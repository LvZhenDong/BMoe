package com.kklv.bmoe;

import android.app.Activity;
import android.os.Bundle;

import com.kklv.bmoe.data.HttpUtil;

public class MainActivity extends Activity {
    HttpUtil mHttpUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHttpUtil=new HttpUtil(this);
        test();
    }

    @Override
    protected void onResume() {
        super.onResume();
        test();
    }

    private void test() {
        mHttpUtil.get();
    }


}
