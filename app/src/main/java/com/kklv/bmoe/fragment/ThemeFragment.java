package com.kklv.bmoe.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.kklv.bmoe.BMoeApplication;
import com.kklv.bmoe.MainActivity;
import com.kklv.bmoe.R;
import com.kklv.bmoe.activity.BaseActivity;
import com.kklv.bmoe.adapter.ThemeRecycleViewAdapter;
import com.kklv.bmoe.utils.ThemeHelper;
import com.kklv.bmoe.view.DividerItemDecoration;

/**
 * 主题选择
 *
 * @author LvZhenDong
 * created at 2016/7/18 13:56
 */
public class ThemeFragment extends Fragment {
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theme, container, false);
        bindId(view);
        initView();

        return view;
    }

    private void bindId(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_theme);
    }

    private void initView() {
        ThemeRecycleViewAdapter adapter = new ThemeRecycleViewAdapter(getActivity(),
                (BaseActivity)getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRecyclerView.setAdapter(adapter);

    }

}
