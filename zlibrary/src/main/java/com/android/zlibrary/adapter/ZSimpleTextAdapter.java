package com.android.zlibrary.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.zlibrary.R;

import java.util.List;

/**
 * Created by win7 on 2016/5/23.
 */
public class ZSimpleTextAdapter<T> extends ZModelAdapter {
    private MenuNameListener menuNameListener=null;
    public ZSimpleTextAdapter(List<T> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
    }
    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, Object model) {
        convertView.setBackgroundColor(0xffffff);
        TextView tvName = (TextView)convertView.findViewById(R.id.item_simple_text_tv_name);
        if(menuNameListener==null)
            tvName.setText(getItem(position).toString());
        else
            menuNameListener.MenuName(tvName,position);
        convertView.setOnClickListener(new ZOnClickListener(position,convertView,model)
        {
            @Override
            public void onClick(View v) {
                if(menuNameListener!=null)
                    menuNameListener.OnClick(position);
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.item_simple_text;
    }
    public void  setMenuNameListener(MenuNameListener menuNameListener)
    {
        this.menuNameListener=menuNameListener;
    }
    public interface MenuNameListener{
         public void MenuName(TextView tv, int position);
        public void OnClick(int index);
    }

}
