package com.android.aitaojie.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.android.aitaojie.R;
import com.android.zlibrary.adapter.ViewPagerAdapter;
import com.android.zlibrary.customView.ZPagerDots;
import com.android.zlibrary.util.PreferenceUtil;

import java.util.ArrayList;

/**
 * Created by win7 on 2016/6/14.
 */
public class GuideActivity extends ZBaseActivity {
    ViewPager viewpager;
    ZPagerDots dots;
    TextView jump;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<View> views;
    private int dotnum=5;
    private int GuideImage[]={R.mipmap.guide1,R.mipmap.guide2,R.mipmap.guide3,R.mipmap.guide4,R.mipmap.guide5};
    @Override
    protected int layoutId() {
        return R.layout.act_guide;
    }
    protected void Init() {
        PreferenceUtil.putBoolean("firstIn",false);
    	jump=(TextView)findViewById(R.id.jump);
    	viewpager=(ViewPager)findViewById(R.id.viewpager);
    	dots=(ZPagerDots)findViewById(R.id.dots);
        dots.setDotsNum(dotnum).initDots();
        views = new ArrayList<View>();
        viewPagerAdapter = new ViewPagerAdapter(views);
        dots.setDotsNormalIcon(R.mipmap.guide_white_icon);
        dots.setDotsSelecteIcon(R.mipmap.guide_black_icon);
        jump.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuideActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout.LayoutParams ivlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i <dotnum; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(ivlp);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(GuideImage[i]);
            views.add(iv);
        }
        viewpager.setAdapter(viewPagerAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
          
            @Override
            public void onPageSelected(int position) {

                dots.setCurrent(position);
                if(position==4) {
                    dots.setVisibility(View.GONE);
                    jump.setVisibility(View.VISIBLE);
                }
                else
                {
                    dots.setVisibility(View.VISIBLE);
                    jump.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.setCurrentItem(0);
        dots.setCurrent(0);
    }


}
