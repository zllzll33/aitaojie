package com.android.aitaojie.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.aitaojie.MainActivity;
import com.android.aitaojie.Manifest;
import com.android.aitaojie.R;
import com.android.aitaojie.config.App;
import com.android.aitaojie.config.Constant;
import com.android.aitaojie.config.EnumEventTag;
import com.android.aitaojie.config.HttpMap;
import com.android.aitaojie.config.StringRequestGet;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.zlibrary.adapter.ZSimpleTextMenuAdapter;
import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.custom.ZPicChooser;
import com.android.zlibrary.customView.CircleImageView;
import com.android.zlibrary.dialog.ZBaseDialog;
import com.android.zlibrary.dialog.ZDialogMenu;
import com.android.zlibrary.dialog.ZInputDialog;
import com.android.zlibrary.util.PreferenceUtil;
import com.android.zlibrary.util.ViewUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.sunday.eventbus.SDEventManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by win7 on 2016/7/25.
 */
public class MyAccountActivity extends BaseActivity implements View.OnClickListener {
    @InjectView(R.id.header)
    CircleImageView header;
    @InjectView(R.id.nick)
    TextView nick;
    @InjectView(R.id.gender)
    TextView gender;
    @InjectView(R.id.mobile)
    TextView mobile;
    @InjectView(R.id.sign)
    TextView sign;
    @InjectView(R.id.login_icon)
    ImageView loginIcon;
    @InjectView(R.id.ll_header)
    LinearLayout llHeader;
    @InjectView(R.id.back)
    View back;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.ll_nick)
    LinearLayout llNick;
    @InjectView(R.id.ll_gender)
    LinearLayout llGender;
    @InjectView(R.id.ll_sign)
    LinearLayout llSign;
    @InjectView(R.id.ll_addr)
    LinearLayout llAddr;
    @InjectView(R.id.ll_changePW)
    LinearLayout llChangePW;
    private ZDialogMenu headerDialog, genderDialog;
    private ZInputDialog nickDialog, signDialog;
    private ZSimpleTextMenuAdapter<String> headerChooseAdapter, genderChooseAdapter;
    private ZPicChooser picChooser;

    @Override
    protected int layoutId() {
        return R.layout.act_my_account;
    }

    @Override
    protected void Init() {
        super.Init();
        setTitle("账号管理");
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("account");
        httpMap.putAct("index");
        httpMap.putDataMap("uid", Constant.UID);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String m_avatar = jsonObject.optString("m_avatar");
                    String m_nickName = jsonObject.optString("m_nickname");
                    String sex = jsonObject.optString("sex");
                    String autograph = jsonObject.optString("autograph");
                    String m_phone = jsonObject.optString("m_phone");
                    String type = jsonObject.optString("type");
                    if (!m_avatar.isEmpty())
                        ViewUtil.setImageView(header, m_avatar);
                    nick.setText(m_nickName);
                    if(sex.equals("0"))
                    gender.setText("保密");
                     if(sex.equals("1"))
                        gender.setText("男");
                    if(sex.equals("2"))
                        gender.setText("女");
                    sign.setText(autograph);
                    mobile.setText(m_phone);
                    if (type.equals("0"))
                        loginIcon.setImageResource(R.mipmap.my_login_pw);
                    else if (type.equals("1"))
                        loginIcon.setImageResource(R.mipmap.my_login_qq);
                    else if (type.equals("2"))
                        loginIcon.setImageResource(R.mipmap.my_login_wx);
                    else if (type.equals("3"))
                        loginIcon.setImageResource(R.mipmap.my_login_mobile);
                } catch (JSONException e) {

                }

            }
        });
        picChooser = new ZPicChooser(MyAccountActivity.this);
        List<String> headerChooseList = new ArrayList<String>();
        headerChooseList.add("拍照");
        headerChooseList.add("本地图库");
        headerChooseAdapter = new ZSimpleTextMenuAdapter<>(headerChooseList, MyAccountActivity.this);
        headerDialog = new ZDialogMenu(MyAccountActivity.this, com.android.zlibrary.R.style.BlackDialog).setzMenuDialogListener(new ZDialogMenu.ZMenuDialogListener() {
            @Override
            public void onCancelClick(View v, ZDialogMenu dialog) {
            }

            @Override
            public void onItemClick(View v, int index, ZDialogMenu dialog) {
                switch (index) {
                    case 0:
                        if (Build.VERSION.SDK_INT >= 23) {
                            int checkCallPhonePermission = ContextCompat.checkSelfPermission(App.getInstance(), Constant.CameraPermision);
                            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                                ActivityCompat.requestPermissions(MyAccountActivity.this,new String[]{Constant.CameraPermision},Constant.PermissionCamera);
                                return;
                            }else{
                                //上面已经写好的拨号方法
                                picChooser.startActionCamera();
                            }
                        } else {
                            //上面已经写好的拨号方法
                            picChooser.startActionCamera();
                        }

                        break;
                    case 1:
                        picChooser.startImagePick();
                        break;
                }

            }
        });
        headerDialog.setAdapter(headerChooseAdapter);
        headerChooseList = new ArrayList<String>();
        headerChooseList.add("男");
        headerChooseList.add("女");
        headerChooseList.add("保密");
        genderChooseAdapter = new ZSimpleTextMenuAdapter<String>(headerChooseList, MyAccountActivity.this);

        genderDialog = new ZDialogMenu(MyAccountActivity.this, com.android.zlibrary.R.style.BlackDialog).setzMenuDialogListener(new ZDialogMenu.ZMenuDialogListener() {
            @Override
            public void onCancelClick(View v, ZDialogMenu dialog) {
            }

            @Override
            public void onItemClick(View v, int index, ZDialogMenu dialog) {
                switch (index) {
                    case 0:
                        genderDialog.dismiss();
                        htttpSetGender("男");
                        break;
                    case 1:
                        genderDialog.dismiss();
                        htttpSetGender("女");
                        break;
                    case 2:
                        genderDialog.dismiss();
                        htttpSetGender("保密");
                        break;
                }

            }
        });

        genderDialog.setAdapter(genderChooseAdapter);
        nickDialog = new ZInputDialog(MyAccountActivity.this);
        nickDialog.setTitle("昵称");
        nickDialog.setzInputDialogListener(new ZInputDialog.ZInputDialogListener() {
            @Override
            public void onCancelClick(View v, ZBaseDialog dialog) {
            }

            @Override
            public void onComfirm(View v, String editText, ZBaseDialog dialog) {
                httpSetNick(editText);
                nickDialog.dismiss();
            }
        });
        signDialog = new ZInputDialog(MyAccountActivity.this);
        signDialog.setTitle("个性签名");
        signDialog.setzInputDialogListener(new ZInputDialog.ZInputDialogListener() {
            @Override
            public void onCancelClick(View v, ZBaseDialog dialog) {

            }

            @Override
            public void onComfirm(View v, String editText, ZBaseDialog dialog) {
                if(editText.length()>15)
                {
                    ViewUtil.showToast("签名字数不大于15");
                }
                else {
                    httpSetSign(editText);
                    signDialog.dismiss();
                }
            }
        });
        llHeader.setOnClickListener(this);
        llNick.setOnClickListener(this);
        llGender.setOnClickListener(this);
        llSign.setOnClickListener(this);
        llAddr.setOnClickListener(this);
        llChangePW.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_header:
                headerDialog.showDialog();
                break;
            case R.id.ll_nick:
                nickDialog.showDialog();
                break;
            case R.id.ll_gender:
                genderDialog.showDialog();
                break;
            case R.id.ll_sign:
                signDialog.showDialog();
                break;
            case R.id.ll_changePW:
                Intent c_intent=new Intent(MyAccountActivity.this,ChangePWActivity.class);
                startActivity(c_intent);
                break;
            case R.id.ll_addr:
                Intent intent = new Intent(MyAccountActivity.this, MyAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void htttpSetGender(String u_sex) {
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("account");
        httpMap.putAct("changeSex");
        httpMap.putDataMap("uid", Constant.UID);
        if(u_sex.equals("保密"))
        httpMap.putDataMap("sex", "0");
        else if(u_sex.equals("男"))
            httpMap.putDataMap("sex", "1");
        else if(u_sex.equals("女"))
            httpMap.putDataMap("sex", "2");
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                if(data.equals("0"))
                    gender.setText("保密");
               else  if(data.equals("1"))
                    gender.setText("男");
                else  if(data.equals("2"))
                    gender.setText("女");
            }
        });
    }

    private void httpSetNick(String m_nickname) {
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("account");
        httpMap.putAct("changeNickName");
        httpMap.putDataMap("uid", Constant.UID);
        httpMap.putDataMap("m_nickname", m_nickname);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                nick.setText(data);
                SDEventManager.post(data, EnumEventTag.Change_NICK.ordinal());
            }
        });
    }

    private void httpSetSign(String m_sign) {
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("account");
        httpMap.putAct("changeSignature");
        httpMap.putDataMap("uid", Constant.UID);
        httpMap.putDataMap("autograph", m_sign);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
                sign.setText(data);
                SDEventManager.post(data, EnumEventTag.CHANG_SIGN.ordinal());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ZPicChooser.PicCamera:
                picChooser.startActionCrop();// 拍照后裁剪
                break;
            case ZPicChooser.PicGallery:
                picChooser.startActionCrop(data.getData());// 选图后裁剪
                break;
            case ZPicChooser.PicCrop:
                uploadNewPhoto();// 上传新照片
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
 private void uploadNewPhoto()
 {
     StringRequestGet request =   new StringRequestGet(Constant.Server+"/User/qiniu/getToken", null, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
            try{
//                Log.e("uptoken",response);
                JSONObject jsonObject=new JSONObject(response);
            String   uptoken=jsonObject.optString("uptoken");
                upLoadQiniu(uptoken);
              } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
         }
     }
     );
     App.getInstance().HttpGet(request);
 }
    private void upLoadQiniu(String utoken)
    {
        UploadManager uploadManager = new UploadManager();
        String pic=picChooser.getCorpPicPath();
//             Log.e("picpath",pic);
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String key="";
        if(pic.contains(".jpg"))
            key ="android_header_"+Constant.UID+timeStamp+".jpg";
        else if(pic.contains(".png"))
            key ="android_header_"+Constant.UID+timeStamp+".png";
        uploadManager.put(picChooser.getCorpBytes(), key, utoken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置。
                        httpUpHeader(key);
//
                    }
                }, null);
    }
    private void httpUpHeader(String header_str)
    {
        HttpMap httpMap = new HttpMap();
        httpMap.putMode("User");
        httpMap.putCtl("account");
        httpMap.putAct("changeavatar");
        httpMap.putDataMap("uid", Constant.UID);
        httpMap.putDataMap("m_avatar", header_str);
        httpMap.setHttpListener(new HttpMap.HttpListener() {
            @Override
            public void onSuccess(String response, String data) {
            if(data.isEmpty())
                return;
              SDEventManager.post(data,EnumEventTag.CHAGE_HEADER.ordinal());
                ViewUtil.setImageView(header,data);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Constant.PermissionCamera:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    picChooser.startActionCamera();;
                } else {
                   ViewUtil.showToast("请同意访问相机后 再从相机中选择头像");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
