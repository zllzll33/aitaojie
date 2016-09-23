package com.android.aitaojie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.activity.GoodDetailActivity;
import com.android.aitaojie.activity.WebViewActivity;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.customview.AddCartDialog;
import com.android.aitaojie.model.AddCartModel;
import com.android.aitaojie.model.ClassifyGoodListModel;
import com.android.zlibrary.adapter.ZModelAdapter;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.customView.ZImageView;
import com.android.zlibrary.util.ViewUtil;
import com.google.gson.Gson;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/22.
 */
public class GoodsListAdapter extends ZModelAdapter<ClassifyGoodListModel.GoodsBean> {

    public GoodsListAdapter(List<ClassifyGoodListModel.GoodsBean> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }

    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, ClassifyGoodListModel.GoodsBean model) {
        ZImageView tv=(ZImageView) convertView.findViewById(R.id.tv);
        TextView des = (TextView) convertView.findViewById(R.id.des);
        TextView current_price = (TextView) convertView.findViewById(R.id.current_price);
        ImageView cart=(ImageView)convertView.findViewById(R.id.cart);
        TextView origin_price = (TextView) convertView.findViewById(R.id.origin_price);
        origin_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线
        if(!model.getGimg().isEmpty())
        ViewUtil.setImageView(tv,model.getGimg());
        des.setText(model.getGdescription());
        current_price.setText("￥"+model.getGdprice());
        origin_price.setText("￥"+model.getGoprice());
        cart.setOnClickListener(new ZOnClickListener(position,convertView,model)
        {
            @Override
            public void onClick(View v) {
                HttpMap httpMap = new HttpMap();
                httpMap.putMode("Merchant");
                httpMap.putCtl("goods");
                httpMap.putAct("goodsSpec");
                httpMap.putDataMap("gid", model.getGid());
                httpMap.setHttpListener(new HttpMap.HttpListener() {
                    @Override
                    public void onSuccess(String response, String data) {
                        Gson gson = new Gson();
                        AddCartModel addCartModel = gson.fromJson(data, AddCartModel.class);
                        AddCartDialog addCartDialog=new AddCartDialog(addCartModel,false);
                        addCartDialog.showDiaglog();
                    }
                });

            }
        });
        tv.setOnClickListener(new ZOnClickListener(position,convertView,model){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity, WebViewActivity.class);
                String weburl=String.format(Constant.GoodDetaiUrl,model.getGid());
                intent.putExtra("webUrl",weburl);
                mActivity.startActivity(intent);
      /*          Intent intent=new Intent(mActivity, GoodDetailActivity.class);
                intent.putExtra("gid",model.getGid());
                mActivity.startActivity(intent);*/
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.item_goods_list;
    }
}
