package com.android.aitaojie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.activity.GoodDetailActivity;
import com.android.aitaojie.activity.GoodWebDetailActiivty;
import com.android.aitaojie.activity.WebViewActivity;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.model.HomeIndexModel;
import com.android.zlibrary.adapter.ZMoreAdapter;
import com.android.zlibrary.customView.ZImageView;
import com.android.zlibrary.util.ViewUtil;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/21.
 */
public class HomeGoodAdapter extends ZMoreAdapter<HomeIndexModel.GoodsBean> {
    public HomeGoodAdapter(List<HomeIndexModel.GoodsBean> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }

    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent,final HomeIndexModel.GoodsBean model) {
        ImageView iv=(ImageView)convertView.findViewById(R.id.iv);
        TextView des=(TextView)convertView.findViewById(R.id.des);
        TextView price=(TextView)convertView.findViewById(R.id.price);
        TextView good=(TextView)convertView.findViewById(R.id.good);
        des.setText(model.getGdescription());
        price.setText("￥"+model.getGdprice());
        good.setText(model.getGname());
        ViewUtil.setImageView(iv,model.getGimg());

        iv.setOnClickListener(new ZOnClickListener(position, convertView, model) {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity, GoodWebDetailActiivty.class);
                String weburl=String.format(Constant.GoodDetaiUrl,model.getGid());
                intent.putExtra("webUrl",weburl);
                intent.putExtra("gid",Integer.parseInt(model.getGid()));
                mActivity.startActivity(intent);
      /*          Intent intent = new Intent(mActivity, GoodDetailActivity.class);
                intent.putExtra("gid",model.getGid());
                mActivity.startActivity(intent);*/
            }
        });
    }
    @Override
    protected int layoutId() {
        return R.layout.item_good;
    }
}
