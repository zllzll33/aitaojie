package com.android.zlibrary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zlibrary.adapter.ZModelAdapter;

/**
 * Created by win7 on 2016/6/12.
 */
public class ZMoreGridView extends LinearLayout {
    private Context context;
    public TextView tv,title;
    public ZGridView zGridView;
    private ZModelAdapter zNormalAdapter,zMoreAdapter;
    private boolean isExpand=false;
    private String expandText="更多",unExpandText="收起";
    public ZMoreGridView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public ZMoreGridView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        this.context=context;
        init();
    }

    public ZMoreGridView(Context context, AttributeSet attr) {
        super(context, attr);
        this.context=context;
        init();
    }
    public TextView getTextView()
    {
        return tv;
    }
    private void init()
    {
        setOrientation(VERTICAL);
        LinearLayout.LayoutParams titlelp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        title=new TextView(context);
        title.setTextSize(30);
        title.setLayoutParams(titlelp);
        title.setGravity(Gravity.CENTER);
        addView(title);
        LinearLayout.LayoutParams zlp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        zGridView=new ZGridView(context);
        zGridView.setLayoutParams(zlp);
        LinearLayout.LayoutParams tlp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv=new TextView(context);
        tv.setLayoutParams(tlp);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(30);
        tv.setText("更多");
        tv.setTextColor(0xff000000);
        addView(zGridView);
        addView(tv);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isExpand==false)
                {
                    setMoreAdapter();
                    isExpand=true;
                    tv.setText(unExpandText);
                }
                else
                {
                    setNormalAdapter();
                    isExpand=false;
                    tv.setText(expandText);
                }

            }
        });
    }
    public void setTitle(String titlestr)
    {
        title.setText(titlestr);
    }
    public ZMoreGridView setExpandText(String expandText)
    {
        this.expandText=expandText;
        return this;
    }
    public ZMoreGridView setUnExpandText(String unExpandText)
    {
        this.unExpandText=unExpandText;
        return this;
    }
    public ZMoreGridView setColumn(int column)
    {
        zGridView.setColums(column) ;
        return this;
    }
    public ZMoreGridView setAdapter(ZModelAdapter zNormalAdapter,ZModelAdapter zMoreAdapter)
    {
        this.zNormalAdapter=zNormalAdapter;
        this.zMoreAdapter=zMoreAdapter;
        setNormalAdapter();
        return this;
    }
    private void setNormalAdapter()
    {
        zGridView.setAdapter(zNormalAdapter);
    }
    private void setMoreAdapter()
    {
        zGridView.setAdapter(zMoreAdapter);
    }
}
