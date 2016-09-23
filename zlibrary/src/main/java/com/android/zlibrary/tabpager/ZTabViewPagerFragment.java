package com.android.zlibrary.tabpager;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.android.zlibrary.R;
import com.android.zlibrary.base.ZNavigatorManager;
import com.android.zlibrary.base.ZBaseView;
import com.android.zlibrary.fragment.ZBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2016/5/25.
 */
@SuppressLint("ValidFragment")
public class ZTabViewPagerFragment extends ZBaseFragment {
    private  ViewPager viewpager;
    private LinearLayout ll_title;
    private List<ZTabPagerModel> zTabPagerModelList;
    private List<Fragment> mFragmentList;
    private ZNavigatorManager zNavigatorManager;
    private  ZBaseView[] items;
    @Override
    protected int layoutId() {
        return R.layout.frag_tabviewpager;
    }

    @SuppressLint("ValidFragment")
    public ZTabViewPagerFragment(List<ZTabPagerModel> zTabPagerModelList) {
        super();
        this.zTabPagerModelList = zTabPagerModelList;
    }

    @Override
    protected void Init() {
        super.Init();
        ll_title=(LinearLayout)view.findViewById(R.id.ll_title);
        viewpager=(ViewPager)view.findViewById(R.id.viewpager);
        initViewPager();
    }
   private void initViewPager()
   {
       items=new ZBaseView[zTabPagerModelList.size()];
       zNavigatorManager=new ZNavigatorManager();

    for(int i=0;i<zTabPagerModelList.size();i++)
    {
        ZTabPagerModel zTabPagerModel=zTabPagerModelList.get(i);
        ZTabPagerTitleView zTabPagerTitle=new ZTabPagerTitleView(getActivity());
        zTabPagerTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.MATCH_PARENT, 1.0f));
        zTabPagerTitle.setTitle(zTabPagerModel.getTitleText());
        if(zTabPagerModel.getTitleIcon()!=0)
            zTabPagerTitle.setIcon(zTabPagerModel.getTitleIcon());
        items[i]=zTabPagerTitle;
        ll_title.addView(zTabPagerTitle);
    }
       mFragmentList = new ArrayList<Fragment>();
       for(int i=0;i<zTabPagerModelList.size();i++)
       {
           mFragmentList.add(zTabPagerModelList.get(i).getPagerFragment());
       }
       ZViewPagerAdapter mViewPagerAdapter = new ZViewPagerAdapter(getChildFragmentManager(), mFragmentList);
       viewpager.setAdapter(mViewPagerAdapter);
       viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
           }

           @Override
           public void onPageSelected(int position) {
//               Log.e("viewpager", "切换到" + String.valueOf(position));
               zNavigatorManager.setItemCurrent(position);
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
       zNavigatorManager.setItems(items);
       zNavigatorManager.setmListener(new ZNavigatorManager.ZNavigatorManagerListener() {
           @Override
           public void onItemClick(ZBaseView view, int index) {

               viewpager.setCurrentItem(index,true);
           }
       });
   }

}
