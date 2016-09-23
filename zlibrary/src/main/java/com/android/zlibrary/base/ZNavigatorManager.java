package com.android.zlibrary.base;

import android.os.Handler;
import android.view.View;

import java.util.Arrays;

/**
 * Created by win7 on 2016/5/19.
 */
public class ZNavigatorManager {
    private ZBaseView[] items;
    private ZNavigatorManagerListener mListener;
    int i=0;
    int selectIndex;
    int styleIndex;
    private Handler mHanler;
    public ZNavigatorManager()
    {
        mHanler=new Handler();
    }
    public void setmListener( ZNavigatorManagerListener mListener)
    {
        this.mListener=mListener;
    }
    public ZNavigatorManager setItems(ZBaseView[] items)
    {
        if (items != null && items.length > 0)
        {
            this.items=items;
                for(i=0;i<items.length;i++)
            {
                items[i].setStyle(i,items.length);
               items[i].Normal();
                items[i].setOnClickListener(new itemOnClickListener(i) {
                    @Override
                    public void onClick(View v) {
                        for(int t=0;t<ZNavigatorManager.this.items.length;t++)
                        {
                            ZNavigatorManager.this.items[t].Normal();
                        }
                        ZNavigatorManager.this.items[index].Select();
                        if(mListener!=null)
                        {
                            mListener.onItemClick(ZNavigatorManager.this.items[index],index);
                        }

                    }
                });
                if(items[i].getTabTag())
                    selectIndex=i;
            }
            mHanler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ZNavigatorManager.this.items[selectIndex].performClick();
                }
            },100);
        }
        return this;

    }
public ZNavigatorManager setItemCurrent(int toselect)
{
    items[toselect].performClick();
    return this;
}
    public ZNavigatorManager setItemCurrent(ZBaseView toView)
    {
        if(Arrays.asList(items).contains(toView))
        {
            for (i = 0; i < items.length; i++) {
                if(items[i].equals(toView))
                {
                    selectIndex=i;
                    items[selectIndex].performClick();

                }
                else
                items[i].Normal();
            }
        }

         return  this;

    }
    public interface ZNavigatorManagerListener
    {
         void onItemClick(ZBaseView view , int index);
    }
   class itemOnClickListener implements View.OnClickListener
    {
         int index;
        itemOnClickListener(int index)
        {
              this.index=index;
        }
        public void onClick(View v)
        {

        }

    }
}
