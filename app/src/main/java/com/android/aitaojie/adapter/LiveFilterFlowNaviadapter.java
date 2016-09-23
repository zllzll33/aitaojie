package com.android.aitaojie.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.model.LiveModel;
import com.android.zlibrary.adapter.ZNaviAdapter;
import com.android.zlibrary.custom.ZGradientDrawable;

import java.util.List;

/**
 * Created by win7 on 2016/8/6.
 */
public class LiveFilterFlowNaviadapter extends ZNaviAdapter<LiveModel.FilterBean.ItemBean> {
    public LiveFilterFlowNaviadapter(List<LiveModel.FilterBean.ItemBean> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }
    @Override
    protected int layoutId() {
        return R.layout.item_live_filter_flow_navi;
    }

    @Override
    protected void Noraml(View convertView, LiveModel.FilterBean.ItemBean model, int position) {
        TextView tv=(TextView) convertView.findViewById(R.id.navi);
        tv.setTextColor(0xff838383);
        tv.setText(model.getLabel());
        ZGradientDrawable zGradientDrawable=new ZGradientDrawable();
        zGradientDrawable.setCornerDp(5);
        zGradientDrawable.setStrokeDp(1,0xffc3c3c3);
        tv.setPadding(15,10,15,10);
        tv.setBackground(zGradientDrawable);
    }

    @Override
    protected void Select(View convertView, LiveModel.FilterBean.ItemBean model, int position) {
        TextView tv=(TextView) convertView.findViewById(R.id.navi);
        tv.setTextColor(0xffffffff);
        tv.setText(model.getLabel());
        ZGradientDrawable zGradientDrawable=new ZGradientDrawable();
        zGradientDrawable.setCornerDp(5);
        zGradientDrawable.setStrokeDp(1,0xffc3c3c3);
        zGradientDrawable.setSolidColor(0xffff0000);
        tv.setPadding(15,10,15,10);
        tv.setBackground(zGradientDrawable);
    }
}
