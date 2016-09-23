package com.android.zlibrary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.zlibrary.R;

/**
 * Created by win7 on 2016/6/2.
 */
public abstract class ZPullBaseAutoScrollView extends ScrollView{
    private View header,footer;
    private int headerHeight,footerHeight,scrollLength;
    private Context context;
    private final int PullDownNone=1;
    protected final int PullDownStart=2;
    protected final int PullDownReady=3;
    protected final int  PullDownRelease=4;
    private  int PullDownState=PullDownNone;
    private final int PullUpNone=1;
    private final int  PullUpFresh=4;
    private  int PullUpState=PullUpNone;
    private int lastHeaderPadding;
    private int moveY;
    private boolean downflag=false,isdownrefreshing=false,iscanUprefreshing=true,iscanUpRefresh=true;
    private    RefreshListener refreshListener;
    protected  int bottemRefreshRange=200;
    private  LinearLayout.LayoutParams footerlp;
    LinearLayout scrll_ll,schild_ll;
    public ZPullBaseAutoScrollView(Context context)
    {
        super(context);
        this.context=context;
        init();
    }
    public ZPullBaseAutoScrollView(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        this.context=context;
        init();
    }
    protected abstract  int getHeaderLayout();
    protected abstract  int getFooterLayout();
    protected void init()
    {
            header= LayoutInflater.from(context).inflate(getHeaderLayout(),null,false);
            getHeaderHeight();
            footer= LayoutInflater.from(context).inflate(getFooterLayout(),null,false);
         getFooterHeight();

        setVerticalScrollBarEnabled(false);
        footerlp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        setOnTouchListener(new View.OnTouchListener() {
            private  int beginY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_MOVE:

                        if(downflag==false)
                        {
                            beginY=(int)event.getY();
                            downflag=true;

                        }
                        moveY=((int)event.getY()-beginY)/2;
                            int scrollY=getScrollY();
                            scrollLength=getScrollRange();
                      /*      Log.e("scrollY",String.valueOf(scrollY));
                            Log.e("scrollLength",String.valueOf(scrollLength));*/
                            if((scrollLength-scrollY)<bottemRefreshRange&&iscanUprefreshing==true&&PullUpState!=PullUpFresh)
                            {
                                if(refreshListener!=null)
                                    refreshListener.onPullUp(ZPullBaseAutoScrollView.this);
                                changeFooterState(PullUpFresh,footer);
                            }
                            if(moveY>0)
                            {
                                if((scrollY==0&&iscanUpRefresh==true)||PullDownState==PullDownStart||PullDownState==PullDownReady)
                                {
                                    lastHeaderPadding=-headerHeight+moveY;
                                    if(moveY<headerHeight){
                                        header.setPadding(0,lastHeaderPadding,0,0);
                                        changeHeaderState(PullDownStart,header);
                                    }
                                    if(moveY>=headerHeight)
                                    {
                                        header.setPadding(0,lastHeaderPadding,0,0);
                                        changeHeaderState(PullDownReady,header);
                                    }

                                }

                            }
                            else if(moveY<=0&&PullDownState!=PullDownStart&&PullDownState!=PullDownReady){
                                PullDownState=PullDownNone;
                            }



                        break;
                    case MotionEvent.ACTION_UP:
                        int upMoveY=((int)event.getY()-beginY)/2;
                        downflag=false;

                            if(upMoveY>=headerHeight&&PullDownState==PullDownReady)
                            {
                                header.setPadding(0,0,0,0);
                                isdownrefreshing=true;
                                changeHeaderState(PullDownRelease,header);
                                if(refreshListener!=null)
                                    refreshListener.onPullDown(ZPullBaseAutoScrollView.this);
                                PullDownState=PullDownNone;
                            }
                            else{
                                PullDownState=PullDownNone;
                                if(isdownrefreshing==false)
                                    header.setPadding(0,-headerHeight,0,0);
                            }



                        break;
                    default:break;

                }
                if(PullDownState==PullDownReady||PullDownState==PullDownStart)
                    return true;
                else
                    return false;
            }
        });

    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }
    public void setBottomRange(int bottemRefreshRange)
    {
        this.bottemRefreshRange=bottemRefreshRange;
    }
    public void setRefreshing()
    {

            if(refreshListener!=null)
                refreshListener.onPullDown(ZPullBaseAutoScrollView.this);
    }
    public void PullHeaderComplete()
    {

            header.setPadding(0,-headerHeight,0,0);
            changeHeaderState(PullDownNone,header);
            isdownrefreshing=false;


    }
    public void PullFooterComplete()
    {
        changeFooterState(PullUpNone,footer);
    }
    public void setNoPullDown()
    {
        this.iscanUpRefresh=false;
    }
    public void setCanPullDown()
    {
        this.iscanUpRefresh=true;
    }
    public void setNoPullUp()
    {
        iscanUprefreshing=false;
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0,0,0,-footerHeight);
        footer.setLayoutParams(llp);
    }
    public void setCanPullUp()
    {
        iscanUprefreshing=true;
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0,0,0,0);
        footer.setLayoutParams(llp);
    }
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params){
        LinearLayout.LayoutParams sllp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        scrll_ll=new LinearLayout(context);
        scrll_ll.setLayoutParams(sllp);
        scrll_ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams scllp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        schild_ll=new LinearLayout(context);
        schild_ll.setOrientation(LinearLayout.VERTICAL);
        schild_ll.setLayoutParams(scllp);


            scrll_ll.addView(header, 0, params);
            schild_ll.addView(child,0,params);
            scrll_ll.addView(schild_ll,1,params);
            scrll_ll.addView(footer, 2, params);
            super.addView(scrll_ll,0,params);
//        child.setBackgroundColor(0xffffff00);
//        super.addView(child, index, params);
    }
    public int getScrollRange()
    {
        return getChildAt(0).getHeight()- (getHeight() -getPaddingBottom() -getPaddingTop());
    }
    protected void changeHeaderState(int PullDownState,View header)
    {
        this.PullDownState=PullDownState;
    }
    protected void changeFooterState(int PullUpState ,View footer)
    {
    this.PullUpState=PullUpState;
    }

    private void getHeaderHeight()
    {
        int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        header.measure(width,height);
        headerHeight=header.getMeasuredHeight();
        header.setPadding(0,-headerHeight,0,0);
        header.invalidate();
    }
    public void getFooterHeight()
    {
        int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        footer.measure(width,height);
        footerHeight=footer.getMeasuredHeight();
        LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0,0,0,0);
        footer.setLayoutParams(llp);
        footer.invalidate();
    }
    public void setOnRefreshListener(RefreshListener refreshListener)
    {
        this.refreshListener=refreshListener;
    }
    public interface RefreshListener
    {
        public void onPullDown(ScrollView scrollView);
        public void onPullUp(ScrollView scrollView);
    }
}
