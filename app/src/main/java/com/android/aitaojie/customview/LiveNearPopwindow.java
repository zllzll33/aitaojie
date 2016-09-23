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
import com.android.zlibrary.customView.ZGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/8/5.
 */
public class LiveNearPopwindow extends ZBasePopupWindow {
    List<LiveModel.AreaBean> areaBeanList;
    GridView grid1;
    GridView grid2;
    private  int grid1Index;
   private SelectListener selectListener;
    public LiveNearPopwindow(Context context) {
        super(context);
    }

    @Override
    protected int layoutId() {
        return R.layout.pop_live_near;
    }
    @Override
    protected void init() {
        super.init();
        grid1=(GridView)view.findViewById(R.id.grid1);
        grid2=(GridView)view.findViewById(R.id.grid2);
        areaBeanList = Constant.area;
        if (areaBeanList == null)
            areaBeanList = new ArrayList<>();
        if(areaBeanList.size()>0) {
            if (!areaBeanList.get(0).getLabel().equals("全部")) {
                LiveModel.AreaBean allareaBean = new LiveModel.AreaBean();
                allareaBean.setLabel("全部");
                areaBeanList.add(0, allareaBean);
                if(Constant.distance!=null) {
                    LiveModel.AreaBean areaBean = new LiveModel.AreaBean();
                    areaBean.setLabel("附近");
                    areaBeanList.add(1, areaBean);
                }
            }
        }
        else
        {
            if(Constant.distance!=null)
            {
                LiveModel.AreaBean allareaBean = new LiveModel.AreaBean();
                allareaBean.setLabel("全部");
                areaBeanList.add(0, allareaBean);
                LiveModel.AreaBean areaBean = new LiveModel.AreaBean();
                areaBean.setLabel("附近");
                areaBeanList.add(1, areaBean);
            }
        }

        ZSimpleTextAdapter<LiveModel.AreaBean> nearAdapter = new ZSimpleTextAdapter<>(areaBeanList, ZActivityManager.getInstance().getLastActivity());
        nearAdapter.setMenuNameListener(new ZSimpleTextAdapter.MenuNameListener() {
            @Override
            public void MenuName(TextView tv, int position) {
                tv.setText(areaBeanList.get(position).getLabel());
            }
            @Override
            public void OnClick(final int index) {
                grid1Index=index;
              if(index ==0||index==1)
              {
                  if(grid1Index==0) {
                      if(selectListener!=null) {
                          String[] area = new String[3];
                          area[0] = "0";
                          area[2] = "全部";
                          selectListener.onSelect(area);
                      }
                      Dismiss();
                  }
                  else {
                      ZSimpleTextAdapter<LiveModel.DistanceBean> distanceAdapter = new ZSimpleTextAdapter<LiveModel.DistanceBean>(Constant.distance, ZActivityManager.getInstance().getLastActivity());
                      distanceAdapter.setMenuNameListener(new ZSimpleTextAdapter.MenuNameListener() {
                          @Override
                          public void MenuName(TextView tv, int position) {
                              tv.setText(Constant.distance.get(position).getLabel());
                          }

                          @Override
                          public void OnClick(int index) {
                              Dismiss();
                              if (selectListener != null) {

                                  if (grid1Index == 1) {
                                      String[] area = new String[3];
                                      area[0] = "1";
                                      area[1] = Constant.distance.get(index).getValue();
                                      area[2] = Constant.distance.get(index).getLabel();
                                      selectListener.onSelect(area);
                                  }
                              }

                          }
                      });
                      grid2.setAdapter(distanceAdapter);
                  }
              }
                else
              {
                  ZSimpleTextAdapter<LiveModel.AreaBean.StreetBean> streetAdapter=new ZSimpleTextAdapter(Constant.area.get(index).getStreet(),ZActivityManager.getInstance().getLastActivity());
                  streetAdapter.setMenuNameListener(new ZSimpleTextAdapter.MenuNameListener() {
                      @Override
                      public void MenuName(TextView tv, int position) {
                          tv.setText(Constant.area.get(index).getStreet().get(position).getLabel());
                      }

                      @Override
                      public void OnClick(int index) {
                          Dismiss();
                          if(selectListener!=null) {
                              String[] area = new String[3];
                              area[0] = "2";
                              area[1] = Constant.area.get(grid1Index).getStreet().get(index).getValue();
                              area[2] = Constant.area.get(grid1Index).getStreet().get(index).getLabel();
                              selectListener.onSelect(area);
                          }

                      }
                  });
                  grid2.setAdapter(streetAdapter);
              }
            }

        });
        grid1.setAdapter(nearAdapter);
        nearAdapter.getView(1,null,null).performClick();

    }
    public void setSelectListener(SelectListener selectListener)
    {
        this.selectListener=selectListener;
    }
  public   interface SelectListener
    {
        public void onSelect(String[] area);
    }
}
