package com.kklv.bmoe.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kklv.bmoe.R;
import com.kklv.bmoe.activity.BaseActivity;
import com.kklv.bmoe.adapter.ThemeRecycleViewAdapter;
import com.kklv.bmoe.view.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 主题选择
 *
 * @author LvZhenDong
 * created at 2016/7/18 13:56
 */
public class ThemeFragment extends BaseFragment {
    @BindView(R.id.rv_theme)
     RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theme, container, false);
        mUnbinder= ButterKnife.bind(this,view);
        initView();

        return view;
    }



    private void initView() {
        ThemeRecycleViewAdapter adapter = new ThemeRecycleViewAdapter(getActivity(),
                (BaseActivity)getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRecyclerView.setAdapter(adapter);

    }

}
