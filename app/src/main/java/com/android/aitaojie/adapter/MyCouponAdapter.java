package com.android.aitaojie.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.aitaojie.R;
import com.android.zlibrary.adapter.ZModelAdapter;

import java.util.List;

/**
 * Created by win7 on 2016/7/25.
 */
public class MyCouponAdapter extends ZModelAdapter<String>{
    public MyCouponAdapter(List<String> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }
    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, String model) {
        LinearLayout ll_coupon_title=(LinearLayout)convertView.findViewById(R.id.ll_coupon_title);
       if(position==0)
           ll_coupon_title.setVisibility(View.VISIBLE);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_my_coupon;
    }
}
