package com.android.aitaojie.wxapi;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.zlibrary.util.TypeUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by win7 on 2016/8/18.
 */
public class WXRequestPost extends StringRequest{
        private Map<String, String> mParams = null;
        public WXRequestPost(int method, String url, Response.Listener<String> listener,
                                 Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }
        public WXRequestPost(String url, Map<String, String> params, Response.Listener<String> listener,
                                 Response.ErrorListener errorListener) {
            super(Method.POST, url, listener, errorListener);
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

        //发起post请求传递的参数
      /*  @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            String mapStr=Map2JsonStr(mParams);
            Log.e("Httpmap",mapStr);
            return mParams;
        }*/
    	@Override
	public byte[] getBody() throws AuthFailureError {
		String mapStr= TypeUtil.Map2Xml(mParams);
		return mapStr.getBytes();
	}
}
