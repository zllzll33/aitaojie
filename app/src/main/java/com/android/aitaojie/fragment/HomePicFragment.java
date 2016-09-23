package com.android.aitaojie.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.aitaojie.R;
import com.android.aitaojie.model.HomeIndexModel;
import com.android.aitaojie.util.ViewSwitchUtil;
import com.android.zlibrary.fragment.ZBaseFragment;
import com.android.zlibrary.util.ViewUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/21.
 */
public class HomePicFragment extends ZBaseFragment {
    @InjectView(R.id.left)
    ImageView left;
    @InjectView(R.id.r_top)
    ImageView rTop;
    @InjectView(R.id.r_bottom1)
    ImageView rBottom1;
    @InjectView(R.id.r_bottom2)
    ImageView rBottom2;
    private List<HomeIndexModel.RecommendBean> recommendBeanList;

    public void setRecommendBean(List<HomeIndexModel.RecommendBean> recommendBeanList) {
        this.recommendBeanList = recommendBeanList;
    }

    @Override
    protected int layoutId() {
        return R.layout.frag_home_pic;
    }

    @Override
    protected void Init() {
        super.Init();
        if(recommendBeanList.size()==0)
            return;
        ViewUtil.setImageView(left,recommendBeanList.get(0).getImg());
        ViewUtil.setImageView(rTop,recommendBeanList.get(1).getImg());
        ViewUtil.setImageView(rBottom1,recommendBeanList.get(2).getImg());
        ViewUtil.setImageView(rBottom2,recommendBeanList.get(3).getImg());
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewSwitchUtil.ViewSwicth(recommendBeanList.get(0).getUrl(),recommendBeanList.get(0).getPk());
            }
        });
        rTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewSwitchUtil.ViewSwicth(recommendBeanList.get(1).getUrl(),recommendBeanList.get(1).getPk());
            }
        });
        rBottom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewSwitchUtil.ViewSwicth(recommendBeanList.get(2).getUrl(),recommendBeanList.get(2).getPk());
            }
        });
        rBottom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewSwitchUtil.ViewSwicth(recommendBeanList.get(3).getUrl(),recommendBeanList.get(3).getPk());
            }
        });
    }

}
