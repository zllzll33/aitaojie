package com.android.aitaojie.activity;

import android.os.Handler;


import com.android.aitaojie.R;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.constant.WBConstants;

/**
 * Created by win7 on 2016/6/6.
 */
/*public class WBShareActivity extends WBShareCallBackActivity {
}*/
public class WBShareActivity extends ZBaseActivity implements IWeiboHandler.Response
{
    @Override
    protected int layoutId() {
        return R.layout.act_sina_share ;
    }

    @Override
    protected void Init() {
        super.Init();

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
           /*     Intent intent=new Intent(WBShareActivity.this, TestActivity.class);
                startActivity(intent);*/
                finish();
            }
        },100);

    }

    @Override
    public void onResponse(BaseResponse baseResponse) {
        if(baseResponse!= null){
            switch (baseResponse.errCode) {
                case WBConstants.ErrorCode.ERR_OK:

                    break;
                case WBConstants.ErrorCode.ERR_CANCEL:

                    break;
                case WBConstants.ErrorCode.ERR_FAIL:

                    break;
            }
        }
    }
/*    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
//        ZSinaShare.getInstance().getmWeiboShareAPI().handleWeiboResponse(intent, this);
    }*/
}