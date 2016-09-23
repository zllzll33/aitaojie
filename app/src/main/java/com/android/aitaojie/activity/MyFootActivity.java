package com.android.aitaojie.activity;

import android.os.Bundle;
import android.widget.GridView;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.MoreFootAdapter;
import com.android.aitaojie.adapter.MyGoodsAdapter;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.MyFootModel;
import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.ViewUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/25.
 */
public class MyFootActivity extends BaseActivity {
    @InjectView(R.id.grid_foot)
    PullToRefreshGridView gridFoot;
    int page=1;
    MoreFootAdapter moreFootAdapter;
    List<MyFootModel> footModels;
    @Override
    protected int layoutId() {
        return R.layout.act_my_foot;
    }
    @Override
    protected void Init() {
        super.Init();
        setTitle("我的足迹");
        gridFoot.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        gridFoot.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                httpFoot();

            }
        });
        httpFoot();

    }
public void httpFoot()
{
    HttpMap httpMap = new HttpMap();
    httpMap.putMode("User");
    httpMap.putCtl("footPrint");
    httpMap.putAct("index");
    httpMap.putDataMap("uid", Constant.UID);
    httpMap.putDataMap("page", String.valueOf(page));
    httpMap.setHttpListener(new HttpMap.HttpListener() {
        @Override
        public void onSuccess(String response, String data) {
            if(!data.isEmpty())
            {
                Gson gson = new Gson();
                footModels = gson.fromJson(data, new TypeToken<List<MyFootModel>>() {
                }.getType());
                if(page==1) {
                    moreFootAdapter = new MoreFootAdapter(footModels, MyFootActivity.this);
                    gridFoot.setAdapter(moreFootAdapter);
                    page++;
                }
                else
                {
                    gridFoot.onRefreshComplete();
                    if(footModels==null)
                        return;
                    if(footModels.size()==0)
                        return;
                    moreFootAdapter.addMoreList(footModels);
                    page++;
                }
            }
            else
            {
                ViewUtil.showToast(ResourceUtil.getString(R.string.no_more_toast));
                gridFoot.onRefreshComplete();
            }
        }
    });

}

}
