package com.kklv.bmoe.data;


import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import com.kklv.bmoe.constant.HttpUrl;
import com.kklv.bmoe.database.RoleIntradayCountDao;
import com.kklv.bmoe.object.Camp;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleInfo;
import com.kklv.bmoe.object.RoleIntradayCount;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/2 11:18
 */
public class DataHelper {
    private static final String TAG = "DataHelper";
    public static final String DB_HANDLER_THREAD_NAME = "BMoe";

    private Map<String, String> mParamMap;

    private Context mContext;
    private RequestQueue mRequestQueue;
    public GsonRequest mRoleIntradayCountRequest;

    private DataHelperCallBack mCallBack;

    private HandlerThread mHandlerThread;
    private Handler mSubThreadHandler;
    private Handler mUIHandler = new Handler();

    public DataHelper(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);

        initHandler();
    }

    /**
     * 获取所有阵营信息--对应网页端的“阵营表格”
     */
    public void getAllCamps() {
        String url = HttpUrl.ALL_CAMP;
        GsonRequest gsonRequest = new GsonRequest<List<Camp>>(Request.Method.GET, url,
                new TypeToken<List<Camp>>() {
                }.getType(), new Response.Listener<List<Camp>>() {
            @Override
            public void onResponse(List<Camp> response) {
                mCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        });
        mRequestQueue.add(gsonRequest);
    }

    /**
     * 获取该阵营所有角色排名--对应网页端的“角色数据表格”
     *
     * @param bangumi
     */
    public void getCampRank(String bangumi) {
        String url = HttpUrl.RANK + "?bangumi=" + EncodeChinese(bangumi);
        GsonRequest gsonRequest = new GsonRequest<List<RoleInfo>>(Request.Method.GET, url,
                new TypeToken<List<RoleInfo>>() {
                }.getType(), new Response.Listener<List<RoleInfo>>() {
            @Override
            public void onResponse(List<RoleInfo> response) {
                List<RoleInfo> list = response;
                Log.i(TAG, "角色数量：" + list.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        });
        mRequestQueue.add(gsonRequest);
    }

    /**
     * 先从数据库取数据，如果数据库没有就从网络上取
     *
     * @param map
     */
    public void getRoleIntradayCount(Map<String, String> map) {
        mParamMap = map;
        List<RoleIntradayCount> databaseResult = new RoleIntradayCountDao(mContext).
                getRoleIntradayCounts(mParamMap.get(RoleIntradayCount.DATE), mParamMap.get(RoleIntradayCount.SEX));
        if (databaseResult != null && databaseResult.size() > 0) {
            mCallBack.onSuccess(databaseResult);
        } else {
            getRoleIntradayCountFromInterNet(mParamMap);
        }

    }

    private void getRoleIntradayCountFromInterNet(final Map<String, String> map) {
        String url = HttpUrl.ROLE + getURL(map);
        Log.i(TAG, "url:" + url);
        mRoleIntradayCountRequest = new GsonRequest<List<RoleIntradayCount>>(Request.Method.GET, url,
                new TypeToken<List<RoleIntradayCount>>() {
                }.getType(), new Response.Listener<List<RoleIntradayCount>>() {
            @Override
            public void onResponse(List<RoleIntradayCount> response) {
                response = setRoleIntradayCountsMaxCount(response);   //拿到数据后先设置maxCount
                Message msg = new Message();
                msg.obj = response;
                mSubThreadHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
                mCallBack.onFailure(error);
            }
        });
        //TODO
        //为Request添加Tag，当Activity的onDestroy执行后取消所有请求，网上写的是onStop，但我觉得为什么
        //不应该是onDestroy呢？
//        mRoleIntradayCountRequest.setTag(mContext);
        mRequestQueue.add(mRoleIntradayCountRequest);
    }


    /**
     * 初始化HandlerThread，在子线程中进行数据库操作
     */
    private void initHandler() {
        mHandlerThread = new HandlerThread(DB_HANDLER_THREAD_NAME);
        mHandlerThread.start();
        mSubThreadHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //HandlerThread开辟的子线程
                List<RoleIntradayCount> response = (List<RoleIntradayCount>) msg.obj;
                List<RoleIntradayCount> databaseResult = null;
                if (response != null && response.size() > 0) {
                    RoleIntradayCountDao roleIntradayCountDao = new RoleIntradayCountDao(mContext);
                    //将数据添加到数据库
                    roleIntradayCountDao.addOrUpdateRoleIntradayCounts(response);
                    //因为需要排序后的数据，所有还是从数据库读取
                    databaseResult = roleIntradayCountDao.
                            getRoleIntradayCounts(mParamMap.get(RoleIntradayCount.DATE), mParamMap.get(RoleIntradayCount.SEX));
                }
                final List<RoleIntradayCount> finalDatabaseResult = databaseResult;
                mUIHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //主线程
                        mCallBack.onSuccess(finalDatabaseResult);
                    }
                });
            }
        };
    }

    private String getURL(Map<String, String> map) {
        String result = "?";
        if (!(TextUtils.isEmpty(map.get(RoleIntradayCount.DATE)))) {
            result += "&date=" + map.get(RoleIntradayCount.DATE);
        }
//        if (!(TextUtils.isEmpty(map.get(RoleIntradayCount.SEX)))) {
//            result += "&sex=" + map.get(RoleIntradayCount.SEX);
//        }
//        if (!(TextUtils.isEmpty(map.get("group")))) {
//            result += "&group=" + map.get("group");
//        }
        return result;
    }

    /**
     * 对中文进行URLEncode编码，不然无法解析URL
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String EncodeChinese(String str) {
        try {
            return java.net.URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public interface DataHelperCallBack {
        public <T> void onSuccess(List<T> result);

        public void onFailure(Exception error);
    }

    public void registerCallBack(DataHelperCallBack callBack) {
        this.mCallBack = callBack;
    }

    /**
     * 为RoleIntradayCount list 设置maxCount
     *
     * @param list
     * @return
     */
    public List<RoleIntradayCount> setRoleIntradayCountsMaxCount(List<RoleIntradayCount> list) {
        if (list != null && list.size() > 0) {
            for (RoleIntradayCount item : list
                    ) {
                List<DataBean> dataBeanList = new ArrayList<>();
                dataBeanList.addAll(item.getData());
                if (dataBeanList != null && dataBeanList.size() > 0) {
                    String maxCount = dataBeanList.get(dataBeanList.size() - 1).getCount();
                    item.setMaxCount(maxCount);
                }
            }
        }
        return list;
    }

}
