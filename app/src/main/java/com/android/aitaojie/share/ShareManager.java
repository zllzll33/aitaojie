package com.android.aitaojie.share;

import android.os.Handler;
import android.os.Message;
import android.view.View;


import com.android.aitaojie.R;
import com.android.zlibrary.adapter.ZTextIconAdapter;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.dialog.ZDialogMenu;
import com.android.zlibrary.model.TextIconModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2016/6/20.
 */
public class ShareManager {
   private static ShareManager  shareManager;
    private  String QQAppid="1104850635";
    public  String WX_appid="wx80465530f6ade4ce";
    private String SinaAppId="2366712195";
    private List<TextIconModel> shareList;
    private ZTextIconAdapter shareAdapter;
    private ZDialogMenu zDialogMenu;
  private  String title, content, imgUrl, targetUrl;
    private Handler handler;
    public static ShareManager getInstance()
    {
        if(shareManager==null)
            shareManager=new ShareManager();
        return shareManager;
    }
    public ShareManager()
    {
//        ZQQShare.getInstance().setQQAppId(QQAppid);
//        ZWXShare.getInstance().setWXAppid(WX_appid);
//        ZSinaShare.getInstance().setSinaAppId(SinaAppId);
        shareList=new ArrayList<TextIconModel>();
        TextIconModel weixin=new TextIconModel(R.mipmap.umeng_socialize_wechat,"微信");
        shareList.add(weixin);
        TextIconModel weixin_circle=new TextIconModel(R.mipmap.umeng_socialize_wxcircle,"朋友圈");
        shareList.add(weixin_circle);
        TextIconModel qq= new TextIconModel(R.mipmap.umeng_socialize_qq_on,"QQ");
        shareList.add(qq);
        TextIconModel qqZone= new TextIconModel(R.mipmap.umeng_socialize_qzone_on,"QQ空间");
        shareList.add(qqZone);
/*        TextIconModel sina= new TextIconModel(R.mipmap.umeng_socialize_sina_on,"sina微博");
        shareList.add(sina);*/
        handler=new Handler()
        {
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case 0:
                        ZWXShare.getInstance().shareWX(title,content,imgUrl,targetUrl);
                        break;
                    case 1:
                        ZWXShare.getInstance().shareCircle(title,content,imgUrl,targetUrl);
                        break;
                    case 2:
                        ZQQShare.getInstance().ShareQQ(title,content,imgUrl,targetUrl);
                        break;
                    case 3:
                        ZQQShare.getInstance().ShareQzone(title,content,imgUrl,targetUrl);
                        break;
                    case 4:
                        ZSinaShare.getInstance().SinaShare(title,content,imgUrl,targetUrl);
                        break;
                }
            }
        };
    }
    public void ShowshareMenu(String title, String content,String imgUrl, String targetUrl){
        this.title=title;
        this.content=content;
        this.imgUrl=imgUrl;
        this.targetUrl=targetUrl;

        shareAdapter=new ZTextIconAdapter(shareList, ZActivityManager.getInstance().getLastActivity());
        zDialogMenu=    new ZDialogMenu(ZActivityManager.getInstance().getLastActivity(),com.android.zlibrary.R.style.BlackDialog).setGridColumn(4).setzMenuDialogListener(new ZDialogMenu.ZMenuDialogListener() {
            @Override
            public void onCancelClick(View v, ZDialogMenu dialog) {

            }

            @Override
            public void onItemClick(View v, int index, ZDialogMenu dialog) {
              handler.sendEmptyMessage(index) ;

            }
        });
        zDialogMenu.setAdapter(shareAdapter).showDialog();
    }
}
