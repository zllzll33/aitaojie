package com.android.aitaojie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.activity.GoodWebDetailActiivty;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.model.MyIndexModel;
import com.android.zlibrary.adapter.ZModelAdapter;
import com.android.zlibrary.customView.CircleImageView;
import com.android.zlibrary.customView.ZImageView;
import com.android.zlibrary.util.ViewUtil;

import java.util.List;

/**
 * Created by win7 on 2016/7/25.
 */
public class LittleFootPrintAdapter  extends ZModelAdapter<MyIndexModel.GoodsBean>{
    public LittleFootPrintAdapter(List<MyIndexModel.GoodsBean> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }
    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, MyIndexModel.GoodsBean model) {
        ZImageView  good_img=(ZImageView)convertView.findViewById(R.id.good_img);
        CircleImageView header=(CircleImageView)convertView.findViewById(R.id.header);
       TextView price=(TextView)convertView.findViewById(R.id.price);
        TextView good_name=(TextView)convertView.findViewById(R.id.good_name);
        ViewUtil.setImageView(good_img,model.getGimg());
        if(!model.getMerchant_logo().isEmpty())
        ViewUtil.setImageView(header,model.getMerchant_logo());
        price.setText("￥"+model.getGdprice());
        good_name.setText(model.getGname());
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
        return R.layout.item_little_foot_print;
    }
}
