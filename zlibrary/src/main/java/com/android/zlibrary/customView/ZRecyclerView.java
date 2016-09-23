package com.android.zlibrary.customView;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.android.zlibrary.base.ZLibrary;

/**
 * Created by win7 on 2016/5/23.
 */

public class ZRecyclerView extends RecyclerView{
    public static final int GRID_V          = 0;
    public static final int GRID_H        = 1;
    public static final int STAGGERED_V        = 2;
    public static final int STAGGERED_H       = 3;
    private  int GRID_SPAN_COUNT       = 1;
    private  int STAGGERED_SPAN_COUNT  = 1;

    private  Context context;
    private RecyclerView.LayoutManager mLayoutManager;
    public ZRecyclerView(Context context)
    {
        super(context);
        this.context=context;
       mLayoutManager= new GridLayoutManager(context,GRID_SPAN_COUNT,LinearLayoutManager.VERTICAL,false);
    }
    public ZRecyclerView(Context context, AttributeSet attr)
    {
        super(context,attr);
        this.context=context;
        mLayoutManager= new GridLayoutManager(context,GRID_SPAN_COUNT,LinearLayoutManager.VERTICAL,false);
    }

    public  ZRecyclerView setLayoutType(int type)
    {
        setLayoutManager(setLayoutManager(type));
        return ZRecyclerView.this;
    }
    public ZRecyclerView setLayoutType(int type,int spanNum)
    {
        setLayoutManager(setLayoutManager(type,spanNum));
        return ZRecyclerView.this;
    }
    public ZRecyclerView setGridSpan(int num)
    {
        this.GRID_SPAN_COUNT=num;
        return ZRecyclerView.this;
    }
    public ZRecyclerView setStaggerSpan(int num)
    {
        this.STAGGERED_SPAN_COUNT=num;
        return ZRecyclerView.this;
    }
    private RecyclerView.LayoutManager setLayoutManager(int type,int spanNum)
    {

        switch (type)
        {
            case 0:
                this.GRID_SPAN_COUNT=spanNum;
                mLayoutManager= new GridLayoutManager(context,GRID_SPAN_COUNT,LinearLayoutManager.VERTICAL,false);
                break;
            case 1:
                this.GRID_SPAN_COUNT=spanNum;
                mLayoutManager= new GridLayoutManager(context,GRID_SPAN_COUNT,LinearLayoutManager.HORIZONTAL,false);
            break;
            case 2:
                this.STAGGERED_SPAN_COUNT=spanNum;
                mLayoutManager= new StaggeredGridLayoutManager(STAGGERED_SPAN_COUNT,StaggeredGridLayoutManager.VERTICAL);
            break;
            case 3:
                this.STAGGERED_SPAN_COUNT=spanNum;
                mLayoutManager= new StaggeredGridLayoutManager(STAGGERED_SPAN_COUNT,StaggeredGridLayoutManager.HORIZONTAL);
            break;
            default:
                break;
        }
        return  mLayoutManager;
    }
    private RecyclerView.LayoutManager setLayoutManager(int type)
    {
        switch (type)
        {
            case 0:
                mLayoutManager= new GridLayoutManager(context,GRID_SPAN_COUNT,LinearLayoutManager.VERTICAL,false);
                break;
            case 1:
                mLayoutManager= new GridLayoutManager(context,GRID_SPAN_COUNT,LinearLayoutManager.HORIZONTAL,false);
                break;
            case 2:
                mLayoutManager= new StaggeredGridLayoutManager(STAGGERED_SPAN_COUNT,StaggeredGridLayoutManager.VERTICAL);
                break;
            case 3:
                mLayoutManager= new StaggeredGridLayoutManager(STAGGERED_SPAN_COUNT,StaggeredGridLayoutManager.HORIZONTAL);
                break;
            default:
                break;
        }
        return  mLayoutManager;
    }
}
