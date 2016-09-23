package com.android.zlibrary.customView;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;


import com.android.zlibrary.R;
import com.android.zlibrary.fragment.ZBaseFragment;
import com.android.zlibrary.tabpager.ZViewPagerAdapter;


import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/5/25.
 */
@SuppressLint("ValidFragment")
public class ZViewPagerFragment extends ZBaseFragment {
    ViewPager viewpager;
    private List<Fragment> mFragmentList;
  private   PagerChangeListener pagerChangeListener=null;
    @Override
    protected int layoutId() {
        return R.layout.frag_viewpager;
    }

    @SuppressLint("ValidFragment")
    public ZViewPagerFragment(List<Fragment> mFragmentList) {
        super();
        this.mFragmentList = mFragmentList;
    }

    @Override
    protected void Init() {
        super.Init();
        initViewPager();
    }

    private void initViewPager() {
        ZViewPagerAdapter mViewPagerAdapter = new ZViewPagerAdapter(getChildFragmentManager(), mFragmentList);
        viewpager=(ViewPager)view.findViewById(R.id.viewpager);
        viewpager.setAdapter(mViewPagerAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//               Log.e("viewpager", "切换到" + String.valueOf(position));
           if(pagerChangeListener!=null)
               pagerChangeListener.onChange(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//       viewpager.setCurrentItem(index,true);
    }
    public void setPagerChangeListener(PagerChangeListener pagerChangeListener)
    {
        this.pagerChangeListener=pagerChangeListener;
    }
    public void setCurrent(int index)
    {
        viewpager.setCurrentItem(index);
    }
public interface PagerChangeListener{
 public void    onChange(int position);
}
}
