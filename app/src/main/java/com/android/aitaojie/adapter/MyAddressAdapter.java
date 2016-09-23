package com.android.aitaojie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.activity.MyNewAddressActivity;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.AddressModel;
import com.android.zlibrary.adapter.ZModelAdapter;
import com.android.zlibrary.util.ResourceUtil;
import com.sunday.eventbus.SDEventManager;

import java.io.Serializable;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/26.
 */
public class MyAddressAdapter extends ZModelAdapter<AddressModel> {


    public MyAddressAdapter(List<AddressModel> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }

    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, AddressModel model) {
        LinearLayout ll_default=(LinearLayout) convertView.findViewById(R.id.ll_default);
        TextView name=(TextView)convertView.findViewById(R.id.name);
        TextView mibile=(TextView)convertView.findViewById(R.id.mibile);
        TextView address=(TextView)convertView.findViewById(R.id.address);
        TextView deafaut_text=(TextView)convertView.findViewById(R.id.deafaut_text);
        TextView delte=(TextView)convertView.findViewById(R.id.delte);
        ImageView deafaut_icon=(ImageView)convertView.findViewById(R.id.deafaut_icon);
        TextView edit = (TextView) convertView.findViewById(R.id.edit);
        name.setText(model.getContact());
        mibile.setText(model.getPhone());
        address.setText(model.getArea_txt()+model.getAddress());
        if(model.getDefaultX().equals("0"))
        {
            deafaut_text.setTextColor(ResourceUtil.getColor(R.color.text_black));
            deafaut_icon.setImageResource(R.mipmap.addr_not_default);
        }
      else if(model.getDefaultX().equals("1"))
        {
            deafaut_text.setTextColor(ResourceUtil.getColor(R.color.mainColor));
            deafaut_icon.setImageResource(R.mipmap.addr_default);
        }
        edit.setOnClickListener(new ZOnClickListener(position, convertView, model) {
            @Override
            public void onClick(View v) {
                Constant.addressModel=model;
                Intent intent = new Intent(mActivity, MyNewAddressActivity.class);
                intent.putExtra("edit_address",true);
                mActivity.startActivity(intent);
            }
        });
        delte.setOnClickListener(new ZOnClickListener(position, convertView, model) {
            @Override
            public void onClick(View v) {
                HttpMap httpMap = new HttpMap();
                httpMap.putMode("User");
                httpMap.putCtl("address");
                httpMap.putAct("delete");
                httpMap.putDataMap("uid", Constant.UID);
                httpMap.putDataMap("id", model.getId());
                httpMap.setHttpListener(new HttpMap.HttpListener() {
                    @Override
                    public void onSuccess(String response, String data) {
                        SDEventManager.post(EnumEventTag.REFRESH_ADDRESS_LIST.ordinal());
                    }
                });
            }
        });
        ll_default.setOnClickListener(new ZOnClickListener(position, convertView, model) {
            @Override
            public void onClick(View v) {
                HttpMap httpMap = new HttpMap();
                httpMap.putMode("User");
                httpMap.putCtl("address");
                httpMap.putAct("setdefault");
                httpMap.putDataMap("uid", Constant.UID);
                httpMap.putDataMap("id", model.getId());
                httpMap.setHttpListener(new HttpMap.HttpListener() {
                    @Override
                    public void onSuccess(String response, String data) {
                        SDEventManager.post(EnumEventTag.REFRESH_ADDRESS_LIST.ordinal());
                    }
                });
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.item_my_address;
    }
}
