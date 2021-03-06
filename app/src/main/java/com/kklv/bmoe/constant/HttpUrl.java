package com.kklv.bmoe.constant;

/**
 * API接口地址
 *
 * @author LvZhenDong
 * created at 2016/6/1 10:53
 */
public class HttpUrl {
    /**
     * API接口地址
     * 8月17号后用http就出现了问题，返回301
     * 改为https后就好了
     */
    private static final String HTTP_HOME = "https://bmoe.uuzsama.me/api/data";
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

    /**
     * Bing图片搜索
     */
    public static final String BING_IMAGE_SEARCH = "https://api.cognitive.microsoft.com/bing/v5.0/images/search?q=";
}
