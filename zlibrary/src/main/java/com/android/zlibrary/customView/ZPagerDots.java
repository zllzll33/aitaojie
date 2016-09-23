package com.android.zlibrary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.android.zlibrary.R;
import com.android.zlibrary.util.TypeUtil;

/**
 * Created by win7 on 2016/6/3.
 */
public class ZPagerDots extends LinearLayout{
    Context context;
    int dotsNum=2,dotsSizedp=10, margin=20;
     View[] dots;
    private int normalBg= R.mipmap.zdotnormal,selecteBg=R.mipmap.zdotselect;
    public ZPagerDots(Context context)
    {
        super(context);
        this.context=context;
        init();
    }
    public ZPagerDots(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        this.context=context;
        init();
    }
    private void init()
    {
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        setLayoutParams(llp);
        setOrientation(HORIZONTAL);
    }
    public ZPagerDots setDotsNum(int dotsNum)
    {
        this.dotsNum=dotsNum;
          return this;
    }
    public ZPagerDots initDots()
    {
        dots=new View[dotsNum];
        for(int i=0;i<dotsNum;i++)
        {
            LinearLayout.LayoutParams dlp=new LinearLayout.LayoutParams(TypeUtil.dp2px(dotsSizedp),TypeUtil.dp2px(dotsSizedp));
            View view=new View(context);
            if(i!=0)
                dlp.setMargins(margin,0,0,0);
            view.setLayoutParams(dlp);
            dots[i]=view;
            addView(view);
        }
        return  this;
    }
    public ZPagerDots setDotsNormalIcon(int normalBg)
    {
       this.normalBg=normalBg;
        return this;
    }
    public ZPagerDots setDotsSelecteIcon(int selecteBg)
    {
        this.selecteBg=selecteBg;
        return this;
    }
    public ZPagerDots setCurrent(int currentId)
    {
        for(int i=0;i<dotsNum;i++)
        {
            dots[i].setBackgroundResource(normalBg);
        }
        dots[currentId] .setBackgroundResource(selecteBg);
        return this;
    }

}
