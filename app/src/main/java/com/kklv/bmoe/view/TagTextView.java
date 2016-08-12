package com.kklv.bmoe.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kklv.bmoe.R;

/**
 * @author LvZhenDong
 *         created at 2016/8/12 15:13
 */
public class TagTextView extends TextView {
    private String mTag = "";

    public TagTextView(Context context) {
        super(context);
    }

    public TagTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TagTextView);
        mTag = array.getString(R.styleable.TagTextView_tag);

        array.recycle();
    }

    /**
     * 使用这个方法可以保留tag
     *
     * @param text
     */
    public void setTextWithTag(String text) {
        super.setText(mTag + text);
    }

    /**
     * 去掉tag
     *
     * @return
     */
    @Override
    public CharSequence getText() {
        String result = super.getText() + "";
        return result.substring(0, mTag.length());
    }
}
