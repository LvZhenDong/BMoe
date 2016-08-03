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
import com.kklv.bmoe.adapter.ThemeRecycleViewAdapter;
import com.kklv.bmoe.utils.ThemeHelper;

/**
 * 主题选择
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/18 13:56
 */
public class ThemeFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RadioGroup mRadioGroup;
    private RadioButton mPurpleRB, mPinkRB;

    private View rootView;
    private int lastCheckId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_theme, container, false);
        bindId(rootView);
        initView();

        return rootView;
    }

    private void bindId(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_theme);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.rg_theme);
        mPinkRB = (RadioButton) view.findViewById(R.id.rb_theme_pink);
        mPurpleRB = (RadioButton) view.findViewById(R.id.rb_theme_purple);
    }

    private void initView() {
        initRadioButton();
        ThemeRecycleViewAdapter adapter = new ThemeRecycleViewAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int theme = 0;
                switch (checkedId) {
                    case R.id.rb_theme_pink:
                        theme = ThemeHelper.CARD_SAKURA;
                        break;
                    case R.id.rb_theme_purple:
                        theme = ThemeHelper.CARD_HOPE;
                        break;
                }
                RadioButton rb = (RadioButton) rootView.findViewById(checkedId);
                rb.setText(getString(R.string.using));
                RadioButton lastRB = (RadioButton) rootView.findViewById(lastCheckId);
                lastRB.setText(getString(R.string.use));
                lastCheckId = checkedId;
                if (theme != 0) {
                    setTheme(theme);
                }

            }
        });
    }

    private void setTheme(int theme) {
        if (ThemeHelper.getTheme(getActivity()) != theme) {
            ThemeHelper.setTheme(getActivity(), theme);
            ThemeUtils.refreshUI(getActivity(), new ThemeUtils.ExtraRefreshable() {
                        @Override
                        public void refreshGlobal(Activity activity) {
                            MainActivity mainActivity = (MainActivity) activity;
                            mainActivity.setNavItemColor();
                        }

                        @Override
                        public void refreshSpecificView(View view) {
                            //TODO: will do this for each traversal
                        }
                    }
            );
        }
    }

    /**
     * 根据主题选择checked
     */
    private void initRadioButton() {
        BMoeApplication application = (BMoeApplication) getActivity().getApplication();
        switch (application.getThemeColor(getActivity())) {
            case R.color.theme_color_primary:
                lastCheckId=R.id.rb_theme_pink;
                break;
            case R.color.pink:
                lastCheckId=R.id.rb_theme_pink;
                break;
            case R.color.purple:
                lastCheckId=R.id.rb_theme_purple;
                break;
        }
        RadioButton rb= (RadioButton) rootView.findViewById(lastCheckId);
        rb.setChecked(true);
        rb.setText(getString(R.string.using));
    }
}
