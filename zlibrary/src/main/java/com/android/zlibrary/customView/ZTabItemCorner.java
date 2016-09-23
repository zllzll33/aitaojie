package com.android.zlibrary.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zlibrary.R;
import com.android.zlibrary.base.ZBaseView;
import com.android.zlibrary.util.TypeUtil;

/**
 * Created by win7 on 2016/5/26.
 */
public class ZTabItemCorner extends ZBaseView{
protected TextView tv;
    private int normalBgColor=0xff0000ff;
    private int selectBgColor=0xff00ff00;
    private int normalTextColor=0xff00ff00;
    private int selectTextColor=0xffff0000;
    private GradientDrawable gradientDrawable;
    private LinearLayout ll_corner;
    private int cornerDp=10;
    private float cornerPx=20;
    public ZTabItemCorner(Context context)
    {
        super(context);
    }
    public ZTabItemCorner(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);

    }
    @Override
     protected   void init()
  {
      View layout=  LayoutInflater.from(context).inflate(R.layout.item_tab_corner,this,true);
      tv=(TextView)layout.findViewById(R.id.tv);
      ll_corner=(LinearLayout)layout.findViewById(R.id.ll_corner);
      gradientDrawable=new GradientDrawable();

   }
    @Override
    public void Normal() {
        gradientDrawable.setColor(normalBgColor);
        ll_corner.setBackground(gradientDrawable);
        tv.setTextColor(normalTextColor);
    }

    @Override
    public void Select() {
        gradientDrawable.setColor(selectBgColor);
        ll_corner.setBackground(gradientDrawable);
        tv.setTextColor(selectTextColor);
    }
    public ZTabItemCorner setCornerRadius(int dp)
    {
        this.cornerDp=dp;
        cornerPx=TypeUtil.dp2px(cornerDp);
        return this;
    }
 public ZTabItemCorner setBgNormalColor(int color)
 {
     this.normalBgColor=color;
     return  this;
 }
    public ZTabItemCorner setBgSelectColor(int color)
    {
        this.selectBgColor=color;
        return this;
    }
    public ZTabItemCorner setText(String str)
    {
        tv.setText(str);
        return this;
    }
    public ZTabItemCorner setTextSize(int dp)
    {
        tv.setTextSize(TypeUtil.dp2px(dp));
        return this;
    }
    public ZTabItemCorner setTextNormalColor(int color)
    {
        this.normalTextColor=color;
        return  this;
    }
    public ZTabItemCorner setTextSelectColor(int color)
    {
        this.selectTextColor=color;
        return this;
    }

    @Override
    public void setStyle(int index, int length) {
       if(index==0)
       {
           //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
           gradientDrawable.setCornerRadii(new float[]{cornerPx,cornerPx,0,0,0,0,cornerPx,cornerPx});
       }
        else if(index==length-1)
       {
           gradientDrawable.setCornerRadii(new float[]{0,0,cornerPx,cornerPx,cornerPx,cornerPx,0,0});
       }
    }
}
