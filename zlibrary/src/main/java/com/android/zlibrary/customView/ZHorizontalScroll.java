package com.android.zlibrary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by win7 on 2016/7/19.
 */
public class ZHorizontalScroll extends HorizontalScrollView{
    private ScrollListener scrollListener=null;
    public ZHorizontalScroll(Context context) {
        super(context);
        init();
    }

    public ZHorizontalScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZHorizontalScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init()
    {
       setHorizontalScrollBarEnabled(false);
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
