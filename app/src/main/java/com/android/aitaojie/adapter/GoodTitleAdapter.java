package com.android.aitaojie.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.model.ClassifyGoodListModel;
import com.android.zlibrary.adapter.ZNaviAdapter;


import java.util.List;

/**
 * Created by win7 on 2016/7/20.
 */
public class GoodTitleAdapter extends ZNaviAdapter<ClassifyGoodListModel.ClassBean> {
    public GoodTitleAdapter(List<ClassifyGoodListModel.ClassBean> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_good_title;
    }

    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, ClassifyGoodListModel.ClassBean model) {
        super.BindModel(position, convertView, parent, model);
    }

    @Override
    protected void Noraml(View view, ClassifyGoodListModel.ClassBean model, int position) {
        TextView tv=(TextView)view.findViewById(R.id.tv);
        tv.setTextColor(0xff000000);
        tv.setText(model.getCname());
    }
    @Override
    protected void Select(View view, ClassifyGoodListModel.ClassBean model, int position) {
        TextView tv=(TextView)view.findViewById(R.id.tv);
        tv.setTextColor(0xffff0000);
    }
    public static class ZOnClickListener implements View.OnClickListener {
        public  int position;
        public String model;
        public View view;
        public ZOnClickListener(int position, View view, String model)
        {
            this.model=model;
            this.position=position;
            this.view=view;
        }
        @Override
        public void onClick(View v) {

        }
    }
}
