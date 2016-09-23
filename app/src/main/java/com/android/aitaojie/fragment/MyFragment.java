package com.android.aitaojie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.aitaojie.MainActivity;
import com.android.aitaojie.R;
import com.android.aitaojie.activity.MyAccountActivity;
import com.android.aitaojie.activity.MyCollectionActivity;
import com.android.aitaojie.activity.MyCouponActivity;
import com.android.aitaojie.activity.MyFootActivity;
import com.android.aitaojie.activity.MySetActivity;
import com.android.aitaojie.activity.WebViewActivity;
import com.android.aitaojie.adapter.LittleFootPrintAdapter;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.MyIndexModel;
import com.android.zlibrary.customView.CircleImageView;
import com.android.zlibrary.fragment.ZBaseFragment;
import com.android.zlibrary.util.PreferenceUtil;
import com.android.zlibrary.util.ViewUtil;
import com.google.gson.Gson;
import com.sunday.eventbus.SDBaseEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/25.
 */
public class MyFragment extends ZBaseFragment {
    @InjectView(R.id.header)
    CircleImageView header;
    @InjectView(R.id.nick)
    TextView nick;
    @InjectView(R.id.sign)
    TextView sign;
    @InjectView(R.id.order)
    LinearLayout order;
    @InjectView(R.id.appointment)
    LinearLayout appointment;
    @InjectView(R.id.cart)
    LinearLayout cart;
    @InjectView(R.id.collect)
    LinearLayout collect;
    @InjectView(R.id.my_coupon)
    LinearLayout myCoupon;
    @InjectView(R.id.my_more_footprint)
    TextView myMoreFootprint;
    @InjectView(R.id.ll_little_foot)
    LinearLayout llLittleFoot;
    @InjectView(R.id.set)
    View set;

    @Override
    protected int layoutId() {
        return R.layout.frag_my;
    }

    @Override
    protected void Init() {
        super.Init();
        HttpMap httpMap=new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("index");
        httpMap.putAct("index");
        httpMap.putDataMap("uid",Constant.UID);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {

                Gson gson=new Gson();
                MyIndexModel myIndexModel=  gson.fromJson(data,MyIndexModel.class);
                if(myIndexModel.getUser().getM_nickname().isEmpty())
                    nick.setText(myIndexModel.getUser().getM_phone());
                else
                    nick.setText(myIndexModel.getUser().getM_nickname());
                if(!myIndexModel.getUser().getM_avatar().isEmpty())
                    ViewUtil.setImageView(header,myIndexModel.getUser().getM_avatar());
                sign.setText("签名:"+myIndexModel.getUser().getAutograph());
                if(!myIndexModel.getGoods().isEmpty()) {
                    LittleFootPrintAdapter littleFootPrintAdapter = new LittleFootPrintAdapter(myIndexModel.getGoods(), getActivity());
                    llLittleFoot.removeAllViews();
                    for (int i = 0; i < myIndexModel.getGoods().size(); i++) {
                        View view = littleFootPrintAdapter.getView(i, null, null);
                        llLittleFoot.addView(view);
                    }
                }
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
                String weburl=String.format(Constant.MyCartUrl,Constant.UID);
                intent.putExtra("webUrl",weburl);
                startActivity(intent);
            }
        });
        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
                String weburl=String.format(Constant.MyAppointUrl,Constant.UID);
                intent.putExtra("webUrl",weburl);
                startActivity(intent);
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
                String weburl=String.format(Constant.MyOrderUrl,Constant.UID);
                intent.putExtra("webUrl",weburl);
                startActivity(intent);
            }
        });
        myCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
                String weburl=String.format(Constant.MyVoucherUrl,Constant.UID);
                intent.putExtra("webUrl",weburl);
                startActivity(intent);
                /*Intent intent = new Intent(getActivity(), MyCouponActivity.class);
                startActivity(intent);*/
            }
        });
        myMoreFootprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyFootActivity.class);
                startActivity(intent);
            }
        });
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyAccountActivity.class);
                startActivityForResult(intent,1);
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(getActivity(), MySetActivity.class);
                startActivity(intent);
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), MyCollectionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onEventMainThread(SDBaseEvent event)
    {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt()))
        {
            case CHAGE_HEADER:
               ViewUtil.setImageView(header,(String)event.getData());
                break;
            case Change_NICK:
                nick.setText((String)event.getData());
                break;
            case CHANG_SIGN:
                sign.setText((String)event.getData());
                break;
        }
    }

}
