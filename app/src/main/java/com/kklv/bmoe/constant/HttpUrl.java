package com.kklv.bmoe.constant;

/**
 * API接口地址
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/1 10:53
 */
public class HttpUrl {
    /**
     * API接口地址
     */
    private static final String HTTP_HOME = "http://bmoe.uuzsama.me/api/data";
    /**
     * 角色得票
     */
    public static final String ROLE = HTTP_HOME + "/role";
    /**
     * 票仓
     */
    public static final String BALLOT = HTTP_HOME + "/ballot";
    /**
     * 排名
     */
    public static final String RANK = HTTP_HOME + "/rank";
    /**
     * 动画
     */
    public static final String CAMP = HTTP_HOME + "/camp";
    /**
     * 所有阵营
     */
    public static final String ALL_CAMP = HTTP_HOME + "/camp";
}
