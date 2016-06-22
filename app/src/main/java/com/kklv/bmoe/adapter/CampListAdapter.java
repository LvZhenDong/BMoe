package com.kklv.bmoe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kklv.bmoe.R;
import com.kklv.bmoe.object.Camp;
import com.kklv.bmoe.object.PercentCamp;

import java.util.ArrayList;
import java.util.List;

/**
 * 阵营信息列表Adapter
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/13 10:47
 */
public class CampListAdapter extends BaseAdapter {
    private List<PercentCamp> mPercentCampList;
    private Context mContext;
    private LayoutInflater mInflater;

    public CampListAdapter(Context context, List<Camp> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mPercentCampList = getPercentCampList(list);
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

    /**
     * 计算百分比
     *
     * @param member
     * @param denominator
     * @return
     */
    private double getPercent(double member, double denominator) {
        return denominator > 0 ? (member / denominator)*100 : 0;
    }

    public void setData(List<Camp> list) {
        this.mPercentCampList = getPercentCampList(list);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPercentCampList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPercentCampList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_camp_listview, parent, false);
            holder = new ViewHolder();
            holder.idTV = (TextView) convertView.findViewById(R.id.tv_id);
            holder.bangumiTV = (TextView) convertView.findViewById(R.id.tv_bangumi);
            holder.totalTV = (TextView) convertView.findViewById(R.id.tv_total);
            holder.sucTV = (TextView) convertView.findViewById(R.id.tv_suc);
            holder.waitTV = (TextView) convertView.findViewById(R.id.tv_wait);
            holder.failTV = (TextView) convertView.findViewById(R.id.tv_fail);
            holder.percentSucTV = (TextView) convertView.findViewById(R.id.tv_suc_percent);
            holder.percentWaitTV = (TextView) convertView.findViewById(R.id.tv_wait_percent);
            holder.percentFailTV = (TextView) convertView.findViewById(R.id.tv_fail_percent);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PercentCamp percentCamp = mPercentCampList.get(position);
        Camp item = percentCamp.getCamp();
        holder.idTV.setText((position + 1) + "");
        holder.bangumiTV.setText(item.getBangumi());
        holder.totalTV.setText(item.getTotal() + "");
        holder.sucTV.setText(item.getSuc() + "");
        holder.waitTV.setText(item.getWait() + "");
        holder.failTV.setText(item.getFail() + "");
        holder.percentSucTV.setText(percentCamp.getPercentSuc() + "%");
        holder.percentWaitTV.setText(percentCamp.getPercentWait() + "%");
        holder.percentFailTV.setText(percentCamp.getPercentFail() + "%");
        //item颜色交替
        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.drawable.selector_item_camp_listview_bgcolor1);
        } else {
            convertView.setBackgroundResource(R.drawable.selector_item_camp_listview_bgcolor2);
        }

        return convertView;
    }

    private class ViewHolder {
        TextView idTV;
        TextView bangumiTV;
        TextView totalTV;
        TextView sucTV;
        TextView waitTV;
        TextView failTV;
        TextView percentSucTV;
        TextView percentWaitTV;
        TextView percentFailTV;
    }
}
