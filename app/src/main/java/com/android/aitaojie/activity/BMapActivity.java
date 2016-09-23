package com.android.aitaojie.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.bmap.BMapManager;
import com.android.aitaojie.config.App;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.BMapModel;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/6/18.
 */
public class BMapActivity extends BaseActivity {
    @InjectView(R.id.bmapView)
    MapView bmapView;
    BaiduMap baiduMap;

    /**
     * name : 茅台镇-地方特色
     * address : 贵州省遵义市仁怀市茅台镇
     * views : 222
     * likes : 555
     * lat :
     * lng :
     * jid : 67
     * img : http://www.luofangyun.comhttp://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/02/04/ChMkJlbKyBmIHnT1AARH98qGLIsAALH9QAYXO8ABEgP381.jpg
     * logo : http://www.luofangyun.comhttp://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/02/04/ChMkJlbKyBmIHnT1AARH98qGLIsAALH9QAYXO8ABEgP381.jpg
     * distance : 800米
     * trade : 美食
     */

    private List<BMapModel.MerchantBean> merchant;
   private BMapModel bMapModel;
    protected int layoutId() {
        return R.layout.act_bmap;
    }
    @Override
    protected void initBeforeView() {
        super.initBeforeView();
        SDKInitializer.initialize(App.getInstance()); //必须在setcontentView 前面
    }
    protected void Init() {
        super.Init();
     /*   Intent intent=getIntent();
        List<LiveFragment.Location> locationList=(List<LiveFragment.Location>)intent.getSerializableExtra("location");*/
        setTitle("生活圈");
        baiduMap=bmapView.getMap();
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("Live");
        httpMap.putCtl("Index");
        httpMap.putAct("map");
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                Gson gson=new Gson();
                bMapModel=gson.fromJson(data,BMapModel.class);
                merchant=bMapModel.getMerchant();
                if(merchant==null)
                    return;
                for(int i=0;i<merchant.size();i++) {
                    if(!merchant.get(i).getLat().equals("")) {
                        LatLng loca = new LatLng(Double.parseDouble(merchant.get(i).getLat()),Double.parseDouble(merchant.get(i).getLng()));
                        View view = LayoutInflater.from(BMapActivity.this).inflate(R.layout.bmap_marker, null, false);
                        TextView tag = (TextView) view.findViewById(R.id.text);
                        tag.setText(merchant.get(i).getName());
                        ImageView icon = (ImageView) view.findViewById(R.id.icon);
                        BitmapDescriptor bitmap1 = BitmapDescriptorFactory.fromView(view);
                        OverlayOptions option = new MarkerOptions()
                                .position(loca)
                                .icon(bitmap1).title(merchant.get(i).getJid());
                        baiduMap.addOverlay(option);
                    }
                }
            }
        });

        LatLng point = new LatLng(BMapManager.getInstance().getLocationModel().getLatitude(),BMapManager.getInstance().getLocationModel().getLongitude());
//        LatLng point = new LatLng(lat,lng);
        //设置中心位置、图层level  level小  图缩小
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().target(point).zoom(15).build()));
        View my_view = LayoutInflater.from(BMapActivity.this).inflate(R.layout.bmap_marker, null, false);
        TextView my_tag = (TextView) my_view.findViewById(R.id.text);
        my_tag.setText("我");
        ImageView my_icon = (ImageView) my_view.findViewById(R.id.icon);
        my_icon.setImageResource(R.mipmap.location_icon_my);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(my_view);
        OverlayOptions my_option = new MarkerOptions()
                .position(point)
                .icon(bitmap).title("0");
        baiduMap.addOverlay(my_option);
 /*       BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.location_icon);*/
        //构建MarkerOption，用于在地图上添加Marker

      /*  baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//               int id=13;
//                SDEventManager.post(id, EnumEventTag.GO_HOME_FRAGMENT.ordinal());
                return false;
            }
        });*/
    }


}
