package com.android.aitaojie.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.model.LiveModel;
import com.android.zlibrary.adapter.ZModelAdapter;
import com.android.zlibrary.customView.ZImageView;
import com.android.zlibrary.util.ViewUtil;
import com.sunday.eventbus.SDEventManager;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/25.
 */
public class LiveListAdapter extends ZModelAdapter<LiveModel.MerchantBean> {


    public LiveListAdapter(List<LiveModel.MerchantBean> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }

    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, LiveModel.MerchantBean model) {
        TextView browse=(TextView)convertView.findViewById(R.id.browse);
        TextView focus_num=(TextView)convertView.findViewById(R.id.focus_num);
        ZImageView iv=(ZImageView)convertView.findViewById(R.id.iv);
        TextView industry=(TextView)convertView.findViewById(R.id.industry);
        TextView addr=(TextView)convertView.findViewById(R.id.addr);
        TextView go=(TextView)convertView.findViewById(R.id.go);
        ImageView header=(ImageView)convertView.findViewById(R.id.header);
        TextView storeName=(TextView)convertView.findViewById(R.id.store_name);
        TextView distance=(TextView)convertView.findViewById(R.id.distance);
        ViewUtil.setImageView(iv,model.getImg());
        ViewUtil.setImageView(header,model.getLogo());
        industry.setText(model.getTrade());
        addr.setText(model.getAddress());
        storeName.setText(model.getName());
        distance.setText(model.getDistance());
        browse.setText("浏览量:"+model.getViews());
        focus_num.setText("关注量:"+model.getLikes());
        go.setOnClickListener(new ZOnClickListener(position,convertView,model)
        {
            @Override
            public void onClick(View v) {
                SDEventManager.post(model.getJid(),EnumEventTag.GO_HOME_FRAGMENT.ordinal());
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.item_live_list;
    }
}
