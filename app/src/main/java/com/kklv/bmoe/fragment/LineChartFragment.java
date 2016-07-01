package com.kklv.bmoe.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.kklv.bmoe.R;
import com.kklv.bmoe.activity.FullscreenActivity;
import com.kklv.bmoe.chart.BaseChart;
import com.kklv.bmoe.chart.Chart;
import com.kklv.bmoe.object.RoleDailyCount;
import com.kklv.bmoe.utils.StringUtils;
import com.touchmenotapps.widget.radialmenu.menu.v1.RadialMenuItem;
import com.touchmenotapps.widget.radialmenu.menu.v1.RadialMenuWidget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class LineChartFragment extends Fragment implements BaseChart.ChartCallBack {
    private LineChart mLineChart;
    private Chart mChart;


    private EditText mDatePickerET;
    private ImageButton mFullScreenIBtn, mLeftIBtn, mRightIBtn;
    private RadioGroup mSexRG;
    private RadioButton mMoeRB, mLightRB, mMoeAndLightRB;

    private ProgressDialog mProgressDialog;

    private RadialMenuWidget pieMenu;
    private RadialMenuItem centerItem, menuUpItem, menuDownItem, menuLeftItem;
    public RadialMenuItem firstChildItem, secondChildItem, thirdChildItem;
    private List<RadialMenuItem> children = new ArrayList<>();

    private Map<String, String> mParamsMap = new HashMap<>();

    /**
     * 类似于06-04-12这样的日期
     */
//    private String mDateStr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);

        bindId(view);
        initView();
//        mChart.getData(mParamsMap);
        return view;
    }

    private void bindId(View view) {
        mLineChart = (LineChart) view.findViewById(R.id.lineChart);
        mDatePickerET = (EditText) view.findViewById(R.id.et_date);
        mFullScreenIBtn = (ImageButton) view.findViewById(R.id.ibtn_full_screen);
        mLeftIBtn = (ImageButton) view.findViewById(R.id.ibtn_left);
        mRightIBtn = (ImageButton) view.findViewById(R.id.ibtn_right);
        mSexRG = (RadioGroup) view.findViewById(R.id.rg_sex);
        mMoeRB = (RadioButton) view.findViewById(R.id.rb_moe);
        mLightRB = (RadioButton) view.findViewById(R.id.rb_light);
        mMoeAndLightRB = (RadioButton) view.findViewById(R.id.rb_moe_light);
    }

    private void initView() {
        pieMenu = new RadialMenuWidget(getActivity());
        pieMenu.setAnimationSpeed(0L);
        pieMenu.setSourceLocation(200, 200);
        pieMenu.setIconSize(15, 30);
        pieMenu.setTextSize(13);
        pieMenu.setOutlineColor(Color.WHITE, 225);
        pieMenu.setInnerRingColor(0x0000ff, 180);
        pieMenu.setDisabledColor(0xff0000, 0xff);
        pieMenu.setOuterRingColor(0x00ff00, 0xff);
        pieMenu.setSelectedColor(Color.GRAY, 255);

        centerItem = new RadialMenuItem("center", "asdf");
        menuDownItem = new RadialMenuItem("down", "fghs");
        menuUpItem = new RadialMenuItem("up", "erfgge");
        menuLeftItem = new RadialMenuItem("left", "left");

        firstChildItem = new RadialMenuItem("kklv", "sd");
        secondChildItem = new RadialMenuItem("kklvs", "sdf");
        thirdChildItem = new RadialMenuItem("sdf", "sdfsd");
        children.add(firstChildItem);
        children.add(secondChildItem);
        children.add(thirdChildItem);
        menuDownItem.setMenuChildren(children);
        pieMenu.setCenterCircle(centerItem);
        pieMenu.addMenuEntry(new ArrayList<RadialMenuItem>() {
            {
                add(menuDownItem);
                add(menuUpItem);
                add(menuLeftItem);
            }
        });
        /***************************************************************************/
        initProgressDialog();
        mChart = new Chart(getActivity(), mLineChart);
        mChart.registerChartCallBack(this);

        mDatePickerET.setInputType(InputType.TYPE_NULL);
        String dateStr = getTodayDate();
        mDatePickerET.setText(dateStr);
        mParamsMap.put(RoleDailyCount.DATE, StringUtils.formatDateString(dateStr));
        mDatePickerET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] date = mParamsMap.get(RoleDailyCount.DATE).split("-");
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
                mParamsMap.put(RoleDailyCount.DATE, StringUtils.formatDateString(mDatePickerET.getText() + ""));
                mProgressDialog.show();
                mChart.getData(mParamsMap);
                Toast.makeText(getActivity(), StringUtils.formatDateString(mDatePickerET.getText() + ""),
                        Toast.LENGTH_SHORT).show();
            }
        });
        mDatePickerET.setFocusable(false);

        mFullScreenIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FullscreenActivity.class);
                intent.putExtra(FullscreenActivity.CAMP_LIST,
                        (ArrayList<RoleDailyCount>) mChart.getSplitList());
                startActivity(intent);
//                pieMenu.show(mFullScreenIBtn);
            }
        });
        mLeftIBtn.setOnClickListener(mChartRankListener);
        mRightIBtn.setOnClickListener(mChartRankListener);

        initRadioGroup();
    }

    private void initRadioGroup() {
        mSexRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_moe:
                        mChart.showMoe("0");
                        break;
                    case R.id.rb_light:
                        mChart.showMoe("1");
                        break;
                    case R.id.rb_moe_light:
                        mChart.showMoe("");
                        break;
                }
            }
        });
    }

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
    public void onLoadCompleted(boolean result) {
        mProgressDialog.dismiss();
        if(result){
            mMoeAndLightRB.setChecked(true);
        }
    }

    @Override
    public void showGroup(Set<String> groups) {

    }
}
