package com.android.zlibrary.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by win7 on 2016/5/23.
 */
public abstract class ZModelAdapter<T> extends ZBaseAdapter {
    protected  View view;
    public ZModelAdapter(List<T> mlistModel , Activity mActivity)
    {
        super(mlistModel,mActivity);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            view=  LayoutInflater.from(mActivity).inflate(layoutId(),parent,false);
            T model=  (T)getItem(position);
            BindModel(position,view,parent,model);
            return view;

    }
    protected abstract void BindModel(int position, View convertView, ViewGroup parent,T model);
    protected abstract  int layoutId();
    public void addMoreList(List<T> mzList)
    {
        if(mzList==null)
            return;
        if(mzList.size()==0)
            return;
        for(int i=0;i<mzList.size();i++)
        {
            mlistModel.add(mzList.get(i));
        }
        notifyDataSetChanged();
    }
    public class ZOnClickListener implements View.OnClickListener {
        public  int position;
        public  T model;
        public View view;
        public ZOnClickListener(int position, View view, T model)
        {
            this.model=model;
            this.position=position;
            this.view=view;
        }
        @Override
        public void onClick(View v) {

        }
    }
}
