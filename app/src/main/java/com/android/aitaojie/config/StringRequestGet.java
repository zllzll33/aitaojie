package com.android.aitaojie.config;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/10 0010.
 */
public class StringRequestGet extends StringRequest {
    private String pStr;
    private Map<String, String> mParams = null;
    public StringRequestGet(int method, String url, Response.Listener<String> listener,
                             Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }
    public StringRequestGet(String url, Map<String, String> params, Response.Listener<String> listener,
                             Response.ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
        this.mParams = params;
    }
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {

        String str = null;
        try {
            str = new String(response.data,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Charset", "UTF-8");
        headers.put("Content-Type","application/x-www-form-urlencoded");
        return headers;
    }

    //发起get请求传递的参数
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mParams;
	}

//    @Override
//    public byte[] getBody() throws AuthFailureError {
//        String mapStr= TypeUtil.Map2JsonStr(mParams);
//        Log.e("postData",mapStr);
//        if(HttpMap.Debug==false)
//            pStr=HttpMap.PBase64(mapStr);
//        else
//            pStr=mapStr;
//        return pStr.getBytes();
//    }
}
