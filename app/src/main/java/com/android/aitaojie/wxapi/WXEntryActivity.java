package com.android.aitaojie.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.aitaojie.MainActivity;
import com.android.aitaojie.activity.BindMobileActivity;
import com.android.aitaojie.config.App;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.config.StringRequestGet;
import com.android.aitaojie.share.ZWXShare;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.util.PreferenceUtil;
import com.google.gson.JsonObject;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ntop on 15/9/4.
 */
public class WXEntryActivity extends Activity  implements IWXAPIEventHandler {
    RequestQueue mQueue;
    private String wx_logurl="https://api.weixin.qq.com/sns/oauth2/access_token?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ZWXShare.getInstance().getIWXAPIinstance().handleIntent(getIntent(), this);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onReq(BaseReq arg0) { }

    @Override
    public void onResp(BaseResp resp) {
        Log.e("WX_share", "resp.errCode:" + resp.errCode + ",resp.errStr:"
                + resp.errStr);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()) {
//                    Log.e("WX_share","分享成功");
                }
                if(ConstantsAPI.COMMAND_SENDAUTH== resp.getType())
                {
//                    Log.e("WX_Login","登录成功");
                    String code = ((SendAuth.Resp) resp).code; //即为所需的code
                    String url=wx_logurl+"&appid="+Constant.WX_APPID+"&secret="+Constant.WX_SECRET+"&grant_type=authorization_code&code="+code;
                   Log.e("wx_logurl",url);
                    StringRequestGet request =   new StringRequestGet(url, null, new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {
//                             Log.e("wx_login",response);
//                              {"access_token":"qb40-og7fvXAb_KIjZZW2lY356oEA-NwCj83cmokq0S8ITEN2WS3v_85Fatu0xkAK6evZIoJKeA98umOaBC6C4oFMRfNU1XfDhpI8qjpx8k","expires_in":7200,"refresh_token":"I4VuNzh-beyKweCicE7eSbdS43ih4s4XUfkdFdOVdbbGL6pQO5O_M5UMNdHtJH5aXYQ9lJGfOvURqZKlqJGCxLa5pxNViNMjBBu_4HukLf4","openid":"olFzfv2b2sLAB2VaTfhJJ8do4pbw","scope":"snsapi_userinfo","unionid":"oSc-ot8ys7ii4Sq4AzTwy5__MR2Y"}
                             try {
                                 JSONObject wxobject=new JSONObject(response);

                                 String refresh_token= wxobject.optString("refresh_token");
                                 String access_token= wxobject.optString("access_token");
                                 String openid= wxobject.optString("openid");
                                 String unionid= wxobject.optString("unionid");
                                 String scope= wxobject.optString("scope");
//                                 Log.e("login_info","openid:"+openid+",unionid:"+unionid+",scope:"+scope);
                                 StringRequestGet requestInfo=new StringRequestGet("https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid, null, new Response.Listener<String>() {
                                     @Override
                                     public void onResponse(String response) {
//                                     Log.e("wx_info",response);
                                         try {
                                             JSONObject info=new JSONObject(response);
                                         final    String openid= info.optString("openid");
                                             final   String nick=   info.optString("nickname");
                                              int gender= info.optInt("sex");
                                             final String sex=String.valueOf(gender);
                                             final   String   headimgurl=info.optString("headimgurl");
                                             HttpMap httpMap=new HttpMap();
                                             httpMap.putMode("User");
                                             httpMap.putCtl("Public");
                                             httpMap.putAct("ckbind");
                                             httpMap.putDataMap("type","2");
                                             httpMap.putDataMap("id",openid);
                      /*  httpMap.putDataMap("nick",nickname);
                        httpMap.putDataMap("sex",gender);
                        httpMap.putDataMap("head",figureurl_qq_2);*/
                                             httpMap.setHttpListener(new HttpMap.HttpListener() {
                                                 @Override
                                                 public void onSuccess(String response, String data) {
                                                     JSONObject jsonObject= null;
                                                     try {
                                                         jsonObject = new JSONObject(data);
                                                         String uid = jsonObject.optString("uid");
                                                         if(uid.equals("0"))
                                                         {
                                                             Intent intent=new Intent(WXEntryActivity.this, BindMobileActivity.class);
                                                             Bundle bundle=new Bundle();
                                                             bundle.putString("id",openid);
                                                             bundle.putString("type","wx");
                                                             bundle.putString("nick",nick);
                                                             bundle.putString("sex",sex);
                                                             bundle.putString("head",headimgurl);
                                                             intent.putExtra("info",bundle);
                                                             ZActivityManager.getInstance().getLastActivity().startActivity(intent);
                                                         }
                                                         else
                                                         {
                                                             String jid = jsonObject.optString("jid");
                                                             Constant.huanxin_name = jsonObject.optString("huanxin_name");
                                                             Constant.huanxin_pwd = jsonObject.optString("huanxin_pwd");
                                                             Constant.UID = uid;
                                                             Constant.JID = jid;
                                                             PreferenceUtil.putString("UID", uid);
                                                             PreferenceUtil.putString("JID", jid);
                                                             PreferenceUtil.putString("huanxin_name", Constant.huanxin_name);
                                                             PreferenceUtil.putString("huanxin_pwd", Constant.huanxin_pwd);
                                                             Intent intent = new Intent(ZActivityManager.getInstance().getLastActivity(), MainActivity.class);
                                                             ZActivityManager.getInstance().getLastActivity().startActivity(intent);
                                                      /*   Constant.UID=data;
                                                         PreferenceUtil.putString("UID",data);
                                                         Intent intent =new Intent(WXEntryActivity.this, MainActivity.class);
                                                         startActivity(intent);*/
                                                         }
                                                     }catch (JSONException e) {
                                                         e.printStackTrace();
                                                     }

                                                 }
                                             });
                                         } catch (JSONException e) {
                                             e.printStackTrace();
                                         }
//                                         {"openid":"olFzfv2b2sLAB2VaTfhJJ8do4pbw","nickname":"wdzphhh","sex":0,"language":"zh_CN","city":"","province":"","country":"CN","headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/1mwUnLLPPic7gtPWibDOStUOXRosZdPtpX6rbXm6rSQ40kicCt63gUzibkZyLVldrOp8EibGbzh0Obed92Zkc4CcxnUUHxUevfapia\/0","privilege":[],"unionid":"oSc-ot8ys7ii4Sq4AzTwy5__MR2Y"}
                                     }
                                 }, new Response.ErrorListener() {
                                     @Override
                                     public void onErrorResponse(VolleyError error) {
                                     }
                                 });
                                 mQueue.add(requestInfo);
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         }
                     }, new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error) {
                         }
                     }
                     );
                         mQueue = Volley.newRequestQueue(App.getInstance());
                    mQueue.add(request);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.e("WX_share","分享取消");
                //分享取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.e("WX_share","分享拒绝");
                //分享拒绝
                break;
        }
    }
}
