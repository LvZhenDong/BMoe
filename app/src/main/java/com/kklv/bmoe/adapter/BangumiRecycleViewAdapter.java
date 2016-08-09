package com.kklv.bmoe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kklv.bmoe.R;
import com.kklv.bmoe.object.RoleInfo;

import java.util.List;

/**
 * @author LvZhenDong
 * created at 2016/7/13 16:53
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

    public void setData(List<RoleInfo> list){
        this.mList=list;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.
                inflate(R.layout.item_bangumi_recycle_view, parent, false));
    }

    @Override
    public void onBindViewHolder(BangumiRecycleViewAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item_bangumi_name);
        }
    }
}
