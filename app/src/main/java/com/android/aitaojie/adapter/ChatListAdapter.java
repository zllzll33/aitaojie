package com.android.aitaojie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.activity.ChatActivity;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.model.ChatModel;
import com.android.zlibrary.adapter.ZModelAdapter;
import com.android.zlibrary.customView.CircleImageView;
import com.android.zlibrary.customView.SwipeLayout;
import com.android.zlibrary.util.ViewUtil;
import com.sunday.eventbus.SDEventManager;

import java.util.List;

/**
 * Created by win7 on 2016/7/23.
 */
public class ChatListAdapter extends ZModelAdapter<ChatModel> {
    public ChatListAdapter(List<ChatModel> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }
    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, ChatModel model) {
        TextView delete=(TextView)convertView.findViewById(R.id.delete);
        TextView unread_num=(TextView)convertView.findViewById(R.id.unread_num);
        if(model.getNum().equals("0"))
        {
            unread_num.setVisibility(View.GONE);
        }
        else
        {
            unread_num.setVisibility(View.VISIBLE);
            unread_num.setText(model.getNum());
        }
        TextView isHomeStore=(TextView)convertView.findViewById(R.id.isHomeStore);
        if(model.getJid().equals(Constant.JID))
            isHomeStore.setVisibility(View.VISIBLE);
        else
            isHomeStore.setVisibility(View.GONE);
        TextView chat_name=(TextView)convertView.findViewById(R.id.chat_name);
        CircleImageView header=(CircleImageView)convertView.findViewById(R.id.header);
        TextView classification=(TextView)convertView.findViewById(R.id.classification);
        TextView addr=(TextView)convertView.findViewById(R.id.addr);
        addr.setText(model.getAddress());
        classification.setText(model.getTrade());
       if(!model.getLogo().isEmpty())
           ViewUtil.setImageView(header,model.getLogo());
        chat_name.setText(model.getMerchant_name());
        SwipeLayout  swipe_layout=(SwipeLayout)convertView.findViewById(R.id.swipe_layout);
        swipe_layout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipe_layout.addDrag(SwipeLayout.DragEdge.Right,delete);
        swipe_layout.getSurfaceView().setOnClickListener(new ZOnClickListener(position,convertView,model) {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity, ChatActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("userId",model.getHx_openid());
                bundle.putString("name",model.getMerchant_name());
                intent.putExtra("user",bundle);
                mActivity.startActivity(intent);
                SDEventManager.post(model.getHx_openid(), EnumEventTag.CHAT_MESSAGE_READ.ordinal());
            }
        });
        delete.setOnClickListener(new ZOnClickListener(position,convertView,model)
        {
            @Override
            public void onClick(View v) {
                SDEventManager.post(model.getHx_openid(), EnumEventTag.DELETE_CHAT.ordinal());
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.item_chat_list;
    }
}
