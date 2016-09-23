package com.android.zlibrary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zlibrary.R;
import com.android.zlibrary.base.ZBaseView;

/**
 * Created by win7 on 2016/5/19.
 */
public class ZTabItemBottomView extends ZBaseView {
    public  TextView tv;
    public ImageView iv;
    public TextView tvnum;
    private int ivPress=R.mipmap.ic_launcher;
    private int ivNormal=R.mipmap.ic_launcher;
    private int tvPressColor=0xff000000;
    private int tvNormalColor=0xff000000;
    private String tvtext="首页";
    private int num;
    public ZTabItemBottomView(Context context)
    {
        super(context);

    }
    public ZTabItemBottomView(Context context, AttributeSet attr)
    {
        super(context,attr);

    }
    @Override
    protected void init()
    {

     View layout=  LayoutInflater.from(context).inflate(R.layout.ztabbottom,this,true);
         tv=(TextView)layout.findViewById(R.id.tv);
         iv=(ImageView)layout.findViewById(R.id.iv);
        tv=(TextView)layout.findViewById(R.id.tv);
        tvnum=(TextView)layout.findViewById(R.id.tvnum);
        tvnum.setVisibility(GONE);
    }
    public ZTabItemBottomView setImagePress(int ivPress)
    {
        this.ivPress=ivPress;
        return this;
    }
    public ZTabItemBottomView setImageNormal(int ivNormal)
    {
        this.ivNormal=ivNormal;
        return this;
    }
    public ZTabItemBottomView setTextPressColor(int tvPressColor)
    {
        this.tvPressColor=tvPressColor;
        return this;
    }
    public ZTabItemBottomView setTextNormalColor(int tvNormalColor)
    {
        this.tvNormalColor=tvNormalColor;
        return this;
    }
    public ZTabItemBottomView setText(String tvtext)
    {
        this.tvtext=tvtext;
        return  this;
    }
    public TextView setNumber(int num)
    {
        tvnum.setText(String.valueOf(num));
        return  tvnum;
    }
   public void Normal()
   {
       tv.setText(tvtext);
       tv.setTextColor(tvNormalColor);
       iv.setImageResource(ivNormal);
   }

    public void Select()
    {
        tv.setTextColor(tvPressColor);
        iv.setImageResource(ivPress);
    }
    public  void  setStyle(int index,int length)
    {

    }
}
