package com.android.aitaojie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.fragment.JSBridgeFragment;
import com.android.aitaojie.fragment.ZWebJSBridgeFragment;
import com.android.zlibrary.fragment.ZWebviewFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/8/15.
 */
public class WebViewActivity extends BaseActivity {
    @InjectView(R.id.title)
    TextView title;
    protected Intent intent;
    @Override
    protected int layoutId() {
        return R.layout.act_webview;
    }
    @Override
    protected void Init() {
        super.Init();
        intent = getIntent();
        String webUrl = intent.getStringExtra("webUrl");
        JSBridgeFragment webviewFragment = new JSBridgeFragment();
        webviewFragment.putString("webUrl", webUrl);
        webviewFragment.setWebActionListener(new ZWebJSBridgeFragment.WebActionListener() {
            @Override
            public void webAction(WebView webview, String url) {
                Intent webIntent = new Intent(WebViewActivity.this, WebViewActivity.class);
                webIntent.putExtra("webUrl", url);
                startActivity(webIntent);
            }

            @Override
            public void webTitle(WebView webview, String titleStr) {
                setTitle(titleStr);
            }
        });
       /* webviewFragment.setWebActionListener(new ZWebviewFragment.WebActionListener() {
            @Override
            public void webAction(WebView webview, String url) {
                Intent webIntent = new Intent(WebViewActivity.this, WebViewActivity.class);
                webIntent.putExtra("webUrl", url);
                startActivity(webIntent);
            }

            @Override
            public void webTitle(WebView webview, String titleStr) {
                setTitle(titleStr);
            }
        });*/
        getZFragmentManager().replace(R.id.frag,webviewFragment);
    }


}
