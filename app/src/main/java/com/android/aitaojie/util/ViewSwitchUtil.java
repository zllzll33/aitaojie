package com.android.aitaojie.util;

import android.content.Intent;
import android.util.Log;

import com.android.aitaojie.activity.GoodDetailActivity;
import com.android.aitaojie.activity.GoodListActivity;
import com.android.aitaojie.activity.WebViewActivity;
import com.android.aitaojie.activity.WelcomeActivity;
import com.android.aitaojie.config.Constant;
import com.android.zlibrary.base.ZActivityManager;

/**
 * Created by win7 on 2016/8/11.
 */
public class ViewSwitchUtil {
    /* 页面跳转type*/
    public static final  String GoodList ="1";   //商品列表
    public static final String Appointment ="2";  //预约
    public static void ViewSwicth(String url,String pk)
    {
         Log.e("swithc_url",url);
        if(url.contains("goods")&&url.contains("index"))
        {
            Intent intent=new Intent(ZActivityManager.getInstance().getLastActivity(),GoodListActivity.class);
            ZActivityManager.getInstance().getLastActivity().startActivity(intent);
        }
        else if(url.contains("goods")&&url.contains("view"))
        {
            Intent intent=new Intent(ZActivityManager.getInstance().getLastActivity(),GoodDetailActivity.class);
//            String weburl=String.format(Constant.GoodDetaiUrl,Constant.UID);
//            intent.putExtra("webUrl",url);
            ZActivityManager.getInstance().getLastActivity().startActivity(intent);
        }
    }
}
