package com.android.aitaojie.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.GoodsListAdapter;
import com.android.aitaojie.adapter.MyGoodsAdapter;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.ClassifyGoodListModel;
import com.android.zlibrary.customView.ZGridView;
import com.android.zlibrary.fragment.ZBaseFragment;
import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.ViewUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.sunday.eventbus.SDEventManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by win7 on 2016/7/20.
 */
public class GoodListFragment extends ZBaseFragment {
    @InjectView(R.id.goods_grid)
    PullToRefreshGridView goodsGrid;
    String cid;
    List<ClassifyGoodListModel.GoodsBean> goodList;
   int page=1;
    GoodsListAdapter goodsListAdapter;
    ClassifyGoodListModel classifyGoodListModel;
    @Override
    protected int layoutId() {
        return R.layout.frag_test_good_list;
    }
    public void setCid(String cid)
    {
        this.cid=cid;
    }
    public void setGoodList(List<ClassifyGoodListModel.GoodsBean> goodList)
    {
        this.goodList=goodList;
    }
    @Override
    protected void Init() {
        setNotRegister();
        super.Init();
        page=1;
        goodsGrid.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        goodsGrid.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                httpGoods();

            }
        });
        httpGoods();

    }
  public void  httpGoods()
  {
      if(cid!=null) {
          HttpMap httpMap = new HttpMap();
          httpMap.putMode("Merchant");
          httpMap.putCtl("goods");
          httpMap.putAct("index");
          httpMap.putDataMap("jid", Constant.JID);
          httpMap.putDataMap("cid", cid);
          httpMap.putDataMap("page", String.valueOf(page));
          httpMap.setHttpListener(new HttpMap.HttpListener() {
              @Override
              public void onSuccess(String response, String data) {
                  if(!data.isEmpty()) {
                      Gson gson = new Gson();
                    classifyGoodListModel = gson.fromJson(data, ClassifyGoodListModel.class);
                      if(page==1) {
                          goodsGrid.onRefreshComplete();
                          if(classifyGoodListModel.getGoods().size()==0)
                              return;
                          goodsListAdapter = new GoodsListAdapter(classifyGoodListModel.getGoods(), getActivity());
                          goodsGrid.setAdapter(goodsListAdapter);
                          page++;
                      }
                      else
                      {
                          goodsGrid.onRefreshComplete();
                          if(classifyGoodListModel.getGoods().size()==0) {
                              ViewUtil.showToast(ResourceUtil.getString(R.string.no_more_toast));
                              return;
                          }
                          goodsListAdapter.addMoreList(classifyGoodListModel.getGoods());
                          page++;
                      }

                  }
                  else
                  {
                      ViewUtil.showToast(ResourceUtil.getString(R.string.no_more_toast));
                      goodsGrid.onRefreshComplete();
                  }
              }
          });
      }
      else{
          goodsListAdapter = new GoodsListAdapter(goodList, getActivity());
          goodsGrid.setAdapter(goodsListAdapter);
      }
  }

}
