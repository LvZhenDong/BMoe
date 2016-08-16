package com.kklv.bmoe.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kklv.bmoe.R;
import com.kklv.bmoe.activity.BangumiActivity;
import com.kklv.bmoe.object.Camp;
import com.kklv.bmoe.object.PercentCamp;
import com.kklv.bmoe.view.TagTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LvZhenDong
 *         created at 2016/8/8 16:36
 */
public class CampRecyclerViewAdapter extends
        RecyclerView.Adapter<CampRecyclerViewAdapter.CampViewHolder> {
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
        return new CampViewHolder(mLayoutInflater
                .inflate(R.layout.item_camp_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(CampViewHolder holder, final int position) {
        PercentCamp percentCamp = mPercentCampList.get(position);
        Camp item = percentCamp.getCamp();

        holder.mCampNameTV.setText(item.getBangumi());
        holder.mSucValueTV.setMessage(percentCamp.getPercentSuc() + "%");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BangumiActivity.class);
                intent.putExtra(BangumiActivity.BANGUMI, mList.get(position).getBangumi());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPercentCampList.size();
    }

    public static class CampViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mSimpleDraweeView;
        TextView mCampNameTV;
        TagTextView mSucValueTV;

        public CampViewHolder(View itemView) {
            super(itemView);
            mSimpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_item_head);
            mSucValueTV = (TagTextView) itemView.findViewById(R.id.tv_item_suc_percent_value);
            mCampNameTV = (TextView) itemView.findViewById(R.id.tv_item_camp_name);
        }
    }
}
