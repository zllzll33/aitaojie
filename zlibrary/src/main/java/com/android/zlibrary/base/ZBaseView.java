package com.android.zlibrary.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by win7 on 2016/5/19.
 */
public abstract  class ZBaseView extends LinearLayout {
    protected Context context;
    protected boolean isTabTag=false;
  public ZBaseView(Context context)
  {
      super(context);
      this.context=context;
      init();
  }
    public ZBaseView(Context context, AttributeSet attr)
    {
        super(context,attr);
        this.context=context;
        init();
    }
    protected abstract void init();
    public abstract void Normal();
    public  abstract void Select();
    public   boolean getTabTag()
    {
        return isTabTag;
    }
    public  void setTabTag()
    {
     this.isTabTag=true;
    }//初始化时选中
    public abstract  void setStyle(int index,int length);
}
