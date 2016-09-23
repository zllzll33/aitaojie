package com.android.zlibrary.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2016/6/12.
 */
public abstract  class ZMoreAdapter<T> extends ZModelAdapter<T> {
    private List<T> zmlistModel,mmlistModel;
    public ZMoreAdapter(List<T> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
        mmlistModel=mlistModel;
    }
  public List<T> getSonList(int num)
  {
      if(num<mmlistModel.size())
      {
          zmlistModel=new ArrayList<T>();
          for(int i=0;i<num;i++)
          {
              zmlistModel.add(mmlistModel.get(i));
          }
      }
      else
          zmlistModel=mmlistModel;
      return zmlistModel;
  }
    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, T model) {

    }

    @Override
    protected int layoutId() {
        return 0;
    }
}
