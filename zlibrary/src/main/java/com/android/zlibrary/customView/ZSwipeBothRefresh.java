package com.android.zlibrary.customView;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.android.zlibrary.R;


/**
 * Created by win7 on 2016/7/19.
 */
public class ZSwipeBothRefresh extends ZSwipeRefreshLayout {
    private boolean CanBottomRefresh =true,isBottomRefresh=false,isTopRefresh=false;
     private int i=0;
    private LinearLayout scrll_ll,schild_ll;
    private View footer;
    private ZScrollView scrollView;
    private int ScrollRange;
    private RefreshListener bottomRefreshListener=null;
    public ZSwipeBothRefresh(Context context) {
        super(context);
    }
    public ZSwipeBothRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }
    @Override
    protected void init() {
        super.init();
        footer = LayoutInflater.from(context).inflate(R.layout.zpullscrollbasefooter, null, false);
        LinearLayout.LayoutParams scollerlp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scrollView = new ZScrollView(context);
        scrollView.setLayoutParams(scollerlp);
        LinearLayout.LayoutParams sllp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scrll_ll = new LinearLayout(context);
        scrll_ll.setLayoutParams(sllp);
        scrll_ll.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(scrll_ll, 0);
        LinearLayout.LayoutParams scllp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        schild_ll = new LinearLayout(context);
        schild_ll.setOrientation(LinearLayout.VERTICAL);
        schild_ll.setLayoutParams(scllp);
        scrll_ll.addView(schild_ll,0);
        scrll_ll.addView(footer, 1);
       setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(bottomRefreshListener!=null&&isTopRefresh==false)
                {
                    isTopRefresh=true;
                    bottomRefreshListener.onTopRefresh();
                }
            }
        });
        scrollView.setScrollListener(new ZScrollView.ScrollListener() {
            @Override
            public void onScrollChange(int X, int Y, int oldX, int oldY) {
                ScrollRange=getScrollRange();
                if(Y>ScrollRange-200&&bottomRefreshListener!=null&&isBottomRefresh==false)
                {
                    isBottomRefresh=true;
                    if(bottomRefreshListener!=null)
                        bottomRefreshListener.onBottomRefresh();
                }
            }
        });
    }
    public int getScrollRange()
    {
        return scrollView.getChildAt(0).getHeight()- (getHeight() -getPaddingBottom() -getPaddingTop());
    }
    public void setTopRefreshComplete()
    {
        isTopRefresh=false;
        setRefreshing(false);
    }
    public void setBottomRefreshComplete()
    {
        isBottomRefresh=false;
    }
    public void setNoBottomRefresh()
    {
        CanBottomRefresh =false;
        footer.setVisibility(GONE);
    }
    public void setBottomRefresh()
    {
        CanBottomRefresh =true;
        footer.setVisibility(VISIBLE);
    }
    public boolean isCanBottomRefresh()
    {
        return CanBottomRefresh;
    }
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params){
        if(context!=null)
        {
            schild_ll.addView(child,i);
            if(i==0)
                super.addView(scrollView,index,params);
            i++;
        }
        else
            super.addView(child,index,params);
    }
    public void setBottomRefreshListener(RefreshListener bottomRefreshListener)
    {
        this.bottomRefreshListener=bottomRefreshListener;
    }
    public interface RefreshListener
    {
        public void onBottomRefresh();
        public void onTopRefresh();
    }
}
