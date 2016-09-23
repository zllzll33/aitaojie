package com.android.aitaojie.activity;

import android.support.v4.app.Fragment;
import android.view.View;

import com.android.aitaojie.R;
import com.android.zlibrary.customView.ZViewPagerFragment;
import com.android.aitaojie.fragment.MyGoodsFragment;
import com.android.aitaojie.fragment.MyStoreListFragment;
import com.android.zlibrary.base.ZBaseView;
import com.android.zlibrary.base.ZNavigatorManager;
import com.android.zlibrary.customView.ZTabItemCorner;
import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.TypeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/28.
 */
public class MyCollectionActivity extends ZBaseActivity {
    @InjectView(R.id.corner1)
    ZTabItemCorner corner1;
    @InjectView(R.id.corner2)
    ZTabItemCorner corner2;
    ZViewPagerFragment zTabViewPagerFragment;
    ZNavigatorManager zNavigatorManager;
    @InjectView(R.id.back)
    View back;
    @Override
    protected int layoutId() {
        return R.layout.act_my_collection;
    }

    @Override
    protected void Init() {
        super.Init();
        corner1.setText("店铺").setTextSize(5).setCornerRadius(20).setBgNormalColor(0xffE4E4E4).setBgSelectColor(ResourceUtil.getColor(R.color.mainColor)).setTextNormalColor(0xff808080).setTextSelectColor(0xffffffff);
        corner2.setText("商品").setTextSize(5).setCornerRadius(20).setBgNormalColor(0xffE4E4E4).setBgSelectColor(ResourceUtil.getColor(R.color.mainColor)).setTextNormalColor(0xff808080).setTextSelectColor(0xffffffff);
        ZBaseView[] items = new ZBaseView[]{corner1, corner2};
        zNavigatorManager = new ZNavigatorManager();
        zNavigatorManager.setItems(items);
        zNavigatorManager.setItemCurrent(0);
        MyStoreListFragment myStoreListFragment = new MyStoreListFragment();
        MyGoodsFragment myGoodsFragment = new MyGoodsFragment();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(myStoreListFragment);
        fragmentList.add(myGoodsFragment);
        zTabViewPagerFragment = new ZViewPagerFragment(fragmentList);
        zTabViewPagerFragment.setPagerChangeListener(new ZViewPagerFragment.PagerChangeListener() {
            @Override
            public void onChange(int position) {
                zNavigatorManager.setItemCurrent(position);
            }
        });
        zNavigatorManager.setmListener(new ZNavigatorManager.ZNavigatorManagerListener() {
            @Override
            public void onItemClick(ZBaseView view, int index) {
                zTabViewPagerFragment.setCurrent(index);
            }
        });
        getZFragmentManager().replace(R.id.frag_collection, zTabViewPagerFragment);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
