package com.android.aitaojie.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.customview.AddCartDialog;
import com.android.aitaojie.fragment.AdvFragment;
import com.android.aitaojie.model.ChatModel;
import com.android.aitaojie.model.GoodDetailModel;
import com.android.aitaojie.share.ShareManager;
import com.android.zlibrary.adv.ADInfo;
import com.android.zlibrary.customView.CircleImageView;
import com.android.zlibrary.fragment.ZWebviewFragment;
import com.android.zlibrary.util.SysUtil;
import com.android.zlibrary.util.ViewUtil;
import com.google.gson.Gson;
import com.sunday.eventbus.SDEventManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/22.
 */
public class GoodDetailActivity extends BaseActivity {
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.header)
    CircleImageView header;
    @InjectView(R.id.store_name)
    TextView storeName;
    @InjectView(R.id.store_addr)
    TextView storeAddr;
    @InjectView(R.id.adv)
    FrameLayout adv;
    @InjectView(R.id.price)
    TextView price;
    @InjectView(R.id.freight)
    TextView freight;
    @InjectView(R.id.focus_num)
    TextView focusNum;
    @InjectView(R.id.browse_num)
    TextView browseNum;
    @InjectView(R.id.fee_ll)
    LinearLayout feeLl;
    @InjectView(R.id.des)
    TextView des;
    @InjectView(R.id.picText)
    FrameLayout picText;
    @InjectView(R.id.store)
    LinearLayout store;
    @InjectView(R.id.collect)
    LinearLayout collect;
    @InjectView(R.id.addCart)
    TextView addCart;
    @InjectView(R.id.buy)
    TextView buy;
    @InjectView(R.id.share)
    ImageView share;
    @InjectView(R.id.call)
    LinearLayout call;
    String gid;
    GoodDetailModel goodDetailModel;
    @Override
    protected int layoutId() {
        return R.layout.act_good_detail;
    }

    @Override
    protected void Init() {
        super.Init();
        setTitle("商品详情");
        Intent intent=getIntent();
        gid=intent.getStringExtra("gid");
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShareManager.getInstance().ShowshareMenu("标题","内容","http://pic2.sc.chinaz.com/Files/pic/pic9/201607/fpic5778_s.jpg","http://www.baidu.com");
                HttpMap shareMap=new HttpMap();
                shareMap.putMode("User");
                shareMap.putCtl("Share");
                shareMap.putAct("index");
                shareMap.putDataMap("gid",gid);
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
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpMap httpMap = new HttpMap();
                httpMap.putMode("User");
                httpMap.putCtl("Collect");
                httpMap.putAct("addGoods");
                httpMap.putDataMap("gid",gid);
                httpMap.putDataMap("uid",Constant.UID);
                httpMap.setHttpListener(new HttpMap.HttpListener() {
                    @Override
                    public void onSuccess(String response, String data) {

                    }
                });
            }
        });
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!goodDetailModel.getShop().getHxid().isEmpty()) {
                    ChatModel chatModel =new ChatModel();
                    chatModel.setMerchant_name(goodDetailModel.getShop().getSname());
                    chatModel.setLogo(goodDetailModel.getShop().getLogo());
                    chatModel.setCtime(SysUtil.getSysTimeFormatDay());
                    chatModel.setHx_openid(goodDetailModel.getShop().getHxid());
                    chatModel.setNum("0");
                    chatModel.setUid(Constant.UID);
                    chatModel.setTrade(goodDetailModel.getShop().getVocation());
                    chatModel.setAddress(goodDetailModel.getShop().getSaddress());
                    SDEventManager.post(chatModel, EnumEventTag.NEW_CHAT_USER.ordinal());
                    Intent chat_intent=new Intent(GoodDetailActivity.this,ChatActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("userId",goodDetailModel.getShop().getHxid());
                    bundle.putString("name",goodDetailModel.getShop().getSname());
                    chat_intent.putExtra("user",bundle);
                    startActivity(chat_intent);
                }
            }
        });
      /*  HttpMap httpMap = new HttpMap();
        httpMap.putMode("Merchant");
        httpMap.putCtl("goods");
        httpMap.putAct("goodslist");
        httpMap.putDataMap("gid",gid);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                Gson gson=new Gson();
                 goodDetailModel=gson.fromJson(data,GoodDetailModel.class);
                ViewUtil.setImageView(header,goodDetailModel.getShop().getLogo());
                storeName.setText(goodDetailModel.getShop().getName());
                storeAddr.setText(goodDetailModel.getShop().getAddress());
                freight.setText("运费:"+goodDetailModel.getGoods().getFreight()+"元");
                price.setText(goodDetailModel.getGoods().getGdprice());
                focusNum.setText("关注量:"+goodDetailModel.getGoods().getLikes());
                browseNum.setText("浏览量:"+goodDetailModel.getGoods().getViews());
                des.setText("【"+goodDetailModel.getShop().getName()+"】"+goodDetailModel.getGoods().getGdescription());
                AdvFragment advFragment = new AdvFragment();
                List<ADInfo> infos=new ArrayList<ADInfo>();
                for(int i=0;i<goodDetailModel.getGoodsimg().size();i++)
                {
                    ADInfo adInfo=new ADInfo();
                    adInfo.setUrl(goodDetailModel.getGoodsimg().get(i).getImg());
                    infos.add(adInfo);
                }
                advFragment.setAdInfoList(infos);
                getZFragmentManager().replace(R.id.adv, advFragment);
                ZWebviewFragment zWebviewFragment = new ZWebviewFragment();
                zWebviewFragment.putString("webUrl", "http://www.baidu.com");
                getZFragmentManager().replace(R.id.picText, zWebviewFragment);
                buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddCartDialog addCartDialog = new AddCartDialog(GoodDetailActivity.this);
                        addCartDialog.showDiaglog();
                    }
                });
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String mobile=goodDetailModel.getShop().getScontactstel();
                        Intent TelIntent = new Intent(Intent.ACTION_VIEW, Uri
                                .parse("tel:"+mobile));
                        startActivity(TelIntent);
                    }
                });

                store.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!goodDetailModel.getShop().getHxid().isEmpty()) {
                            ChatModel chatModel =new ChatModel();
                            chatModel.setMerchant_name(goodDetailModel.getShop().getName());
                            chatModel.setLogo(goodDetailModel.getShop().getLogo());
                            chatModel.setCtime(SysUtil.getSysTimeFormatDay());
                            chatModel.setHx_openid(goodDetailModel.getShop().getHxid());
                            chatModel.setNum("0");
                            chatModel.setTrade(goodDetailModel.getShop().getVocation());
                            chatModel.setAddress(goodDetailModel.getShop().getAddress());
                            SDEventManager.post(chatModel, EnumEventTag.NEW_CHAT_USER.ordinal());
                            Intent chat_intent=new Intent(GoodDetailActivity.this,ChatActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("userId",goodDetailModel.getShop().getHxid());
                            bundle.putString("name",goodDetailModel.getShop().getName());
                            chat_intent.putExtra("user",bundle);
                            startActivity(chat_intent);
                        }
                    }
                });
            }
        });
*/
    }

}
