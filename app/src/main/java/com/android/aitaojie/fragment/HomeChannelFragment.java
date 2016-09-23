package com.android.aitaojie.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.android.aitaojie.R;
import com.android.aitaojie.activity.GoodListActivity;
import com.android.aitaojie.adapter.GoodTitleAdapter;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.model.HomeIndexModel;
import com.android.aitaojie.util.ViewSwitchUtil;
import com.android.zlibrary.adapter.ZTextIconAdapter;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.customView.ZHorizontalScroll;
import com.android.zlibrary.fragment.ZBaseFragment;
import com.android.zlibrary.model.TextIconModel;
import com.android.zlibrary.util.SysUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/21.
 */
public class HomeChannelFragment extends ZBaseFragment {
    private List<TextIconModel> textIconModelList;
    @InjectView(R.id.horizotal_ll)
    LinearLayout horizotalLl;
    @InjectView(R.id.horizotalView)
    ZHorizontalScroll horizotalView;
    private int channelNum = 5, width;
    private List<HomeIndexModel.NavigationBean> channelList;
    @Override
    protected int layoutId() {
        return R.layout.frag_home_channel;
    }
 public void setChannelList( List<HomeIndexModel.NavigationBean> channelList)
 {
     this.channelList=channelList;
 }
    @Override
    protected void Init() {
        super.Init();
        if(channelList==null)
            return;
       width = SysUtil.getSreeenParam()[0];

        textIconModelList = new ArrayList<TextIconModel>();
      for(int i=0;i<channelList.size();i++)
      {
          TextIconModel textIconModel=new TextIconModel();
          textIconModel.setUrl(channelList.get(i).getImg());
          textIconModel.setText(channelList.get(i).getName());
          textIconModelList.add(textIconModel);
      }
        ZTextIconAdapter zTextIconAdapter = new ZTextIconAdapter(textIconModelList, getActivity());
        channelNum=textIconModelList.size();
        if (channelNum > 4) {
            horizotalLl.removeAllViews();
            for (int i = 0; i < channelNum; i++) {
                View view = zTextIconAdapter.getView(i, null, null);
                LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(width / 4, LinearLayout.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(vlp);
                view.setOnClickListener(new GoodTitleAdapter.ZOnClickListener(i, null, null) {
                    @Override
                    public void onClick(View v) {

                        ViewSwitchUtil.ViewSwicth(channelList.get(position).getUrl(),channelList.get(position).getPk());
                    }
                });
                horizotalLl.addView(view, i);
            }
        }
        else
        {
            horizotalLl.removeAllViews();
            for (int i = 0; i < channelNum; i++) {
                View view = zTextIconAdapter.getView(i, null, null);
                LinearLayout.LayoutParams vlp = new LinearLayout.LayoutParams(width /channelNum, LinearLayout.LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(vlp);
                view.setOnClickListener(new GoodTitleAdapter.ZOnClickListener(i, null, null) {
                    @Override
                    public void onClick(View v) {
                        ViewSwitchUtil.ViewSwicth(channelList.get(position).getUrl(),channelList.get(position).getPk());
                    }
                });
                horizotalLl.addView(view, i);
            }
        }
    }
}
