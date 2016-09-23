package com.android.zlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zlibrary.R;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.customView.ZGridView;

/**
 * Created by win7 on 2016/6/15.
 */
public class ZBaseDialog extends Dialog {
    protected   Context context;
    protected TextView cancle,comfirm,dialog_menu_tv_title;
    private View view;
    private LinearLayout ll_content;
    private ZDialogListener zDialogListener;
    public ZBaseDialog()
    {
        this(ZActivityManager.getInstance().getLastActivity(),R.style.Basedialog);
        context=ZActivityManager.getInstance().getLastActivity();
        init();
    }
    public ZBaseDialog(Context context) {
        super(context, R.style.Basedialog);
        this.context=context;
        init();
    }

    public ZBaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context=context;
        init();
    }
    protected void init()
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_base, null);
        addContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        view=layout.findViewById(R.id.stroke);
        dialog_menu_tv_title=(TextView) layout.findViewById(R.id.dialog_menu_tv_title);
        ll_content=(LinearLayout)layout.findViewById(R.id.ll_content);
        initContentView(ll_content);
        cancle=(TextView)layout.findViewById(R.id.dialog_tv_cancel);
        comfirm=(TextView)layout.findViewById(R.id.dialog_tv_comfirm);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancle(v);
                dismiss();
            }
        });
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onComfirm(v);
            }
        });
    }
    protected void onComfirm(View v)
    {
        if(zDialogListener!=null)
            zDialogListener.onComfirm(v,ZBaseDialog.this);
        dismiss();
    }
    protected void onCancle(View v)
    {
        if(zDialogListener!=null)
            zDialogListener.onCancelClick(v,ZBaseDialog.this);
    }
    protected void initContentView(LinearLayout ll_content)
    {

    }
    public ZBaseDialog showDialog()
    {
        if(!isShowing()) {
            Window window = getWindow();
            window.setGravity(Gravity.CENTER);
            setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            show();
        }
        return this;

    }

    public ZBaseDialog setComfirmMode(int Mode)
    {
            view.setVisibility(View.GONE);
            cancle.setVisibility(View.GONE);
        return this;
    }
    public ZBaseDialog setZDialogListener(ZDialogListener zDialogListener)
    {
        this.zDialogListener=zDialogListener;
        return this;
    }
 public ZBaseDialog setTitle(String str)
 {
     dialog_menu_tv_title.setText(str);
     return this;
 }
    public interface ZDialogListener{
   public void onCancelClick(View v, ZBaseDialog dialog);
    public void onComfirm(View v, ZBaseDialog dialog);
}
}
