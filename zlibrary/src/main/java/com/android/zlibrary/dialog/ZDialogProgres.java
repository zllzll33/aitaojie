package com.android.zlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zlibrary.R;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.TypeUtil;

/**
 * Created by win7 on 2016/5/25.
 */
public class ZDialogProgres extends Dialog{
    private Context context;
    private   TextView tv;
    private Handler handler;
    private int httptimeout=10000;
    public ZDialogProgres()
    {
        this(ZActivityManager.getInstance().getLastActivity());
        context=ZActivityManager.getInstance().getLastActivity();
        init();
    }
 public ZDialogProgres(Context context)
 {
     super(context,R.style.Basedialog);
     this.context=context;
     init();
 }
    public ZDialogProgres(Context context, int theme)
    {
        super(context,theme);
        this.context=context;
        init();
    }
    private void init()
    {
        handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
               if(isShowing()==true)
                   dismiss();
                super.handleMessage(msg);
            }
        };
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_progress, null);
        addContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        View progressBar=(View)layout.findViewById(R.id.progressBar);
        tv=(TextView)layout.findViewById(R.id.tv);
    }
    public ZDialogProgres showProgress()
    {
        if(!isShowing()) {
            Window window = ZDialogProgres.this.getWindow();
            window.setGravity(Gravity.CENTER);
            show();
        }
        return this;
    }
   public void setText(String str)
   {
       tv.setText(str);
       tv.setVisibility(View.VISIBLE);
   }
    public ZDialogProgres setTimeOut(int httptimeout)
    {
        this.httptimeout=httptimeout;
        return this;
    }
    public ZDialogProgres showHttpProgress()
    {
        if(!isShowing()) {
            Window window = ZDialogProgres.this.getWindow();
            window.setGravity(Gravity.CENTER);
            ZDialogProgres.this.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            tv.setVisibility(View.GONE);
            show();
            handler.sendEmptyMessageDelayed(1, httptimeout);
        }
        return this;
    }
    public ZDialogProgres showHttpProgress(String str)
    {
        Window window = ZDialogProgres.this.getWindow();
        window.setGravity(Gravity.CENTER);
        ZDialogProgres.this.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        setText(str);
        show();
        handler.sendEmptyMessageDelayed(1,httptimeout);
        return this;
    }
}
