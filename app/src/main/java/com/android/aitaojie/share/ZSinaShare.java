package com.android.aitaojie.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;


import com.android.aitaojie.config.App;
import com.android.aitaojie.config.StringRequestGet;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.zlibrary.base.ZActivityManager;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.open.t.Weibo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by win7 on 2016/6/25.
 */
public class ZSinaShare {
    private IWeiboShareAPI mWeiboShareAPI = null;
  public  SsoHandler   mSsoHandler;
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    private String SinaAppId = "";
    private static ZSinaShare zSinaShare;

    public static ZSinaShare getInstance() {
        if (zSinaShare == null)
            zSinaShare = new ZSinaShare();
        return zSinaShare;
    }

    public ZSinaShare() {

    }

    public ZSinaShare setSinaAppId(String SinaAppId) {
        this.SinaAppId = SinaAppId;
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(App.getInstance(), SinaAppId);
        mWeiboShareAPI.registerApp();
        return this;
    }

    public IWeiboShareAPI getmWeiboShareAPI() {
        return mWeiboShareAPI;
    }

    public ZSinaShare SinaShare(String title, String content, String imgUrl, String targetUrl) {
        new BitMapThread(title, content, imgUrl, targetUrl).start();
        return this;
    }

    public class BitMapThread extends Thread {
        private String imgUrl, targetUrl, title, content;

        public BitMapThread(String title, String content, String imgUrl, String targetUrl) {
            this.title = title;
            this.content = content;
            this.imgUrl = imgUrl;
            this.targetUrl = targetUrl;
        }

        public void run() {
            try {
                URL url = new URL(imgUrl);
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                long fileLeght = conn.getContentLength();
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bitmap;
                if (fileLeght > 100000) {
                    int scale = (int) (fileLeght / 100000) + 1;
                    options.inSampleSize = scale;
                    bitmap = BitmapFactory.decodeStream(is, null, options);
                } else
                    bitmap = BitmapFactory.decodeStream(is);
              /*  Bitmap  bitmap = BitmapFactory.decodeResource(App.getInstance().getResources(), R.drawable.ic_logo);*/
                WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
                TextObject textObject = new TextObject();
                textObject.text = title + "   " + content;
                weiboMessage.textObject = textObject;
                ImageObject imageObject = new ImageObject();
                imageObject.setImageObject(bitmap);
                weiboMessage.imageObject = imageObject;
                WebpageObject mediaObject = new WebpageObject();
                mediaObject.identify = Utility.generateGUID();
                mediaObject.title = "";
                mediaObject.description = "";
                mediaObject.setThumbImage(bitmap);
                mediaObject.actionUrl = targetUrl;
                mediaObject.defaultText = "分享";
                weiboMessage.mediaObject = mediaObject;
                SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
                request.transaction = String.valueOf(System.currentTimeMillis());
                request.multiMessage = weiboMessage;
                mWeiboShareAPI.sendRequest(ZActivityManager.getInstance().getLastActivity(), request);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public ZSinaShare sian_Login()
    {
        AuthInfo  mWeibo =new AuthInfo(App.getInstance(),SinaAppId,"https://api.weibo.com/oauth2/default.html",SCOPE );
        mSsoHandler = new SsoHandler(ZActivityManager.getInstance().getLastActivity(), mWeibo);
        AuthListener authListener=new AuthListener();
        mSsoHandler.authorize(authListener);
        return this;
    }
    public class AuthListener implements WeiboAuthListener {

        @Override
        public void onCancel() {

        }

        @Override
        public void onComplete(Bundle values) {
            Log.e("sian_login","授权成功");

                Oauth2AccessToken  accessToken = Oauth2AccessToken.parseAccessToken(values); // 从Bundle中解析Token

            if (accessToken.isSessionValid())
            {
                String phoneNum = accessToken.getPhoneNum();// 从这里获取用户输入的 电话号码信息
                String access_token=accessToken.getToken();
                String uid=accessToken.getUid();
                Log.e("access_Token",access_token);
               StringRequestGet user_info=new StringRequestGet("https://api.weibo.com/2/users/show.json?access_token=" + access_token + "&uid=" + uid, null, new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                   Log.e("sina_user_info",response);
//{"id":5770338606,"idstr":"5770338606","class":1,"screen_name":"空中小舟","name":"空中小舟","province":"33","city":"1","location":"浙江 杭州","description":"","url":"","profile_image_url":"http://tva3.sinaimg.cn/default/images/default_avatar_male_50.gif","cover_image_phone":"http://ww1.sinaimg.cn/crop.0.0.640.640.640/549d0121tw1egm1kjly3jj20hs0hsq4f.jpg","profile_url":"u/5770338606","domain":"","weihao":"","gender":"m","followers_count":5,"friends_count":114,"pagefriends_count":0,"statuses_count":4,"favourites_count":0,"created_at":"Tue Nov 24 16:22:49 +0800 2015","following":false,"allow_all_act_msg":false,"geo_enabled":true,"verified":false,"verified_type":-1,"remark":"","status":{"created_at":"Tue May 03 10:30:40 +0800 2016","id":3970995809374770,"mid":"3970995809374770","idstr":"3970995809374770","text":"艺隆演出http://t.cn/RqHQKc6","textLength":27,"source_allowclick":0,"source_type":1,"source":"<a href=\"http://app.weibo.com/t/feed/3Osa9d\" rel=\"nofollow\">洛方云</a>","favorited":false,"truncated":false,"in_reply_to_status_id":"","in_reply_to_user_id":"","in_reply_to_screen_name":"","pic_urls":[{"thumbnail_pic":"http://ww2.sinaimg.cn/thumbnail/006ivKFwgw1f3hzoht8n5j3050047747.jpg"}],"thumbnail_pic":"http://ww2.sinaimg.cn/thumbnail/006ivKFwgw1f3hzoht8n5j3050047747.jpg","bmiddle_pic":"http://ww2.sinaimg.cn/bmiddle/006ivKFwgw1f3hzoht8n5j3050047747.jpg","original_pic":"http://ww2.sinaimg.cn/large/006ivKFwgw1f3hzoht8n5j3050047747.jpg","geo":null,"annotations":[{"client_mblogid":"04c0540f-85c8-449b-bbc6-35aeaa73a384"},{"mapi_request":true}],"reposts_count":0,"comments_count":0,"attitudes_count":0,"isLongText":false,"mlevel":0,"visible":{"type":0,"list_id":0},"biz_feature":4294967300,"darwin_tags":[],"hot_weibo_tags":[],"text_tag_tips":[],"userType":0,"positive_recom_flag":0},"ptype":0,"allow_all_comment":true,"avatar_large":"http://tva3.sinaimg.cn/default/images/default_avatar_male_180.gif","avatar_hd":"http://tva3.sinaimg.cn/default/images/default_avatar_male_180.gif","verified_reason":"","verified_trade":"","verified_reason_url":"","verified_source":"","verified_source_url":"","follow_me":false,"online_status":0,"bi_followers_count":0,"lang":"zh-cn","star":0,"mbtype":0,"mbrank":0,"block_word":0,"block_app":0,"credit_score":80,"user_ability":0,"urank":4}
                       try {
                           JSONObject jsonObject=new JSONObject(response);
                           String avt=jsonObject.optString("profile_image_url");//头像
                           String name=jsonObject.optString("name");
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               });
                RequestQueue   mQueue = Volley.newRequestQueue(App.getInstance());
                mQueue.add(user_info);
            }



        }

        @Override
        public void onWeiboException(WeiboException e) {

        }
    }
    //在登录的activity 中加入    否则无法执行回调
/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(ZSinaShare.getInstance().mSsoHandler != null){
            ZSinaShare.getInstance().mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }*/
}