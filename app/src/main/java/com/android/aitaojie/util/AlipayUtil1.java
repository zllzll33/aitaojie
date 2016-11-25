package com.android.aitaojie.util;

import android.util.Base64;
import android.util.Log;

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
public class AlipayUtil1 {
    public static final String PARTNER = "2088421444611887"; // pid
    public static final String SELLER = "admin@56xing.cc";
    public static final String SERVICE="mobile.securitypay.pay";
    public static final String SIGN_TYPE="RSA";
    public static final String it_b_pay="30m";
    public static final String return_url="m.alipay.com";
    public static final String _input_charset="utf-8";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    public static final String PAYMENT_TYPE = "1";
    public static final String NOTIFY_URL="http://www.aiwei1818.com/addons/ewei_shopv2/payment/alipay/notify.php?";
    public static String order_id="";
    public static final String RSA_PRIVATE = "MIICXQIBAAKBgQDHpKOkwM/oBFrD4pi/oR8Ax3uDz4bVaa+MsGodGpX710a95RK4543uqpcl3jhWe8K5sOwrhh1VaPjJ3EjH5rFcOgtIigM/cXKSy5S8j6JWcyZ7TWNS0qxdq9EIa9saFXSYO8pxGT83TsADHKdtFdpe52wOt6aNbOcDEsFFoNL6ZQIDAQABAoGAa8k4zL+IwDRxqKaTX4qmyW3qAq0tl9U6gVPRxqrO5SxCNk4SAKCLoZqahkhm1FJ3PHgH2ivLdIx7Hcj/xmWHxVVw1oilmCpaISKbPgeMdkWHQnEPpojbgwp82g2mBZdH+HoU/rUQSKG+e5mice8PTwAotC+mMixYflo1MBpzo6ECQQD03QPBtMS+EfyoBU7ZWwNrbwbg/mBOKqgIM+tE4sHxQEueCZqJTStZiMk0WDEyErdKNA2O8pS+Us6eazrwqvTpAkEA0LkeL9U2gpiVLbVZtV0JyCyMTeA+auErm435XvglsFR0MSENXFDX5qpDEtDCkzGGlRw/herdkWyAM3R2kPXcHQJAYfnZWfWpz1/FF7nf/eqD6MXpG7fM8xnwWC/mEI8nCRAv8ZFoK04cbVvlHjO3o5CHlIJVdoAr8ieSEyNW1xugmQJBAML7C64XeXKKY2Fttj3sigTLSM4/G0/wxTHtcPU8zshoPJrgi4CfrPC4QSxEhF/ItjZ5SwYpH46IU0K+GzzT0CkCQQDQ2sdo1tefh8fhfiQk9drSC7liWUM0r4ZzMcyOeus4tpDHtu/602Av1v5MFm570+sJ6Er4nhIb0cc70mPlgHDg";
//   public static String private_pkcs="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMeko6TAz+gEWsPimL+hHwDHe4PPhtVpr4ywah0alfvXRr3lErjnje6qlyXeOFZ7wrmw7CuGHVVo+MncSMfmsVw6C0iKAz9xcpLLlLyPolZzJntNY1LSrF2r0Qhr2xoVdJg7ynEZPzdOwAMcp20V2l7nbA63po1s5wMSwUWg0vplAgMBAAECgYBryTjMv4jANHGoppNfiqbJbeoCrS2X1TqBU9HGqs7lLEI2ThIAoIuhmpqGSGbUUnc8eAfaK8t0jHsdyP/GZYfFVXDWiKWYKlohIps+B4x2RYdCcQ+miNuDCnzaDaYFl0f4ehT+tRBIob57maJx7w9PACi0L6YyLFh+WjUwGnOjoQJBAPTdA8G0xL4R/KgFTtlbA2tvBuD+YE4qqAgz60TiwfFAS54JmolNK1mIyTRYMTISt0o0DY7ylL5Szp5rOvCq9OkCQQDQuR4v1TaCmJUttVm1XQnILIxN4D5q4Subjfle+CWwVHQxIQ1cUNfmqkMS0MKTMYaVHD+F6t2RbIAzdHaQ9dwdAkBh+dlZ9anPX8UXud/96oPoxekbt8zzGfBYL+YQjycJEC/xkWgrThxtW+UeM7ejkIeUglV2gCvyJ5ITI1bXG6CZAkEAwvsLrhd5copjYW22PeyKBMtIzj8bT/DFMe1w9TzOyGg8muCLgJ+s8LhBLESEX8i2NnlLBikfjohTQr4bPNPQKQJBANDax2jW15+Hx+F+JCT12tILuWJZQzSvhnMxzI566zi2kMe27/rTYC/W/kwWbnvT6wnoSvieEhvRxzvSY+WAcOA=";
    public static String private_pkcs="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALPDaXJ5NneZ9dbeGo3u7VA/Gb5ZOkC38bl3JONPdWjDDpaIR+bfS5KuyE6lqqMbJyP/QbQl29joh7xoSN0Rltro20RaNGAewKL6PvQrQnXV1v4g4KUJ9k2KMUr5p5WAfz9qon3mj/mAUMJCptlzy+8GyVF7zR1HGANE4oC3XXvDAgMBAAECgYBGyh0sRy97ydVV2AD9A6nQgAjTPLAD7Gv0bUbJfPDvYGDORryJ3kUUbl/TGMopkPfI5SwqlP4OXQOW3jpTVCnjQfNJUhjmX5bcuwf3i5zWWSKPacajJGE3JCW+KVWgM79M6My3Vosj3rbw4r5TvAAyIMKUUj3vgHwSGprIi/KaAQJBANqJMhTXllZ8MtWirh7rCdbLtJJgxvjBxFILDfwklXmB1SfE0el74erJisb3qRQRFIf0RtjaA8WOSjfiV03bkBMCQQDSlJ4zP1e+dGe2azTfOWf4IZSpvNfEqd4ThKXoNPqJxbOBTWcTbpzNmr35O2y9fSI5HJHPXbxbzw28aBX7kbuRAkBa5Jk55fkXe4zBImp9XZC4D/3IrAvVxvHaldJ5PtYUtZVsdwY/sFDkkHLyEmv2dqwtZ8JXy0WqU2Y3HCLntRyVAkEApzjjxBh6CkEQFvldXCKPIKkyD/Rpd7/ZHbDJuvNPPVbk7DpWL0U4ecF3ONLq0DLtmWLJm115dYKgCsyxIDwjMQJALPQsKydgWfrWcXpiroa7o56qUw9vrCMYh9OjH78qCuoBM19hPqPw6GUUMMLT2qj8inbE0fUhLP9HkkyWzaos5Q==";

    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDHpKOkwM/oBFrD4pi/oR8Ax3uDz4bVaa+MsGodGpX710a95RK4543uqpcl3jhWe8K5sOwrhh1VaPjJ3EjH5rFcOgtIigM/cXKSy5S8j6JWcyZ7TWNS0qxdq9EIa9saFXSYO8pxGT83TsADHKdtFdpe52wOt6aNbOcDEsFFoNL6ZQIDAQAB";

    public static String OrderMap(OrderModel orderModel)
    {
        order_id=orderModel.getOrderNum();
        Map<String,String> payOrderMap=new LinkedHashMap();
        payOrderMap.put("partner", AlipayUtil1.PARTNER);
        payOrderMap.put("seller_id", AlipayUtil1.SELLER);
        payOrderMap.put("out_trade_no",orderModel.getOrderNum());
        payOrderMap.put("subject",orderModel.getSubject());
        payOrderMap.put("body",orderModel.getObject());
        payOrderMap.put("total_fee",orderModel.getTotalFee());
        payOrderMap.put("notify_url",NOTIFY_URL);
        payOrderMap.put("service", AlipayUtil1.SERVICE);
        payOrderMap.put("payment_type", AlipayUtil1.PAYMENT_TYPE);
        payOrderMap.put("_input_charset", AlipayUtil1._input_charset);
        payOrderMap.put("it_b_pay", AlipayUtil1.it_b_pay);
        payOrderMap.put("return_url", AlipayUtil1.return_url);
        String unsigned_order=Map2String(payOrderMap);
//        Log.e("unsigned_order",unsigned_order);
        String sign= AlipayUtil1.sign(unsigned_order);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//       Log.e("ali_sign",sign);
        payOrderMap.put("sign",sign);
        payOrderMap.put("sign_type","RSA");
        String order= Map2String(payOrderMap);
        return order;
    }
    public static String sign(String content ) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(private_pkcs,Base64.DEFAULT));
            KeyFactory keyf = KeyFactory.getInstance(SIGN_TYPE);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(_input_charset));
            byte[] signed =signature.sign();



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
