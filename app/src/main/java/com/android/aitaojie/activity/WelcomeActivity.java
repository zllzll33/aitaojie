package com.android.aitaojie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.android.aitaojie.MainActivity;
import com.android.aitaojie.R;
import com.android.aitaojie.bmap.BMapManager;
import com.android.aitaojie.config.App;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.zlibrary.util.PreferenceUtil;
import com.android.zlibrary.util.SysUtil;
import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.sunday.eventbus.SDEventManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/8/4.
 */
public class WelcomeActivity extends ZBaseActivity {
    String uid;
    @Override
    protected void initBeforeView() {
        super.initBeforeView();
        InitSysBar();
    }

    @Override
    protected int layoutId() {
        return R.layout.act_welcome;
    }
    @Override
    protected void Init() {
        super.Init();
        uid = PreferenceUtil.getString("UID", "");
            Log.e("UID", uid);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean firstIn = PreferenceUtil.getBoolean("firstIn", true);
                    if (firstIn) {
                        Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
                        startActivity(intent);
                    } else {
                        if (!uid.isEmpty()) {
                            Constant.UID = uid;
                            Constant.JID=PreferenceUtil.getString("JID","1");
                            Constant.huanxin_name=PreferenceUtil.getString("huanxin_name","");
//                            Log.e("huanxin_name",Constant.huanxin_name);
                            Constant.huanxin_pwd=PreferenceUtil.getString("huanxin_pwd","");
                            ChatLogin();
                            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }, 1000);


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
