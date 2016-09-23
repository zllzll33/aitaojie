package com.android.zlibrary.custom;

import android.graphics.drawable.GradientDrawable;

import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.TypeUtil;

/**
 * Created by win7 on 2016/5/27.
 */
public class ZGradientDrawable extends GradientDrawable {
    //1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
    public ZGradientDrawable setLeftCornerDp(int cornerLeftDp)
    {
        float cornerPx= TypeUtil.dp2px(cornerLeftDp);
        setCornerRadii(new float[]{cornerPx,cornerPx,0,0,0,0,cornerPx,cornerPx});
        return  this;
    }
    public ZGradientDrawable setRightCornerDp(int cornerRightDp)
    {
        float cornerPx= TypeUtil.dp2px(cornerRightDp);
        setCornerRadii(new float[]{0,0,cornerPx,cornerPx,cornerPx,cornerPx,0,0});
        return  this;
    }
    public ZGradientDrawable setCornerDp(int cornerDp)
    {
        float cornerPx= TypeUtil.dp2px(cornerDp);
       setCornerRadius(cornerPx);
        return  this;
    }
    public ZGradientDrawable setSolidColor(int color)
    {
        setColor(color);
        return this;
    }
    public ZGradientDrawable setStrokeDp(int width,int color)
    {
        int strokeWidth= TypeUtil.dp2px(width);
        setStroke(strokeWidth,color);
        return this;
    }
    public ZGradientDrawable setGDAlpha(int alpha)
    {
        setAlpha(alpha);
        return this;
    }

}
