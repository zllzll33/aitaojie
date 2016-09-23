package com.android.zlibrary.tabpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.zlibrary.R;
import com.android.zlibrary.base.ZBaseView;
import com.android.zlibrary.util.TypeUtil;

/**
 * Created by win7 on 2016/5/25.
 */
public class ZTabPagerTitleView extends ZBaseView {
    private ImageView iv;
    private TextView tv;
    private View bar;
    public ZTabPagerTitleView(Context context) {
        super(context);
    }

    public ZTabPagerTitleView(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
    }

    @Override
    public void Normal() {
       bar.setVisibility(INVISIBLE);
    }

    @Override
    public void Select() {
        bar.setVisibility(VISIBLE);
    }

    @Override
    public boolean getTabTag() {
        return false;
    }

    @Override
    public void setTabTag() {

    }

    @Override
    public void setStyle(int index, int length) {

    }

    protected void init() {
        View layout = LayoutInflater.from(context).inflate(R.layout.item_tabpagertitle, this, true);
        iv = (ImageView) layout.findViewById(R.id.iv);
        tv = (TextView) layout.findViewById(R.id.tv);
        bar=layout.findViewById(R.id.bar);
    }

    public ZTabPagerTitleView setTitle(String title) {
        tv.setText(title);
        return this;
    }

    public ZTabPagerTitleView setIcon(int res) {
        iv.setImageResource(res);
        iv.setVisibility(VISIBLE);
        return this;
    }
    public ZTabPagerTitleView setBarColor(int color)
    {
        bar.setBackgroundColor(color);
        return  this;
    }
    public ZTabPagerTitleView setTextColor(int textColor)
    {
        tv.setTextColor(textColor);
        return  this;
    }
    public ZTabPagerTitleView setTextSize(int dp)
    {
        float px= TypeUtil.dp2px(dp);
        tv.setTextSize(px);
        return this;
    }
}
