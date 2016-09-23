package com.android.zlibrary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by win7 on 2016/7/22.
 */
public class ZScrollView extends ScrollView {
    private ScrollListener scrollListener=null;
    public ZScrollView(Context context) {
        super(context);
    }

    public ZScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt){
            if(scrollListener!=null)
            {
                scrollListener.onScrollChange(l,t,oldl,oldt);
            }

    }
    public void setScrollListener(ScrollListener scrollListener)
    {
        this.scrollListener=scrollListener;
    }
    public interface ScrollListener
    {
     public  void  onScrollChange(int X, int Y, int oldX, int oldY);
    }
}
