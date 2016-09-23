package com.android.aitaojie.custom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.ChatModel;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by win7 on 2016/8/10.
 */
public class UpChatListBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.e("BroadcastReceiver","UpChatListBroadcastReceiver");
        List<ChatModel> chatModelList=(List<ChatModel>)intent.getSerializableExtra("chat_list");
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("live");
        httpMap.putCtl("index");
        httpMap.putAct("updatelist");
        httpMap.putDataMap("uid", Constant.UID);
        Gson gson=new Gson();
        String  chatlistStr=   gson.toJson(chatModelList);
//        Log.e("chatlistStr",chatlistStr);
        httpMap.putDataArray("list",chatlistStr);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {

            }
        });
    }
}
