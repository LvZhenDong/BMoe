package com.kklv.bmoe.data;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kklv.bmoe.utils.L;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LvZhenDong
 * created at 2016/6/2 11:15
 */
public class GsonRequest<T> extends Request<T> {
    private final static String TAG = "GsonRequest";
    private final Gson gson = new Gson();
    private final Type type;
    private final Response.Listener<T> listener;
    private final Context context;

    /**
     * @param method
     * @param url
     * @param type          对于json对象传入(Type)(MyClass.class)，
     *                      对于json数组传入new TypeToken(ArrayList<MyClass>)(){}.getType()
     * @param listener
     * @param errorListener
     */
    public GsonRequest(int method, String url, Type type,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.context = null;
        this.type = type;
        this.listener = listener;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String jsonStr = new String(networkResponse.data,
                    HttpHeaderParser.parseCharset(networkResponse.headers));
            L.i(TAG, jsonStr);
            if ("[]".equals(jsonStr)) {
                jsonStr = "";
            }
            return Response.success((T) (gson.fromJson(jsonStr, type)),
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T t) {
        if (listener != null) {
            listener.onResponse(t);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Ocp-Apim-Subscription-Key", "1203c934bfa0485ea1f59517aff05a56");
        return headers;
    }
}
