package com.android.aitaojie.wxapi;

import android.util.Log;
import android.util.Xml;

import com.android.aitaojie.config.App;
import com.android.aitaojie.config.Constant;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.zlibrary.util.SysUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class WxpayManager {
	private IWXAPI api;
    PayReq req;
	private static WxpayManager  wxpayManager=null;
    private String  notify_url="http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php";
   private String mch_id="1293339201" ;
  private String  M_KEY="LuofangyunAitaojieLuofangyun1234";
    private static RequestQueue mQueue=null;
  private String orderid;
 public WxpayManager()
 {
	 api = WXAPIFactory.createWXAPI(App.getInstance(), Constant.WX_APPID);
	 api.registerApp(Constant.WX_APPID);
     req=new PayReq();
 }
    public String getOrderId()
    {
      return orderid;
    }
 public static WxpayManager getInstance()
 {
	 if(wxpayManager==null)
		 wxpayManager=new  WxpayManager();
	 return wxpayManager;
 }

    public void pay(String orderId,String info,String fee)
    {
        orderid=orderId;
        Map<String,String> orderMap=new LinkedHashMap<>();
        orderMap.put("appid",Constant.WX_APPID);
        orderMap.put("body",info);
        orderMap.put("mch_id",mch_id);
        orderMap.put("nonce_str",genNonceStr());
        orderMap.put("notify_url",notify_url);
        orderMap.put("out_trade_no", orderId);
        orderMap.put("spbill_create_ip", SysUtil.getLocalIpAddress());
        orderMap.put("total_fee",fee);
        orderMap.put("trade_type", "APP");
        String sign=genPackageSign(orderMap);
        orderMap.put("sign",sign);
//        Log.e("map2xml", TypeUtil.Map2Xml(orderMap));
        WXRequestPost request=  new WXRequestPost("https://api.mch.weixin.qq.com/pay/unifiedorder", orderMap, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.e("wx_reqid_response",response);
                Map<String,String> prepay=decodeXml(response);
                String prepayId=prepay.get("prepay_id");
               if(prepay.get("prepay_id")==null)
                   return;
//                Log.e("prepayMap",TypeUtil.Map2JsonStr(prepay));
//                Log.e("prepayId",prepay.get("prepay_id"));
                req.prepayId=prepayId;
                req.appId = Constant.WX_APPID;
                req.partnerId = mch_id;
                req.packageValue = "Sign=WXPay";
                req.nonceStr = genNonceStr();
                req.timeStamp = String.valueOf(genTimeStamp());
                Map<String,String> orderMap=new LinkedHashMap<>();
                orderMap.put("appid", req.appId);
                orderMap.put("noncestr", req.nonceStr);
                orderMap.put("package", req.packageValue);
                orderMap.put("partnerid", req.partnerId );
                orderMap.put("prepayid", req.prepayId);
                orderMap.put("timestamp",req.timeStamp);
                String sign=genPackageSign(orderMap);
                req.sign=sign;
                api.registerApp(Constant.WX_APPID);
               boolean pay_ok= api.sendReq(req);
//                Log.e("pay_ok",String.valueOf(pay_ok));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Post(request);
    }
    private void Post(WXRequestPost request)
    {
        if(mQueue==null)
            mQueue = Volley.newRequestQueue(App.getInstance());
        mQueue.add(request);
    }
    private String genNonceStr() {
        Random random = new Random();
        return getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }
    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
    private String getMessageDigest(byte[] buffer) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    private String genPackageSign(Map map) {
        StringBuilder sb = new StringBuilder();
      String str=Map2String(map);
        sb.append(str);
        sb.append("&key=");
        sb.append(M_KEY);
//        Log.e("sign前",sb.toString());
        String packageSign =getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("orion",packageSign);
        return packageSign;
    }
    public  String Map2String(Map map){
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();

        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (Map.Entry)iterator.next();
            sb.append(entry.getKey().toString()).append( "=" ).append(null==entry.getValue()?"":
                    entry.getValue().toString()).append (iterator.hasNext() ? "&" : "");
        }
        return sb.toString();
    }
    public Map<String,String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName=parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if("xml".equals(nodeName)==false){
                            //实例化student对象
                            xml.put(nodeName,parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
//            Log.e("orion",e.toString());
        }
        return null;

    }
}
