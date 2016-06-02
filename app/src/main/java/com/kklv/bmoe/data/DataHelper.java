package com.kklv.bmoe.data;


import android.content.Context;
import android.service.voice.VoiceInteractionSession;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import com.kklv.bmoe.constant.HttpUrl;
import com.kklv.bmoe.object.Camp;
import com.kklv.bmoe.object.RoleInfo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 *
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/2 11:18
 */
public class DataHelper {
    private static final String TAG = "DataHelper";
    private static DataHelper instance;
    private Context mContext;
    private RequestQueue mRequestQueue;

    private DataHelper(Context context) {
        mContext = context;
        mRequestQueue= Volley.newRequestQueue(mContext);
    }

    public static DataHelper getInstance(Context context) {
        if(instance == null){
            instance=new DataHelper(context);
        }
        return instance;
    }

    /**
     * 获取所有阵营信息--对应网页端的“阵营表格”
     */
    public void getAllCamps(){
        GsonRequest gsonRequest=new GsonRequest<ArrayList<Camp>>(Request.Method.GET, HttpUrl.ALL_CAMP,
                new TypeToken<ArrayList<Camp>>() {
                }.getType(), new Response.Listener<ArrayList<Camp>>() {
            @Override
            public void onResponse(ArrayList<Camp> response) {
                ArrayList<Camp> list = response;
                Log.i(TAG,"阵营数量："+list.size());
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
     * @param bangumi 阵营名称
     */
    public void getCampRank(String bangumi){
        String url=HttpUrl.RANK+"?bangumi="+DataHelper.EncodeChinese(bangumi);
        GsonRequest gsonRequest=new GsonRequest<ArrayList<RoleInfo>>(Request.Method.GET, url,
                new TypeToken<ArrayList<RoleInfo>>() {
                }.getType(), new Response.Listener<ArrayList<RoleInfo>>() {
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
}
