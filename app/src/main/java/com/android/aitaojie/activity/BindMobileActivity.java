package com.android.aitaojie.activity;

import android.content.Intent;
import android.os.Bundle;

import com.android.aitaojie.MainActivity;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.HttpMap;
import com.android.zlibrary.util.PreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by win7 on 2016/8/4.
 */
public class BindMobileActivity extends SmsLoginActivity{
    private String type,nick,sex,head,id;
    @Override
    protected void Init() {
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("info");
        id=bundle.getString("id");
        type=bundle.getString("type");
        nick=bundle.getString("nick");
        sex=bundle.getString("sex");
        head=bundle.getString("head");
        super.Init();
        setTitle("绑定手机号");
        login.setText("绑定");
    }

    @Override
    protected void Login() {
        HttpMap httpMap=new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("Public");
        httpMap.putAct("bind");
        httpMap.putDataMap("mobile",mobile.getText().toString());
        httpMap.putDataMap("code",SMS.getText().toString());
        if(type.equals("qq"))
        {
            httpMap.putDataMap("type","1");
            httpMap.putDataMap("sex",sex);
        }
        else if(type.equals("wx")) {
            httpMap.putDataMap("type", "2");
            if(sex.equals("0"))
                httpMap.putDataMap("sex","保密");
            else if(sex.equals("1"))
                httpMap.putDataMap("sex","男");
            else if(sex.equals("2"))
                httpMap.putDataMap("sex","女");
        }
        httpMap.putDataMap("id",id);
        httpMap.putDataMap("nick",nick);
        httpMap.putDataMap("head",head);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                try {
                    JSONObject jsonObject=new JSONObject(data);
                    String uid=jsonObject.optString("uid");
                    String jid=jsonObject.optString("jid");
                    Constant.huanxin_name=jsonObject.optString("huanxin_name");
                    Constant.huanxin_pwd=jsonObject.optString("huanxin_pwd");
                    Constant.UID=uid;
                    Constant.JID=jid;
                    PreferenceUtil.putString("UID",uid);
                    PreferenceUtil.putString("JID",jid);
                    PreferenceUtil.putString("huanxin_name",Constant.huanxin_name);
                    PreferenceUtil.putString("huanxin_pwd",Constant.huanxin_pwd);
                    Intent intent =new Intent(BindMobileActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
