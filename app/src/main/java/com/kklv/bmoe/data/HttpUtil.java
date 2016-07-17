package com.kklv.bmoe.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kklv.bmoe.utils.L;

import java.io.UnsupportedEncodingException;

/**
 * @author LvZhenDong
 * @email lvzhendong1993@gmail.com
 * created at 2016/6/1 11:25
 */
public class HttpUtil {
    private static final String TAG = "HttpUtil";
    private Context mContext;
    private RequestQueue mRequestQueue;
    private HttpCallBack mHttpCallBack;

    public HttpUtil(Context context, HttpCallBack callBack) {
        this.mContext = context;
        this.mRequestQueue = Volley.newRequestQueue(mContext);
        this.mHttpCallBack = callBack;
    }

    /**
     * get方法
     *
     * @param url
     */
    public void get(String url) {
        StringRequest stringRequest = new StringRequest(url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        L.i(TAG, "response:" + response);
                        mHttpCallBack.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                L.e(TAG, "error:" + error);
                mHttpCallBack.onFailure();
            }
        });
        mRequestQueue.add(stringRequest);
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

    /**
     * 网络请求结果
     */
    public interface HttpCallBack {
        public void onSuccess(String response);

        public void onFailure();
    }
}
