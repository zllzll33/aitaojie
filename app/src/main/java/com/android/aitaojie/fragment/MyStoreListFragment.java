package com.android.aitaojie.fragment;

import android.util.Log;
import android.widget.GridView;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.MyStoreAdapter;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.ChatModel;
import com.android.aitaojie.model.MyColloctStoreModel;
import com.android.zlibrary.fragment.ZBaseFragment;
import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.ViewUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.sunday.eventbus.SDBaseEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/28.
 */
public class MyStoreListFragment extends ZBaseFragment {
    @InjectView(R.id.grid_store)
    PullToRefreshGridView gridStore;
    List<MyColloctStoreModel> myColloctStoreModels;
    MyStoreAdapter myStoreAdapter;
    int page=1;
    @Override
    protected int layoutId() {
        return R.layout.frag_my_store;
    }

    @Override
    protected void Init() {
        super.Init();
        gridStore.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        gridStore.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                httpStore();
            }
        });
        httpStore();

    }
    public void httpStore()
    {
        HttpMap httpMap=new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("collect");
        httpMap.putAct("merchant");
        httpMap.putDataMap("uid", Constant.UID);
        httpMap.putDataMap("page",String.valueOf(page));
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                if(!data.isEmpty()) {
                    Gson gson = new Gson();
                     myColloctStoreModels = gson.fromJson(data, new TypeToken<List<MyColloctStoreModel>>() {
                    }.getType());
                    if(page==1) {
                        myStoreAdapter = new MyStoreAdapter(myColloctStoreModels, getActivity());
                        gridStore.setAdapter(myStoreAdapter);
                        page++;
                    }
                    else
                    {
                        gridStore.onRefreshComplete();
                        if(myColloctStoreModels==null)
                            return;
                        if(myColloctStoreModels.size()==0)
                            return;
                        myStoreAdapter.addMoreList(myColloctStoreModels);
                        page++;
                    }
                }
                else
                {
                    ViewUtil.showToast(ResourceUtil.getString(R.string.no_more_toast));
                    gridStore.onRefreshComplete();
                }

            }
        });

    }
    @Override
    public void onEventMainThread(SDBaseEvent event)
    {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt()))
        {
            case REFRESH_COLLECT_STORE_LIST:
                myColloctStoreModels.clear();
                myStoreAdapter.notifyDataSetChanged();
                httpStore();
                break;
        }
    }
}
