package com.kklv.bmoe.data;


import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kklv.bmoe.R;
import com.kklv.bmoe.constant.HttpUrl;
import com.kklv.bmoe.database.RoleDailyCountDao;
import com.kklv.bmoe.diskLruCache.DiskLruCacheHelper;
import com.kklv.bmoe.object.BingImageSearchResult;
import com.kklv.bmoe.object.Camp;
import com.kklv.bmoe.object.DataBean;
import com.kklv.bmoe.object.RoleDailyCount;
import com.kklv.bmoe.object.RoleInfo;
import com.kklv.bmoe.utils.L;
import com.kklv.bmoe.utils.ListUtils;
import com.kklv.bmoe.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author LvZhenDong
 * created at 2016/6/2 11:18
 */
public class DataHelper {
    private static final String TAG = "DataHelper";
    private static final String DB_HANDLER_THREAD_NAME = "BMoe";
    private static final int DB_HANDLER_THREAD_WHAT_ADD = 0X01;
    private static final int DB_HANDLER_THREAD_WHAT_QUERY = 0X02;
    public GsonRequest mRoleDailyCountRequest;
    /**
     * 添加数据到数据库的任务是否还在执行
     */
    private boolean isAddingToDataBase = false;
    private Map<String, String> mParamMap;
    private Context mContext;
    private RequestQueue mRequestQueue;
    private DataHelperCallBack mCallBack;

    private HandlerThread mHandlerThread;
    private Handler mSubThreadHandler;
    private Handler mUIHandler = new Handler();

    private DiskLruCacheHelper mDiskLruCacheHelper;

    public DataHelper(Context context) {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
        initHandler();
        mDiskLruCacheHelper = DiskLruCacheHelper.getInstance(mContext);
    }

    /**
     * 根据date得到url
     *
     * @param map
     * @return
     */
    private static String getURL(Map<String, String> map) {
        String result = "?";
        if (!(TextUtils.isEmpty(map.get(RoleDailyCount.DATE)))) {
            result += "&date=" + map.get(RoleDailyCount.DATE);
        }
        return result;
    }

    /**
     * 获取所有阵营信息--对应网页端的“阵营表格”
     */
    public void getAllCamps() {
        String url = HttpUrl.ALL_CAMP;
        GsonRequest gsonRequest = new GsonRequest<List<Camp>>(Request.Method.GET, url, new
                TypeToken<List<Camp>>() {
        }.getType(), new Response.Listener<List<Camp>>() {
            @Override
            public void onResponse(List<Camp> response) {
                mCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.e(TAG, error.getMessage());
                mCallBack.onFailure(mContext.getString(R.string.net_error));
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
        String url = HttpUrl.RANK + "?bangumi=" + StringUtils.encodeChinese(bangumi);
        GsonRequest gsonRequest = new GsonRequest<List<RoleInfo>>(Request.Method.GET, url, new
                TypeToken<List<RoleInfo>>() {
        }.getType(), new Response.Listener<List<RoleInfo>>() {
            @Override
            public void onResponse(List<RoleInfo> response) {
                L.i(TAG, "角色数量：" + response.size());
                mCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.e(TAG, error.getMessage());
                mCallBack.onFailure(mContext.getString(R.string.net_error));
            }
        });
        mRequestQueue.add(gsonRequest);
    }

    /**
     * 必应图片搜索
     *
     * @param keyWords
     */
    public void getImageUrl(final String keyWords) {
        BingImageSearchResult response = mDiskLruCacheHelper.readBingImageSearchResultFromDisk
                (StringUtils.encodeChinese(keyWords));
        if (response != null) {
            //仅仅是把结果放到List里面了而已，没有别的意思
            List<BingImageSearchResult> result = new ArrayList<>();
            result.add(response);
            mCallBack.onSuccess(result);
        } else {
            readBingImageSearchResultFromInternet(keyWords);
        }
    }

    /**
     * 根据关键字搜索图片路径
     *
     * @param keyWords
     */
    private void readBingImageSearchResultFromInternet(final String keyWords) {
        String url = HttpUrl.BING_IMAGE_SEARCH + StringUtils.encodeChinese(keyWords) +
                "&ImageType=Photo&mkt=zh-CN&count=100&size=Medium";
        L.i(TAG, "image search url:" + url);
        GsonRequest gsonRequest = new GsonRequest<>(Request.Method.GET, url, new
                TypeToken<BingImageSearchResult>() {
        }.getType(), new Response.Listener<BingImageSearchResult>() {

            @Override
            public void onResponse(BingImageSearchResult response) {
                if (response == null) {
                    mCallBack.onFailure(mContext.getString(R.string.no_data));
                    return;
                }
                //设置搜索关键字keywords
                response.setKeyWords(keyWords);
                mDiskLruCacheHelper.writeBingImageSearchResult2Disk(StringUtils.encodeChinese
                        (keyWords), response);
                List<BingImageSearchResult> result = new ArrayList<>();
                result.add(response);
                mCallBack.onSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.e(TAG, error.getMessage());
                mCallBack.onFailure(error.getMessage());
            }
        });

        mRequestQueue.add(gsonRequest);
    }

    public BingImageSearchResult syncGetImageUrl(String keyWords){
        BingImageSearchResult response = mDiskLruCacheHelper.readBingImageSearchResultFromDisk
                (StringUtils.encodeChinese(keyWords));
        if(response != null){
            return response;
        }else{
            return syncReadBingImageSearchResultFromInternet(keyWords);
        }
    }
    /**
     * 根据关键字搜索图片路径，但是这个方法是同步的
     *
     * @param keyWords
     */
    private BingImageSearchResult syncReadBingImageSearchResultFromInternet(String keyWords){
        BingImageSearchResult result=null;

        String url = HttpUrl.BING_IMAGE_SEARCH + StringUtils.encodeChinese(keyWords) +
                "&ImageType=Photo&mkt=zh-CN&count=100&size=Medium";
        RequestFuture future=RequestFuture.newFuture();
        StringRequest request=new StringRequest(url,future,future){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Ocp-Apim-Subscription-Key", "1203c934bfa0485ea1f59517aff05a56");
                return headers;
            }
        };
        mRequestQueue.add(request);

        try {
            String str= (String) future.get(20, TimeUnit.SECONDS);
            Gson gson=new Gson();
            result=gson.fromJson(str,BingImageSearchResult.class);
            L.i(TAG,result.getIndexUrl());
            //设置搜索关键字keywords
            result.setKeyWords(keyWords);
            mDiskLruCacheHelper.writeBingImageSearchResult2Disk(StringUtils.encodeChinese
                    (keyWords), result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            L.i(TAG,e.getCause().getMessage()+"");
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 先从数据库取数据，如果数据库没有就从网络上取
     *
     * @param map
     */
    public void getRoleDailyCount(Map<String, String> map) {
        mParamMap = map;
        Message msg = new Message();
        msg.obj = map;
        msg.what = DB_HANDLER_THREAD_WHAT_QUERY;

        //如果数据库还在进行写操作，就不从数据库读取数据，而改从服务器读取数据
        synchronized (DataHelper.class) {
            if (!mSubThreadHandler.hasMessages(DB_HANDLER_THREAD_WHAT_ADD) && !isAddingToDataBase) {
                //如果队列里没有有message且没有数据库写操作
                mSubThreadHandler.sendMessage(msg);
            } else {
                L.i(TAG, "isAddingToDataBase");
                getRoleDailyCountFromInterNet(mParamMap);
            }
        }
    }

    /**
     * 从服务器读取RoleDailyCount数据
     *
     * @param map
     */
    private void getRoleDailyCountFromInterNet(final Map<String, String> map) {
        String url = HttpUrl.ROLE + getURL(map);
        L.i(TAG, "url:" + url);
        mRoleDailyCountRequest = new GsonRequest<List<RoleDailyCount>>(Request.Method.GET, url,
                new TypeToken<List<RoleDailyCount>>() {
        }.getType(), new Response.Listener<List<RoleDailyCount>>() {
            @Override
            public void onResponse(List<RoleDailyCount> response) {
                response = setRoleDailyCountsMaxCount(response);   //拿到数据后先设置maxCount
                RoleDailyCount.MaxCountCompare maxCountCompare = new RoleDailyCount
                        .MaxCountCompare();
                if (!ListUtils.isEmpty(response))
                    Collections.sort(response, maxCountCompare); //按maxCountCompare排序
                mCallBack.onSuccess(response);
                Message msg = new Message();
                msg.obj = response;
                msg.what = DB_HANDLER_THREAD_WHAT_ADD;
                mSubThreadHandler.sendMessage(msg);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.e(TAG, error.getMessage());
                mCallBack.onFailure(error.getMessage());
            }
        });
        //TODO
        //为Request添加Tag，当Activity的onDestroy执行后取消所有请求，网上写的是onStop，但我觉得为什么
        //不应该是onDestroy呢？
//        mRoleDailyCountRequest.setTag(mContext);
        mRequestQueue.add(mRoleDailyCountRequest);
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
                switch (msg.what) {
                    case DB_HANDLER_THREAD_WHAT_ADD:
                        handlerAdd(msg);
                        break;
                    case DB_HANDLER_THREAD_WHAT_QUERY:
                        handlerQuery(msg);
                        break;
                }

            }
        };
    }

    /**
     * 数据库查询List<RoleDailyCount>
     * 如果数据库里没有数据，就从服务器读取
     *
     * @param msg
     */
    private void handlerQuery(Message msg) {
        L.i(TAG, "begin handlerQuery");
        final List<RoleDailyCount> databaseResult = new RoleDailyCountDao(mContext).
                getRoleDailyCounts(mParamMap.get(RoleDailyCount.DATE));
        L.i(TAG, "after handlerQuery");
        if (!ListUtils.isEmpty(databaseResult)) {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCallBack.onSuccess(databaseResult);
                }
            });

        } else {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    getRoleDailyCountFromInterNet(mParamMap);
                }
            });
        }
    }

    /**
     * 数据库添加List<RoleDailyCount>
     *
     * @param msg
     */
    private void handlerAdd(Message msg) {
        L.i(TAG, "begin handlerAdd");
        synchronized (DataHelper.class) {
            isAddingToDataBase = true;
        }
        List<RoleDailyCount> response = (List<RoleDailyCount>) msg.obj;
        if (!ListUtils.isEmpty(response)) {
            RoleDailyCountDao roleDailyCountDao = new RoleDailyCountDao(mContext);
            //将数据添加到数据库
            roleDailyCountDao.addOrUpdateRoleDailyCounts(response);
        }
        synchronized (DataHelper.class) {
            isAddingToDataBase = false;
        }
        L.i(TAG, "after handlerAdd");
    }

    public void registerCallBack(DataHelperCallBack callBack) {
        this.mCallBack = callBack;
    }

    /**
     * 为RoleDailyCount list 设置maxCount
     *
     * @param list
     * @return
     */
    public List<RoleDailyCount> setRoleDailyCountsMaxCount(List<RoleDailyCount> list) {
        if (!ListUtils.isEmpty(list)) {
            for (RoleDailyCount item : list
                    ) {
                List<DataBean> dataBeanList = new ArrayList<>();
                dataBeanList.addAll(item.getData());
                if (dataBeanList != null && dataBeanList.size() > 0) {
                    int maxCount = dataBeanList.get(dataBeanList.size() - 1).getCount();
                    item.setMaxCount(maxCount);
                }
            }
        }
        return list;
    }

    public interface DataHelperCallBack {
        <T> void onSuccess(List<T> result);

        void onFailure(String error);
    }

}
