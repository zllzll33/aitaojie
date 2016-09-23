package com.android.aitaojie.util;

import android.util.Base64;
import android.util.Log;

import com.android.zlibrary.util.TypeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by win7 on 2016/6/23.
 */
public class AlipayUtil {
    public static final String PARTNER = "2088021730992142"; // pid
    public static final String SELLER = "yunying@luofangyun.com";
    public static final String SERVICE="mobile.securitypay.pay";
    public static final String SIGN_TYPE="RSA";
    public static final String it_b_pay="30m";
    public static final String return_url="m.alipay.com";
    public static final String _input_charset="utf-8";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    public static final String PAYMENT_TYPE = "1";
    public static final String NOTIFY_URL="http://h.luofangyun.com/Api/alipayNotify.html";
    public static String order_id="";
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALpKnelQ5RLEk0E5"
            +"2vnrkFAi9LKnkRRMRHEBCHD0d+i7DvwJCf1jkA7nqV82iCTljnov471yIY1VNJE8"
            +"ACMNeG8804dHSKf7uoZy0STBYcavl5AFjLH3gn/nnOS6RtFrnW/XJiTCDvspfoSz"
            +"QTTuS8U0PKxMYgn4hyIdpC0txN1LAgMBAAECgYBwzaX5FabgSm/wWxwhoWZtX/1U"
            +"AOt0own+c2WtIBtLrZlRPBf0e0lL/TMuGFMwJGC11DHPEUEH18RQ/UgH8awuhR6k"
            +"qX6RyCD5oIr4CpvsQRRKTQ1VRRNPZDxMhzqQYdc+SoAOq6c+uK97q8N6ZuqK0Xhh"
            +"jY07Vo0mf8hMe5UBwQJBAPVS+IwQgrogGwezG5AFTj1YJvmslWeH4p9xBmEmkm1s"
            +"+lcZvx9MAoRzE2OlRWExkx5kqt/Nl06Bo967OPQPUrkCQQDCZf5vcDjS8EH3t/pY"
            +"bbBh76eWFU3bqG0VUGBGCXa6BJdyYg7pMZpVA0KwMswA+zhHNjInt5zhceoFvISf"
            +"4f4jAkEAxfqcnKRia4Sna7JOhw7REk6Gva1asT+HUzbqeBKhzu9IpGwhwkccLPmV"
            +"ZzAkaoiQetGw2IZZGkEjFweusb9/+QJACidHncxvu1y/JVAfrES2ZLceurEMKv/m"
            +"CqnzDVVrNRYOTvQeMUHc/Lm53vgYPajhbJ7BCeAGOqQ3g+svxiYInwJATZC9KLOc"
            +"WxLF75TCQGhWbZPlZrKcjWJhvnjbHyqUoeAA8PlZkFstgqkNw6tR51i2nOhMk9P5"
            +"Es0iwaV02JDr1w==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6Sp3pUOUSxJNBOdr565BQIvSy"
            +"p5EUTERxAQhw9Hfouw78CQn9Y5AO56lfNogk5Y56L+O9ciGNVTSRPAAjDXhvPNOH"
            +"R0in+7qGctEkwWHGr5eQBYyx94J/55zkukbRa51v1yYkwg77KX6Es0E07kvFNDys"
            +"TGIJ+IciHaQtLcTdSwIDAQAB";

    public static String OrderMap(OrderModel orderModel)
    {
        order_id=orderModel.getOrderNum();
        Map<String,String> payOrderMap=new LinkedHashMap();
        payOrderMap.put("partner",AlipayUtil.PARTNER);
        payOrderMap.put("seller_id",AlipayUtil.SELLER);
        payOrderMap.put("out_trade_no",orderModel.getOrderNum());
        payOrderMap.put("subject",orderModel.getSubject());
        payOrderMap.put("body",orderModel.getObject());
        payOrderMap.put("total_fee",orderModel.getTotalFee());
        payOrderMap.put("notify_url",NOTIFY_URL);
        payOrderMap.put("service",AlipayUtil.SERVICE);
        payOrderMap.put("payment_type",AlipayUtil.PAYMENT_TYPE);
        payOrderMap.put("_input_charset",AlipayUtil._input_charset);
        payOrderMap.put("it_b_pay",AlipayUtil.it_b_pay);
        payOrderMap.put("return_url",AlipayUtil.return_url);
        String unsigned_order=Map2String(payOrderMap);
//        Log.e("unsigned_order",unsigned_order);
        String sign=AlipayUtil.sign(unsigned_order);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//       Log.e("ali_sign",sign);
        payOrderMap.put("sign",sign);
        payOrderMap.put("sign_type","RSA");
        String order= TypeUtil.Map2String(payOrderMap);
        return order;
    }
    public static String sign(String content ) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(RSA_PRIVATE,Base64.DEFAULT));
            KeyFactory keyf = KeyFactory.getInstance(SIGN_TYPE);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(_input_charset));
            byte[] signed = signature.sign();
            return Base64.encodeToString(signed,Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String Map2String(Map map){
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();

        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (Map.Entry)iterator.next();
            sb.append(entry.getKey().toString()).append( "=" ).append("\"").append(null==entry.getValue()?"":
                    entry.getValue().toString()).append("\"").append (iterator.hasNext() ? "&" : "");
        }

        return sb.toString();
    }
    public static String Map2JsonStr(Map map)
    {
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();

        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (Map.Entry)iterator.next();
            sb.append("\"").append(entry.getKey().toString()).append("\"").append( ":" ).append("\"").append(null==entry.getValue()?"":
                    entry.getValue().toString()).append("\"").append (iterator.hasNext() ? "," : "");
        }

        return sb.toString();
    }
public static class OrderModel
{
    private String OrderNum;
    private String Subject;
    private String Object;
    private String TotalFee;

    public String getOrderNum() {
        return OrderNum;
    }

    public void setOrderId(String orderNum) {
        OrderNum = orderNum;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getObject() {
        return Object;
    }

    public void setObject(String object) {
        Object = object;
    }

    public String getTotalFee() {
        return TotalFee;
    }

    public void setTotalFee(String totalFee) {
        TotalFee = totalFee;
    }
}
    public static String  PayokStr2Jsonstr(String str)
    {
        Map<String, String> PayokMap=new HashMap<String, String>();
        PayokMap.put("type","ACW_ALIPAY_ORDER_STATUS");
        str= str.substring(0, str.indexOf(";result"));
        str=  str.replace("=", ":");
        str=  str.replace("{","\"");
        str= str.replace("}","\"");
        str= str.replace(";",",");
        str="{"+str+"}";
        Log.e("pay_status",str);
        try {
            JSONObject jsonObject=new JSONObject(str);
            String resultStatus=jsonObject.optString("resultStatus");
            String memo=jsonObject.optString("memo");
            PayokMap.put("resultStatus",resultStatus);
            PayokMap.put("order_info",memo);
            if(resultStatus.equals("9000"))
            {
                PayokMap.put("order_id",order_id);
                PayokMap.put("order_status","success");
            }
            else if(resultStatus.equals("6001"))
            {
                PayokMap.put("order_status","cancle");
                PayokMap.put("order_id",order_id);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
     String payokJsonStr  = Map2JsonStr(PayokMap);
        payokJsonStr="{"+payokJsonStr+"}";
        return payokJsonStr;
    }
}
