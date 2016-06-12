package com.kklv.bmoe.fragment;

import android.app.DatePickerDialog;
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
import com.kklv.bmoe.chart.Chart;
import com.kklv.bmoe.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class LineChartFragment extends Fragment {
    private LineChart mLineChart;
    private Chart mChart;


    private EditText mDatePickerET;
    private Button mFullScreenBtn;

    private String dateStr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);

        bindId(view);
        initView();

        return view;
    }

    private void bindId(View view) {
        mLineChart = (LineChart) view.findViewById(R.id.lineChart);
        mDatePickerET = (EditText) view.findViewById(R.id.et_date);
        mFullScreenBtn= (Button) view.findViewById(R.id.btn_full_screen);
    }

    private void initView() {
        mChart = new Chart(getActivity(), mLineChart);
        mChart.showData();

        mDatePickerET.setInputType(InputType.TYPE_NULL);
        dateStr=getTodayDate();
        mDatePickerET.setText(dateStr);
        mDatePickerET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mDatePickerET.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
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
                Toast.makeText(getActivity(), StringUtils.formatDateString(mDatePickerET.getText()+""),
                        Toast.LENGTH_SHORT).show();
            }
        });

        mFullScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 传递数据过去
                Intent intent=new Intent(getActivity(),FullscreenActivity.class);

                startActivity(intent);
            }
        });
    }

    /**
     * 获取当天日期
     * @return
     */
    private String getTodayDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sDateFormat.format(new java.util.Date());
    }
}
