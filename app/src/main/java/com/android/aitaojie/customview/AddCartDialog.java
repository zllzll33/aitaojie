package com.android.aitaojie.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.adapter.GoodSpecificationAdapter;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.AddCartModel;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.customView.ZGridView;
import com.android.zlibrary.util.SysUtil;
import com.android.zlibrary.util.TypeUtil;
import com.android.zlibrary.util.ViewUtil;
import com.google.gson.Gson;
import com.sunday.eventbus.SDEventManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/22.
 */
public class AddCartDialog extends Dialog {
    String gid;
    protected Context context;
    TextView currentPrice;
    TextView inventory;
    ImageView img;
    AddCartModel addCartModel;
    ZGridView specification;
    TextView num;
   String imgStr;
  View   grid_line;
    GoodSpecificationAdapter goodSpecificationAdapter;
    private boolean isWeb;
    public AddCartDialog(AddCartModel addCartModel,boolean isWeb) {
        super(ZActivityManager.getInstance().getLastActivity(), com.android.zlibrary.R.style.Basedialog);
        this.isWeb=isWeb;
        this.addCartModel=addCartModel;
        this.context = ZActivityManager.getInstance().getLastActivity();
        init();
    }

    public AddCartDialog(Context context) {
        super(context, com.android.zlibrary.R.style.Basedialog);
        this.context = context;
        init();
    }

    public AddCartDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_add_cart, null, false);
        View disappear = layout.findViewById(R.id.disappear);
         img=(ImageView)layout.findViewById(R.id.img);
        currentPrice=(TextView)layout.findViewById(R.id.current_price);
        TextView originPrice=(TextView)layout.findViewById(R.id.origin_price);
       inventory=(TextView)layout.findViewById(R.id.inventory);
        ImageView mimus=(ImageView)layout.findViewById(R.id.mimus);
        num=(TextView)layout.findViewById(R.id.num);
        grid_line=(View)layout.findViewById(R.id.grid_line);
        ImageView add=(ImageView)layout.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(String.valueOf(Integer.parseInt(num.getText().toString())+1));
            }
        });
        mimus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buy_num=Integer.parseInt(num.getText().toString());
                if(buy_num==1)
                    return;
                num.setText(String.valueOf(buy_num-1));
            }
        });
        TextView comfirm=(TextView)layout.findViewById(R.id.comfirm);
        addContentView(layout, new LinearLayout.LayoutParams(
                SysUtil.getSreeenParam()[0], LinearLayout.LayoutParams.WRAP_CONTENT));
        disappear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCartDialog.this.dismiss();
            }
        });
        specification = (ZGridView) layout.findViewById(R.id.specification);
        currentPrice.setText("￥"+addCartModel.getDefault_spce().getPrice());
        inventory.setText("库存:"+addCartModel.getDefault_spce().getStock());
        imgStr=addCartModel.getDefault_spce().getImg();
        ViewUtil.setImageView(img,addCartModel.getDefault_spce().getImg());
        if(addCartModel.getSpce().size()==0) {

            return;
        }
        grid_line.setVisibility(View.VISIBLE);
        List<AddCartModel.SpceBean> list =addCartModel.getSpce();
        goodSpecificationAdapter = new GoodSpecificationAdapter(list, ZActivityManager.getInstance().getLastActivity());
        goodSpecificationAdapter.setSpecListener(new GoodSpecificationAdapter.SpecListener() {
            @Override
            public void onSpec() {
                HttpMap httpMap = new HttpMap();
                httpMap.putMode("Merchant");
                httpMap.putCtl("goods");
                httpMap.putAct("spec");
                httpMap.putDataMap("gid", addCartModel.getDefault_spce().getGoods_id());
                Map.Entry entry;
                StringBuffer sb = new StringBuffer();
               {
                    for (Iterator iterator = goodSpecificationAdapter.getTagMap().entrySet().iterator(); iterator.hasNext(); ) {
                        entry = (Map.Entry) iterator.next();
                        httpMap.putDataMap(entry.getKey().toString(), entry.getValue().toString());
                    }
                }
                httpMap.setHttpListener(new HttpMap.HttpListener() {
                    @Override
                    public void onSuccess(String response, String data) {
                     try {
                         JSONObject jsonObject=new JSONObject(data);
                         String gprice=jsonObject.optString("price");
                         String gstock=jsonObject.optString("stock");
                         String gimg=jsonObject.optString("img");
                         currentPrice.setText("￥"+gprice);
                         inventory.setText("库存:"+gstock);
                         if(imgStr.equals(gimg))
                             return;
                         ViewUtil.setImageView(img,gimg);
                         imgStr=gimg;
                     }catch (Exception e)
                     {}
                    }
                });
            }
        });
        specification.setAdapter(goodSpecificationAdapter);
        comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(goodSpecificationAdapter.getTagMap().size()<addCartModel.getSpce().size())
             {
                 ViewUtil.showToast("请选择规格");
                 return;
             }
                HttpMap httpMap = new HttpMap();
                httpMap.putMode("Merchant");
                httpMap.putCtl("ShopCart");
                httpMap.putAct("add");
                httpMap.putDataMap("jid", addCartModel.getDefault_spce().getJid());
                httpMap.putDataMap("uid", Constant.UID);
                httpMap.putDataMap("gid",  addCartModel.getDefault_spce().getGoods_id());
                httpMap.putDataMap("buy_num",num.getText().toString());
                Map.Entry entry;
                if(addCartModel.getSpce().size()!=0) {
                    for (Iterator iterator = goodSpecificationAdapter.getTagMap().entrySet().iterator(); iterator.hasNext(); ) {
                        entry = (Map.Entry) iterator.next();
                        httpMap.putDataMap(entry.getKey().toString(), null==entry.getValue()?"":entry.getValue().toString());
                    }
                }
                if(!isWeb) {
                    httpMap.setHttpListener(new HttpMap.HttpListener() {
                        @Override
                        public void onSuccess(String response, String data) {
                            dismiss();
                        }
                    });
                }
                else
                {
                    httpMap.putDataMap("type","ACW_SPEC");
                    String oreder_str= TypeUtil.Map2JsonStr(httpMap.getDataMap());
                    SDEventManager.post(oreder_str, EnumEventTag.CART_ORDER.ordinal());
                    dismiss();
                }
            }
        });

    }

    public void showDiaglog() {
        if (!isShowing()) {
            Window window = getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(com.android.zlibrary.R.style.menuDialogshowStyle);
            setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
            show();
        }
    }

}
