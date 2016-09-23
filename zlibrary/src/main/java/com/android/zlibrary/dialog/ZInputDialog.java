package com.android.zlibrary.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.zlibrary.R;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.ViewUtil;

/**
 * Created by win7 on 2016/6/15.
 */
public class ZInputDialog extends ZBaseDialog {
    private ZInputDialogListener zInputDialogListener;
    EditText editText;
    public  ZInputDialog() {
        super(ZActivityManager.getInstance().getLastActivity(),R.style.Basedialog);
    }
    public ZInputDialog(Context context) {
        super(context);
    }

    public ZInputDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void initContentView(LinearLayout ll_content) {
        super.initContentView(ll_content);
         editText=new EditText(context);
        LinearLayout.LayoutParams elp=new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams vlp=new LinearLayout.LayoutParams(500, 2);
        View view=new View(context);
        view.setBackgroundColor(ResourceUtil.getColor(R.color.DivLine));
        view.setLayoutParams(vlp);
        View view1=new View(context);
        view1.setBackgroundColor(ResourceUtil.getColor(R.color.DivLine));
        view1.setLayoutParams(vlp);
        editText.setLayoutParams(elp);
        editText.setGravity(Gravity.CENTER);
        editText.setBackgroundResource(R.drawable.shape_dialog_edit);
        ll_content.removeAllViews();
        ll_content.addView(view);
        ll_content.addView(editText);
        ll_content.addView(view1);

    }
    @Override
    protected void onComfirm(View v) {
        if(editText.getText().toString().isEmpty())
            ViewUtil.showToast("请输入");
        else {
            if (zInputDialogListener != null)
                zInputDialogListener.onComfirm(v, editText.getText().toString(), ZInputDialog.this);
        }
    }
    @Override
    protected void onCancle(View v) {
        if(zInputDialogListener!=null)
            zInputDialogListener.onCancelClick(v,ZInputDialog.this);
    }
 public ZInputDialog setzInputDialogListener(ZInputDialogListener zInputDialogListener)
 {
     this.zInputDialogListener=zInputDialogListener;
     return this;
 }
    public interface ZInputDialogListener{
        public void onCancelClick(View v, ZBaseDialog dialog);
        public void onComfirm(View v, String editText,ZBaseDialog dialog);
    }
}
