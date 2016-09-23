package com.android.aitaojie.wxapi;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import com.android.aitaojie.R;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.zlibrary.util.TypeUtil;
import com.sunday.eventbus.SDEventManager;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wxpay_result);
    	api = WXAPIFactory.createWXAPI(this, Constant.WX_APPID);
		api.registerApp(Constant.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode==-2) {
				Map<String,String> wxMap=new HashMap<>();
				wxMap.put("type","WCA_WX_PAY");
				wxMap.put("order_status","cancle");
				wxMap.put("type","ACW_WXPAY_ORDER_STATUS");
				wxMap.put("order_id",WxpayManager.getInstance().getOrderId());
				String order_info= TypeUtil.Map2JsonStr(wxMap);
				SDEventManager.post(order_info,EnumEventTag.WX_PAY_ORDER.ordinal());
			}
		    else if(resp.errCode==-1) {
//				SDEventManager.post("noorder",EnumEventTag.WX_PAY_ORDER.ordinal());
			}
			else	if(resp.errCode==0) {
				Map<String,String> wxMap=new HashMap<>();
				wxMap.put("order_status","success");
				wxMap.put("type","ACW_WXPAY_ORDER_STATUS");
				wxMap.put("order_id",WxpayManager.getInstance().getOrderId());
				String order_info= TypeUtil.Map2JsonStr(wxMap);
				SDEventManager.post(order_info,EnumEventTag.WX_PAY_ORDER.ordinal());
			}
		/*	AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage( String.valueOf(resp.errCode));
			builder.show();*/
			finish();
		}
	}
}