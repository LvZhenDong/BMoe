package com.kklv.bmoe.utils;

/**
 * 字符串工具类
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/12 14:21
 */
public class StringUtils {
    public static String formatDateString(String date) {
        String[] value = date.substring(2).split("-");
        value[1] = addZero(value[1]);
        value[2] = addZero(value[2]);
        return value[0] + "-" + value[1] + "-" + value[2];
    }

    /**
     * 如果month或者day的数字小于10，则要在它们前面加0
     * @param date
     * @return
     */
    private static String addZero(String date) {
        int month = Integer.parseInt(date);
        if (month < 10) {
            date = "0" + date;
        }
        return date;
    }
}
