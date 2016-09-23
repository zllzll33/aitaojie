package com.android.zlibrary.customView;

import android.content.Context;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;


import com.android.zlibrary.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * Created by win7 on 2016/7/19.
 */
public class ZSwipeRefreshLayout extends SwipeRefreshLayout {
    protected Context context;
    public ZSwipeRefreshLayout(Context context) {
        super(context);
        this.context=context;
        init();

    }

    public ZSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }
    protected void init()
    {
       setColorSchemeResources(R.color.main);
//     setProgressViewEndTarget(true, 300);
//        setDistanceToTriggerSync(100);// 设置手指在屏幕下拉多少距离会触发下拉刷新
//       setProgressBackgroundColorSchemeColor(0xff00ff00);
        setSize(SwipeRefreshLayout.DEFAULT); // 设置圆圈的大小  SwipeRefreshLayout.Large

    }

  public void autoRefresh()
  {
      try {
          Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
          mCircleView.setAccessible(true);
          View progress = (View) mCircleView.get(this);
          progress.setVisibility(VISIBLE);

          Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
          setRefreshing.setAccessible(true);
          setRefreshing.invoke(this, true, true);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}
