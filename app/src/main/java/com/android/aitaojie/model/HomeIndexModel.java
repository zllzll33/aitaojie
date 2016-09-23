package com.android.aitaojie.model;

import java.util.List;

/**
 * Created by win7 on 2016/8/10.
 */
public class HomeIndexModel {


    /**
     * slide : [{"img":"http://imgsrc.baidu.com/forum/w%3D580/sign=52c78966f5faaf5184e381b7bc5594ed/8a47ac51f8198618aaa6cec442ed2e738ad4e66d.jpg","url":""},{"img":"http://imgsrc.baidu.com/forum/w%3D580/sign=52c78966f5faaf5184e381b7bc5594ed/8a47ac51f8198618aaa6cec442ed2e738ad4e66d.jpg","url":""},{"img":"http://imgsrc.baidu.com/forum/w%3D580/sign=52c78966f5faaf5184e381b7bc5594ed/8a47ac51f8198618aaa6cec442ed2e738ad4e66d.jpg","url":""},{"img":"http://imgsrc.baidu.com/forum/w%3D580/sign=52c78966f5faaf5184e381b7bc5594ed/8a47ac51f8198618aaa6cec442ed2e738ad4e66d.jpg","url":""}]
     * navigation : [{"name":"商品","url":"www.sina.com","img":"http://pic4.nipic.com/20091219/818147_095917906901_2.jpg"},{"name":"预约","url":"www.baidu.com","img":"http://www.yooyoo360.com/photo/2009-1-1/20090112112621789.jpg"}]
     * recommend : [{"img":"http://t1.s2.dpfile.com/pc/mc/16b8a7fd8b67eaea19170a3d37fded1e%28640x1024%29/thumb.jpg","url":"www.sina.com","rorder":"1"},{"img":"http://imagefile.n21.cc/2015/9/13/20159131442115118054_34.jpg","url":"www.baidu,com","rorder":"2"},{"img":"http://pic.58pic.com/58pic/15/00/62/74u58PICyGg_1024.jpg","url":"www.163.com","rorder":"3"},{"img":"http://img1.juimg.com/150919/330529-15091910322281.jpg","url":"www.baidu.com","rorder":"4"}]
     * shop : {"jid":"1","name":"杭州分店","scontactstel":"18357120994","address":"浙江省杭州市西湖区","city":"330100","likes":"250","views":"400"}
     * vocation : {"v_id":"17","v_title":"茶馆"}
     * goods : [{"gid":"10481","gname":"测试商品11","gdescription":"测试商品描述","gdprice":"50","gimg":"http://img06.tooopen.com/images/20160802/tooopen_sy_173677692116.jpg"},{"gid":"10474","gname":"测试11","gdescription":"风格的风格","gdprice":"1208","gimg":"http://img06.tooopen.com/images/20160808/tooopen_sy_174565314441.jpg"},{"gid":"10473","gname":"测试123","gdescription":"风格的风格","gdprice":"968","gimg":"http://img05.tooopen.com/images/20150202/sy_80219211654.jpg"},{"gid":"10472","gname":"20M 宽带 两年0","gdescription":"大范甘迪","gdprice":"728","gimg":"http://img06.tooopen.com/images/20160731/tooopen_sy_173429136985.jpg"},{"gid":"10471","gname":"酱香名酒1","gdescription":"大范甘迪","gdprice":"720","gimg":"http://img06.tooopen.com/images/20160724/tooopen_sy_171572235394.jpg"},{"gid":"10470","gname":"水电费过水电费","gdescription":"大幅度发","gdprice":"20","gimg":"http://img06.tooopen.com/images/20160724/tooopen_sy_171644288795.jpg"},{"gid":"10391","gname":"年货卤味礼盒装卤味粽子熟食","gdescription":"好运当道，福运一方。","gdprice":"50","gimg":"http://img06.tooopen.com/images/20160725/tooopen_sy_171740978178.jpg"},{"gid":"10390","gname":"年货卤味礼盒装卤味粽子熟食","gdescription":"好运当道，福运一方。","gdprice":"118","gimg":"http://pic15.nipic.com/20110714/128199_165139702002_2.jpg"},{"gid":"8566","gname":"印尼进口饼干Danisa/皇冠丹麦曲奇368g/盒年货","gdescription":"年货分享装 进口黄油制作 原装进口","gdprice":"42","gimg":"http://img.taopic.com/uploads/allimg/121015/240425-12101521295419.jpg"},{"gid":"7429","gname":"德国进口牧牌部分脱脂纯牛奶200ml*24盒整箱","gdescription":"真正欧洲大牌 滴滴醇香","gdprice":"149","gimg":"http://img2.imgtn.bdimg.com/it/u=791312913,2018349329&fm=21&gp=0.jpg"}]
     * address : {"aname":"杭州市"}
     * voucher : [{"vu_id":"111","vu_name":"5元红包","vu_price":"5","vu_stime":"2016-08-09 10:00:00","vu_etime":"2016-08-19 16:00:00","vu_description":"5元红包"},{"vu_id":"57","vu_name":"20元优惠券","vu_price":"20","vu_stime":"2016-01-27 11:47:00","vu_etime":"2016-10-27 11:47:00","vu_description":"限时抢购"}]
     */

        /**
         * jid : 1
         * name : 杭州分店
         * scontactstel : 18357120994
         * address : 浙江省杭州市西湖区
         * city : 330100
         * likes : 250
         * views : 400
         */

        private ShopBean shop;
        /**
         * v_id : 17
         * v_title : 茶馆
         */

        private VocationBean vocation;
        /**
         * aname : 杭州市
         */

        private AddressBean address;
        /**
         * img : http://imgsrc.baidu.com/forum/w%3D580/sign=52c78966f5faaf5184e381b7bc5594ed/8a47ac51f8198618aaa6cec442ed2e738ad4e66d.jpg
         * url :
         */

        private List<SlideBean> slide;
        /**
         * name : 商品
         * url : www.sina.com
         * img : http://pic4.nipic.com/20091219/818147_095917906901_2.jpg
         */

        private List<NavigationBean> navigation;
        /**
         * img : http://t1.s2.dpfile.com/pc/mc/16b8a7fd8b67eaea19170a3d37fded1e%28640x1024%29/thumb.jpg
         * url : www.sina.com
         * rorder : 1
         */

        private List<RecommendBean> recommend;
        /**
         * gid : 10481
         * gname : 测试商品11
         * gdescription : 测试商品描述
         * gdprice : 50
         * gimg : http://img06.tooopen.com/images/20160802/tooopen_sy_173677692116.jpg
         */

        private List<GoodsBean> goods;
        /**
         * vu_id : 111
         * vu_name : 5元红包
         * vu_price : 5
         * vu_stime : 2016-08-09 10:00:00
         * vu_etime : 2016-08-19 16:00:00
         * vu_description : 5元红包
         */

        private List<VoucherBean> voucher;

        public ShopBean getShop() {
            return shop;
        }

        public void setShop(ShopBean shop) {
            this.shop = shop;
        }

        public VocationBean getVocation() {
            return vocation;
        }

        public void setVocation(VocationBean vocation) {
            this.vocation = vocation;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public List<SlideBean> getSlide() {
            return slide;
        }

        public void setSlide(List<SlideBean> slide) {
            this.slide = slide;
        }

        public List<NavigationBean> getNavigation() {
            return navigation;
        }

        public void setNavigation(List<NavigationBean> navigation) {
            this.navigation = navigation;
        }

        public List<RecommendBean> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<RecommendBean> recommend) {
            this.recommend = recommend;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public List<VoucherBean> getVoucher() {
            return voucher;
        }

        public void setVoucher(List<VoucherBean> voucher) {
            this.voucher = voucher;
        }

        public static class ShopBean {
            private String jid;
            private String name;
            private String scontactstel;
            private String address;
            private String city;
            private String likes;
            private String views;
            private String lat;
            private String lng;
            private String logo;
            private String trade;
            private String  hxid;
            private String hxusername;

            public String getHxusername() {
                return hxusername;
            }

            public void setHxusername(String hxusername) {
                this.hxusername = hxusername;
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

            public String getHxid() {
                return hxid;
            }

            public void setHxid(String hxid) {
                this.hxid = hxid;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getScontactstel() {
                return scontactstel;
            }

            public void setScontactstel(String scontactstel) {
                this.scontactstel = scontactstel;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getLikes() {
                return likes;
            }

            public void setLikes(String likes) {
                this.likes = likes;
            }

            public String getViews() {
                return views;
            }

            public void setViews(String views) {
                this.views = views;
            }
        }

        public static class VocationBean {
            private String v_id;
            private String v_title;

            public String getV_id() {
                return v_id;
            }

            public void setV_id(String v_id) {
                this.v_id = v_id;
            }

            public String getV_title() {
                return v_title;
            }

            public void setV_title(String v_title) {
                this.v_title = v_title;
            }
        }

        public static class AddressBean {
            private String aname;

            public String getAname() {
                return aname;
            }

            public void setAname(String aname) {
                this.aname = aname;
            }
        }

        public static class SlideBean {
            private String img;
            private String url;
            private String type;
            private  String pk;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPk() {
                return pk;
            }

            public void setPk(String pk) {
                this.pk = pk;
            }
            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class NavigationBean {
            private String name;
            private String url;
            private String img;
            private String type;
            private  String pk;

            public String getPk() {
                return pk;
            }

            public void setPk(String pk) {
                this.pk = pk;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }

        public static class RecommendBean {
            private String img;
            private String url;
          private String type;
            private String pk;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPk() {
                return pk;
            }

            public void setPk(String pk) {
                this.pk = pk;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }



        }

        public static class GoodsBean {
            private String gid;
            private String gname;
            private String gdescription;
            private String gdprice;
            private String gimg;

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getGname() {
                return gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getGdescription() {
                return gdescription;
            }

            public void setGdescription(String gdescription) {
                this.gdescription = gdescription;
            }

            public String getGdprice() {
                return gdprice;
            }

            public void setGdprice(String gdprice) {
                this.gdprice = gdprice;
            }

            public String getGimg() {
                return gimg;
            }

            public void setGimg(String gimg) {
                this.gimg = gimg;
            }
        }

        public static class VoucherBean {
            private String vu_id;
            private String vu_name;
            private String vu_price;
            private String vu_stime;
            private String vu_etime;
            private String vu_description;

            public String getVu_id() {
                return vu_id;
            }

            public void setVu_id(String vu_id) {
                this.vu_id = vu_id;
            }

            public String getVu_name() {
                return vu_name;
            }

            public void setVu_name(String vu_name) {
                this.vu_name = vu_name;
            }

            public String getVu_price() {
                return vu_price;
            }

            public void setVu_price(String vu_price) {
                this.vu_price = vu_price;
            }

            public String getVu_stime() {
                return vu_stime;
            }

            public void setVu_stime(String vu_stime) {
                this.vu_stime = vu_stime;
            }

            public String getVu_etime() {
                return vu_etime;
            }

            public void setVu_etime(String vu_etime) {
                this.vu_etime = vu_etime;
            }

            public String getVu_description() {
                return vu_description;
            }

            public void setVu_description(String vu_description) {
                this.vu_description = vu_description;
            }
        }

}
