package com.kklv.bmoe.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kklv.bmoe.R;
import com.kklv.bmoe.activity.BangumiActivity;
import com.kklv.bmoe.adapter.CampListAdapter;
import com.kklv.bmoe.data.DataHelper;
import com.kklv.bmoe.object.Camp;

import java.util.ArrayList;
import java.util.List;

/**
 * 阵营信息
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/13 10:08
 */
public class CampFragment extends Fragment implements DataHelper.DataHelperCallBack {
    private static final String TAG = "CampFragment";
    private ListView mCampLV;
    private CampListAdapter mCampListAdapter;
    private List<Camp> mList;

    private DataHelper mDataHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_camp, container, false);

        bindId(view);
        initView();
        getData();
        return view;
    }

    private void bindId(View view) {
        mCampLV = (ListView) view.findViewById(R.id.lv_camp);
    }

    private void initView() {
        mDataHelper = new DataHelper(getActivity());
        mDataHelper.registerCallBack(this);

        mCampLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), BangumiActivity.class);
                intent.putExtra(BangumiActivity.BANGUMI,mList.get(position).getBangumi());
                startActivity(intent);
            }
        });
    }

    private void getData() {
        mDataHelper.getAllCamps();
    }

    @Override
    public <T> void onSuccess(List<T> result) {
        if (!isAdded()) {
            return;
        }
        Log.i(TAG, "result:" + result.size());
        if (result != null) {
            mList = (List<Camp>) result;
            if (mCampListAdapter == null) {
                mCampListAdapter = new CampListAdapter(getActivity(), mList);
                mCampLV.setAdapter(mCampListAdapter);
            } else {
                mCampListAdapter.setData(mList);
            }
        } else {
            Toast.makeText(getActivity(), R.string.no_data, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Exception error) {
        if (!isAdded()) {
            return;
        }
        Toast.makeText(getActivity(), R.string.net_error, Toast.LENGTH_SHORT).show();
    }

}
