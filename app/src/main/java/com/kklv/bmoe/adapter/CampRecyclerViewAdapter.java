package com.kklv.bmoe.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kklv.bmoe.R;
import com.kklv.bmoe.activity.BangumiActivity;
import com.kklv.bmoe.data.DataHelper;
import com.kklv.bmoe.object.BingImageSearchResult;
import com.kklv.bmoe.object.Camp;
import com.kklv.bmoe.object.PercentCamp;
import com.kklv.bmoe.utils.ThemeHelper;
import com.kklv.bmoe.view.AsyncSimpleDraweeView;
import com.kklv.bmoe.view.TagTextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author LvZhenDong
 *         created at 2016/8/8 16:36
 */
public class CampRecyclerViewAdapter extends RecyclerView.Adapter<CampRecyclerViewAdapter
        .CampViewHolder> {
    private static final java.lang.String TAG = "CampRecyclerViewAdapter";

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Camp> mList;
    private List<PercentCamp> mPercentCampList;

    public CampRecyclerViewAdapter(Context context, List<Camp> list) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mList = list;
        this.mPercentCampList = getPercentCampList(list);
    }

    /**
     * 计算百分比
     *
     * @param member
     * @param denominator
     * @return
     */
    private static double getPercent(double member, double denominator) {
        return denominator > 0 ? (member / denominator) * 100 : 0;
    }

    public void setData(List<Camp> list) {
        this.mPercentCampList = getPercentCampList(list);
        notifyDataSetChanged();
    }

    /**
     * 根据List<Camp>计算晋级率、复活率、淘汰率，得到List<PercentCamp>
     *
     * @param list
     * @return
     */
    private List<PercentCamp> getPercentCampList(List<Camp> list) {
        List<PercentCamp> percentCampList = new ArrayList<>();
        for (Camp item : list) {
            PercentCamp percentCamp = new PercentCamp();
            percentCamp.setCamp(item);
            int total = item.getTotal();
            percentCamp.setPercentSuc(getPercent(item.getSuc(), total));
            percentCamp.setPercentWait(getPercent(item.getWait(), total));
            percentCamp.setPercentFail(getPercent(item.getFail(), total));
            percentCampList.add(percentCamp);
        }
        return percentCampList;
    }

    @Override
    public CampViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CampViewHolder(mLayoutInflater.inflate(R.layout.item_camp_recycler_view,
                parent, false));
    }

    @Override
    public void onBindViewHolder(CampViewHolder holder, final int position) {
        PercentCamp percentCamp = mPercentCampList.get(position);
        Camp item = mList.get(position);

        holder.mCampNameTV.setText(item.getBangumi());
        holder.mSucPercentTV.setMessage(percentCamp.getPercentSuc() + "%");
        holder.mWaitPercentTV.setMessage(percentCamp.getPercentWait()+"%");
        holder.mFailPercentTV.setMessage(percentCamp.getPercentFail()+"%");

        holder.mTotalCountTV.setMessage(item.getTotal()+"");
        holder.mSucCountTV.setMessage(item.getSuc()+"");
        holder.mWaitCountTV.setMessage(item.getWait()+"");
        holder.mFailCountTV.setMessage(item.getFail()+"");

        if (TextUtils.isEmpty(item.getImeUrl())) {
            loadImage(item.getBangumi(), holder.mSimpleDraweeView);
        } else {
            holder.mSimpleDraweeView.setImageURI(item.getImeUrl());
        }


    }

    @Override
    public int getItemCount() {
        return mPercentCampList.size();
    }

    public class CampViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.sdv_item_head)
        AsyncSimpleDraweeView mSimpleDraweeView;
        @BindView(R.id.tv_item_camp_name)
        TextView mCampNameTV;
        @BindView(R.id.tv_item_suc_percent)
        TagTextView mSucPercentTV;
        @BindView(R.id.tv_item_wait_percent)
        TagTextView mWaitPercentTV;
        @BindView(R.id.tv_item_fail_percent)
        TagTextView mFailPercentTV;
        @BindView(R.id.tv_item_total_count)
        TagTextView mTotalCountTV;
        @BindView(R.id.tv_item_suc_count)
        TagTextView mSucCountTV;
        @BindView(R.id.tv_item_wait_count)
        TagTextView mWaitCountTV;
        @BindView(R.id.tv_item_fail_count)
        TagTextView mFailCountTV;

        public CampViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mSucPercentTV.setMessageColorId(R.color.colorAccent);
            mWaitPercentTV.setMessageColorId(R.color.text_secondary_color);
            mFailPercentTV.setMessageColorId(R.color.text_disabled_color);

            mSucCountTV.setMessageColorId(R.color.colorAccent);
            mWaitCountTV.setMessageColorId(R.color.text_secondary_color);
            mFailCountTV.setMessageColorId(R.color.text_disabled_color);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BangumiActivity.class);
                    intent.putExtra(BangumiActivity.BANGUMI, mList.get(getAdapterPosition())
                            .getBangumi());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private void loadImage(String keyWords, AsyncSimpleDraweeView imageView) {


        if (cancelBeforeTask(keyWords, imageView)) {
            SearchImageByKeyWordsTask task = new SearchImageByKeyWordsTask(imageView);
            imageView.setTask(task);
            Uri uri = Uri.parse("res:///" + R.drawable.loading);
            imageView.setImageURI(uri);
            task.execute(keyWords);

        }
    }

    private boolean cancelBeforeTask(String keyWords, AsyncSimpleDraweeView imageView) {
        SearchImageByKeyWordsTask task = imageView.getTask();

        if (task != null) {
            String taskKeyWords = task.keyWords;
            if (taskKeyWords != keyWords || TextUtils.isEmpty(taskKeyWords)) {
                task.cancel(true);
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * 搜索图片
     */
    public class SearchImageByKeyWordsTask extends AsyncTask<String, Void, BingImageSearchResult> {
        private AsyncSimpleDraweeView imageView;
        private WeakReference<AsyncSimpleDraweeView> imageViewWeakReference;
        public String keyWords;
        private DataHelper dataHelper;

        public SearchImageByKeyWordsTask(AsyncSimpleDraweeView imageView) {
            dataHelper = new DataHelper(mContext);

            this.imageView = imageView;
            imageViewWeakReference = new WeakReference<AsyncSimpleDraweeView>(imageView);
        }

        @Override
        protected BingImageSearchResult doInBackground(String... params) {

            keyWords = params[0];
            return dataHelper.syncGetImageUrl(params[0]);
        }

        @Override
        protected void onPostExecute(BingImageSearchResult result) {
            if (isCancelled()) {
                result = null;
            }

            if (imageViewWeakReference != null && result != null) {
                AsyncSimpleDraweeView imageView = imageViewWeakReference.get();
                SearchImageByKeyWordsTask task = imageView.getTask();
                if (this == task && imageView != null) {
                    imageView.setImageURI(result.getIndexUrl());

                    for (Camp item : mList) {
                        if (item.getBangumi().equals(keyWords)) {
                            item.setImeUrl(result.getIndexUrl());
                            break;
                        }
                    }

                }
            }
        }
    }

}
