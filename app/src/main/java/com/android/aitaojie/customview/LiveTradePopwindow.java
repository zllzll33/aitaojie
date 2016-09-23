package com.android.aitaojie.customview;

import android.content.Context;
import android.widget.GridView;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.model.LiveModel;
import com.android.zlibrary.adapter.ZSimpleTextAdapter;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.customView.ZBasePopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2016/8/5.
 */
public class LiveTradePopwindow extends ZBasePopupWindow{
    GridView grid1;
    List<LiveModel.TradeBean> tradeBeanList;
    private SelectListener selectListener;
    public LiveTradePopwindow(Context context) {
        super(context);
    }
    @Override
    protected int layoutId() {
        return R.layout.pop_live_trade;
    }
    @Override
    protected void init() {
        super.init();
        tradeBeanList= Constant.trade;
        if(tradeBeanList==null)
        return;
        grid1=(GridView)view.findViewById(R.id.grid1);
        if (tradeBeanList == null)
          return;
        if(!tradeBeanList.get(0).getLabel().equals("全部"))
        {
            LiveModel.TradeBean tradeBean = new LiveModel.TradeBean();
            tradeBean.setLabel("全部");
            tradeBean.setValue("0");
            tradeBeanList.add(0, tradeBean);
        }
        ZSimpleTextAdapter<LiveModel.TradeBean> tradeAdapter = new ZSimpleTextAdapter<>(tradeBeanList, ZActivityManager.getInstance().getLastActivity());
        tradeAdapter.setMenuNameListener(new ZSimpleTextAdapter.MenuNameListener() {
            @Override
            public void MenuName(TextView tv, int position) {
                tv.setText(tradeBeanList.get(position).getLabel());
            }

            @Override
            public void OnClick(int index) {
                Dismiss();
              if(selectListener!=null)
              {
                  String[] trade=new String[2];
                  trade[0]=tradeBeanList.get(index).getValue();
                  trade[1]=tradeBeanList.get(index).getLabel();
                  selectListener.onSelect(trade);
              }
            }

        });
        grid1.setAdapter(tradeAdapter);
    }
    public void setSelectListener(SelectListener selectListener)
    {
        this.selectListener=selectListener;
    }
    public   interface SelectListener
    {
        public void onSelect(String[] trade);
    }
}
