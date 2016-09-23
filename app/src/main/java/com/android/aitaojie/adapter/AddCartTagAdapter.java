package com.android.aitaojie.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.model.AddCartModel;
import com.android.aitaojie.model.LiveModel;
import com.android.zlibrary.adapter.ZNaviAdapter;
import com.android.zlibrary.adapter.ZNaviGridAdapter;
import com.android.zlibrary.custom.ZGradientDrawable;

import java.util.List;

/**
 * Created by win7 on 2016/8/19.
 */
public class AddCartTagAdapter extends ZNaviAdapter<AddCartModel.SpceBean.ItemsBean> {
    public AddCartTagAdapter(List<AddCartModel.SpceBean.ItemsBean> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }
    @Override
    protected int layoutId() {
        return R.layout.item_live_filter_flow_navi;
    }

    @Override
    protected void Noraml(View convertView, AddCartModel.SpceBean.ItemsBean model, int position) {
        TextView tv=(TextView) convertView.findViewById(R.id.navi);
        tv.setTextColor(0xff333333);
        tv.setText(model.getName());
        ZGradientDrawable zGradientDrawable=new ZGradientDrawable();
        zGradientDrawable.setCornerDp(5);
        zGradientDrawable.setStrokeDp(1,0xffc3c3c3);
        tv.setPadding(15,10,15,10);
        tv.setBackground(zGradientDrawable);
    }

    @Override
    protected void Select(View convertView, AddCartModel.SpceBean.ItemsBean model, int position) {
        TextView tv=(TextView) convertView.findViewById(R.id.navi);
        tv.setTextColor(0xffffffff);
        tv.setText(model.getName());
        ZGradientDrawable zGradientDrawable=new ZGradientDrawable();
        zGradientDrawable.setCornerDp(5);
        zGradientDrawable.setStrokeDp(1,0xffc3c3c3);
        zGradientDrawable.setSolidColor(0xffff0000);
        tv.setPadding(15,10,15,10);
        tv.setBackground(zGradientDrawable);
    }
    public static class   onTagClickListener implements View.OnClickListener{
        protected int index;
        protected View view;
        protected   AddCartModel.SpceBean.ItemsBean Itemmodel;
        public onTagClickListener(int index,View view,AddCartModel.SpceBean.ItemsBean Itemmodel)
        {
            this.index=index;
            this.view=view;
            this.Itemmodel=Itemmodel;
        }
        @Override
        public void onClick(View v) {

        }
    }
}
