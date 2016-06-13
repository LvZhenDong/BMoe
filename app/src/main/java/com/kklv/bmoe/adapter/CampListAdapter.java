package com.kklv.bmoe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kklv.bmoe.R;
import com.kklv.bmoe.object.Camp;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;

/**
 * 阵营信息列表Adapter
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/13 10:47
 */
public class CampListAdapter extends BaseAdapter {
    private List<Camp> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public CampListAdapter(Context context, List<Camp> list) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mList = list;
    }

    public void setData(List<Camp> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
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
            holder.aliveTV = (TextView) convertView.findViewById(R.id.tv_alive);
            holder.failTV = (TextView) convertView.findViewById(R.id.tv_fail);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Camp item = mList.get(position);
        holder.idTV.setText((position + 1) + "");
        holder.bangumiTV.setText(item.getBangumi());
        holder.totalTV.setText(item.getTotal() + "");
        holder.sucTV.setText(item.getSuc() + "");
        holder.waitTV.setText(item.getWait() + "");
        holder.aliveTV.setText(item.getAlive() + "");
        holder.failTV.setText(item.getFail() + "");
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
        TextView aliveTV;
        TextView failTV;
    }
}
