package com.android.aitaojie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.MyGoodsAdapter;
import com.android.aitaojie.adapter.MyStoreAdapter;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.MyCollectGoodModel;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/28.
 */
public class MyGoodsFragment extends ZBaseFragment {
    @InjectView(R.id.grid_goods)
    PullToRefreshGridView gridGoods;
    List<MyCollectGoodModel> myCollectGoodModels;
    MyGoodsAdapter myGoodsAdapter;
   int  page=1;
    @Override
    protected int layoutId() {
        return R.layout.frag_my_goods;
    }
    @Override
    protected void Init() {
        super.Init();
        page=1;
        gridGoods.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        gridGoods.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                httpGood();
            }
        });
        httpGood();
    }
    public void httpGood()
    {
        HttpMap httpMap=new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("collect");
        httpMap.putAct("goods");
        httpMap.putDataMap("uid", Constant.UID);
        httpMap.putDataMap("page",String.valueOf(page));
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {

                if(!data.isEmpty()) {
                    Gson gson = new Gson();
                    myCollectGoodModels = gson.fromJson(data, new TypeToken<List<MyCollectGoodModel>>() {
                    }.getType());
                    if(page==1) {
                        myGoodsAdapter = new MyGoodsAdapter(myCollectGoodModels, getActivity());
                        gridGoods.setAdapter(myGoodsAdapter);
                        page++;
                    }
                    else
                    {
                        gridGoods.onRefreshComplete();
                        if(myCollectGoodModels==null)
                            return;
                        if(myCollectGoodModels.size()==0)
                            return;
                        myGoodsAdapter.addMoreList(myCollectGoodModels);
                        page++;
                    }
                }
            else
                {
                    ViewUtil.showToast(ResourceUtil.getString(R.string.no_more_toast));
                    gridGoods.onRefreshComplete();
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
            case REFRESH_COLLECT_GOOD_LIST:
                myCollectGoodModels.clear();
                myGoodsAdapter.notifyDataSetChanged();
                httpGood();
                break;
        }
    }
}
