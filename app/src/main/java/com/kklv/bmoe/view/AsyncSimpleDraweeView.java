package com.kklv.bmoe.view;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kklv.bmoe.adapter.CampRecyclerViewAdapter;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/8/18.
 */
public class AsyncSimpleDraweeView extends SimpleDraweeView {
    private WeakReference<CampRecyclerViewAdapter.SearchImageByKeyWordsTask> taskWeakReference;

    public AsyncSimpleDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AsyncSimpleDraweeView(Context context) {
        super(context);
    }

    public void setTask(CampRecyclerViewAdapter.SearchImageByKeyWordsTask task) {
        taskWeakReference = new WeakReference<CampRecyclerViewAdapter.SearchImageByKeyWordsTask>
                (task);
    }

    public CampRecyclerViewAdapter.SearchImageByKeyWordsTask getTask() {

        return taskWeakReference == null ? null : taskWeakReference.get();
    }
}
