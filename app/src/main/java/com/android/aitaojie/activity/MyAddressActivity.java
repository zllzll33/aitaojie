package com.android.aitaojie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.MyAddressAdapter;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.AddressModel;
import com.android.zlibrary.customView.ZGridView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/26.
 */
public class MyAddressActivity extends BaseActivity {
    @InjectView(R.id.grid_addr)
     ZGridView gridAddr;
    @InjectView(R.id.add_addr)
    TextView addAddr;
   int type=0;
    @Override
    protected int layoutId() {
        return R.layout.act_my_address;
    }

    @Override
    protected void Init() {
        super.Init();
        Intent intent=getIntent();
        type=intent.getIntExtra("type",0);
        setTitle("收货地址");
        addAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyAddressActivity.this,MyNewAddressActivity.class);
                startActivity(intent);
            }
        });
        httpAddressList();
    }
    public void httpAddressList()
    {
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("address");
        httpMap.putAct("index");
        httpMap.putDataMap("uid", Constant.UID);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                if(data.isEmpty())
                    return;
                Gson gson=new Gson();
                List<AddressModel> addressModelList=gson.fromJson(data,new TypeToken<List<AddressModel>>(){}.getType());
                MyAddressAdapter myAddressAdapter = new MyAddressAdapter(addressModelList, MyAddressActivity.this);
                gridAddr.setAdapter(myAddressAdapter);
            }
        });
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(type==1)
            SDEventManager.post(EnumEventTag.ADDRESS.ordinal());
    }
    @Override
    public void onEventMainThread(SDBaseEvent event)
    {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt()))
        {
            case REFRESH_ADDRESS_LIST:
                httpAddressList() ;
                break;

        }
    }
}
