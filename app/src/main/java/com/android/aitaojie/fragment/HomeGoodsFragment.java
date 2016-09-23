package com.android.aitaojie.fragment;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.HomeGoodAdapter;
import com.android.aitaojie.model.HomeIndexModel;
import com.android.zlibrary.customView.ZGridView;
import com.android.zlibrary.fragment.ZBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/22.
 */
public class HomeGoodsFragment extends ZBaseFragment {
    @InjectView(R.id.grid)
    ZGridView grid;
    List<HomeIndexModel.GoodsBean> goodsBeanList;
    @Override
    protected int layoutId() {
        return R.layout.frag_home_grid;
    }
public void setGoodList(List<HomeIndexModel.GoodsBean> goodsBeanList)
{
    this.goodsBeanList=goodsBeanList;
}
    @Override
    protected void Init() {
        super.Init();
    if(goodsBeanList.size()==0)
        return;
        HomeGoodAdapter homeGoodAdapter=new HomeGoodAdapter(goodsBeanList,getActivity());
        grid.setAdapter(homeGoodAdapter);
    }
}
