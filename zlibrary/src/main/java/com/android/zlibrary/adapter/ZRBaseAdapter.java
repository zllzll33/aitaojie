package com.android.zlibrary.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/5/24.
 */
public abstract class ZRBaseAdapter<T> extends RecyclerView.Adapter{
    protected List<T> mListModel;
    protected Activity mActivity;
    protected View view;
    public ZRBaseAdapter(List<T> mListModel,Activity mActivity)
    {
        this.mListModel=mListModel;
        this.mActivity=mActivity;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         view = LayoutInflater.from(mActivity).inflate(layoutId(),parent, false);
       ZViewHolder viewHolder = new ZViewHolder(view);
        initView(viewHolder);
        return viewHolder;
    }
    public  void initView(ZViewHolder viewHolder)
    {
        viewHolder.initView();
    }
    protected abstract int layoutId();
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BindModel(holder,view,position,mListModel.get(position));
    }
public abstract void BindModel(RecyclerView.ViewHolder holder,View convertView,int position,T model);
    @Override
    public int getItemCount() {
        return mListModel.size();
    }
    public  class ZViewHolder extends RecyclerView.ViewHolder
    {
        private View Hview;
        public ZViewHolder(View view)
        {
            super(view);
            this.Hview=view;
        }
       public  void initView()
       {
           ButterKnife.inject(this, Hview);
       }
    }

}
