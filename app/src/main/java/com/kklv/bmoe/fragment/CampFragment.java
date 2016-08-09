package com.kklv.bmoe.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kklv.bmoe.MainActivity;
import com.kklv.bmoe.R;
import com.kklv.bmoe.activity.BangumiActivity;
import com.kklv.bmoe.adapter.CampListAdapter;
import com.kklv.bmoe.adapter.CampRecyclerViewAdapter;
import com.kklv.bmoe.data.DataHelper;
import com.kklv.bmoe.object.Camp;
import com.kklv.bmoe.utils.L;
import com.kklv.bmoe.utils.ListUtils;
import com.kklv.bmoe.utils.T;
import com.squareup.haha.perflib.Main;

import java.util.List;

/**
 * 阵营信息
 *
 * @author LvZhenDong
 * created at 2016/6/13 10:08
 */
public class CampFragment extends Fragment implements DataHelper.DataHelperCallBack {
    private static final String TAG = "CampFragment";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mCampRV;
    private CampRecyclerViewAdapter mCampRecyclerViewAdapter;
    private List<Camp> mList;

    private DataHelper mDataHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camp2, container, false);

        bindId(view);
        initView();
        getData();

        /**
         * 触发SwipeRefreshLayout
         */
        mSwipeRefreshLayout.post(new Runnable() {
            @Override public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        return view;
    }

    private void bindId(View view) {
        mCampRV = (RecyclerView) view.findViewById(R.id.rv_camp);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_camp);
    }

    private void initView() {
        mDataHelper = new DataHelper(getActivity());
        mDataHelper.registerCallBack(this);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    private void getData() {
        mSwipeRefreshLayout.setColorSchemeColors(((MainActivity)getActivity()).mThemeColor);
        mDataHelper.getAllCamps();
    }

    @Override
    public <T> void onSuccess(List<T> result) {
        if (!isAdded()) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(false);
        L.i(TAG, "result:" + result.size());
        if (!ListUtils.isEmpty(result) && (result.get(0) instanceof Camp)) {
            mList = (List<Camp>) result;
            if (mCampRecyclerViewAdapter == null) {
                mCampRecyclerViewAdapter = new CampRecyclerViewAdapter(getActivity(), mList);
                mCampRV.setLayoutManager(new LinearLayoutManager(getActivity()));
//                mCampRV.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
                mCampRV.setAdapter(mCampRecyclerViewAdapter);
            } else {
                mCampRecyclerViewAdapter.setData(mList);
            }
        } else {
            com.kklv.bmoe.utils.T.showShort(getActivity(), R.string.no_data);
        }
    }

    @Override
    public void onFailure(String error) {
        if (!isAdded()) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(false);
        T.showShort(getActivity(), R.string.net_error);
    }
}
