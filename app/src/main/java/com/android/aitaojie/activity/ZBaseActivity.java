package com.android.aitaojie.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.android.aitaojie.R;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.base.ZFragmentManager;
import com.android.zlibrary.util.ResourceUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;
import com.sunday.eventbus.SDEventObserver;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;

/**
 * Created by win7 on 2016/5/25.
 */
public abstract class ZBaseActivity extends AutoLayoutActivity implements SDEventObserver {
    private ZFragmentManager mFragmentManager;
    protected Handler zHandler;
    protected int sysBarColor=0xffFF8403;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBeforeView();
        setContentView(layoutId());
        SDEventManager.register(this);
        ButterKnife.inject(this);
        ZActivityManager.getInstance().addActivity(this);
        initHandler();
        Init();
    }
    protected void initBeforeView()
    {
        sysBarColor= ResourceUtil.getColor(R.color.mainColor);
    }

    protected void InitSysBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(0x00ffffff);
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    @Override
    protected void onResume() {

        super.onResume();
    }

    protected   abstract    int layoutId();
    protected  void Init()
    {

    }
    @Override
    public void onBackPressed() {
        finish();
    }
    private void initHandler()
    {
        zHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
             /*   switch (msg.what)
                {
                    case 1:

                        break;
                    default:break;
                }*/
                zHandlerMessage(msg,msg.what);
                super.handleMessage(msg);
            }
        };
    }
    public ZFragmentManager getZFragmentManager()
    {

        if (mFragmentManager == null)
        {
            mFragmentManager = new ZFragmentManager(getSupportFragmentManager());
        }
        return mFragmentManager;
    }
    @Override
    protected void onDestroy() {
        ButterKnife.reset(this);
        ZActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }
    protected  void zHandlerMessage(Message msg,int id)
    {
    }
    @Override
    public void onEvent(SDBaseEvent event)
    {

    }

    @Override
    public void onEventMainThread(SDBaseEvent event)
    {

    }

    @Override
    public void onEventBackgroundThread(SDBaseEvent event)
    {

    }

    @Override
    public void onEventAsync(SDBaseEvent event)
    {

    }

}
