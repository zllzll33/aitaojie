package com.android.aitaojie.config;



import com.android.aitaojie.model.AddressModel;
import com.android.aitaojie.model.LiveModel;

import java.util.List;

/**
 * Created by win7 on 2016/6/4.
 */
public class Constant {
    public static final String Server="http://app.api.ali521.com";
    public static final String  WX_APPID="wx80465530f6ade4ce";
    public static final String  WX_SECRET="152199531b31f6f7caa7048ba2e14e6d";
    public static final   String QQ_APPID="1105486565";

    /*动态权限*/
    public static final    int PermissionCamera=200;
    public static final String CameraPermision="android.permission.CAMERA";
    public static final    int PermissionLocation=201;
    public static final  String LocationPermision="android.permission.ACCESS_FINE_LOCATION";


    public static String UID,CityCode="",JID="1",huanxin_name,huanxin_pwd,isChattionId="",ticker;
    public static  final String urlServer="http://m.luofangos.com";
    public static String MyCartUrl =urlServer+"/merchant/shopcart/index.html?uid=%s";              //购物车
    public static String   MyVoucherUrl= urlServer+ "/user/voucher/index.html?uid=%s"; //优惠券
    public static String   VoucherDetailUrl=urlServer+"/user/voucher/view.html?vu_id=%s&jid=%s" ;
    public static String   AppointUrl=urlServer+"merchant/reserve.html?gid=%s&jid=%s" ;
    public static String   MyOrderUrl=urlServer+"/user/order/index.html?uid=%s" ;
    public static String   MyAppointUrl=urlServer+"/user/bespeak/index.html?uid=%s" ;
    public static String   GoodDetaiUrl=urlServer+"/merchant/goods/view.html?gid=%s" ;
    public static List<LiveModel.AreaBean> area;
    public static  List<LiveModel.DistanceBean> distance;
    public static  List<LiveModel.TradeBean> trade;
    public static List<LiveModel.FilterBean> filter;
    public static AddressModel addressModel;
}
