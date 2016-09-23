package com.android.zlibrary.tabpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by win7 on 2016/5/24.
 */
public class ZViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    public ZViewPagerAdapter(FragmentManager mFragmentManager, List<Fragment> mFragmentList)
    {
        super(mFragmentManager);
        this.mFragmentList=mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
