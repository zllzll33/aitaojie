package com.android.aitaojie.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.android.aitaojie.activity.ChatActivity;
import com.android.aitaojie.activity.MyAddressActivity;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.customview.AddCartDialog;
import com.android.aitaojie.model.AddCartModel;
import com.android.aitaojie.model.ChatModel;
import com.android.aitaojie.util.AlipayUtil;
import com.android.aitaojie.wxapi.WxpayManager;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.util.SysUtil;
import com.android.zlibrary.util.ViewUtil;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.sunday.eventbus.SDBaseEvent;
import com.sunday.eventbus.SDEventManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by win7 on 2016/6/22.
 */
public class JSBridgeFragment extends ZWebJSBridgeFragment {
    DefaultHandler defaultHandler;
    String order_id;
    String order_info;
    String order_fee;
    String payJsonStr;
   public JSBridgeFragment()
   {
       super();
   }

    @Override
    protected void Init() {
        super.Init();
//        WxpayManager.getInstance().pay("123456789004","测试","1");
    }
    @Override
    protected void initWebView(final BridgeWebView wv) {
        super.initWebView(wv);
        defaultHandler=new DefaultHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                ViewUtil.showToast(data);
                Log.e("jsData",data);
                try {
                    JSONObject jsObject=new JSONObject(data);
                    String type=jsObject.optString("type");
                    if(type.equals("WCA_UID"))
                    {
                        function.onCallBack(Constant.UID);
                    }
                     if(type.equals("WCA_PHONE_CALL"))
                    {
                        String mobile=jsObject.optString("mobile");
                        Intent TelIntent = new Intent(Intent.ACTION_VIEW, Uri
                                .parse("tel:"+mobile));
                        startActivity(TelIntent);
                    }
                     else if(type.equals("WCA_WX_PAY"))
                     {
                         order_id=jsObject.optString("id");
                         order_info=jsObject.optString("info");
                         order_fee=jsObject.optString("fee");
                         WxpayManager.getInstance().pay(order_id,order_info,order_fee);

                     }
                     else if(type.equals("WCA_CHAT"))
                     {
                       String  hx_username=jsObject.optString("hx_username");
                         String  merchant_name=jsObject.optString("merchant_name");
                         String  logo=jsObject.optString("logo");
                         String  jid=jsObject.optString("jid");
                         String  trade=jsObject.optString("trade");
                         String  addr=jsObject.optString("addr");
                         ChatModel chatModel =new ChatModel();
                         chatModel.setMerchant_name(merchant_name);
                         chatModel.setLogo(logo);
                         chatModel.setCtime(SysUtil.getSysTimeFormatDay());
                         chatModel.setHx_openid(hx_username);
                         chatModel.setNum("0");
                         chatModel.setJid(jid);
                         chatModel.setUid(Constant.UID);
                         chatModel.setTrade(trade);
                         chatModel.setAddress(addr);
                         SDEventManager.post(chatModel, EnumEventTag.NEW_CHAT_USER.ordinal());
                         Intent chat_intent=new Intent(ZActivityManager.getInstance().getLastActivity(),ChatActivity.class);
                         Bundle bundle=new Bundle();
                         bundle.putString("userId",hx_username);
                         bundle.putString("name",merchant_name);
                         chat_intent.putExtra("user",bundle);
                         startActivity(chat_intent);
                     }
                     else if(type.equals("WCA_ADDRESS"))
                     {
                         Intent intent=new Intent(getActivity(), MyAddressActivity.class);
                         intent.putExtra("type",1);
                         startActivity(intent);
                     }
                     else if(type.equals("WCA_SPEC"))
                     {
                       String  gid=jsObject.optString("gid");
                         HttpMap httpMap = new HttpMap();
                         httpMap.putMode("Merchant");
                         httpMap.putCtl("goods");
                         httpMap.putAct("goodsSpec");
                         httpMap.putDataMap("gid", gid);
                         httpMap.setHttpListener(new HttpMap.HttpListener() {
                             @Override
                             public void onSuccess(String response, String data) {
                                 Gson gson = new Gson();
                                 AddCartModel addCartModel = gson.fromJson(data, AddCartModel.class);
                                 AddCartDialog addCartDialog=new AddCartDialog(addCartModel,true);
                                 addCartDialog.showDiaglog();
                             }
                         });

                     }
                    else if(type.equals("WCA_ALI_PAY"))
                    {
                        order_id=jsObject.optString("id");
                        order_info=jsObject.optString("info");
                        order_fee=jsObject.optString("fee");
                            new Thread()
                        {
                            @Override
                            public void run() {
                                AlipayUtil.OrderModel orderModel=new AlipayUtil.OrderModel();
                                orderModel.setOrderId(order_id);
                                orderModel.setSubject(order_info);
                                orderModel.setObject(order_info);
                                orderModel.setTotalFee(order_fee);
                                String order= AlipayUtil.OrderMap(orderModel);
                                Log.e("payorder",order);
                                PayTask payTask = new PayTask(getActivity());
                                String pay_ok= payTask.pay(order,true);
                                Log.e("pay_ok",pay_ok);
                                 payJsonStr=AlipayUtil.PayokStr2Jsonstr(pay_ok);
                                Log.e("payJsonStr",payJsonStr);
                                zHander.sendEmptyMessage(1);


                            }
                        }.start();
                }

                    else if(type.equals("WCA_WX_PAY"))
                    {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                function.onCallBack("hello world");
            }
        };
        wv.setDefaultHandler(defaultHandler);
        wv.registerHandler(null,defaultHandler);
      /*  wv.registerHandler("submitFromWeb", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("jsbridge2",data);
                function.onCallBack(" nice");
            }
        });
        */
    }
    protected void handerReceive(int index)
    {
       if(index==1)
       {
           wv.send(payJsonStr, new CallBackFunction() {
               @Override
               public void onCallBack(String data) {
                   Log.e("ali订单回执",data);
               }
           });
       }
    }
    @Override
    public void onEventMainThread(SDBaseEvent event)
    {
        super.onEventMainThread(event);
        switch (EnumEventTag.valueOf(event.getTagInt()))
        {
            case WX_PAY_ORDER:
                String wx_info=(String)event.getData();
                Log.e("wx_info",wx_info);
               if(getActivity()== ZActivityManager.getInstance().getLastActivity()) {
                   Log.e("wx_oredre_status",wx_info);
                   wv.send(wx_info, new CallBackFunction() {
                       @Override
                       public void onCallBack(String data) {
                           Log.e("Wx订单回执",data);
                       }
                   });
               }
                break;
            case CART_ORDER:
                String cart_info=(String)event.getData();
                Log.e("cart_info",cart_info);
                if(getActivity()== ZActivityManager.getInstance().getLastActivity()) {
                    wv.send(cart_info, new CallBackFunction() {
                        @Override
                        public void onCallBack(String data) {
                            Log.e("cart回执",data);
                        }
                    });
                }
                break;
            case ADDRESS:
                if(getActivity()== ZActivityManager.getInstance().getLastActivity()) {
                    Log.e("address","{\"type\":\"ACW_ADDRESS\"}");
                    wv.send("{\"type\":\"ACW_ADDRESS\"}", new CallBackFunction() {
                        @Override
                        public void onCallBack(String data) {
                            Log.e("address回执",data);
                        }
                    });
                }
                break;

        }
    }
}
