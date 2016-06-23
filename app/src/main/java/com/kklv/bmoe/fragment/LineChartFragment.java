package com.kklv.bmoe.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.kklv.bmoe.R;
import com.kklv.bmoe.activity.FullscreenActivity;
import com.kklv.bmoe.chart.BaseChart;
import com.kklv.bmoe.chart.Chart;
import com.kklv.bmoe.database.RoleIntradayCountDao;
import com.kklv.bmoe.database.TestDatabase;
import com.kklv.bmoe.object.RoleIntradayCount;
import com.kklv.bmoe.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class LineChartFragment extends Fragment implements BaseChart.ChartCallBack {
    private LineChart mLineChart;
    private Chart mChart;


    private EditText mDatePickerET;
    private Button mFullScreenBtn;
    private Button mDrawChartBtn;

    private ProgressDialog mProgressDialog;

    /**
     * 类似于06-04-12这样的日期
     */
    private String mDateStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);

        bindId(view);
        initView();
        mChart.showData(mDateStr);
        return view;
    }

    private void bindId(View view) {
        mLineChart = (LineChart) view.findViewById(R.id.lineChart);
        mDatePickerET = (EditText) view.findViewById(R.id.et_date);
        mFullScreenBtn = (Button) view.findViewById(R.id.btn_full_screen);
        mDrawChartBtn = (Button) view.findViewById(R.id.btn_draw_chart);
    }

    private void initView() {
        initProgressDialog();
        mChart = new Chart(getActivity(), mLineChart);
        mChart.registerChartCallBack(this);

        mDatePickerET.setInputType(InputType.TYPE_NULL);
        mDateStr = getTodayDate();
        mDatePickerET.setText(mDateStr);
        mDateStr = StringUtils.formatDateString(mDateStr);
        mDatePickerET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] date = mDateStr.split("-");
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mDatePickerET.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, Integer.parseInt("20" + date[0]), Integer.parseInt(date[1]) - 1,
                        Integer.parseInt(date[2])).show();
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
                mDateStr = StringUtils.formatDateString(mDatePickerET.getText() + "");
//                mChart.setData();
                Toast.makeText(getActivity(), StringUtils.formatDateString(mDatePickerET.getText() + ""),
                        Toast.LENGTH_SHORT).show();
            }
        });
        mDatePickerET.setFocusable(false);

        mFullScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                intent.putExtra(FullscreenActivity.CAMP_LIST, (ArrayList<RoleIntradayCount>)mChart.getCampList());
                startActivity(intent);
            }
        });
        mDrawChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog.show();
                mChart.showData(mDateStr);
            }
        });
    }

    private void initProgressDialog(){
        mProgressDialog=new ProgressDialog(getActivity());
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // TODO 取消网络请求
            }
        });
        mProgressDialog.setCancelable(true);
    }
    /**
     * 获取当天日期
     *
     * @return
     */
    private String getTodayDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sDateFormat.format(new java.util.Date());
    }

    @Override
    public void onLoadCompleted() {
        mProgressDialog.dismiss();
    }
}
