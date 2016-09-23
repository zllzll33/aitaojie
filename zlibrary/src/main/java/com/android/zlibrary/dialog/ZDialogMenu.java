package com.android.zlibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.zlibrary.R;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.customView.ZGridView;

/**
 * Created by win7 on 2016/5/17.
 */
public class ZDialogMenu extends Dialog {
    private Context context;
    private ZGridView mLvContent;
    private TextView cancle;
    private ZMenuDialogListener zMenuDialogListener;
    private BaseAdapter adapter;
    private LinearLayout ll_menu_cancel;
    public ZDialogMenu()
    {
        this(ZActivityManager.getInstance().getLastActivity(),R.style.Basedialog);
    }
    public ZDialogMenu(Context context)
    {
        super(context,R.style.Basedialog);
        this.context=context;
        initView();
    }
    public ZDialogMenu(Context context, int theme) {
        super(context,theme);
        this.context=context;
        initView();
    }
    public  ZDialogMenu setzMenuDialogListener(ZMenuDialogListener zMenuDialogListener)
    {
        this.zMenuDialogListener=zMenuDialogListener;
        return ZDialogMenu.this;
    }
    public ZDialogMenu setCancleColor(int color)
    {
        cancle.setTextColor(color);
        return  this;
    }
    public  ZDialogMenu setAdapter(BaseAdapter adapter)
    {
        this.adapter=adapter;
        mLvContent.setAdapter(adapter);
        mLvContent.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (zMenuDialogListener != null)
                {
                    zMenuDialogListener.onItemClick(view, (int) id, ZDialogMenu.this);
                }
                dismiss();
            }
        });
        return this;
    }
    public void showDialog()
    {
        if(!isShowing()) {
            Window window = ZDialogMenu.this.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.menuDialogshowStyle);
            ZDialogMenu.this.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            setAdapter(adapter);
           show();
        }
    }
    private void initView()
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_menu, null);
       addContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mLvContent=(ZGridView)layout.findViewById(R.id.dialog_menu_lv_content);

        cancle=(TextView)layout.findViewById(R.id.dialog_menu_tv_cancel);
        ll_menu_cancel=(LinearLayout) layout.findViewById(R.id.ll_menu_cancel);
        ll_menu_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zMenuDialogListener.onCancelClick(v,ZDialogMenu.this);
                dismiss();
            }
        });
    }
    public ZDialogMenu setGridColumn(int num)
    {
        mLvContent.setColums(num);
        return this;
    }
    public interface ZMenuDialogListener
    {
        public void onCancelClick(View v, ZDialogMenu dialog);
        public void onItemClick(View v, int index, ZDialogMenu dialog);
    }
}
