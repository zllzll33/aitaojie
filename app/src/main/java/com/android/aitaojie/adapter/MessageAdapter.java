package com.android.aitaojie.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.model.MessageModel;
import com.android.zlibrary.adapter.ZModelAdapter;
import com.android.zlibrary.util.ViewUtil;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/8/12.
 */
public class MessageAdapter extends ZModelAdapter<MessageModel> {

    public MessageAdapter(List<MessageModel> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }

    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, MessageModel model) {
        ImageView icon=(ImageView)convertView.findViewById(R.id.icon);
        View newIcon=convertView.findViewById(R.id.new_icon);
        TextView title=(TextView)convertView.findViewById(R.id.title);
        TextView content=(TextView)convertView.findViewById(R.id.content);
        TextView time=(TextView)convertView.findViewById(R.id.time);
        if(model.getStatus().equals("0"))
            newIcon.setVisibility(View.VISIBLE);
        else
            newIcon.setVisibility(View.GONE);
        if(model.getType().equals("1")) {
            icon.setImageResource(R.mipmap.message_system);
            title.setText("平台活动推荐");
        }
       else if(model.getType().equals("2")) {
            icon.setImageResource(R.mipmap.message_coupon);
            title.setText("商家优惠券指定发放");
        }
        else if(model.getType().equals("3")) {
            icon.setImageResource(R.mipmap.message_ready_pay);
            title.setText("待付款提示");
        }
        else if(model.getType().equals("4")) {
            icon.setImageResource(R.mipmap.message_ready_get);
            title.setText("待收货提示");
        }
        else if(model.getType().equals("5")) {
            icon.setImageResource(R.mipmap.message_retrun_money);
            title.setText("退款提示");
        }
        else {
            title.setText(model.getMnickname());
            if (!model.getLogo().isEmpty()) {
                ViewUtil.setImageView(icon, model.getLogo());

            }
           }
        content.setText(model.getTitle());
        time.setText(model.getSet_time());
    }

    @Override
    protected int layoutId() {
        return R.layout.item_message;
    }
}

