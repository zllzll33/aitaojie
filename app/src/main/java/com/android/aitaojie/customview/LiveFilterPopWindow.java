package com.android.aitaojie.customview;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.activity.LiveFilterTagAdapter;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.model.LiveModel;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.customView.ZBasePopupWindow;

import java.util.List;
import java.util.Map;

/**
 * Created by win7 on 2016/8/5.
 */
public class LiveFilterPopWindow extends ZBasePopupWindow {
    GridView grid1;
    TextView comfirm;
    OnComfirmListener onComfirmListener;
    private List<LiveModel.FilterBean> filterBeanList;
    public LiveFilterPopWindow(Context context) {
        super(context);
    }
    @Override
    protected int layoutId() {
        return R.layout.pop_live_filter;
    }
    @Override
    protected void init() {
        super.init();
        filterBeanList= Constant.filter;
        if(filterBeanList==null)
            return;
        grid1=(GridView)view.findViewById(R.id.grid1);
        comfirm=(TextView)view.findViewById(R.id.comfirm);
      final   LiveFilterTagAdapter liveFilterTagAdapter=new LiveFilterTagAdapter(filterBeanList, ZActivityManager.getInstance().getLastActivity());
        grid1.setAdapter(liveFilterTagAdapter);
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onComfirmListener!=null)
                    onComfirmListener.onComfirm(liveFilterTagAdapter.getTagMap());
                Dismiss();
            }
        });
    }
    public void setOnComfirmListener(OnComfirmListener onComfirmListener)
    {
        this.onComfirmListener=onComfirmListener;
    }
  public  interface  OnComfirmListener{
        public void onComfirm(Map<String,String> map);
    }
}
