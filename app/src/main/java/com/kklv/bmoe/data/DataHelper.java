package com.kklv.bmoe.data;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import com.kklv.bmoe.constant.HttpUrl;
import com.kklv.bmoe.object.Camp;
import com.kklv.bmoe.object.RoleInfo;
import com.kklv.bmoe.object.RoleIntradayCount;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/2 11:18
 */
public class DataHelper {
    private static final String TAG = "DataHelper";
    private Context mContext;
    private RequestQueue mRequestQueue;

    private DataHelperCallBack mCallBack;
    public DataHelper(Context context) {
        mContext = context;
        mRequestQueue= Volley.newRequestQueue(mContext);
    }

    /**
     * 获取所有阵营信息--对应网页端的“阵营表格”
     */
    public void getAllCamps(){
        String url=HttpUrl.ALL_CAMP;
        GsonRequest gsonRequest=new GsonRequest<ArrayList<Camp>>(Request.Method.GET, url,
                new TypeToken<ArrayList<Camp>>() {
                }.getType(),new Response.Listener<ArrayList<Camp>>() {
            @Override
            public void onResponse(ArrayList<Camp> response) {
                mCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.getMessage(),error);
            }
        });
        mRequestQueue.add(gsonRequest);
    }

    /**
     * 获取该阵营所有角色排名--对应网页端的“角色数据表格”
     * @param bangumi
     */
    public void getCampRank(String bangumi){
        String url=HttpUrl.RANK+"?bangumi="+EncodeChinese(bangumi);
        GsonRequest gsonRequest=new GsonRequest<ArrayList<RoleInfo>>(Request.Method.GET, url,
                new TypeToken<ArrayList<RoleInfo>>() {
                }.getType(),new Response.Listener<ArrayList<RoleInfo>>() {
            @Override
            public void onResponse(ArrayList<RoleInfo> response) {
                ArrayList<RoleInfo> list = response;
                Log.i(TAG,"角色数量："+list.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.getMessage(),error);
            }
        });
        mRequestQueue.add(gsonRequest);
    }

    /**
     *
     * @param map
     */
    public void getRoleIntradayCount(Map<String,String> map){
        String url=HttpUrl.ROLE+getURL(map);
        Log.i(TAG,"url:"+url);
        GsonRequest gsonRequest=new GsonRequest<ArrayList<RoleIntradayCount>>(Request.Method.GET, url,
                new TypeToken<ArrayList<RoleIntradayCount>>() {
                }.getType(), new Response.Listener<ArrayList<RoleIntradayCount>>() {
            @Override
            public void onResponse(ArrayList<RoleIntradayCount> response) {
                mCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.getMessage(),error);
                mCallBack.onFailure(error);
            }
        });
        mRequestQueue.add(gsonRequest);
    }

    private String getURL(Map<String,String> map){
        String result="?";
        if (!(TextUtils.isEmpty(map.get("date")))){
            result +="&date="+map.get("date");
        }
        if (!(TextUtils.isEmpty(map.get("sex")))){
            result +="&sex="+map.get("sex");
        }
        if (!(TextUtils.isEmpty(map.get("group")))){
            result +="&group="+map.get("group");
        }
        return result;
    }

    /**
     * 对中文进行URLEncode编码，不然无法解析URL
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String EncodeChinese(String str) {
        try {
            return java.net.URLEncoder.encode(str,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public interface DataHelperCallBack{
        public <T>void onSuccess(ArrayList<T> result);
        public void onFailure(Exception error);
    }

    public void registerCallBack(DataHelperCallBack callBack){
        this.mCallBack=callBack;
    }
}
