package com.kklv.bmoe.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.kklv.bmoe.R;
import com.kklv.bmoe.activity.FullscreenActivity;
import com.kklv.bmoe.chart.BaseChart;
import com.kklv.bmoe.object.RoleDailyCount;
import com.kklv.bmoe.utils.DensityUtils;
import com.kklv.bmoe.utils.ListUtils;
import com.kklv.bmoe.utils.StringUtils;
import com.kklv.bmoe.utils.T;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineChartFragment extends Fragment implements BaseChart.ChartCallBack,
        DatePickerDialog.OnDateSetListener{
    private LineChart mLineChart;
    private BaseChart mChart;

    private EditText mDatePickerET;
    private ImageButton mFullScreenIBtn, mLeftIBtn, mRightIBtn;
    //选择萌燃
    private RadioButton mMoeRB, mLightRB, mMoeAndLightRB;
    private int checkedSexId = R.id.rb_moe_light;
    //选择分组
    private RadioGroup mGroupRG;
    private RadioButton mGroupAllRB;
    private int checkedGroupRBId = R.id.rb_group_all;
    //选择图表类型
    private RadioGroup mCreatorRG;
    private int checkedCreatorRBId = R.id.rb_total_tickets_count_creator;

    private ProgressDialog mProgressDialog;

    private Map<String, String> mParamsMap = new HashMap<>();
    /**
     * Sex的监听
     */
    private View.OnClickListener mMoeLightListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO 感觉写的一点也不优雅
            int id = v.getId();
            if (id == checkedSexId) return;
            switch (id) {
                case R.id.rb_moe:
                    mChart.showMoe(RoleDailyCount.SEX_MOE);
                    break;
                case R.id.rb_light:
                    mChart.showMoe(RoleDailyCount.SEX_LIGHT);
                    break;
                case R.id.rb_moe_light:
                    mChart.showMoe(RoleDailyCount.SEX_ALL);
                    break;
            }
            checkedSexId = id;
        }
    };
    /**
     * Group的监听
     */
    private View.OnClickListener mGroupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == checkedGroupRBId) return;
            RadioButton rb = (RadioButton) v;
            mChart.showGroup(rb.getText() + "");
            checkedGroupRBId = id;
        }
    };
    private View.OnClickListener mChartRankListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ibtn_left:
                    mChart.goLeftSplitLists();
                    break;
                case R.id.ibtn_right:
                    mChart.goRightSplitLists();
                    break;
            }
        }
    };

    /**
     * 获取当天日期
     *
     * @return
     */
    private static String getTodayDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sDateFormat.format(new java.util.Date());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);

        bindId(view);
        initView();
//        mDatePickerET.setMessage("2015-12-12");  //自动化测试
        return view;
    }

    private void bindId(View view) {
        mLineChart = (LineChart) view.findViewById(R.id.lineChart);
        mDatePickerET = (EditText) view.findViewById(R.id.et_date);
        mFullScreenIBtn = (ImageButton) view.findViewById(R.id.ibtn_full_screen);
        mLeftIBtn = (ImageButton) view.findViewById(R.id.ibtn_left);
        mRightIBtn = (ImageButton) view.findViewById(R.id.ibtn_right);
        mMoeRB = (RadioButton) view.findViewById(R.id.rb_moe);
        mLightRB = (RadioButton) view.findViewById(R.id.rb_light);
        mMoeAndLightRB = (RadioButton) view.findViewById(R.id.rb_moe_light);
        mGroupRG = (RadioGroup) view.findViewById(R.id.rg_group);
        mGroupAllRB = (RadioButton) view.findViewById(R.id.rb_group_all);
        mCreatorRG = (RadioGroup) view.findViewById(R.id.rg_creator);
    }

    private void initView() {

        initProgressDialog();
        mChart = new BaseChart(getActivity(), mLineChart, BaseChart.CREATOR_TOTAL_TICKETS_COUNT);
        mChart.registerChartCallBack(this);

        mDatePickerET.setInputType(InputType.TYPE_NULL);
        String dateStr = getTodayDate();
        mDatePickerET.setText(dateStr);
        mParamsMap.put(RoleDailyCount.DATE, StringUtils.formatDateString(dateStr));
        mDatePickerET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] date = mParamsMap.get(RoleDailyCount.DATE).split("-");

                //日期选择器
                DatePickerDialog datePickerDialog=DatePickerDialog.newInstance(LineChartFragment.this,
                        Integer.parseInt("20" + date[0]), Integer.parseInt(date[1]) - 1,
                        Integer.parseInt(date[2]));
                datePickerDialog.vibrate(false);
                datePickerDialog.show(getFragmentManager(),"Datepickerdialog");
            }
        });
        mDatePickerET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mParamsMap.put(RoleDailyCount.DATE, StringUtils.formatDateString(mDatePickerET.getText() + ""));
                mProgressDialog.show();
                mChart.getData(mParamsMap);
                T.showShort(getActivity(), StringUtils.formatDateString(mDatePickerET.getText() + ""));
            }
        });
        mDatePickerET.setFocusable(false);

        mFullScreenIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                intent.putExtra(FullscreenActivity.KEY_ROLE_DAILY_LIST,
                        (ArrayList<RoleDailyCount>) mChart.getSplitList());
                intent.putExtra(FullscreenActivity.KEY_CREATOR_TYPE, mChart.getCreatorType());
                startActivity(intent);
            }
        });
        mLeftIBtn.setOnClickListener(mChartRankListener);
        mRightIBtn.setOnClickListener(mChartRankListener);

        mCreatorRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != checkedCreatorRBId) {
                    checkedCreatorRBId = checkedId;

                    switch (checkedId) {
                        case R.id.rb_total_tickets_count_creator:
                            mChart.setChartTypeAndShow(BaseChart.CREATOR_TOTAL_TICKETS_COUNT);
                            break;
                        case R.id.rb_one_hour_tickets_count_creator:
                            mChart.setChartTypeAndShow(BaseChart.CREATOR_ONE_HOUR_TICKETS_COUNT);
                            break;
                        case R.id.rb_one_hour_tickets_percent_creator:
                            mChart.setChartTypeAndShow(BaseChart.CREATOR_ONE_HOUR_TICKETS_PERCENT);
                            break;
                        case R.id.rb_total_tickets_percent_creator:
                            mChart.setChartTypeAndShow(BaseChart.CREATOR_TOTAL_TICKETS_PERCENT);
                            break;
                    }
                }
            }
        });
        initSexRadioGroup();
        initGroupRadioGroup();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        //设置选择的日期
        mDatePickerET.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
    }

    private void initSexRadioGroup() {
        mMoeRB.setOnClickListener(mMoeLightListener);
        mLightRB.setOnClickListener(mMoeLightListener);
        mMoeAndLightRB.setOnClickListener(mMoeLightListener);
    }

    private void initGroupRadioGroup() {
        mGroupAllRB.setOnClickListener(mGroupListener);
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO 取消网络请求
                mChart.cancelRequest();
                // TODO 这里只取消了网络请求，没有取消数据库请求
            }
        });
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onLoadCompleted(boolean result) {
        mProgressDialog.dismiss();
    }

    @Override
    public void resetRG() {
        mMoeAndLightRB.setChecked(true);
        checkedSexId = R.id.rb_moe_light;
        mGroupAllRB.setChecked(true);
        checkedGroupRBId = R.id.rb_group_all;
    }

    @Override
    public void setDescription(String description) {
        setParentActivityActionBarTitle(description);
    }

    @Override
    public void showGroup(List<String> list) {
        //删除除第一个以外的RadioButton
        if (mGroupRG.getChildCount() > 1) {
            mGroupRG.removeViews(1, mGroupRG.getChildCount() - 1);
        }

        //生成RadioButton
        if (!ListUtils.isEmpty(list)) {
            mGroupRG.setVisibility(View.VISIBLE);
            for (String item : list) {
                if (!isAdded()) return;
                RadioButton rb = (RadioButton) LayoutInflater.from(getActivity()).
                        inflate(R.layout.item_radio_button, null);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                int margin = DensityUtils.dip2px(getActivity(), 5);
                params.setMargins(margin, margin, margin, margin);
                rb.setText(item);
                rb.setOnClickListener(mGroupListener);
                mGroupRG.addView(rb);
                rb.setLayoutParams(params);
            }

        } else {
            mGroupRG.setVisibility(View.GONE);
        }
    }

    private void setParentActivityActionBarTitle(String title) {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

}
