package com.android.zlibrary.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zlibrary.R;
import com.android.zlibrary.model.TextIconModel;
import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.TypeUtil;
import com.android.zlibrary.util.ViewUtil;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/6/6.
 */
public class ZTextIconAdapter extends ZModelAdapter<TextIconModel> {
    public  ImageView iv;
    public TextView tv;
    public ZTextIconAdapter(List<TextIconModel> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }
    @Override
    protected void BindModel(final int position,final View convertView, ViewGroup parent,final TextIconModel model) {
        iv=(ImageView)convertView.findViewById(R.id.iv);
        if(model.getUrl()!=null)
            ViewUtil.setImageView(iv,model.getUrl());
        else
        iv.setImageResource(model.getIconRes());
        tv=(TextView)convertView.findViewById(R.id.tv);
        tv.setText(model.getText());

    }

    @Override
    protected int layoutId() {
        return R.layout.item_texticon;
    }
}
