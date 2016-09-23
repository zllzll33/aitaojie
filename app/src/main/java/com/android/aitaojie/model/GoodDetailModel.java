package com.android.aitaojie.model;

import java.util.List;

/**
 * Created by win7 on 2016/8/11.
 */
public class GoodDetailModel {


        private GoodsBean goods;
        /**
         * sid : 1
         * hxid :
         * sname : 金家渡店
         * saddress : 金家渡路58号
         * scontactstel : 18668197203
         */

        private ShopBean shop;
        /**
         * img : http://o9zkkc1qy.bkt.clouddn.com/2016_07_525145a2-aae2-4d63-a4b1-4c3529d6da57.jpg
         */

        private List<GoodsimgBean> goodsimg;

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
        }

        public List<GoodsimgBean> getGoodsimg() {
            return goodsimg;
        }

        public void setGoodsimg(List<GoodsimgBean> goodsimg) {
            this.goodsimg = goodsimg;
        }

        public static class GoodsBean {
            private String gid;
            private String sid;
            private String gname;
            private String gdprice;
            private String gdescription;
            private String detail;
            private String freight;
            private String follow;
            private String watch;
            private List<?> specs;

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getSid() {
                return sid;
            }

            public void setSid(String sid) {
                this.sid = sid;
            }

            public String getGname() {
                return gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getGdprice() {
                return gdprice;
            }

            public void setGdprice(String gdprice) {
                this.gdprice = gdprice;
            }

            public String getGdescription() {
                return gdescription;
            }

            public void setGdescription(String gdescription) {
                this.gdescription = gdescription;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getFreight() {
                return freight;
            }

            public void setFreight(String freight) {
                this.freight = freight;
            }

            public String getFollow() {
                return follow;
            }

            public void setFollow(String follow) {
                this.follow = follow;
            }

            public String getWatch() {
                return watch;
            }

            public void setWatch(String watch) {
                this.watch = watch;
            }

            public List<?> getSpecs() {
                return specs;
            }

            public void setSpecs(List<?> specs) {
                this.specs = specs;
            }
        }

        public static class ShopBean {
            private String sid;
            private String hxid;
            private String sname;
            private String saddress;
            private String scontactstel;
            private  String logo;
           private  String vocation;

            public String getVocation() {
                return vocation;
            }

            public void setVocation(String vocation) {
                this.vocation = vocation;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getSid() {
                return sid;
            }

            public void setSid(String sid) {
                this.sid = sid;
            }

            public String getHxid() {
                return hxid;
            }

            public void setHxid(String hxid) {
                this.hxid = hxid;
            }

            public String getSname() {
                return sname;
            }

            public void setSname(String sname) {
                this.sname = sname;
            }

            public String getSaddress() {
                return saddress;
            }

            public void setSaddress(String saddress) {
                this.saddress = saddress;
            }

            public String getScontactstel() {
                return scontactstel;
            }

            public void setScontactstel(String scontactstel) {
                this.scontactstel = scontactstel;
            }
        }

        public static class GoodsimgBean {
            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

}
