package com.android.aitaojie.config;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.android.aitaojie.MainActivity;
import com.android.aitaojie.R;
import com.android.aitaojie.activity.ChatActivity;
import com.android.aitaojie.bmap.BMapManager;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.zlibrary.base.ZLibrary;
import com.android.zlibrary.util.SysUtil;
import com.baidu.mapapi.SDKInitializer;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.sunday.eventbus.SDEventManager;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.Iterator;
import java.util.List;

/**
 * Created by win7 on 2016/7/18.
 */
public class App extends Application {
    private static App app;
    private static RequestQueue mQueue=null;
    public static  App getInstance()
    {
        return app;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        app=this;
        SDKInitializer.initialize(this);
        BMapManager.getInstance().startBMapService();
        BMapManager.getInstance().startLocation();
        ZLibrary.getInstance().initZlibraryApplicatin(this);
        AutoLayoutConifg.getInstance().useDeviceSize().init(this);
        initIM();
    }
    public void HttpPost(StringRequestPost request)
    {
        if(mQueue==null)
            mQueue = Volley.newRequestQueue(App.getInstance());
        mQueue.add(request);
    }
    public void HttpGet(StringRequestGet request)
    {
        if(mQueue==null)
            mQueue = Volley.newRequestQueue(App.getInstance());
        mQueue.add(request);
    }
    private void initIM()
    {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null ||!processAppName.equalsIgnoreCase(getPackageName())) {
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(this, options);
        EaseUI.getInstance().init(this, options);
        EMClient.getInstance().chatManager().addMessageListener( new EMMessageListener()
        {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                EMMessage message=messages.get(0);
                Constant.ticker = EaseCommonUtils.getMessageDigest(message, App.getInstance());
                SDEventManager.post(message.getFrom(), EnumEventTag.CHAT_NEW_MESSAGE.ordinal());
                Log.e("消息","1+"+message.getFrom());
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
//                Log.e("消息","2");
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
                //收到已读回执
//                Log.e("消息","3");
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
                //收到已送达回执
//                Log.e("消息","4");
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
//                Log.e("消息","5");
            }
        });
  /*      EaseUI.getInstance().getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {
            @Override
            public String getDisplayedText(EMMessage message) {
                String ticker = EaseCommonUtils.getMessageDigest(message,App.getInstance());
                if(message.getType() == EMMessage.Type.TXT){
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = EaseUI.getInstance().getUserProfileProvider().getUser(message.getFrom());
                if(user != null){
                    return  user.getNick() + ": " + ticker;
                }else{
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                 return fromUsersNum + "个商户，发来了" + messageNum + "条消息";
            }

            @Override
            public String getTitle(EMMessage message) {
                return "爱淘街：对话";
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                return R.mipmap.logo;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {

                Intent intent = new Intent(App.getInstance(), ChatActivity.class);
                EMMessage.ChatType chatType = message.getChatType();
                if (chatType == EMMessage.ChatType.Chat) {
                    intent.putExtra("userId", message.getFrom());
                    intent.putExtra("name","小二");
                }
                return intent;
            }
        });*/
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
