package com.kklv.bmoe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kklv.bmoe.R;
import com.kklv.bmoe.activity.BaseActivity;
import com.kklv.bmoe.object.RoleInfo;
import com.kklv.bmoe.utils.ThemeHelper;
import com.kklv.bmoe.view.TagTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        holder.nameTV.setMessageColor(ThemeHelper.getThemePrimaryColor(mContext));

        holder.nameTV.setMessage(roleInfo.getName());
        holder.dateTV.setMessage(roleInfo.getDate());
        holder.stageTV.setMessage(RoleInfo.STAGE[roleInfo.getStage()]);
        holder.countTV.setMessage(roleInfo.getCount() + "");
        holder.rankTV.setMessage(roleInfo.getRank() + "");
        holder.statTV.setMessage(RoleInfo.STAT[roleInfo.getStat()]);

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
        @BindView(R.id.tv_item_role_name)
        TagTextView nameTV;
        @BindView(R.id.tv_item_role_date)
        TagTextView dateTV;
        @BindView(R.id.tv_item_role_stage)
        TagTextView stageTV;
        @BindView(R.id.tv_item_role_count)
        TagTextView countTV;
        @BindView(R.id.tv_item_role_rank)
        TagTextView rankTV;
        @BindView(R.id.tv_item_role_stat)
        TagTextView statTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

    }
}
