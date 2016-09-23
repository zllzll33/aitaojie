package com.android.zlibrary;

/**
 * Created by win7 on 2016/7/21.
 */
public class Note {
    //            horizotalView.setSmoothScrollingEnabled(false);
        /*    horizotalView.requestDisallowInterceptTouchEvent(false);
            horizotalView.setOnTouchListener(new View.OnTouchListener() {
                int MX;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction())
                    {
                        case MotionEvent.ACTION_DOWN:
                            scrollStatus=true;
                            break;
                        case MotionEvent.ACTION_MOVE:
                              if(isfirst)
                              {
                                  MX=(int)event.getX();
                                  isfirst=false;

                              }
                            scrollStatus=true;
                            break;
                        case MotionEvent.ACTION_UP:
                            isfirst=true;
                          int X=(int)event.getX()-MX;
                            if(X<0)
                            {
                                if(scrollIndex<channelNum-4)
                                scrollIndex++;
                            }
                            else
                            {
                                if(scrollIndex>0)
                                    scrollIndex--;
                            }
                            horizotalView.scrollTo(scrollIndex*width/4,0);
                            scrollStatus=false;
                            break;
                    }
                    return scrollStatus;
                }
            });*/
}
