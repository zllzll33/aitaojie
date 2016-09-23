package com.android.aitaojie.activity;

import com.android.aitaojie.config.HttpMap;
import com.android.zlibrary.util.ViewUtil;

/**
 * Created by win7 on 2016/8/6.
 */
public class ChangePWActivity extends RegisterActivity {
    @Override
    protected void Init() {
        super.Init();
        setTitle("修改密码");
        register.setText("确定修改");
    }
    @Override
    protected void getSms() {
        HttpMap httpMap=new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("Public");
        httpMap.putAct("sendsms");
        httpMap.putDataMap("mobile",mobile.getText().toString());
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
            }
        });
    }
    @Override
    protected void comfirm() {
        HttpMap httpMap=new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("account");
        httpMap.putAct("changePwd");
        httpMap.putDataMap("account",mobile.getText().toString());
        httpMap.putDataMap("m_password",comfirmPw.getText().toString());
        httpMap.putDataMap("code",SMS.getText().toString());
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                ViewUtil.hideHtttpProgress();
            }
        });
        ViewUtil.showHtttpProgress();
    }
}
