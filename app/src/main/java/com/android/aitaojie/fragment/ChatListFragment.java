package com.android.aitaojie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.ChatListAdapter;
import com.android.aitaojie.model.ChatModel;
import com.android.zlibrary.fragment.ZBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/23.
 */
public class ChatListFragment extends ZBaseFragment {
    @InjectView(R.id.chat_list)
    GridView chatGrid;
    private List<ChatModel> chatList;
    private  ChatListAdapter chatListAdapter;
    @Override
    protected int layoutId() {
        return R.layout.frag_chat_list;
    }
    public void setChatList(List<ChatModel> chatList) {
        this.chatList=null;
        this.chatList = chatList;
        if(chatListAdapter!=null)
        {
            chatListAdapter=null;
            chatListAdapter = new ChatListAdapter(this.chatList, getActivity());
            chatGrid.setAdapter(chatListAdapter);
        }
    }
    @Override
    protected void Init() {
        super.Init();
        chatListAdapter = new ChatListAdapter(this.chatList, getActivity());
        chatGrid.setAdapter(chatListAdapter);
    }
}
