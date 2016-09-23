package com.android.zlibrary.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2016/6/14.
 */
public class ZNaviAdapter<T> extends ZModelAdapter<T> {
    private List<View> viewList=new ArrayList<View>();
    public ZNaviAdapter(List<T> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);

    }

    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, T model) {
       if(!viewList.contains(convertView))
       {
           viewList.add(convertView);
       }
        Noraml(convertView,model,position);
    }
    public void setSelect(int position)
    {
       for(int i=0;i<mlistModel.size();i++)
       {
           Noraml(viewList.get(i),(T)mlistModel.get(i),i);
       }
        Select(viewList.get(position), (T)mlistModel.get(position),position);
    }
  protected void Noraml(View convertView,T model,int position)
  {

  }
    protected void Select(View convertView,T model,int position)
    {

    }
    @Override
    protected int layoutId() {
        return 0;
    }
}
