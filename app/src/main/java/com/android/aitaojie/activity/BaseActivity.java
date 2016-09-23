package com.android.aitaojie.activity;

import android.view.View;
import android.widget.TextView;

import com.android.aitaojie.R;


/**
 * Created by win7 on 2016/4/28.
 */
public abstract  class BaseActivity extends ZBaseActivity {
    private View back;
    private TextView title;
    protected int layoutId()
    {
        return R.layout.title;
    }
    protected  void Init()
    {
        super.Init();
    }
    public void initUI()
    {
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
        title=(TextView)findViewById(R.id.title);
    }

    protected void setTitle(String titleText)
    {
        initUI();
        title.setText(titleText);
    }
    protected   void finishActivity()
    {
        finish();
    }
 protected  void setTitle(String titleText,boolean isHideBack)
 {
     initUI();
     title.setText(titleText);
     if(isHideBack)
         back.setVisibility(View.GONE);
 }
    protected void hideBack()
    {
        back.setVisibility(View.GONE);
    }
}
