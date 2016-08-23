package com.kklv.bmoe.fragment;

import android.app.Fragment;

import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/8/23.
 */
public class BaseFragment extends Fragment {

    Unbinder mUnbinder;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) mUnbinder.unbind();
    }
}
