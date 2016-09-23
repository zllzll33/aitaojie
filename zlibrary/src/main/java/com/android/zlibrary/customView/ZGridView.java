package com.android.zlibrary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by win7 on 2016/5/24.
 */
public class ZGridView extends GridView {
    private int numColums=1;
    public ZGridView(Context context)
    {
        super(context);
    }
    public ZGridView(Context context, AttributeSet attr, int defStyle)
    {
        super(context,attr,defStyle);

    }
    public ZGridView(Context context, AttributeSet attr)
    {
        super(context,attr);
    }
    public ZGridView setColums(int num)
    {
        setNumColumns(num);
        return this;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
