package com.kklv.bmoe.utils;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;

/**
 * 字符串工具类
 *
 * @author LvZhenDong
 * created at 2016/6/12 14:21
 */
public class StringUtils {
    public static String formatDateString(String date) {
        if(TextUtils.isEmpty(date))return "";
        String[] value = date.substring(2).split("-");
        value[1] = addZero(value[1]);
        value[2] = addZero(value[2]);
        return value[0] + "-" + value[1] + "-" + value[2];
    }

    /**
     * 如果month或者day的数字小于10，则要在它们前面加0
     *
     * @param date
     * @return
     */
    private static String addZero(String date) {
        int value = Integer.parseInt(date);
        if (value < 10) {
            date = "0" + value;
        }
        return date;
    }

    /**
     * 对中文进行URLEncode编码，不然无法解析URL
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeChinese(String str) {
        try {
            return java.net.URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
