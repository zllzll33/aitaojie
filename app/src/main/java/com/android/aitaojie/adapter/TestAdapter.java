package com.android.aitaojie.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.android.aitaojie.R;
import com.android.zlibrary.adapter.ZModelAdapter;


import java.util.List;

/**
 * Created by win7 on 2016/7/19.
 */
public class TestAdapter extends ZModelAdapter<String> {
    public TestAdapter(List<String> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }

    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, String model) {

    }

    @Override
    protected int layoutId() {
        return R.layout.item_test;
    }
}
