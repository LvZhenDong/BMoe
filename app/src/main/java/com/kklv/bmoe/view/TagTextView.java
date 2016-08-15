package com.kklv.bmoe.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.kklv.bmoe.R;

/**
 * @author LvZhenDong
 *         created at 2016/8/12 15:13
 */
public class TagTextView extends TextView {
    private String mTag = "";

    private int mTagColor = getResources().getColor(R.color.text_primary_color);
    private int mTextColor = getResources().getColor(R.color.text_primary_color);

    public TagTextView(Context context) {
        super(context);
    }

    public TagTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TagTextView);
        mTag = array.getString(R.styleable.TagTextView_tag);
        mTagColor = array.getColor(R.styleable.TagTextView_tag_color, mTagColor);
        mTextColor = array.getColor(R.styleable.TagTextView_text_color, mTextColor);

        array.recycle();
    }

    /**
     * 要在setTextWithTag()前执行才能生效
     *
     * @param tagColor
     */
    public void setTagColor(int tagColor) {
        mTagColor = tagColor;
    }

    /**
     * 要在setTextWithTag()前执行才能生效
     *
     * @param textColor
     */
    public void setMessageColor(int textColor) {
        mTextColor = textColor;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    /**
     * 使用这个方法可以保留tag
     *
     * @param text
     */
    public void setTextWithTag(String text) {
        super.setText(null);

        if(TextUtils.isEmpty(mTag)){
            super.setText(text);
            super.setTextColor(mTextColor);
        }else{
            Spannable tagSpannable=new SpannableString(mTag);
            tagSpannable.setSpan(new ForegroundColorSpan(mTagColor),0,tagSpannable.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            super.setText(tagSpannable);

            Spannable textSpannable=new SpannableString(text);
            textSpannable.setSpan(new ForegroundColorSpan(mTextColor),0,textSpannable.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            super.append(textSpannable);

        }
    }

    /**
     * 去掉tag
     *
     * @return
     */
    @Override
    public CharSequence getText() {
        String result = super.getText() + "";
        return TextUtils.isEmpty(mTag) ? result : result.substring(0,mTag.length());
    }

}
