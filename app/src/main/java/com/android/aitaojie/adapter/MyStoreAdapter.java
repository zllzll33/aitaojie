package com.android.aitaojie.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.MyColloctStoreModel;
import com.android.aitaojie.share.ShareManager;
import com.android.zlibrary.adapter.ZModelAdapter;
import com.android.zlibrary.adapter.ZTextIconAdapter;
import com.android.zlibrary.customView.CircleImageView;
import com.android.zlibrary.dialog.ZDialogMenu;
import com.android.zlibrary.model.TextIconModel;
import com.android.zlibrary.util.ViewUtil;
import com.sunday.eventbus.SDEventManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/28.
 */
public class MyStoreAdapter extends ZModelAdapter<MyColloctStoreModel> {
    private ZDialogMenu dialogMenu;

    public MyStoreAdapter(List<MyColloctStoreModel> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
        dialogMenu = new ZDialogMenu(mActivity, com.android.zlibrary.R.style.BlackDialog);
        TextIconModel collectModel = new TextIconModel();
        collectModel.setText("取消收藏");
        collectModel.setIconRes(R.mipmap.my_cancle_collect);
        TextIconModel shareModel = new TextIconModel();
        shareModel.setText("分享");
        shareModel.setIconRes(R.mipmap.my_collect_share);
        List<TextIconModel> textIconModels = new ArrayList<>();
        textIconModels.add(collectModel);
        textIconModels.add(shareModel);
        ZTextIconAdapter textIconAdapter = new ZTextIconAdapter(textIconModels, mActivity);
        dialogMenu.setGridColumn(2).setAdapter(textIconAdapter);

    }

    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent,final MyColloctStoreModel model) {
        CircleImageView header = (CircleImageView) convertView.findViewById(R.id.header);
        TextView storeName=(TextView)convertView.findViewById(R.id.store_name);
        TextView industry=(TextView)convertView.findViewById(R.id.industry);
        TextView addr=(TextView)convertView.findViewById(R.id.addr);
        if(!model.getLogo().isEmpty())
            ViewUtil.setImageView(header,model.getLogo());
        storeName.setText(model.getName());
        industry.setText(model.getTrade());
        addr.setText(model.getAddr());
        ImageView share = (ImageView) convertView.findViewById(R.id.shar_menu);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDEventManager.post(model.getJid(),EnumEventTag.GO_HOME_FRAGMENT.ordinal());
                mActivity.finish();
            }
        });
        share.setOnClickListener(new ZOnClickListener(position, convertView, model) {
            @Override
            public void onClick(View v) {
                dialogMenu.showDialog();
                dialogMenu.setzMenuDialogListener(new ZDialogMenu.ZMenuDialogListener() {
                    @Override
                    public void onCancelClick(View v, ZDialogMenu dialog) {
                    }

                    @Override
                    public void onItemClick(View v, int index, ZDialogMenu dialog) {
                        switch (index) {
                            case 0:
                                HttpMap httpMap=new HttpMap();
                                httpMap.putMode("User");
                                httpMap.putCtl("collect");
                                httpMap.putAct("cancel");
                                httpMap.putDataMap("uid", Constant.UID);
                                httpMap.putDataMap("id",model.getId());
                                httpMap.setHttpListener(new HttpMap.HttpListener() {
                                    @Override
                                    public void onSuccess(String response, String data) {
                                        SDEventManager.post(EnumEventTag.REFRESH_COLLECT_STORE_LIST.ordinal());
                                    }
                                });
                                break;
                            case 1:
                                HttpMap shareMap=new HttpMap();
                                shareMap.putMode("User");
                                shareMap.putCtl("Share");
                                shareMap.putAct("shop");
                                shareMap.putDataMap("jid",model.getJid() );
                                shareMap.setHttpListener(new HttpMap.HttpListener() {
                                    @Override
                                    public void onSuccess(String response, String data) {
                                        try {
                                            JSONObject jsonObject=new JSONObject(data) ;
                                            String title=jsonObject.optString("sname");
                                            String content=jsonObject.optString("mexplain");
                                            String img=jsonObject.optString("exterior");
                                            String target=jsonObject.optString("url");
                                            ShareManager.getInstance().ShowshareMenu(title, content, img, target);
                                            dialogMenu.dismiss();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                break;
                        }
                    }
                });
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.item_my_store;
    }

}
