package com.android.aitaojie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.aitaojie.R;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.model.AddressModel;
import com.android.zlibrary.model.ThreePickerModel;
import com.android.zlibrary.custom.ZProvinceParser;
import com.android.zlibrary.dialog.ZThreePickerDialog;
import com.android.zlibrary.dialog.ZBaseDialog;
import com.android.zlibrary.dialog.ZInputDialog;
import com.android.zlibrary.util.RegUtil;
import com.android.zlibrary.util.ViewUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sunday.eventbus.SDEventManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/26.
 */
public class MyNewAddressActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.rl_name)
    RelativeLayout rlName;
    @InjectView(R.id.mobile)
    TextView mobile;
    @InjectView(R.id.rl_mobile)
    RelativeLayout rlMobile;
    @InjectView(R.id.zipcode)
    TextView zipcode;
    @InjectView(R.id.rl_zipcode)
    RelativeLayout rlZipcode;
    @InjectView(R.id.deliver_area)
    TextView deliverArea;
    @InjectView(R.id.rl_deliver_area)
    RelativeLayout rlDeliverArea;
    @InjectView(R.id.deliver_street)
    TextView deliverStreet;
    @InjectView(R.id.rl_deliver_street)
    RelativeLayout rlDeliverStreet;
    @InjectView(R.id.addr_detail)
    TextView addrDetail;
    @InjectView(R.id.rl_addr_detail)
    RelativeLayout rlAddrDetail;
    @InjectView(R.id.comfirm)
    TextView comfirm;
    @InjectView(R.id.name)
    TextView name;
    private ZInputDialog nameDialog, mobileDialog, zipDialog, detailDialog;
    private ZThreePickerDialog areaDialog,streetDialog;
    private AddressModel addressModel;
    private String province,city,city_area,street_code;
    List<ThreePickerModel.ThirdModel> streets;

    @Override
    protected int layoutId() {
        return R.layout.act_my_edit_address;
    }

    @Override
    protected void Init() {
        super.Init();
        Intent intent=getIntent();
       boolean edit_addr=intent.getBooleanExtra("edit_address",false);
        if(edit_addr)
            addressModel= Constant.addressModel;
        if(addressModel==null) {
            setTitle("新添收货地址");
        }
        else {
            setTitle("编辑收货地址");
            comfirm.setText("保存");
            name.setText(addressModel.getContact());
            mobile.setText(addressModel.getPhone());
            zipcode.setText(addressModel.getZipcode());
            province=addressModel.getArea().get(0).getName();
            city=addressModel.getArea().get(1).getName();
            city_area=addressModel.getArea().get(2).getName();
            deliverArea.setText(province+city+city_area);
            deliverStreet.setText(addressModel.getArea().get(3).getName());
            addrDetail.setText(addressModel.getAddress());
            street_code=addressModel.getArea_code();
        }
        nameDialog = new ZInputDialog().setzInputDialogListener(new ZInputDialog.ZInputDialogListener() {
            @Override
            public void onCancelClick(View v, ZBaseDialog dialog) {

            }

            @Override
            public void onComfirm(View v, String editText, ZBaseDialog dialog) {
                if(!editText.isEmpty())
                {
                    name.setText(editText);
                    nameDialog.dismiss();
                }
                else
                    ViewUtil.showToast("请输入正确的手机号");
            }
        });
        nameDialog.setTitle("收货人");
        mobileDialog = new ZInputDialog().setzInputDialogListener(new ZInputDialog.ZInputDialogListener() {
            @Override
            public void onCancelClick(View v, ZBaseDialog dialog) {

            }

            @Override
            public void onComfirm(View v, String editText, ZBaseDialog dialog) {
                if(RegUtil.isMobile(editText)) {
                    mobile.setText(editText);
                    mobileDialog.dismiss();
                }
                else
                    ViewUtil.showToast("请输入正确的邮编");
            }
        });
        mobileDialog.setTitle("手机号");
        zipDialog = new ZInputDialog().setzInputDialogListener(new ZInputDialog.ZInputDialogListener() {
            @Override
            public void onCancelClick(View v, ZBaseDialog dialog) {

            }

            @Override
            public void onComfirm(View v, String editText, ZBaseDialog dialog) {
                if(RegUtil.isZipcode(editText)) {
                    zipcode.setText(editText);
                    zipDialog.dismiss();
                }
                else
                    ViewUtil.showToast("请输入正确的邮政编码");
            }


        });
        zipDialog.setTitle("邮政编码");
        detailDialog = new ZInputDialog().setzInputDialogListener(new ZInputDialog.ZInputDialogListener() {
            @Override
            public void onCancelClick(View v, ZBaseDialog dialog) {

            }

            @Override
            public void onComfirm(View v, String editText, ZBaseDialog dialog) {
                if(!editText.isEmpty())
                {
                    addrDetail.setText(editText);
                    detailDialog.dismiss();
                }
              else
                    ViewUtil.showToast("请输入正确的详细地址");
            }
        });
        detailDialog.setTitle("详细地址");
        areaDialog=new ZThreePickerDialog(MyNewAddressActivity.this,com.android.zlibrary.R.style.BlackDialog);
        areaDialog.setThreePickerList(ZProvinceParser.getInstance().getProvinceList()).setZAreaDialogListener(new ZThreePickerDialog.ZPickerDialogListener() {
            @Override
            public void onCancelClick(View v, ZBaseDialog dialog) {

            }
            @Override
            public void onComfirm(View v, String[] texts, ZBaseDialog dialog) {
                deliverArea.setText(texts[0]+" "+ texts[1]+" "+ texts[2]);
                province=texts[0];
                city=texts[1];
                city_area=texts[2];
            }
        });

        rlName.setOnClickListener(this);
        rlMobile.setOnClickListener(this);
        rlZipcode.setOnClickListener(this);
        rlAddrDetail.setOnClickListener(this);
        rlDeliverArea.setOnClickListener(this);
        rlDeliverStreet.setOnClickListener(this);
        comfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
         switch (v.getId())
         {
             case R.id.rl_name:
                 nameDialog.showDialog();
                 break;
             case R.id.rl_mobile:
                 mobileDialog.showDialog();
                 break;
             case R.id.rl_zipcode:
                 zipDialog.showDialog();
                 break;
             case R.id.rl_addr_detail:
                 detailDialog.showDialog();
                 break;
             case R.id.rl_deliver_area:
                 areaDialog.showDialog();
                 break;
             case R.id.rl_deliver_street:
                 if(deliverArea.getText().toString().equals("请选择配送区域"))
                 {
                     ViewUtil.showToast("请先选择配送区域");
                     return ;
                 }
                 httpStreet();
                 break;
             case R.id.comfirm:
                 postInfo();
                 break;
         }
    }
    public void httpStreet()
    {
        HttpMap httpMap=new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("address");
        httpMap.putAct("getstreet");
        httpMap.putDataMap("province", province);
        httpMap.putDataMap("city", city);
        httpMap.putDataMap("area", city_area);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                if(data.isEmpty())
                {
                    ViewUtil.showToast("该区域没有配送街道,请选择其他区域");
                    return;
                }
                else {
                    Gson gson = new Gson();
                  streets = gson.fromJson(data,new TypeToken<List<ThreePickerModel.ThirdModel>>(){}.getType());
                    List<ThreePickerModel.SecondModel> streetList2=new ArrayList<>();
                    ThreePickerModel.SecondModel streetModel2=new ThreePickerModel.SecondModel();
                    streetModel2.setName("h1");
                    streetModel2.setThirdList(streets);
                    streetList2.add(streetModel2);
                    List<ThreePickerModel.FirstModel> streetList1=new ArrayList<>();
                    ThreePickerModel.FirstModel streetModel1=new ThreePickerModel.FirstModel();
                    streetModel1.setName("h2");
                    streetModel1.setSecondList(streetList2);
                    streetList1.add(streetModel1);
                    streetDialog=new ZThreePickerDialog(MyNewAddressActivity.this,com.android.zlibrary.R.style.BlackDialog);
                    streetDialog.setPickerNum(1).setThreePickerList(streetList1).setZAreaDialogListener(new ZThreePickerDialog.ZPickerDialogListener() {
                        @Override
                        public void onCancelClick(View v, ZBaseDialog dialog) {

                        }

                        @Override
                        public void onComfirm(View v, String[] texts, ZBaseDialog dialog) {
                            deliverStreet.setText(texts[2]);
                            for(int i=0;i<streets.size();i++)
                            {
                                if(streets.get(i).getName().equals(texts[2]))
                                street_code=streets.get(i).getCode();
                            }
                        }
                    });
                    streetDialog.showDialog();
                }
            }
        });
    }
  public void postInfo()
  {
      if(name.getText().toString().equals("请输入收货人"))
      {
          ViewUtil.showToast("请输入收货人");
          return ;
      }
      if(mobile.getText().toString().equals("请输入手机号"))
      {
          ViewUtil.showToast("请输入手机号");
          return ;
      }
      if(zipcode.getText().toString().equals("请输入邮编"))
      {
          ViewUtil.showToast("请输入邮编");
          return ;
      }
      if(deliverArea.getText().toString().equals("请选择配送区域"))
      {
          ViewUtil.showToast("请选择配送区域");
          return ;
      }
      if(deliverStreet.getText().toString().equals("请选择配送街道"))
      {
          ViewUtil.showToast("请选择配送街道");
          return ;
      }
      if(addrDetail.getText().toString().equals("请填写详细地址"))
      {
          ViewUtil.showToast("请填写详细地址");
          return ;
      }
      if(addressModel==null)
         uploadInfo();
      else
          updateInfo();
  }
    public void uploadInfo()
    {
        HttpMap httpMap=new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("address");
        httpMap.putAct("insert");
        httpMap.putDataMap("uid", Constant.UID);
        httpMap.putDataMap("contact", name.getText().toString());
        httpMap.putDataMap("phone", mobile.getText().toString());
        httpMap.putDataMap("zipcode",zipcode.getText().toString());
        httpMap.putDataMap("area_code", street_code);
        httpMap.putDataMap("address", addrDetail.getText().toString());
        httpMap.putDataMap("default", "0");
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                SDEventManager.post(EnumEventTag.REFRESH_ADDRESS_LIST.ordinal());
            }
        });
    }
    public void updateInfo()
    {
        HttpMap httpMap=new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("address");
        httpMap.putAct("update");
        httpMap.putDataMap("uid", Constant.UID);
        httpMap.putDataMap("id", addressModel.getId());
        httpMap.putDataMap("contact", name.getText().toString());
        httpMap.putDataMap("phone", mobile.getText().toString());
        httpMap.putDataMap("zipcode",zipcode.getText().toString());
        httpMap.putDataMap("area_code", street_code);
        httpMap.putDataMap("address", addrDetail.getText().toString());
        httpMap.putDataMap("default", addressModel.getDefaultX());
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                SDEventManager.post(EnumEventTag.REFRESH_ADDRESS_LIST.ordinal());
            }
        });

    }
}
