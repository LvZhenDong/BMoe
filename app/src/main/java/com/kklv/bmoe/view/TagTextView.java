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
 * tag:message 比如  “姓名：吕振东”这种TextView
 * @author LvZhenDong
 *         created at 2016/8/12 15:13
 */
public class TagTextView extends TextView {

    private String mTag = "";
    private String mMessage = "";

    private int mTagColor = getResources().getColor(R.color.text_primary_color);
    private int mMessageColor = getResources().getColor(R.color.text_primary_color);

    public TagTextView(Context context) {
        super(context);
    }

    public TagTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TagTextView);
        mTag = array.getString(R.styleable.TagTextView_tag);
        mTagColor = array.getColor(R.styleable.TagTextView_tag_color, mTagColor);
        mMessageColor = array.getColor(R.styleable.TagTextView_message_color, mMessageColor);

        array.recycle();
    }

    public int getTagColor() {
        return mTagColor;
    }

    /**
     * 要在setTextWithTag()前执行才能生效
     *
     * @param tagColor
     */
    public void setTagColor(int tagColor) {
        mTagColor = tagColor;
    }

    public int getMessageColor() {
        return mMessageColor;
    }

    /**
     * 要在setTextWithTag()前执行才能生效
     *
     * @param messageColor
     */
    public void setMessageColor(int messageColor) {
        mMessageColor = messageColor;
    }

    public void setMessageColorId(int messageColorId){
        mMessageColor=getResources().getColor(messageColorId);
    }

    @Override
    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    public String getMessage() {
        return mMessage;
    }

    /**
     * 使用这个方法可以保留tag
     *
     * @param message
     */
    public void setMessage(String message) {
        super.setText(null);
        mMessage = message;

        if (TextUtils.isEmpty(mTag)) {
            super.setText(message);
            super.setTextColor(mMessageColor);
        } else {
            //为tag设置颜色和text
            Spannable tagSpannable = new SpannableString(mTag);
            tagSpannable.setSpan(new ForegroundColorSpan(mTagColor), 0, tagSpannable.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            super.setText(tagSpannable);

            //为message设置颜色和text
            Spannable textSpannable = new SpannableString(message);
            textSpannable.setSpan(new ForegroundColorSpan(mMessageColor), 0, textSpannable.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            super.append(textSpannable);

        }
    }

}
