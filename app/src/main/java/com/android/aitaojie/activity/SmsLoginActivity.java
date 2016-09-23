package com.android.aitaojie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.aitaojie.MainActivity;
import com.android.aitaojie.R;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.HttpMap;
import com.android.zlibrary.util.PreferenceUtil;
import com.android.zlibrary.util.RegUtil;
import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.ViewUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/21.
 */
public class SmsLoginActivity extends BaseActivity {
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.mobile)
    EditText mobile;
    @InjectView(R.id.getSMS)
    TextView getSMS;
    @InjectView(R.id.SMS)
    EditText SMS;
    @InjectView(R.id.login)
    TextView login;
    private int  Sms_time=0;
    private android.os.Handler handler;
    private TimerTask  task;
    @Override
    protected int layoutId() {
        return R.layout.act_sms_login;
    }

    @Override
    protected void Init() {
        super.Init();
        setTitle("手机验证码登录");
        handler=new android.os.Handler(){
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    if(Sms_time>0)
                    {
                        Sms_time--;
                        getSMS.setText(String.valueOf(Sms_time)+"s");
                    }
                    if(Sms_time==0)
                    {
                        getSMS.setText("获取验证码");
                        getSMS.setTextColor(0xff8C8C8C);
                    }
                }
                super.handleMessage(msg);
            };
        };
        getSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobile.getText().toString().isEmpty())
                {
                    ViewUtil.showToast("请输入手机号");
                    return;
                }
                if(!RegUtil.isMobile(mobile.getText().toString()))
                {
                    ViewUtil.showToast("请输入正确的手机号");
                    return;
                }
                if(Sms_time==0) {
                    Sms_time=60;
                    Timer timer = new Timer();
                     task = new TimerTask() {

                        @Override
                        public void run() {
                            handler.sendEmptyMessage(1);
                        }
                    };
                    getSMS.setText("60s");
                    getSMS.setTextColor(ResourceUtil.getColor(R.color.mainColor));
                    timer.schedule(task, 1000, 1000); // 1s后执行task,经过1s再次执行
                    getSms();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mobile.getText().toString().isEmpty())
                {
                    ViewUtil.showToast("请输入手机号");
                    return;
                }
                if(!RegUtil.isMobile(mobile.getText().toString()))
                {
                    ViewUtil.showToast("请输入正确的手机号");
                    return;
                }
                if(SMS.getText().toString().isEmpty())
                {
                    ViewUtil.showToast("请输入验证码");
                    return;
                }
                Login();
            }
        });
    }

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
    protected   void Login()
    {
        HttpMap httpMap=new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("Public");
        httpMap.putAct("ckqlogin");
        httpMap.putDataMap("mobile",mobile.getText().toString());
        httpMap.putDataMap("code",SMS.getText().toString());
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
                    Intent intent =new Intent(SmsLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
    @Override
    protected void onDestroy() {
        if(task!=null)
        task.cancel();
        super.onDestroy();
    }
}
