package com.android.aitaojie.config;

import android.util.Log;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.zlibrary.util.SysUtil;
import com.android.zlibrary.util.TypeUtil;
import com.android.zlibrary.util.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by win7 on 2016/6/4.
 */
public class HttpMap {
    public static  boolean Debug=true;
    private String Url;
    private Map<String, String> DataMap ;
    private String mode,ctl,act;

   private Response.ErrorListener errorListener;
   private Response.Listener<String> listener;
    private  HttpListener httpListener;
    private String responsStr;
    private String data;
    public HttpMap() {
        DataMap = new HashMap<String, String>();
        DataMap.put("from", "android");
      /*  DataMap.put("version", SysUtil.getPackageInfo()[1]);
        if (access_token != null)
            DataMap.put("access_token", access_token);
        if(Latitude!=0)
        {
            DataMap.put("lat",String.valueOf(Latitude));
            DataMap.put("lng",String.valueOf(Longitude));
        }*/

        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        };
        listener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("response",response);
                if(Debug==false)
                {
                    responsStr=PJBase64(response);
                }
                else
                    responsStr=response;
                try {
                    JSONObject json = new JSONObject(responsStr);
                    int errcode = json.optInt("errcode");
                    if(errcode!=200)
                    {
                        String errmsg=json.optString("errmsg");
                        Log.e("errmsg",errmsg);
                        if(!errmsg.isEmpty())
                        ViewUtil.showToast(errmsg);
                    }
                    else
                    {
                        data=json.optString("data");
                        try {
                            String info=json.optString("info");
                            if(!info.isEmpty())
                                ViewUtil.showToast(info);
                        }catch (Exception e)
                        {

                        }
                        if(httpListener!=null)
                            httpListener.onSuccess(responsStr,data);
                    }


                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    public void  putMode(String mode)    {this.mode=mode;  }
    public void putCtl(String ctl)
    {
        this.ctl=ctl;
    }
    public Response.ErrorListener getErrorListener()
    {
        return errorListener;
    }
    public Response.Listener getListener()
    {
        return  listener;
    }
    public void putAct(String act)
    {
        this.act=act;
    }
    public void putDataMap(String type,String data)
    {
        DataMap.put(type,data);
    }

    public void putDataArray(String type,String data)
    {
        StringRequestPost.arrayType=type;
        DataMap.put(type,data);

    }
    public Map<String, String> getDataMap()
    {
        return DataMap;
    }
    public String getURL()
    {
        Url= Constant.Server+"/"+mode+"/"+ctl+"/"+act;
           /* if(Debug)
            Url+="/debug";*/
        return Url;
    }
    public void setHttpListener(HttpListener httpListener)
    {
        this.httpListener=httpListener;
        sendHttp();
    }
   public  interface  HttpListener
   {
       public void onSuccess(String response, String data);
   }
    private void sendHttp()
    {
        StringRequestPost request = new StringRequestPost(getURL(),
                getDataMap(),getListener()
                ,getErrorListener());
        App.getInstance().HttpPost(request);
    }
    public static  String PJBase64(String base64)
    {
        int length=base64.length();
        String end=base64.substring(length-3).toString();
        String start=base64.substring(0,length-3);
        String  Str= TypeUtil.JBase64(end+start);
        return Str;
    }
    public static String PBase64(String str)
    {
       String baseStr= TypeUtil.Base64(str);
        String start=  baseStr.substring(0,3);
        String end=baseStr.substring(3);
        return  end+start;
    }
}
