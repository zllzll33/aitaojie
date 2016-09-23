package com.android.zlibrary.util;

import android.graphics.drawable.Drawable;
import android.view.animation.Animation;

import com.android.zlibrary.base.ZLibrary;

/**
 * Created by win7 on 2016/5/19.
 */
public class ResourceUtil {
    public static int getColor(int resId)
    {
      return   ZLibrary.getInstance().getApplication().getResources().getColor(resId);
    }
    public static String getString (int resId)
    {
      return   ZLibrary.getInstance().getApplication().getResources().getString(resId);
    }
    public static Drawable getDrawable(int resId)
    {
        return  ZLibrary.getInstance().getApplication().getResources().getDrawable(resId);
    }
}
