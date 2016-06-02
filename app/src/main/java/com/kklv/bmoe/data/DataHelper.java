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

    public void getAllCamp(){
        GsonRequest gsonRequest=new GsonRequest<ArrayList<Camp>>(Request.Method.GET, HttpUrl.ALL_CAMP,
                new TypeToken<ArrayList<Camp>>() {
                }.getType(), new Response.Listener<ArrayList<Camp>>() {
            @Override
            public void onResponse(ArrayList<Camp> response) {
                ArrayList<Camp> list = response;
                Log.i(TAG,"阵营数量："+list.size()+"");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.getMessage(),error);
            }
        });
        mRequestQueue.add(gsonRequest);
    }
}
