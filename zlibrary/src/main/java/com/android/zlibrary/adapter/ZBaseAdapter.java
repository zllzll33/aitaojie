package com.android.zlibrary.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by win7 on 2016/5/23.
 */
public abstract class ZBaseAdapter<T> extends BaseAdapter{
  protected   List<T> mlistModel;
    protected  Activity mActivity;
    public ZBaseAdapter(List<T> mlistModel , Activity mActivity)
    {
        this.mlistModel=mlistModel;
        this.mActivity=mActivity;
    }
    @Override
    public T getItem(int position) {
        return mlistModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      return null;
    }

    @Override
    public int getCount() {
        return mlistModel.size();
    }
}
