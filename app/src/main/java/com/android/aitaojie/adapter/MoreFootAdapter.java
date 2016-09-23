package com.android.aitaojie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.activity.GoodWebDetailActiivty;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.model.MyFootModel;
import com.android.zlibrary.adapter.ZModelAdapter;
import com.android.zlibrary.util.ViewUtil;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/25.
 */
public class MoreFootAdapter extends ZModelAdapter<MyFootModel> {


    public MoreFootAdapter(List<MyFootModel> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }

    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, MyFootModel model) {
        ImageView iv=(ImageView)convertView.findViewById(R.id.iv);
        TextView des=(TextView)convertView.findViewById(R.id.des);
        TextView price=(TextView)convertView.findViewById(R.id.price);
        TextView time=(TextView)convertView.findViewById(R.id.time);
        ViewUtil.setImageView(iv,model.getGimg());
        des.setText(model.getGname());
        price.setText("￥"+model.getGoprice());
        time.setText(model.getSet_time());
        convertView.setOnClickListener(new ZOnClickListener(position,convertView,model)
        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity, GoodWebDetailActiivty.class);
                String weburl=String.format(Constant.GoodDetaiUrl,model.getGid());
                intent.putExtra("webUrl",weburl);
                intent.putExtra("gid",Integer.parseInt(model.getGid()));
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.item_more_foot;
    }
}
