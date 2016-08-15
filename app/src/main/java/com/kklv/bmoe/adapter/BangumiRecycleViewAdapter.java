package com.kklv.bmoe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kklv.bmoe.R;
import com.kklv.bmoe.activity.BaseActivity;
import com.kklv.bmoe.object.RoleInfo;
import com.kklv.bmoe.view.TagTextView;

import java.util.List;

/**
 * @author LvZhenDong
 *         created at 2016/7/13 16:53
 */
public class BangumiRecycleViewAdapter extends
        RecyclerView.Adapter<BangumiRecycleViewAdapter.MyViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<RoleInfo> mList;

    public BangumiRecycleViewAdapter(Context context, List<RoleInfo> list) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mList = list;
    }

    public void setData(List<RoleInfo> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.
                inflate(R.layout.item_bangumi_recycle_view, parent, false));
    }

    @Override
    public void onBindViewHolder(BangumiRecycleViewAdapter.MyViewHolder holder, int position) {

        RoleInfo roleInfo = mList.get(position);


        holder.statTV.setMessageColor(getStatTextColor(roleInfo.getStat()));
        holder.nameTV.setMessageColor(((BaseActivity) mContext).mThemeColor);

        holder.nameTV.setTextWithTag(roleInfo.getName());
        holder.dateTV.setTextWithTag(roleInfo.getDate());
        holder.stageTV.setTextWithTag(RoleInfo.STAGE[roleInfo.getStage()]);
        holder.countTV.setTextWithTag(roleInfo.getCount() + "");
        holder.rankTV.setTextWithTag(roleInfo.getRank() + "");
        holder.statTV.setTextWithTag(RoleInfo.STAT[roleInfo.getStat()]);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 根据stat得到stat的textColor
     *
     * @param stat
     * @return
     */
    private int getStatTextColor(int stat) {
        int textColor = mContext.getResources().getColor(R.color.text_primary_color);
        switch (stat) {
            case 1:
                textColor = mContext.getResources().getColor(R.color.colorAccent);
                break;
            case 2:
                textColor = mContext.getResources().getColor(R.color.text_secondary_color);
                break;
            case 3:
                textColor = mContext.getResources().getColor(R.color.text_disabled_color);
                break;
        }
        return textColor;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TagTextView nameTV, dateTV, stageTV, countTV, rankTV, statTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            bindId(itemView);

        }

        private void bindId(View itemView) {
            nameTV = (TagTextView) itemView.findViewById(R.id.tv_item_role_name);
            dateTV = (TagTextView) itemView.findViewById(R.id.tv_item_role_date);
            stageTV = (TagTextView) itemView.findViewById(R.id.tv_item_role_stage);
            countTV = (TagTextView) itemView.findViewById(R.id.tv_item_role_count);
            rankTV = (TagTextView) itemView.findViewById(R.id.tv_item_role_rank);
            statTV = (TagTextView) itemView.findViewById(R.id.tv_item_role_stat);
        }

    }
}
