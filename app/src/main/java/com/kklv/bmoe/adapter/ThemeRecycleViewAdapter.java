package com.kklv.bmoe.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.kklv.bmoe.BMoeApplication;
import com.kklv.bmoe.MainActivity;
import com.kklv.bmoe.R;
import com.kklv.bmoe.utils.ThemeHelper;

/**
 * 主题选择列表adapter
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/7/27 16:09
 */
public class ThemeRecycleViewAdapter extends
        RecyclerView.Adapter<ThemeRecycleViewAdapter.MyViewHolder> {
    private static final String TAG = "ThemeRecycleViewAdapter";
    private static String[] mThemeNames = {"由乃粉", "荡漾紫", "胖次蓝", "真琴绿",
            "这是啥", "呆毛黄", "奇迹橙", "夏娜红"};
    private static int[] mThemeColors = {R.color.pink, R.color.purple, R.color.blue, R.color.green,
            R.color.green_light, R.color.yellow, R.color.orange, R.color.red};
    private int selectedRB = 0;
    private String themeColorName;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private BMoeApplication mBMoeApplication;

    public ThemeRecycleViewAdapter(Context context, BMoeApplication application) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mBMoeApplication = application;
        initRadioButton();
    }

    /**
     * 根据现在的主题设置第几条item被选中
     */
    private void initRadioButton() {
        int colorId = mBMoeApplication.getThemeColor(mContext);
        if (colorId == R.color.theme_color_primary) {
            selectedRB = 0;
            return;
        }
        for (int i = 0; i < mThemeColors.length; i++) {
            if (colorId == mThemeColors[i]) {
                selectedRB = i;
                break;
            }
        }

        //得到主题颜色名称
        themeColorName = mBMoeApplication.getTheme(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.
                inflate(R.layout.item_theme_recycle_view, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mNameTV.setText(mThemeNames[position]);
        holder.mNameTV.setTextColor(mContext.getResources().getColor(mThemeColors[position]));

        if (position == selectedRB) {
            //设置被选中的item
            holder.mUseTV.setText(mContext.getString(R.string.using));
            //根据主题颜色得到drawable
            int backgroundId = mContext.getResources().getIdentifier("shape_rect_border_" + themeColorName, "drawable",
                    mContext.getPackageName());
            holder.mUseTV.setBackgroundResource(backgroundId);
            holder.mUseTV.setTextColor(mContext.getResources().getColor(mThemeColors[position]));
        } else {
            //设置未被选中的item
            holder.mUseTV.setText(mContext.getString(R.string.use));
            holder.mUseTV.setBackgroundResource(R.drawable.shape_rect_border);
            holder.mUseTV.setTextColor(mContext.getResources().getColor(R.color.gray_default));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theme = 0;
                switch (mThemeColors[position]) {
                    case R.color.pink:
                        theme = ThemeHelper.CARD_SAKURA;
                        break;
                    case R.color.purple:
                        theme = ThemeHelper.CARD_HOPE;
                        break;
                    case R.color.blue:
                        theme = ThemeHelper.CARD_STORM;
                        break;
                    case R.color.green:
                        theme = ThemeHelper.CARD_WOOD;
                        break;
                    case R.color.green_light:
                        theme = ThemeHelper.CARD_LIGHT;
                        break;
                    case R.color.yellow:
                        theme = ThemeHelper.CARD_THUNDER;
                        break;
                    case R.color.orange:
                        theme = ThemeHelper.CARD_SAND;
                        break;
                    case R.color.red:
                        theme = ThemeHelper.CARD_FIREY;
                        break;
                }
                //刷新recycleView
                notifyDataSetChanged();
                setTheme(theme);
                //更新主题颜色名称
                themeColorName = mBMoeApplication.getTheme(mContext);
                //更新被选中的item position
                selectedRB = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mThemeColors.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNameTV, mUseTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            mNameTV = (TextView) itemView.findViewById(R.id.tv_item_theme_name);
            mUseTV = (TextView) itemView.findViewById(R.id.tv_item_theme_use);
        }
    }

    /**
     * 更新主题
     *
     * @param theme
     */
    private void setTheme(int theme) {
        if (ThemeHelper.getTheme(mContext) != theme) {
            ThemeHelper.setTheme(mContext, theme);
            ThemeUtils.refreshUI(mContext, new ThemeUtils.ExtraRefreshable() {
                        @Override
                        public void refreshGlobal(Activity activity) {
                            MainActivity mainActivity = (MainActivity) activity;
                            mainActivity.setNavItemColor();
                        }

                        @Override
                        public void refreshSpecificView(View view) {

                        }
                    }
            );
        }
    }


}
