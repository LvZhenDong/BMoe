package com.kklv.bmoe.adapter;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bilibili.magicasakura.utils.ThemeUtils;
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
    private static String[] mThemeNames = {"由乃粉", "荡漾紫", "胖次蓝", "真琴绿",
            "这是啥", "呆毛黄", "奇迹橙", "夏娜红"};
    private static int[] mThemeColors = {R.color.pink, R.color.purple, R.color.blue, R.color.green,
            R.color.green_light, R.color.yellow, R.color.orange, R.color.red};
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public ThemeRecycleViewAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mLayoutInflater.
                inflate(R.layout.item_theme_recycle_view, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(mThemeNames[position]);
        holder.textView.setTextColor(mContext.getResources().getColor(mThemeColors[position]));
        holder.button.setOnClickListener(new View.OnClickListener() {
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
                        setTheme(theme);
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
                setTheme(theme);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mThemeColors.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item_theme);
            button = (Button) itemView.findViewById(R.id.btn_item_theme);
        }
    }

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
                            //TODO: will do this for each traversal
                        }
                    }
            );
        }
    }
}
