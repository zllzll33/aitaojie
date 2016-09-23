package com.android.zlibrary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.zlibrary.R;
import com.android.zlibrary.base.ZFragmentManager;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;
import com.sunday.eventbus.SDEventObserver;

import butterknife.ButterKnife;

/**
 * Created by win7 on 2016/5/3.
 */
public abstract class ZBaseFragment extends Fragment implements SDEventObserver {
    protected boolean isEventRegister=false;
    protected View view;
    private ZFragmentManager mFragmentManager;
    protected Handler zHander;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(layoutId(), container, false) ;
        ButterKnife.inject(this,view);
        Init();
        zHander =new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                handerReceive(msg.what);
                super.handleMessage(msg);
            }
        };
        return view;
    }
    public void setNotRegister()
    {
        isEventRegister=true;
    }
     protected abstract int layoutId();
    protected void handerReceive(int index)
    {

    }
    protected void Init()
    {
        if(!isEventRegister) {
            SDEventManager.register(this);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    public ZFragmentManager getZFragmentManager()
    {

        if (mFragmentManager == null)
        {
            mFragmentManager = new ZFragmentManager(getChildFragmentManager());
        }
        return mFragmentManager;
    }
    @Override
    public void onEvent(SDBaseEvent event)
    {

    }

    @Override
    public void onEventMainThread(SDBaseEvent event)
    {

    }

    @Override
    public void onEventBackgroundThread(SDBaseEvent event)
    {

    }

    @Override
    public void onEventAsync(SDBaseEvent event)
    {

    }
}
