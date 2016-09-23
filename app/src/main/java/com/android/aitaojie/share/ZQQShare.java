package com.android.aitaojie.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.android.aitaojie.MainActivity;
import com.android.aitaojie.activity.BindMobileActivity;
import com.android.aitaojie.config.App;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.HttpMap;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.util.PreferenceUtil;
import com.android.zlibrary.util.ViewUtil;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by win7 on 2016/6/20.
 */
public class ZQQShare {
    private static String OpenId;
   private static ZQQShare qqShare;
   private Tencent mTencent;
    private LoginUiListener loginUiListener;
    public ZQQShare()
    {
        loginUiListener=new LoginUiListener();
        mTencent = Tencent.createInstance(Constant.QQ_APPID, App.getInstance());
    }
    public void onActivityResultData(int requestCode, int resultCode, Intent data)
    {
        mTencent.onActivityResultData(requestCode, resultCode, data, loginUiListener);
    }
    public static ZQQShare getInstance()
    {
        if(qqShare ==null)
            qqShare =new ZQQShare();
        return qqShare;
    }
public void ShareQQ(String title, String content, String ImgUrl, String targetUrl)
{
    Bundle params = new Bundle();
    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
    params.putString(QQShare.SHARE_TO_QQ_TITLE, title);
    params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  content);
    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,ImgUrl);
    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl );
//    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN); 同时分享到QQ空间
//    params.putString(QQShare.SHARE_TO_QQ_APP_NAME,  "幸福商圈");
    mTencent.shareToQQ(ZActivityManager.getInstance().getLastActivity(), params, new BaseUiListener(2));
}
     public void ShareQzone(String title, String content, String ImgUrl, String targetUrl)
     {
         Bundle params = new Bundle();
         params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT );
         params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//必填
         params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, content);//选填
         params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL,targetUrl);//必填
         ArrayList<String> images=new ArrayList<String>();
         images.add(ImgUrl);
         params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, images);
         mTencent.shareToQzone(ZActivityManager.getInstance().getLastActivity(), params, new BaseUiListener(2));
     }
    public void LoginQQ(Activity activity)
    {
        mTencent.login(activity,"all",  loginUiListener);
    }
    public void LagoutQQ()
    {
        mTencent.logout(App.getInstance());
    }
    private   class LoginUiListener implements IUiListener {

        public void onCancel() {

        }
        public void onComplete(Object response) {

            try {
//                Log.e("LoginQQ", "-------------" + response.toString());
                  OpenId = ((JSONObject) response).getString("openid");
              String  access_token= ((JSONObject) response).getString("access_token");
                String expires_in = ((JSONObject) response).getString("expires_in");
                mTencent.setOpenId(OpenId);
                mTencent.setAccessToken(access_token, expires_in);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            QQToken qqToken = mTencent.getQQToken();
            UserInfo info = new UserInfo(App.getInstance(), qqToken);
            info.getUserInfo(new BaseUiListener(1));
        }
        @Override
        public void onError(UiError uiError) {

        }
    }
    private static class BaseUiListener implements IUiListener {
        public static int user_info=1;
        public static int share=2;
        private  int type;
        public BaseUiListener (int type)
        {
            this.type=type;
        }
        @Override
        public void onComplete(Object response) {

            switch (type)
            {
                case 1:
//                                Log.e("QQInfo", response.toString());
                    try {
                     final    String   nickname = ((JSONObject) response).getString("nickname");
                     final    String gender= ((JSONObject) response).getString("gender");
                    final     String figureurl_qq_2= ((JSONObject) response).getString("figureurl_qq_2");

                        HttpMap httpMap=new HttpMap();
                        httpMap.putMode("User");
                        httpMap.putCtl("Public");
                        httpMap.putAct("ckbind");
                        httpMap.putDataMap("type","1");
                        httpMap.putDataMap("id",OpenId);
                      /*  httpMap.putDataMap("nick",nickname);
                        httpMap.putDataMap("sex",gender);
                        httpMap.putDataMap("head",figureurl_qq_2);*/
                        httpMap.setHttpListener(new HttpMap.HttpListener() {
                            @Override
                            public void onSuccess(String response, String data) {
                                JSONObject jsonObject= null;
                                try {
                                    jsonObject = new JSONObject(data);
                                    String uid=jsonObject.optString("uid");
                                    if(uid.equals("0"))
                                    {
                                        Intent intent=new Intent(ZActivityManager.getInstance().getLastActivity(), BindMobileActivity.class);
                                        Bundle bundle=new Bundle();
                                        bundle.putString("id",OpenId);
                                        bundle.putString("type","qq");
                                        bundle.putString("nick",nickname);
                                        bundle.putString("sex",gender);
                                        bundle.putString("head",figureurl_qq_2);
                                        intent.putExtra("info",bundle);
                                        ZActivityManager.getInstance().getLastActivity().startActivity(intent);
                                    }
                                    else {
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
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                    }catch (Exception e)
                    {

                    }
//                {"ret":0,"msg":"","is_lost":0,"nickname":"西风烈","gender":"男","province":"浙江","city":"杭州","figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1105486565\/A016E7CC631DBD0B2546DE9993EEF984\/30","figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1105486565\/A016E7CC631DBD0B2546DE9993EEF984\/50","figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1105486565\/A016E7CC631DBD0B2546DE9993EEF984\/100","figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1105486565\/A016E7CC631DBD0B2546DE9993EEF984\/40","figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1105486565\/A016E7CC631DBD0B2546DE9993EEF984\/100","is_yellow_vip":"0","vip":"0","yellow_vip_level":"0","level":"0","is_yellow_year_vip":"0"}
         break;
                case 2:
//                   qq_share
                    break;
            }

        }

        @Override
        public void onError(UiError uiError) {

        }

        @Override
        public void onCancel() {

        }
    }
}
