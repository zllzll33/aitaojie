package com.android.aitaojie.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.aitaojie.MainActivity;
import com.android.aitaojie.R;
import com.android.aitaojie.activity.WebViewActivity;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.customview.AddCartDialog;
import com.android.aitaojie.model.AddCartModel;
import com.android.aitaojie.model.MyCollectGoodModel;
import com.android.aitaojie.share.ShareManager;
import com.android.zlibrary.adapter.ZModelAdapter;
import com.android.zlibrary.adapter.ZTextIconAdapter;
import com.android.zlibrary.customView.ZImageView;
import com.android.zlibrary.dialog.ZDialogMenu;
import com.android.zlibrary.model.TextIconModel;
import com.android.zlibrary.util.ViewUtil;
import com.google.gson.Gson;
import com.sunday.eventbus.SDEventManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/28.
 */
public class MyGoodsAdapter extends ZModelAdapter<MyCollectGoodModel> {
    @InjectView(R.id.good_pic)
    ZImageView goodPic;
    @InjectView(R.id.des)
    TextView des;
    @InjectView(R.id.price)
    TextView price;
    @InjectView(R.id.tag)
    TextView tag;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.share)
    ImageView share;
    private ZDialogMenu dialogMenu;
    public MyGoodsAdapter(List<MyCollectGoodModel> mlistModel, Activity mActivity) {
        super(mlistModel, mActivity);
        dialogMenu = new ZDialogMenu(mActivity, com.android.zlibrary.R.style.BlackDialog);
        TextIconModel goStoreModel = new TextIconModel();
        goStoreModel.setText("进入店铺");
        goStoreModel.setIconRes(R.mipmap.my_collect_go_shore);
        TextIconModel collectModel = new TextIconModel();
        collectModel.setText("取消收藏");
        collectModel.setIconRes(R.mipmap.my_cancle_collect);
        TextIconModel addCart = new TextIconModel();
        addCart.setText("加入购物车");
        addCart.setIconRes(R.mipmap.my_collect_add_cart);
        TextIconModel shareModel = new TextIconModel();
        shareModel.setText("分享");
        shareModel.setIconRes(R.mipmap.my_collect_share);
        List<TextIconModel> textIconModels = new ArrayList<>();
        textIconModels.add(goStoreModel);
        textIconModels.add(collectModel);
        textIconModels.add(addCart);
        textIconModels.add(shareModel);
        ZTextIconAdapter textIconAdapter = new ZTextIconAdapter(textIconModels, mActivity);
        dialogMenu.setGridColumn(4).setAdapter(textIconAdapter);

    }

    @Override
    protected void BindModel(int position, View convertView, ViewGroup parent, MyCollectGoodModel model) {
        ZImageView good_pic=(ZImageView)convertView.findViewById(R.id.good_pic);
        TextView des=(TextView)convertView.findViewById(R.id.des);
        TextView price=(TextView)convertView.findViewById(R.id.price);
        TextView tag=(TextView)convertView.findViewById(R.id.tag);
        TextView time=(TextView)convertView.findViewById(R.id.time);
        ViewUtil.setImageView(good_pic,model.getImg());
        des.setText(model.getDesc());
        price.setText("￥"+model.getPrice());
        if(!model.getStatus_txt().isEmpty())
        {
            tag.setVisibility(View.VISIBLE);
            tag.setText(model.getStatus_txt());
        }
        else
        {
            tag.setVisibility(View.GONE);
        }
        time.setText(model.getTime());
        ImageView shareMenu = (ImageView) convertView.findViewById(R.id.share);
        convertView.setOnClickListener(new ZOnClickListener(position, convertView, model)
        {
            @Override
            public void onClick(View v) {
                Intent goodintent=new Intent(mActivity, WebViewActivity.class);
                String web_url=String.format(Constant.GoodDetaiUrl,model.getGid());
                goodintent.putExtra("webUrl",web_url);
                mActivity.startActivity(goodintent);
            }
        });
        shareMenu.setOnClickListener(new ZOnClickListener(position, convertView, model) {
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
                                SDEventManager.post(model.getJid(),EnumEventTag.GO_HOME_FRAGMENT.ordinal());
                                mActivity.finish();
                                break;
                            case 1:
                                HttpMap httpMap=new HttpMap();
                                httpMap.putMode("User");
                                httpMap.putCtl("collect");
                                httpMap.putAct("cancel");
                                httpMap.putDataMap("uid", Constant.UID);
                                httpMap.putDataMap("id",model.getId());
                                httpMap.setHttpListener(new HttpMap.HttpListener() {
                                    @Override
                                    public void onSuccess(String response, String data) {
                                        SDEventManager.post(EnumEventTag.REFRESH_COLLECT_GOOD_LIST.ordinal());
                                    }
                                });
                                break;
                            case 2:
                                HttpMap addCartMap = new HttpMap();
                                addCartMap.putMode("Merchant");
                                addCartMap.putCtl("goods");
                                addCartMap.putAct("goodsSpec");
                                addCartMap.putDataMap("gid", model.getGid());
                                addCartMap.setHttpListener(new HttpMap.HttpListener() {
                                    @Override
                                    public void onSuccess(String response, String data) {
                                        Gson gson = new Gson();
                                        AddCartModel addCartModel = gson.fromJson(data, AddCartModel.class);
                                        AddCartDialog addCartDialog=new AddCartDialog(addCartModel,false);
                                        addCartDialog.showDiaglog();
                                    }
                                });
                                break;
                            case 3:
                                HttpMap shareMap=new HttpMap();
                                shareMap.putMode("User");
                                shareMap.putCtl("Share");
                                shareMap.putAct("index");
                                shareMap.putDataMap("gid",model.getGid() );
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
        return R.layout.item_my_goods;
    }
}
