package com.android.aitaojie.activity;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.config.HttpMap;
import com.android.zlibrary.util.RegUtil;
import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.ViewUtil;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/21.
 */
public class RegisterActivity extends BaseActivity {
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
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.comfirm_pw)
    EditText comfirmPw;
    @InjectView(R.id.register)
    TextView register;
  private int  Sms_time=0;
    private android.os.Handler handler;
    protected boolean isRegister=true;
    private  TimerTask task;
    @Override
    protected int layoutId() {
        return R.layout.act_register;
    }

    @Override
    protected void Init() {
        super.Init();
        setTitle("注册");
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
                    Timer timer = new Timer();
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            handler.sendEmptyMessage(1);
                        }
                    };
                    Sms_time=60;
                    getSMS.setText("60s");
                    getSMS.setTextColor(ResourceUtil.getColor(R.color.mainColor));
                    timer.schedule(task, 1000, 1000); // 1s后执行task,经过1s再次执行
                    getSms();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
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
                if(password.getText().toString().isEmpty())
                {
                    ViewUtil.showToast("请输入密码");
                    return;
                }

                if(comfirmPw.getText().toString().isEmpty())
                {
                    ViewUtil.showToast("请输入确认密码");
                    return;
                }
                if(!comfirmPw.getText().toString().equals(password.getText().toString()))
                {
                    ViewUtil.showToast("请输入相同的密码");
                    return;
                }
                comfirm();
            }
        });
    }
 protected  void getSms()
 {
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
    protected void comfirm()
    {
        HttpMap httpMap=new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("Public");
        httpMap.putAct("ckreg");
        httpMap.putDataMap("mobile",mobile.getText().toString());
        httpMap.putDataMap("password",comfirmPw.getText().toString());
        httpMap.putDataMap("code",SMS.getText().toString());
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                ViewUtil.hideHtttpProgress();
                finishActivity();
            }
        });
        ViewUtil.showHtttpProgress();
    }

    @Override
    protected void onDestroy() {
        if(task!=null)
        task.cancel();
        super.onDestroy();
    }
}
