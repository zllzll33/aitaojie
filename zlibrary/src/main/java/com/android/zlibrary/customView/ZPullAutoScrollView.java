package com.android.zlibrary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.android.zlibrary.R;

/**
 * Created by win7 on 2016/6/2.
 */
public class ZPullAutoScrollView extends ZPullBaseAutoScrollView {
    ZPullAutoScrollView(Context context)
    {
        super(context);
    }
    ZPullAutoScrollView(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
    }
    @Override
    protected int getHeaderLayout() {
        return R.layout.zpullscrollbaseheader;
    }

    @Override
    protected int getFooterLayout() {
        return R.layout.zpullscrollbasefooter;
    }
    protected void changeHeaderState(int PullDownState,View header)
    {
        super.changeHeaderState(PullDownState,header);
        TextView headerTv=(TextView)header.findViewById(R.id.tv);
        switch (PullDownState)
        {
            case PullDownStart:
                headerTv.setText("下拉刷新");
                break;
            case PullDownReady:
                headerTv.setText("放开刷新");
                break;
            case PullDownRelease:
                headerTv.setText("正在刷新");
                break;
        }

    }

    @Override
    protected void init() {
        bottemRefreshRange=200;
        super.init();
    }
}
