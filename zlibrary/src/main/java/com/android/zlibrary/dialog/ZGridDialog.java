package com.android.zlibrary.dialog;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.android.zlibrary.customView.ZGridView;

/**
 * Created by win7 on 2016/6/15.
 */
public class ZGridDialog extends ZBaseDialog{
    private BaseAdapter baseAdapter;
    private ZGridView zGridView;
    private ZGridViewListener zGridViewListener;
    public ZGridDialog() {
        super();
    }
    public ZGridDialog(Context context) {
        super(context);
    }

    public ZGridDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void initContentView(LinearLayout ll_content) {
        super.initContentView(ll_content);
         zGridView=new ZGridView(context);
        LinearLayout.LayoutParams glp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        zGridView.setLayoutParams(glp);
        ll_content.addView(zGridView);
        zGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (zGridViewListener != null) {
                    zGridViewListener.onItemClick(view, (int) id, ZGridDialog.this);
                }
                dismiss();
            }
        });
    }
    public ZGridDialog setGridColumn(int num) {
        zGridView.setColums(num);
        return this;
    }
    @Override
    public ZGridDialog showDialog()
    {
        setAdapter(baseAdapter);
        super.showDialog();
        return this;
    }
    public ZGridDialog setAdapter(BaseAdapter baseAdapter)
    {
        this.baseAdapter=baseAdapter;
        zGridView.setAdapter(baseAdapter);
        return this;
    }
    public ZGridDialog setZGridViewListener(ZGridViewListener zGridViewListener)
    {
        this.zGridViewListener=zGridViewListener;
        return this;
    }
    public interface ZGridViewListener extends ZDialogListener{
        public void onItemClick(View v, int index, ZBaseDialog dialog);
    }
}
