package com.android.aitaojie.share;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.android.aitaojie.config.App;
import com.android.aitaojie.config.Constant;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by win7 on 2016/6/20.
 */
public class ZWXShare {
    private IWXAPI wxApi;
    public static  String WX_appid="";
    private static ZWXShare zwxShare;

    public static ZWXShare getInstance()
    {
        if(zwxShare==null)
            zwxShare=new ZWXShare();
        return zwxShare;
    }
    public ZWXShare()
    {
        this.WX_appid= Constant.WX_APPID;
        wxApi = WXAPIFactory.createWXAPI(App.getInstance(), WX_appid, false);
        wxApi.registerApp(WX_appid);
    }
    public IWXAPI getIWXAPIinstance()
    {
        return wxApi;
    }
/*    public ZWXShare setWXAppid(String WX_appid)
    {
        this.WX_appid=WX_appid;
        wxApi = WXAPIFactory.createWXAPI(App.getInstance(), WX_appid, false);
        wxApi.registerApp(WX_appid);
        return this;
    }*/
    public void LoginWX()
    {
      SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "carjob_wx_login";
        req.transaction=WX_appid;
        wxApi.sendReq(req);
    }
    public void shareWX(String title, String content,String imgUrl, String targetUrl)
    {
        share(title,content,imgUrl,targetUrl,0);
}
    public void shareCircle(String title, String content,String imgUrl, String targetUrl)
    {
        share(title,content,imgUrl,targetUrl,1);
    }
    public void shareWX(String title, String content, int  ImgRes, String targetUrl)
    {
        share(title,content,ImgRes,targetUrl,0);
    }
    public void shareCircle(String title, String content, int  ImgRes, String targetUrl)
    {
        share(title,content,ImgRes,targetUrl,1);
    }
   public void share(String title, String content, String imgUrl, String targetUrl,int shareType)
   {

    /*   ImageSize imageSize=new ImageSize(100,100);//微信分享图片32k 之内  限制为10K  最大为170*170
       Bitmap thumb = ImageLoader.getInstance().loadImageSync(imgUrl,imageSize);*/

  new BitMapThread(title,content,imgUrl,targetUrl,shareType).start();
   }

    public void share(String title, String content, int  ImgRes, String targetUrl,int shareType)
    {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl=targetUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = content;
        Bitmap thumb = BitmapFactory.decodeResource(App.getInstance().getResources(), ImgRes);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = shareType==0? SendMessageToWX.Req.WXSceneSession: SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);
    }
    public class BitMapThread extends Thread
    {

        private String imgUrl,targetUrl,title,content;
        private int shareType;
        public BitMapThread(String title, String content, String imgUrl, String targetUrl,int shareType)
        {
            this.title=title;
            this.content=content;
            this.imgUrl=imgUrl;
            this.shareType=shareType;
            this.targetUrl=targetUrl;
        }
        public void run()
        {
            try {
                URL  url = new URL(imgUrl);
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                long fileLeght=conn.getContentLength();
                BitmapFactory.Options options = new  BitmapFactory.Options();
                Bitmap bitmap;
                if(fileLeght>30000)
               {
                   int scale=(int)(fileLeght/30000)+1;
                   options.inSampleSize=scale;
                  bitmap = BitmapFactory.decodeStream(is,null,options);
               }
                else
                   bitmap = BitmapFactory.decodeStream(is);
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl=targetUrl;
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = title;
                msg.description = content;
                msg.setThumbImage(bitmap);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = shareType==0? SendMessageToWX.Req.WXSceneSession: SendMessageToWX.Req.WXSceneTimeline;
                wxApi.sendReq(req);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
