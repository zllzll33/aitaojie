package com.android.zlibrary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by win7 on 2016/6/7.
 */
public class ZSideSlip extends FrameLayout {
    private View mainView,sideView;
    private int  moveX;
    private int mainViewWidth,sideViewWidth;
    private boolean firstdown =false,isSliding=false,isExpand=false;
    private int childcount=0;
    private int[] mvLayout=new int[4];
    private final  int unexpand=1;
    private final  int middle=2;
    private final  int expand=3;
    private int slidStatus=unexpand;
    private double expandPercent=0.4;
    private SlideListener slideListener;
    public ZSideSlip(Context context) {
        super(context);
    }

    public ZSideSlip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZSideSlip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
    public boolean isUnExpand()
    {
        if(slidStatus==unexpand)
            return true;
        else
            return false;
    }
    public void traversalView(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                traversalView((ViewGroup) view);
            } else {
                sideViewWidth+= view.getWidth();
                view.setOnClickListener(new SlideClickLister(i) {
                    @Override
                    public void onClick(View v) {
                    if(slideListener!=null)
                    {
                        slideListener.onSideView(sideView,v,this.index);
                    }
                    }
                });
            }
        }
    }

    private void init()
    {
        mainView.setOnTouchListener(new OnTouchListener() {
            private int downX;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        isSliding=true;
                        downX=(int)event.getX();
                        if(firstdown ==false)
                        {
                            mainViewWidth= mainView.getWidth();
                            ViewGroup viewGroup=(ViewGroup)sideView;
                            traversalView(viewGroup);
                            firstdown =true;
                            mvLayout[0]=mainView.getLeft();
                            mvLayout[1]=mainView.getTop();
                            mvLayout[2]=mainView.getRight();
                            mvLayout[3]=mainView.getBottom();
                        }
                        if(slidStatus==expand)
                        {
                            mainView.layout( mvLayout[0], mvLayout[1], mvLayout[2], mvLayout[3]);
                            isSliding=false;
                            slidStatus=unexpand;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        moveX=((int)event.getX()-downX)/2;
                        if(slidStatus==unexpand&&isSliding==true)
                        {
                            if(moveX<0)
                            {
                                if((mvLayout[0]-mainView.getLeft())<=sideViewWidth)
                                {
                                    mainView.layout(mainView.getLeft()+moveX,mainView.getTop(),mainView.getRight()+moveX,mainView.getBottom());
                                }
                            }
                        }
                        isSliding=true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if(slidStatus==unexpand&&slideListener!=null&&(mvLayout[0]-mainView.getLeft())<3)
                        {
                            slideListener.onMainView(mainView);
                        }
                        if((mvLayout[0]-mainView.getLeft())>(int)(sideViewWidth*expandPercent))
                    {
                        mainView.layout(mvLayout[0]-sideViewWidth,mvLayout[1],mvLayout[2]-sideViewWidth,mvLayout[3]);
                        slidStatus=expand;
                    }
                        else
                        {
                            mainView.layout(mvLayout[0],mvLayout[1],mvLayout[2],mvLayout[3]);
                            slidStatus=unexpand;
                        }

                        isSliding=false;
                        break;
                }
                return isSliding;
            }
        });
    }
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params)
    {

        if(childcount==0)
        {
            sideView=child;
            super.addView(sideView,childcount,params);
        }

        else if(childcount==1)
        {
            mainView=child;
            super.addView(mainView,childcount,params);
            traversalMainView((ViewGroup)mainView);
            init();
        }
        childcount++;

    }
    public void traversalMainView(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                traversalView((ViewGroup) view);
            } else {
              /*  view.setOnClickListener(new SlideClickLister(i+1) {
                    @Override
                    public void onClick(View v) {
                        if(slideListener!=null&&slidStatus==unexpand)
                        {
                            slideListener.onMainView(mainView,v,this.index);
                        }
                    }
                });*/
            }
        }
    }
    public void setSlideListener(SlideListener slideListener)
    {
         this.slideListener=slideListener;
    }
    public class SlideClickLister implements OnClickListener
    {
         public int index;
        public SlideClickLister(int index) {
            this.index=index;
        }

        @Override
        public void onClick(View v) {

        }
    }
   public  interface SlideListener{
     public void  onMainView(View main);
       public void  onSideView(View main,View child,int index);
   }
}
