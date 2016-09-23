package com.android.aitaojie.config;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.zlibrary.util.TypeUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StringRequestPost extends StringRequest {
	private String pStr;
	private Map<String, String> mParams = null;
	public static String arrayType;
	public StringRequestPost(String url, Map<String, String> params, Listener<String> listener,
							 ErrorListener errorListener) {
		super(Method.POST, url, listener, errorListener);
//		Log.e("url",url);
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
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		String mapStr=Map2JsonStr(mParams);
		Log.e("Httpmap",mapStr);
		return mParams;
	}

	/*@Override
	public byte[] getBody() throws AuthFailureError {
		String mapStr= TypeUtil.Map2JsonStr(mParams);
		Log.e("postData",mapStr);
		if(HttpMap.Debug==false)
		 pStr=HttpMap.PBase64(mapStr);
		else
			pStr=mapStr;
		return pStr.getBytes();
	}*/
	public  String Map2JsonStr(Map map){
		Map.Entry entry;
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
		{
			entry = (Map.Entry)iterator.next();
			if(entry.getKey().toString().equals(arrayType))
			{
				sb.append("\"").append(entry.getKey().toString()).append("\"").append(":").append(null == entry.getValue() ? "" :
						entry.getValue().toString()).append(iterator.hasNext() ? "," : "");
			}
			else {
				sb.append("\"").append(entry.getKey().toString()).append("\"").append(":").append("\"").append(null == entry.getValue() ? "" :
						entry.getValue().toString()).append("\"").append(iterator.hasNext() ? "," : "");
			}
		}
		sb.append("}");
		return sb.toString();
	}
}
