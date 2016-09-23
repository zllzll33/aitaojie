package com.android.aitaojie.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.android.aitaojie.R;
import com.android.aitaojie.config.Constant;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;

/**
 * Created by win7 on 2016/7/23.
 */
public class ChatActivity extends BaseActivity {
    EaseChatFragment chatFragment;
    @Override
    protected int layoutId() {
        return R.layout.act_chat;
    }

    @Override
    protected void Init() {
        super.Init();
        Intent intent=getIntent();
       Bundle user_bundle= intent.getBundleExtra("user");
        String userId=user_bundle.getString("userId");
        String name=user_bundle.getString("name");
        setTitle(name);
        Constant.isChattionId=userId;
       chatFragment = new EaseChatFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        bundle.putString(EaseConstant.EXTRA_USER_ID, userId);
        chatFragment.setArguments(bundle);
        getZFragmentManager().replace(R.id.chat,chatFragment);
    }

    @Override
    public void onDestroy()
    {
        Constant.isChattionId="";
        super.onDestroy();
    }
}
