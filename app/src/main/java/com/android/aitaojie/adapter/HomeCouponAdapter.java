package com.android.aitaojie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.activity.WebViewActivity;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.model.HomeIndexModel;
import com.android.zlibrary.adapter.ZModelAdapter;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/21.
 */
public class HomeCouponAdapter extends ZModelAdapter<HomeIndexModel.VoucherBean> {


    public HomeCouponAdapter(List<HomeIndexModel.VoucherBean> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }

    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, HomeIndexModel.VoucherBean model) {
        TextView price=(TextView)convertView.findViewById(R.id.price);
        TextView name=(TextView)convertView.findViewById(R.id.name);
        TextView time=(TextView)convertView.findViewById(R.id.time);
        TextView go=(TextView)convertView.findViewById(R.id.go);
        price.setText(model.getVu_price());
        name.setText(model.getVu_name());
        time.setText(" "+model.getVu_stime()+"\n-"+model.getVu_etime());
        go.setOnClickListener(new ZOnClickListener(position,convertView,model)
        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mActivity, WebViewActivity.class);
                String weburl=String.format(Constant.VoucherDetailUrl,model.getVu_id(),Constant.JID);
                intent.putExtra("webUrl",weburl);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.item_home_coupon;
    }
}
