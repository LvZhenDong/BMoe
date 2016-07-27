package com.kklv.bmoe.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.kklv.bmoe.R;
import com.kklv.bmoe.utils.ThemeHelper;

/**
 *主题选择
 * 
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/18 13:56
 */
public class ThemeFragment extends Fragment{
    Button mButton;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_theme,container,false);
        bindId(view);
        initView();

        return view;
    }

    private void bindId(View view){
        mButton= (Button) view.findViewById(R.id.btn_theme);
    }

    private void initView(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ThemeHelper.getTheme(getActivity()) == ThemeHelper.CARD_SAKURA){
                    ThemeHelper.setTheme(getActivity(),ThemeHelper.CARD_HOPE);
                }else{
                    ThemeHelper.setTheme(getActivity(),ThemeHelper.CARD_SAKURA);
                }
                //刷新主题
                ThemeUtils.refreshUI(getActivity(), new ThemeUtils.ExtraRefreshable() {
                    @Override
                    public void refreshGlobal(Activity activity) {

                    }

                    @Override
                    public void refreshSpecificView(View view) {

                    }
                });
            }
        });
    }
}
