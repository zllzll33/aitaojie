package com.android.aitaojie.model;

import java.io.Serializable;

/**
 * Created by win7 on 2016/8/8.
 */
public class ChatModel  implements Serializable{
private  String hx_openid;
    private String num;
    private String uid;
    private String merchant_name;
    private String logo;
    private String trade;
    private String address;
    private String ctime;
   private String jid;

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getHx_openid() {
        return hx_openid;
    }

    public void setHx_openid(String hx_openid) {
        this.hx_openid = hx_openid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
}
