package com.android.aitaojie.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.activity.MessageActivity;
import com.android.aitaojie.activity.MyFootActivity;
import com.android.aitaojie.activity.WebViewActivity;
import com.android.aitaojie.config.App;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.ChatModel;
import com.android.aitaojie.model.HomeIndexModel;
import com.android.aitaojie.util.ViewSwitchUtil;
import com.android.aitaojie.zxing.MyCaptureActivity;
import com.android.aitaojie.zxing.ScanResultHandler;
import com.android.zlibrary.adv.ADInfo;
import com.android.zlibrary.customView.ZSwipeBothRefresh;
import com.android.zlibrary.fragment.ZBaseFragment;
import com.android.zlibrary.util.SysUtil;
import com.android.zlibrary.util.ViewUtil;
import com.google.gson.Gson;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/19.
 */
public class HomeFragment extends ZBaseFragment {
    @InjectView(R.id.swipe_layout)
    ZSwipeBothRefresh swipeLayout;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    @InjectView(R.id.adv)
    FrameLayout adv;
    @InjectView(R.id.focus_num)
    TextView focusNum;
    @InjectView(R.id.browse_num)
    TextView browseNum;
    @InjectView(R.id.tag)
    RelativeLayout tag;
    @InjectView(R.id.collect_store)
    TextView collectStore;
    @InjectView(R.id.icon_map)
    ImageView iconMap;
    @InjectView(R.id.addr)
    TextView addr;
    @InjectView(R.id.icon_call)
    ImageView iconCall;
    @InjectView(R.id.channel)
    FrameLayout channel;
    @InjectView(R.id.coupon)
    FrameLayout coupon;
    @InjectView(R.id.pic)
    FrameLayout pic;
    @InjectView(R.id.good_grid)
    FrameLayout goodGrid;
    @InjectView(R.id.home_my_foot)
    ImageView homeMyFoot;
    @InjectView(R.id.cart)
    ImageView cart;
    @InjectView(R.id.message)
    ImageView message;
    @InjectView(R.id.scan)
    ImageView scan;
    @InjectView(R.id.direct)
    ImageView direct;
    @InjectView(R.id.rl_shop_info)
    RelativeLayout rlShopInfo;
    @InjectView(R.id.title)
    TextView title;
    private HomeIndexModel homeIndexModel;
    ChatModel chatModel;
    @Override
    protected int layoutId() {
        return R.layout.frag_home;
    }
    @Override
    protected void Init() {
        super.Init();
        httpHome();
        swipeLayout.setNoBottomRefresh();
        swipeLayout.setBottomRefreshListener(new ZSwipeBothRefresh.RefreshListener() {
            @Override
            public void onBottomRefresh() {
            }

            @Override
            public void onTopRefresh() {
                httpHome();
            }
        });
        homeMyFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyFootActivity.class);
                startActivity(intent);
            }
        });
        homeMyFoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MyFootActivity.class);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), WebViewActivity.class);
                String weburl=String.format(Constant.MyCartUrl,Constant.UID);
                intent.putExtra("webUrl",weburl);
                startActivity(intent);
            }
        });
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(App.getInstance(), Constant.CameraPermision);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Constant.CameraPermision}, Constant.PermissionCamera);
                        return;
                    } else {
                        setZXingListener();
                    }
                } else {
                    setZXingListener();
                }

            }
        });
    }

    @Override
    public void onEventMainThread(SDBaseEvent event) {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt())) {
            case GO_HOME_FRAGMENT:
                String jid = (String) event.getData();
                if (!Constant.JID.equals(jid)) {
                    Constant.JID = jid;
                    httpHome();
                }

                break;
            case GET_HOME_CHAT:
             SDEventManager.post(chatModel,EnumEventTag.NEW_CHAT_USER.ordinal());
                break;
        }
    }

    public void httpHome() {
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("Merchant");
        httpMap.putCtl("index");
        httpMap.putAct("index");
        httpMap.putDataMap("jid", Constant.JID);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                swipeLayout.setTopRefreshComplete();
                Gson gson = new Gson();
                homeIndexModel = gson.fromJson(data, HomeIndexModel.class);
                title.setText(homeIndexModel.getShop().getName());
                AdvFragment mAdvFragment = new AdvFragment();
                List<ADInfo> infos = new ArrayList<ADInfo>();
                if( homeIndexModel.getSlide().size()>0) {
                    for (int i = 0; i < homeIndexModel.getSlide().size(); i++) {
                        ADInfo adInfo = new ADInfo();
                        adInfo.setUrl(homeIndexModel.getSlide().get(i).getImg());
                        infos.add(adInfo);
                    }
                    mAdvFragment.setAdInfoList(infos);
                    mAdvFragment.setAdvListener(new AdvFragment.AdvListener() {
                        @Override
                        public void OnClick(int position) {
                            ViewSwitchUtil.ViewSwicth(homeIndexModel.getSlide().get(position).getUrl(), homeIndexModel.getSlide().get(position).getPk());
                        }
                    });
                }
                getZFragmentManager().rtoggle(R.id.adv, mAdvFragment);
                HomeChannelFragment homeChannelFragment = new HomeChannelFragment();
                homeChannelFragment.setChannelList(homeIndexModel.getNavigation());
                getZFragmentManager().rtoggle(R.id.channel, homeChannelFragment);
                Constant.JID = homeIndexModel.getShop().getJid();
                focusNum.setText("关注量:" + homeIndexModel.getShop().getLikes());
                browseNum.setText("浏览量:" + homeIndexModel.getShop().getViews());
                addr.setText(homeIndexModel.getShop().getAddress());
                register();
                HomeCouponFragment homeCouponFragment = new HomeCouponFragment();
                homeCouponFragment.setCouponList(homeIndexModel.getVoucher());
                getZFragmentManager().rtoggle(R.id.coupon, homeCouponFragment);
                HomePicFragment homePicFragment = new HomePicFragment();
                homePicFragment.setRecommendBean(homeIndexModel.getRecommend());
                getZFragmentManager().rtoggle(R.id.pic, homePicFragment);
                HomeGoodsFragment homeGoodsFragment = new HomeGoodsFragment();
                homeGoodsFragment.setGoodList(homeIndexModel.getGoods());
                getZFragmentManager().rtoggle(R.id.good_grid, homeGoodsFragment);
                 chatModel=new ChatModel();
                chatModel.setUid(Constant.UID);
                chatModel.setTrade(homeIndexModel.getShop().getTrade());
                chatModel.setAddress(homeIndexModel.getShop().getAddress());
                chatModel.setNum("0");
                chatModel.setCtime(SysUtil.getSysTimeFormatDay());
                chatModel.setHx_openid(homeIndexModel.getShop().getHxusername());
                chatModel.setLogo(homeIndexModel.getShop().getLogo());
                chatModel.setMerchant_name(homeIndexModel.getShop().getName());
                chatModel.setJid(Constant.JID);
            }
        });
    }

    public void register() {
        tag.setVisibility(View.VISIBLE);
        collectStore.setVisibility(View.VISIBLE);
        rlShopInfo.setVisibility(View.VISIBLE);
        iconCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = homeIndexModel.getShop().getScontactstel();
                Intent TelIntent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse("tel:" + mobile));
                startActivity(TelIntent);
            }
        });
        collectStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpMap httpMap = new HttpMap();
                httpMap.putMode("User");
                httpMap.putCtl("Collect");
                httpMap.putAct("addmerchant");
                httpMap.putDataMap("jid", Constant.JID);
                httpMap.putDataMap("uid", Constant.UID);
                httpMap.setHttpListener(new HttpMap.HttpListener() {
                    @Override
                    public void onSuccess(String response, String data) {

                    }
                });
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });
        direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat = homeIndexModel.getShop().getLat();
                String lng = homeIndexModel.getShop().getLng();
                String store_name = homeIndexModel.getShop().getName();
                if (SysUtil.isInstallAPK("com.baidu.BaiduMap")) {
                    String buri = "intent://map/marker?location=" + lat + "," + lng + "&title=我的位置&content=" + store_name + "&src= luofangyun|幸福商圈#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
                    try {
                        Intent intent = Intent.parseUri(buri, 0);
                        startActivity(intent); //启动调用
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else if (SysUtil.isInstallAPK("com.autonavi.minimap")) {
                    try {
                        String auri = "androidamap://viewMap?sourceApplication=" + "爱淘街" + "&poiname=" + store_name + "&lat" + lat + "&lon" + lng + "&dev=0";
                        Intent intent = Intent.parseUri(auri, 0);
                        startActivity(intent);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                } else
                    ViewUtil.showToast("请先安装百度或高德地图");
            }
        });
    }

    public void setZXingListener() {
        Intent zxingIntent = new Intent(getActivity(), MyCaptureActivity.class);
        ScanResultHandler mScanResultHandler = new ScanResultHandler(getActivity());
        mScanResultHandler.startScan(zxingIntent);
        mScanResultHandler.setmListener(new ScanResultHandler.ScanResultDealerListener() {
            @Override
            public void onResult(String result) {
                Log.e("scan_result", result);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constant.PermissionCamera:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setZXingListener();
                } else {
                    ViewUtil.showToast("请同意访问相机后 再扫描二维码");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}
