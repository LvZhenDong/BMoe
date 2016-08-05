package com.kklv.bmoe.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.kklv.bmoe.R;


/**
 *可染色、勾选的圆形View
 * 
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/8/5 9:24
 */
public class TintCircleView extends AppCompatImageView {

    public TintCircleView(Context context) {
        super(context);
        init();
    }

    public TintCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_circle_white));
    }

    public void setColor(int color) {
        ViewCompat.setBackgroundTintList(this,ColorStateList.valueOf(color));
    }

    public void setChecked(boolean isChecked){
        if(isChecked){
            setImageResource(R.drawable.ic_selected_white_small);
        }else{
            setImageResource(0);
        }
    }
}
