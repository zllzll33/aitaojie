package com.android.aitaojie.model;

import java.util.List;

/**
 * Created by win7 on 2016/8/16.
 */
public class BMapModel {

        /**
         * name : 茅台镇-地方特色
         * address : 贵州省遵义市仁怀市茅台镇
         * views : 222
         * likes : 555
         * lat :
         * lng :
         * jid : 67
         * img : http://www.luofangyun.comhttp://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/02/04/ChMkJlbKyBmIHnT1AARH98qGLIsAALH9QAYXO8ABEgP381.jpg
         * logo : http://www.luofangyun.comhttp://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/02/04/ChMkJlbKyBmIHnT1AARH98qGLIsAALH9QAYXO8ABEgP381.jpg
         * distance : 800米
         * trade : 美食
         */

        private List<MerchantBean> merchant;

        public List<MerchantBean> getMerchant() {
            return merchant;
        }

        public void setMerchant(List<MerchantBean> merchant) {
            this.merchant = merchant;
        }

        public static class MerchantBean {
            private String name;
            private String address;
            private String views;
            private String likes;
            private String lat;
            private String lng;
            private String jid;
            private String img;
            private String logo;
            private String distance;
            private String trade;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getViews() {
                return views;
            }

            public void setViews(String views) {
                this.views = views;
            }

            public String getLikes() {
                return likes;
            }

            public void setLikes(String likes) {
                this.likes = likes;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getJid() {
                return jid;
            }

            public void setJid(String jid) {
                this.jid = jid;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getTrade() {
                return trade;
            }

            public void setTrade(String trade) {
                this.trade = trade;
            }
        }

}
