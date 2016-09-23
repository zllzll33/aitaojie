package com.android.aitaojie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.aitaojie.MainActivity;
import com.android.aitaojie.R;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.share.ZQQShare;
import com.android.aitaojie.share.ZWXShare;
import com.android.zlibrary.util.PreferenceUtil;
import com.android.zlibrary.util.RegUtil;
import com.android.zlibrary.util.ViewUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/21.
 */
public class LoginActivity extends BaseActivity {
    @InjectView(R.id.accout)
    EditText accout;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.register)
    TextView register;
    @InjectView(R.id.forget)
    TextView forget;
    @InjectView(R.id.login)
    TextView login;
    @InjectView(R.id.QQ_login)
    LinearLayout QQLogin;
    @InjectView(R.id.WX_login)
    LinearLayout WXLogin;
    @InjectView(R.id.SMS_login)
    TextView SMSLogin;
    private int LoginType ,QQ=4,WX=5;
    @Override
    protected int layoutId() {
        return R.layout.act_login;
    }
    @Override
    protected void Init() {
        super.Init();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accout.getText().toString().isEmpty())
                {
                    ViewUtil.showToast("请输入手机号");
                    return;
                }
                if(!RegUtil.isMobile(accout.getText().toString()))
                {
                ViewUtil.showToast("请输入正确的手机号");
                    return;
                }
                if(password.getText().toString().isEmpty())
                {
                    ViewUtil.showToast("请输入密码");
                    return;
                }
                HttpMap httpMap=new HttpMap();
                httpMap.putMode("User");
                httpMap.putCtl("Public");
                httpMap.putAct("cklogin");
                httpMap.putDataMap("account",accout.getText().toString());
                httpMap.putDataMap("password",password.getText().toString());
                httpMap.setHttpListener(new HttpMap.HttpListener() {
                    @Override
                    public void onSuccess(String response, String data) {
                        ViewUtil.hideHtttpProgress();
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
                            ChatLogin();
                            Intent intent =new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      /*  Constant.UID=data;
                        PreferenceUtil.putString("UID",data);*/


                    }
                });
                ViewUtil.showHtttpProgress();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgetPWActivity.class);
                startActivity(intent);
            }
        });
        SMSLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SmsLoginActivity.class);
                startActivity(intent);
            }
        });
        QQLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginType=QQ;
                ZQQShare.getInstance().LoginQQ(LoginActivity.this);
            }
        });
        WXLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZWXShare.getInstance().LoginWX();
            }
        });
    }
      @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          if(LoginType==QQ)
          {
              LoginType=0;
              ZQQShare.getInstance().onActivityResultData(requestCode,resultCode,data);
          }

    }

    private void ChatLogin()
    {

//                            EMClient.getInstance().logout(true);
        EMClient.getInstance().login(Constant.huanxin_name, Constant.huanxin_pwd, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
//                                    EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });

    }

}
