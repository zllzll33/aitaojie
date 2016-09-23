package com.android.aitaojie.activity;

import android.os.Bundle;
import android.widget.GridView;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.MessageAdapter;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.MessageModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/8/12.
 */
public class MessageActivity extends BaseActivity {
    @InjectView(R.id.grid_message)
    GridView gridMessage;

    @Override
    protected int layoutId() {
        return R.layout.act_message;
    }

    @Override
    protected void Init() {
        super.Init();
        setTitle("消息");
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("Merchant");
        httpMap.putCtl("Message");
        httpMap.putAct("index");
        httpMap.putDataMap("uid", Constant.UID);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                Gson gson=new Gson();
                List<MessageModel> messageModels=  gson.fromJson(data,new TypeToken<List<MessageModel>>(){}.getType());
                if(messageModels==null)
                    return;
                MessageAdapter messageAdapter = new MessageAdapter(messageModels, MessageActivity.this);
                gridMessage.setAdapter(messageAdapter);
            }
        });
    }
}
