package com.android.aitaojie.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.aitaojie.R;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.share.ShareManager;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/8/19.
 */
public class GoodWebDetailActiivty extends WebViewActivity {
    int gid;
    @InjectView(R.id.share)
    ImageView share;

    @Override
    protected int layoutId() {
        return R.layout.act_web_good_detail;
    }
    @Override
    protected void Init() {
        super.Init();
        gid = intent.getIntExtra("gid", 0);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpMap shareMap=new HttpMap();
                shareMap.putMode("User");
                shareMap.putCtl("Share");
                shareMap.putAct("index");
                shareMap.putDataMap("gid",String.valueOf(gid));
                shareMap.setHttpListener(new HttpMap.HttpListener() {
                    @Override
                    public void onSuccess(String response, String data) {
                        try {
                            JSONObject jsonObject=new JSONObject(data) ;
                            String title=jsonObject.optString("gname");
                            String content=jsonObject.optString("gdescription");
                            String img=jsonObject.optString("gimg");
                            String target=jsonObject.optString("url");
                            ShareManager.getInstance().ShowshareMenu(title, content, img, target);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


}
