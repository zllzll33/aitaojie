package com.android.aitaojie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.getui.GPushReceiver;
import com.android.zlibrary.base.ImageLoaderManager;
import com.android.zlibrary.customView.SwitchButton;
import com.android.zlibrary.util.FileUtil;
import com.android.zlibrary.util.PreferenceUtil;
import com.android.zlibrary.util.SysUtil;
import com.android.zlibrary.util.ViewUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/28.
 */
public class MySetActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.push_switch)
    SwitchButton pushSwitch;
    @InjectView(R.id.cache_size)
    TextView cacheSize;
    @InjectView(R.id.rl_clear_cache)
    RelativeLayout rlClearCache;
    @InjectView(R.id.verson)
    TextView verson;
    @InjectView(R.id.about)
    RelativeLayout about;
    @InjectView(R.id.contact)
    RelativeLayout contact;
    @InjectView(R.id.exit)
    TextView exit;

    @Override
    protected int layoutId() {
        return R.layout.act_my_set;
    }

    @Override
    protected void Init() {
        super.Init();
        setTitle("设置");
        long imagecache_size=ImageLoaderManager.getImageCacheSize();
         long webcache_size= SysUtil.getWebCacheSize();
        long cache_size=imagecache_size+webcache_size;
        Log.e("webcache_size",String.valueOf(webcache_size));
        cacheSize.setText(FileUtil.formatFileSize(cache_size));
        verson.setText("V"+SysUtil.getPackageInfo()[1]);
        pushSwitch.setSlideListener(new SwitchButton.SlideListener() {
            @Override
            public void open() {
                PreferenceUtil.putBoolean("CanPush",true);
            }
            @Override
            public void close() {
                PreferenceUtil.putBoolean("CanPush",false);
            }
        });
        rlClearCache.setOnClickListener(this);
        about.setOnClickListener(this);
        contact.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_clear_cache:
                ImageLoaderManager.ClearImageCache();
                SysUtil.clearWebCache();
                ViewUtil.showHtttpProgress("正在清理缓存");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        cacheSize.setText("0.00Kb");
                        ViewUtil.hideHtttpProgress();
                    }
                },2000);
                break;
            case R.id.about:

                break;
            case R.id.contact:

                break;
            case R.id.exit:
                PreferenceUtil.putString("UID","");
                Intent intent=new Intent(MySetActivity.this,LoginActivity.class);
                startActivity(intent);
          EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
//                Log.e("hx","exit");
            }
            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
            }
        });

                break;
        }
    }

}
