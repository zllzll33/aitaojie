package com.android.zlibrary.customView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * Created by win7 on 2016/6/15.
 */
public abstract class ZBasePopupWindow extends PopupWindow {
    protected int Mode=2,Match_parent=2;
    protected  View view;
   protected   Context context;
   protected PopupWindow popupWindow;
   public ZBasePopupWindow(Context context)
    {
        super(context);
        this.context=context;
        init();
    }
    protected abstract int layoutId();
    protected void initPop(View view)
    {

    }
protected   void init()
{
    view=LayoutInflater.from(context).inflate(layoutId(),null,false);
    initPop(view);
    if(Mode==Match_parent)
    popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    else
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
}
    public ZBasePopupWindow showAsDown(View parent,int x,int y)
    {
       if(!popupWindow.isShowing())
        popupWindow.showAsDropDown(parent,x,y);
        return this;
    }
    public ZBasePopupWindow showAsDown(View parent)
    {
        if(!popupWindow.isShowing())
        popupWindow.showAsDropDown(parent);
        return this;
    }
    public ZBasePopupWindow Dismiss()
    {
        if(popupWindow.isShowing())
            popupWindow.dismiss();
        return this;
    }
    public ZBasePopupWindow setBGRes(int res)
    {
        view.setBackgroundResource(res);
        return this;
    }
    public ZBasePopupWindow setBGColor(int color)
    {
        popupWindow.setBackgroundDrawable(new ColorDrawable(color));
        return  this;
    }
    public ZBasePopupWindow setPopLayoutParam(int width,int height)
    {
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        return this;
    }
    public ZBasePopupWindow setOutSideDismiss()
    {
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return this;
    }

}
