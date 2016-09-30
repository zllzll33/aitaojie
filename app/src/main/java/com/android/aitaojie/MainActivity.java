package com.android.aitaojie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.android.aitaojie.bmap.BMapManager;
import com.android.aitaojie.config.App;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.custom.UpChatListBroadcastReceiver;
import com.android.aitaojie.fragment.ChatListFragment;
import com.android.aitaojie.fragment.HomeFragment;
import com.android.aitaojie.fragment.LiveFragment;
import com.android.aitaojie.fragment.MyFragment;
import com.android.aitaojie.model.ChatModel;
import com.android.aitaojie.wxapi.WxpayManager;
import com.android.zlibrary.base.ZActivityManager;
import com.android.aitaojie.activity.ZBaseActivity;
import com.android.zlibrary.base.ZBaseView;
import com.android.zlibrary.base.ZNavigatorManager;
import com.android.zlibrary.customView.ZTabItemBottomView;
import com.android.zlibrary.util.ResourceUtil;
import com.android.zlibrary.util.SysUtil;
import com.android.zlibrary.util.TypeUtil;
import com.android.zlibrary.util.ViewUtil;
import com.baidu.mapapi.SDKInitializer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.igexin.sdk.PushManager;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
public class MainActivity extends ZBaseActivity {
    @InjectView(R.id.frag_main)
    FrameLayout fragMain;
    @InjectView(R.id.tab1)
    ZTabItemBottomView tab1;
    @InjectView(R.id.tab2)
    ZTabItemBottomView tab2;
    @InjectView(R.id.tab3)
    ZTabItemBottomView tab3;
    @InjectView(R.id.tab4)
    ZTabItemBottomView tab4;
    @InjectView(R.id.tabbar)
    LinearLayout tabbar;
    private long mExitTime = 0;
    HomeFragment homeFragment;
    ChatListFragment chatListFragment;
    LiveFragment liveFragment;
    MyFragment myFragment;
    private List<ChatModel> chatList;
    @Override
    protected int layoutId() {
        return R.layout.act_main;
    }
    @Override
    protected void Init() {
        super.Init();
        LocationPermission();
        PushManager.getInstance().initialize(this.getApplicationContext());
        httpChatList();
        registerReceiver(mHomeKeyEventReceiver, new IntentFilter(
                Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        homeFragment=new HomeFragment();
        chatListFragment=new ChatListFragment();
        liveFragment=new LiveFragment();
        myFragment=new MyFragment();
        initTabBottom();
    }
    private void initTabBottom() {
        tab1.setText("首页").setTextNormalColor(ResourceUtil.getColor(R.color.tabottomNormal)).setTextPressColor(ResourceUtil.getColor(R.color.tabottomSelect)).setImageNormal(R.mipmap.t_home_noramal).setImagePress(R.mipmap.t_home_select);
        tab2.setText("对话").setTextNormalColor(ResourceUtil.getColor(R.color.tabottomNormal)).setTextPressColor(ResourceUtil.getColor(R.color.tabottomSelect)).setImageNormal(R.mipmap.t_dialog_normal).setImagePress(R.mipmap.t_dialog_select);
        tab3.setText("逛逛").setTextNormalColor(ResourceUtil.getColor(R.color.tabottomNormal)).setTextPressColor(ResourceUtil.getColor(R.color.tabottomSelect)).setImageNormal(R.mipmap.t_go_normal).setImagePress(R.mipmap.t_go_selsect);
        tab4.setText("我的").setTextNormalColor(ResourceUtil.getColor(R.color.tabottomNormal)).setTextPressColor(ResourceUtil.getColor(R.color.tabottomSelect)).setImageNormal(R.mipmap.t_my_normal).setImagePress(R.mipmap.t_my_select);
        ZBaseView[] items = new ZBaseView[]{tab1, tab2, tab3, tab4};
        ZNavigatorManager zNavigatorManager = new ZNavigatorManager();
        zNavigatorManager.setItems(items);
        zNavigatorManager.setItemCurrent(0);
        zNavigatorManager.setmListener(new ZNavigatorManager.ZNavigatorManagerListener() {
            @Override
            public void onItemClick(ZBaseView view, int index) {
                switch (index) {
                    case 0:
//                        getZFragmentManager().stoggle(R.id.frag_main, HomeWebFragment.class);
                        getZFragmentManager().stoggle(R.id.frag_main, homeFragment);
                        break;
                    case 1:

                        SDEventManager.post(EnumEventTag.GET_HOME_CHAT.ordinal());
                        chatListFragment.setChatList(chatList);
                        getZFragmentManager().stoggle(R.id.frag_main, chatListFragment);
                        break;
                    case 2:
                        getZFragmentManager().stoggle(R.id.frag_main, liveFragment);
//                        getZFragmentManager().stoggle(R.id.frag_main, LiveWebFrament.class);
//                        getZFragmentManager().stoggle(R.id.frag_main, liveWebFrament);
                        break;
                    case 3:
                        getZFragmentManager().stoggle(R.id.frag_main, myFragment);
//                        getZFragmentManager().stoggle(R.id.frag_main, myWebFragment);
                        break;
                    default:
                        break;
                }
            }
        });

    }
    private void httpChatList()
    {
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("live");
        httpMap.putCtl("index");
        httpMap.putAct("dialoglist");
        httpMap.putDataMap("uid",Constant.UID);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                Gson gson=new Gson();
                chatList=   gson.fromJson(data, new TypeToken<List<ChatModel>>(){}.getType());
                  if(chatList==null)
                      chatList=new ArrayList<ChatModel>();
                 else
                      updateUnReadNum();
            }
        });
    }
    private  void httpUpChatList()
    {
        if(chatList==null)
            return;
        Intent broadcast_intent = new Intent("com.android.uploadchatlist");
        broadcast_intent.putExtra("chat_list",(Serializable)chatList);
        sendBroadcast(broadcast_intent);
    }
    @Override
    protected void initBeforeView() {
        super.initBeforeView();
//        InitSysBar();
    }
  public void updateChatList(ChatModel chat_model)
  {
      boolean isContain=false;
      if(chatList.size()>0) {
              for(int i=0;i<chatList.size();i++)
              {
                  if (chatList.get(i).getHx_openid().equals(chat_model.getHx_openid())) {
                      if(i>0) {
                          ChatModel chatModel = chatList.get(i);
                          chatList.remove(i);
                          chatList.add(0, chatModel);
                      }
                          isContain=true;
                      updateUnReadNum();
                      chatListFragment.setChatList(chatList);
                  }
              }
            if(!isContain)
            {
                chatList.add(0,chat_model);
                chatListFragment.setChatList(chatList);
            }


      }
  }
    public void updateNewMessageChatList(String chat_id) {
        for (int i = 0; i < chatList.size(); i++) {
            if (chatList.get(i).getHx_openid().equals(chat_id) && !Constant.isChattionId.equals(chat_id)) {
                SysUtil.sendNotification(R.string.app_name, R.mipmap.logo_small, R.mipmap.logo, "对话", Constant.ticker, MainActivity.class, true);
                chatList.get(i).setNum(String.valueOf(Integer.parseInt(chatList.get(0).getNum()) + 1));
                if (i > 0) {
                    if (i > 0)
                    {
                        ChatModel chatModel = chatList.get(i);
                        chatList.remove(i);
                        chatList.add(0, chatModel);
                    }
                }
                updateUnReadNum();
                chatListFragment.setChatList(chatList);
            }
        }


    }
    public void addChatList(ChatModel chatModel)
    {
        if(chatList==null)
        {
            chatList=new ArrayList<>();
        }
        if(chatList.size()==0)
        {
            chatList.add(chatModel);
        }
        else
        {
            updateChatList(chatModel);
        }
    }
    public void ReadMessage(String chat_id)
    {
        if(chatList.size()>0) {
            for(int i=0;i<chatList.size();i++)
            {
                if (chatList.get(i).getHx_openid().equals(chat_id)) {
                    chatList.get(i).setNum("0");
                    updateUnReadNum();
                    chatListFragment.setChatList(chatList);
                }
            }
        }
    }
    public void updateUnReadNum()
    {
        int unread_num=0;
          for(int i=0;i<chatList.size();i++)
          {
              unread_num+=Integer.parseInt(chatList.get(i).getNum());
          }
        if(unread_num==0)
            tab2.tvnum.setVisibility(View.GONE);
        else
        {
            tab2.tvnum.setVisibility(View.VISIBLE);
            tab2.tvnum.setText(String.valueOf(unread_num));
        }
    }
    public void deleteChat(String chat_id)
    {
        if(chatList.size()>0) {
            for (int i = 0; i < chatList.size(); i++)
            {
                if (chatList.get(i).getHx_openid().equals(chat_id))
                {
                    if(chatList.size()==1)
                        chatList.clear();
                    else
                        chatList.remove(i);
                    updateUnReadNum();
                    chatListFragment.setChatList(chatList);

                }
            }
        }
    }
    @Override
    public void onEventMainThread(SDBaseEvent event)
    {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt()))
        {
            case GO_HOME_FRAGMENT:
                zHandler.sendEmptyMessage(1);
                break;
            case CHAT_NEW_MESSAGE:
                String chat_id=(String)event.getData();
                updateNewMessageChatList(chat_id);
                break;
            case CHAT_MESSAGE_READ:
                String chatId=(String)event.getData();
                ReadMessage(chatId);
                break;
            case DELETE_CHAT:
                String chat_Id=(String)event.getData();
                deleteChat(chat_Id);
                break;
            case NEW_CHAT_USER:
                ChatModel chatModel=(ChatModel)event.getData();
               addChatList(chatModel);
                break;
            case SCAN:
               String scan_result=(String)event.getData();
              Log.e("scan_result",scan_result);
                break;

        }
    }

    @Override
    protected void zHandlerMessage(Message msg, int id) {
        super.zHandlerMessage(msg, id);
        tab1.performClick();
    }
    @Override
    protected void onDestroy() {
        BMapManager.getInstance().stopBMapService();
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        exitApp();
    }
    private void exitApp() {
        httpUpChatList();
        if (System.currentTimeMillis() - mExitTime > 2000) {
            ViewUtil.showToast("再按一次退出!");
        } else {
            exitApp(false);
        }
        mExitTime = System.currentTimeMillis();
    }
    public void exitApp(boolean isBackground) {
        ZActivityManager.getInstance().finishAllActivity();
        if (isBackground) {
        } else {
            System.exit(0);
        }
    }
    private  void BMapLocation()
    {

        BMapManager.getInstance().setLocationSuccessListener(new BMapManager.LocationSuccessListener() {
            @Override
            public void onLocationSuccess(BMapManager.LocationModel locationModel) {
                BMapManager.getInstance().stopLocation();
                Log.e("经纬度", "Lat:" + String.valueOf(locationModel.getLatitude()) + "   Lon:" + String.valueOf(locationModel.getLongitude())+"   city"+locationModel.getCityName());
                HttpMap httpMap = new HttpMap();
                httpMap.putMode("live");
                httpMap.putCtl("index");
                httpMap.putAct("citycode");
                httpMap.putDataMap("city_name", locationModel.getCityName());
                httpMap.setHttpListener(new HttpMap.HttpListener() {
                    @Override
                    public void onSuccess(String response, String data) {
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            Constant.CityCode=(String)jsonObject.opt("code");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }
    private void LocationPermission()
    {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(App.getInstance(), Constant.LocationPermision);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Constant.LocationPermision},Constant.PermissionLocation);
                return;
            }else{
                BMapLocation();
            }
        } else {
            BMapLocation();
        }
    }
    private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
        String SYSTEM_REASON = "reason";
        String SYSTEM_HOME_KEY = "homekey";
        String SYSTEM_HOME_KEY_LONG = "recentapps";
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {
                    //表示按了home键,程序到了后台
                 if(!SysUtil.isAppBackground()) {
                     httpUpChatList();
//                     Toast.makeText(getApplicationContext(), "home", Toast.LENGTH_SHORT).show();
                 }
                }else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){
                    //表示长按home键,显示最近使用的程序列表
                }
            }
        }
    };
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constant.PermissionLocation:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    BMapLocation();
                } else {
//                    ViewUtil.showToast("请同意访问相机后 再从相机中选择头像");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
