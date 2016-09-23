package com.android.aitaojie;

import android.widget.GridView;

import com.android.aitaojie.activity.BaseActivity;
import com.android.refresh.MaterialRefreshLayout;
import com.android.zlibrary.adapter.ZSimpleTextAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/8/12.
 */
public class TestActivty extends BaseActivity {
    @InjectView(R.id.grid)
    GridView grid;
    @InjectView(R.id.swipe)
    MaterialRefreshLayout swipe;
    @Override
    protected int layoutId() {
        return R.layout.act_test;
    }

    @Override
    protected void Init() {
        super.Init();
        swipe.setLoadMore(true);
//        swipe.setIsOverLay(true);
        List<String> list=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            list.add("item:"+String.valueOf(i));
        }
        ZSimpleTextAdapter<String> simpleTextAdapter=new ZSimpleTextAdapter<>(list,TestActivty.this);
        grid.setAdapter(simpleTextAdapter);
    }
}
