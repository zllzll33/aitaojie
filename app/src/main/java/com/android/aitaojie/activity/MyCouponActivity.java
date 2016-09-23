package com.android.aitaojie.activity;

import android.support.v4.app.Fragment;
import android.view.View;

import com.android.aitaojie.R;
import com.android.zlibrary.customView.ZViewPagerFragment;
import com.android.aitaojie.fragment.MyCouponListFragment;
import com.android.zlibrary.base.ZBaseView;
import com.android.zlibrary.base.ZNavigatorManager;
import com.android.zlibrary.customView.ZTabItemCorner;
import com.android.zlibrary.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/25.
 */
public class MyCouponActivity extends BaseActivity {
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
        return R.layout.act_my_coupon;
    }
    @Override
    protected void Init() {
        super.Init();
        corner1.setText("未使用").setCornerRadius(10).setBgNormalColor(0xffE4E4E4).setBgSelectColor(ResourceUtil.getColor(R.color.mainColor)).setTextNormalColor(0xff808080).setTextSelectColor(0xffffffff);
        corner2.setText("已失效").setCornerRadius(10).setBgNormalColor(0xffE4E4E4).setBgSelectColor(ResourceUtil.getColor(R.color.mainColor)).setTextNormalColor(0xff808080).setTextSelectColor(0xffffffff);
        ZBaseView[] items = new ZBaseView[]{corner1, corner2};
        zNavigatorManager = new ZNavigatorManager();
        zNavigatorManager.setItems(items);
        zNavigatorManager.setItemCurrent(0);
        MyCouponListFragment myCouponListFragment = new MyCouponListFragment();
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        MyCouponListFragment myCouponListFragment1 = new MyCouponListFragment();
        fragmentList.add(myCouponListFragment);
        fragmentList.add(myCouponListFragment1);
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
        getZFragmentManager().replace(R.id.frag_coupon, zTabViewPagerFragment);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
