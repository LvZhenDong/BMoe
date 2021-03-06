/*
 * Copyright (C) 2016 Bilibili
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kklv.bmoe.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 主题切换工具类
 *
 * @author LvZhenDong
 *         created at 2016/7/27 11:50
 */
public class ThemeHelper {
    public static final int CARD_SAKURA = 0x1;
    public static final int CARD_HOPE = 0x2;
    public static final int CARD_STORM = 0x3;
    public static final int CARD_WOOD = 0x4;
    public static final int CARD_THUNDER = 0x5;
    public static final int CARD_SAND = 0x6;
    public static final int CARD_FIREY = 0x7;
    private static final String CURRENT_THEME = "theme_current";

    public static SharedPreferences getSharePreference(Context context) {
        return context.getSharedPreferences("multiple_theme", Context.MODE_PRIVATE);
    }

    public static void setTheme(Context context, int themeId) {
        getSharePreference(context).edit().putInt(CURRENT_THEME, themeId).commit();
    }

    public static int getTheme(Context context) {
        return getSharePreference(context).getInt(CURRENT_THEME, CARD_SAKURA);
    }

    public static boolean isDefaultTheme(Context context) {
        return getTheme(context) == CARD_SAKURA;
    }

    public static String getName(int currentTheme) {
        switch (currentTheme) {
            case CARD_SAKURA:
                return "THE SAKURA";
            case CARD_STORM:
                return "THE STORM";
            case CARD_WOOD:
                return "THE WOOD";
            case CARD_HOPE:
                return "THE HOPE";
            case CARD_THUNDER:
                return "THE THUNDER";
            case CARD_SAND:
                return "THE SAND";
            case CARD_FIREY:
                return "THE FIREY";
        }
        return "THE RETURN";
    }

    /**
     * 获得当前主题的primary color
     * @param context
     * @return
     */
    public static int getThemePrimaryColor(Context context) {
        int colorId=context.getResources().getIdentifier(getColorXmlName(context), "color", context
                .getPackageName());
        return context.getResources().getColor(colorId);

    }

    /**
     * 获得当前主题的primary color id
     * @param context
     * @return
     */
    public static int getThemePrimaryColorId(Context context){
        return context.getResources().getIdentifier(getColorXmlName(context), "color", context
                .getPackageName());
    }

    public static String getColorXmlName(Context context) {
        if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_STORM) {
            return "blue";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_HOPE) {
            return "purple";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_WOOD) {
            return "green";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_THUNDER) {
            return "yellow";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_SAND) {
            return "orange";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_FIREY) {
            return "red";
        } else {
            return "pink";
        }
    }
}
