package com.android.aitaojie.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.activity.BMapActivity;
import com.android.aitaojie.activity.CitySelectActivity;
import com.android.aitaojie.activity.ShopSearchActivity;
import com.android.aitaojie.adapter.LiveListAdapter;
import com.android.aitaojie.bmap.BMapManager;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.customview.LiveFilterPopWindow;
import com.android.aitaojie.customview.LiveNearPopwindow;
import com.android.aitaojie.customview.LiveTradePopwindow;
import com.android.aitaojie.model.LiveModel;
import com.android.zlibrary.fragment.ZBaseFragment;
import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.ViewUtil;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.sunday.eventbus.SDBaseEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/25.
 */
public class LiveFragment extends ZBaseFragment {
    @InjectView(R.id.addr)
    TextView addr;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.b_map)
    ImageView bMap;
    @InjectView(R.id.search)
    ImageView search;
    @InjectView(R.id.distance)
    TextView distance;
    @InjectView(R.id.ll_distance)
    LinearLayout llDistance;
    @InjectView(R.id.classifi)
    TextView classifi;
    @InjectView(R.id.ll_classifi)
    LinearLayout llClassifi;
    @InjectView(R.id.screening)
    TextView screening;
    @InjectView(R.id.ll_screening)
    LinearLayout llScreening;
    @InjectView(R.id.grid)
    PullToRefreshGridView grid;
    @InjectView(R.id.ll_addr)
    LinearLayout llAddr;
 private String m_distance="",street="", m_trade ="";
    private Map<String,String> filterMap;
    List<LiveModel.MerchantBean> merchantBeanList;
    int page=1;
    LiveListAdapter liveListAdapter;
    @Override
    protected int layoutId() {
        return R.layout.frag_live;
    }
    @Override
    protected void Init() {
        super.Init();
        addr.setText(BMapManager.getInstance().getLocationModel().getCityName());
        grid.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        grid.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                httpFilter();
            }
        });
        httpFilter();
   /*     HttpMap httpMap=new HttpMap();
        httpMap.putMode("live");
        httpMap.putCtl("index");
        httpMap.putAct("index");
        httpMap.putDataMap("uid", Constant.UID);
        httpMap.putDataMap("lat", String.valueOf(BMapManager.getInstance().getLocationModel().getLatitude()));
        httpMap.putDataMap("lng", String.valueOf(BMapManager.getInstance().getLocationModel().getLongitude()));
        if(!Constant.CityCode.isEmpty())
            httpMap.putDataMap("city_code",Constant.CityCode);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                Gson gson=new Gson();
                liveModel=gson.fromJson(data,LiveModel.class);
                Constant.area=liveModel.getArea();
                Constant.distance=liveModel.getDistance();
                Constant.trade=liveModel.getTrade();
                Constant.filter=liveModel.getFilter();
                LiveListAdapter liveListAdapter = new LiveListAdapter(liveModel.getMerchant(), getActivity());
                grid.setAdapter(liveListAdapter);
            }
        });*/

        llAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CitySelectActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ShopSearchActivity.class);
                startActivity(intent);
            }
        });
        bMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BMapActivity.class);

               /* List<Location> locationList = new ArrayList<Location>();
                Location my_location = new Location(BMapManager.getInstance().getLocationModel().getLatitude(),BMapManager.getInstance().getLocationModel().getLongitude(),"我","0");
                locationList.add(my_location);
                if(liveModel.getMerchant()!=null) {
                    for(int i=0;i<liveModel.getMerchant().size();i++) {
                        LiveModel.MerchantBean merchantBean=liveModel.getMerchant().get(i);
                        double lat,lng;
                        if(merchantBean.getLat().isEmpty()) {
                           lat = 0;
                            lng=0;
                        }
                        else
                        {
                            lat=Double.parseDouble(merchantBean.getLat());
                            lng=Double.parseDouble(merchantBean.getLng());
                        }
                        Location store_location = new Location(lat,lng,merchantBean.getName(),merchantBean.getJid());
                        locationList.add(store_location);
                    }
                }
                intent.putExtra("location", (Serializable) locationList);*/
                startActivity(intent);
            }
        });
        llDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveNearPopwindow liveNearPopwindow=new LiveNearPopwindow(getActivity());
                liveNearPopwindow.setOutSideDismiss();
                liveNearPopwindow.showAsDown(llDistance);
                liveNearPopwindow.setSelectListener(new LiveNearPopwindow.SelectListener() {
                    @Override
                    public void onSelect(String[] area) {
                        if(area[0].equals("0"))
                        {
                            distance.setText(area[2]);
                            m_distance="";
                            street="";
                        }
                        if(area[0].equals("1"))
                        {
                            m_distance=area[1];
                            street="";
                            distance.setText(area[2]);
                        }
                        if(area[0].equals("2"))
                        {
                            m_distance="";
                            street=area[1];
                            distance.setText(area[2]);
                        }
                        page=0;
                        httpFilter();
                    }
                });
            }
        });
        llClassifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveTradePopwindow liveTradePopwindow=new LiveTradePopwindow(getActivity());
                liveTradePopwindow.setOutSideDismiss();
                liveTradePopwindow.showAsDown(llClassifi);
                liveTradePopwindow.setSelectListener(new LiveTradePopwindow.SelectListener() {
                    @Override
                    public void onSelect(String[] trade) {
                        if(trade[0].equals("0"))
                            m_trade="";
                        else
                          m_trade=trade[0];
                        classifi.setText(trade[1]);
                        page=0;
                        httpFilter();
                    }
                });
            }
        });
        llScreening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiveFilterPopWindow liveFilterPopWindow=new LiveFilterPopWindow(getActivity());
                liveFilterPopWindow.setOutSideDismiss();
                liveFilterPopWindow.setOnComfirmListener(new LiveFilterPopWindow.OnComfirmListener() {
                    @Override
                    public void onComfirm(Map<String, String> map) {
                        filterMap=map;
                        page=0;
                        httpFilter();
                    }
                });
                liveFilterPopWindow.showAsDown(llScreening);
            }
        });
    }
public void httpFilter()
{
    HttpMap httpMap=new HttpMap();
    httpMap.putMode("live");
    httpMap.putCtl("index");
    httpMap.putAct("index");
    httpMap.putDataMap("page",String.valueOf(page));
    if(!Constant.CityCode.isEmpty())
        httpMap.putDataMap("city_code",Constant.CityCode);
    httpMap.putDataMap("uid", Constant.UID);
    httpMap.putDataMap("lat", String.valueOf(BMapManager.getInstance().getLocationModel().getLatitude()));
    httpMap.putDataMap("lng", String.valueOf(BMapManager.getInstance().getLocationModel().getLongitude()));
    if(!m_distance.isEmpty())
    httpMap.putDataMap("distance", m_distance);
    if(!street.isEmpty())
        httpMap.putDataMap("area", street);
    if(!m_trade.isEmpty())
        httpMap.putDataMap("trade", m_trade);
    if(filterMap!=null)
    {
        Map.Entry entry;
        for(Iterator iterator = filterMap.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (Map.Entry)iterator.next();
            httpMap.putDataMap(entry.getKey().toString(),entry.getValue().toString());
        }
    }
    filterMap=null;
    httpMap.setHttpListener(new HttpMap.HttpListener() {
        @Override
        public void onSuccess(String response, String data) {
            Gson gson=new Gson();
            LiveModel liveModel=gson.fromJson(data,LiveModel.class);
            Constant.area=liveModel.getArea();
            Constant.distance=liveModel.getDistance();
            Constant.trade=liveModel.getTrade();
            Constant.filter=liveModel.getFilter();
            if(page==1) {
                grid.onRefreshComplete();
                merchantBeanList=liveModel.getMerchant();
               liveListAdapter = new LiveListAdapter(merchantBeanList, getActivity());
                grid.setAdapter(liveListAdapter);
                page++;
            }
            else{
                grid.onRefreshComplete();
                if(liveModel.getMerchant().size()==0) {
                    ViewUtil.showToast(ResourceUtil.getString(R.string.no_more_toast));
                    return;
                }
                liveListAdapter.addMoreList(liveModel.getMerchant());
                page++;
                }

        }
    });
}
    @Override
    public void onEventMainThread(SDBaseEvent event)
    {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt()))
        {
            case CHANGE_LIVE_CITY:
                String city_name = (String)event.getData();
                addr.setText(city_name);
                page=1;
                httpFilter();
                break;
        }
    }

}
