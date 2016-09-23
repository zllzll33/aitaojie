package com.android.aitaojie.model;

import java.util.List;

/**
 * Created by win7 on 2016/8/5.
 */
public class LiveModel {

        /**
         * value : 330102
         * label : 上城区
         * street : [{"value":"330102001","label":"清波街道"},{"value":"330102003","label":"湖滨街道"},{"value":"330102004","label":"小营街道"},{"value":"330102008","label":"南星街道"},{"value":"330102009","label":"紫阳街道"},{"value":"330102010","label":"望江街道"}]
         */

        private  List<AreaBean> area;
        /**
         * label : 500米
         * value : 500
         */

        private  List<DistanceBean> distance;
        /**
         * value : 1
         * label : 美食
         */

        private List<TradeBean> trade;
        /**
         * label : 餐厅服务
         * name : service
         * item : [{"label":"不限","value":"no"},{"label":"单人餐","value":"single"}]
         */

        private List<FilterBean> filter;
        /**
         * name : 余杭店
         * address : 金家渡南路4号
         * views : 222
         * likes : 555
         * lat :
         * lng :
         * jid : 1
         * img : http://www.luofangyun.com/Public/Upload/2015-12-29/568288f875edc.jpg
         * logo : http://www.luofangyun.com/Public/Upload/2015-12-29/568288f875edc.jpg
         * distance : 800米
         * trade : 美食
         */

        private List<MerchantBean> merchant;

        public List<AreaBean> getArea() {
            return area;
        }

        public void setArea(List<AreaBean> area) {
            this.area = area;
        }

        public List<DistanceBean> getDistance() {
            return distance;
        }

        public void setDistance(List<DistanceBean> distance) {
            this.distance = distance;
        }

        public List<TradeBean> getTrade() {
            return trade;
        }

        public void setTrade(List<TradeBean> trade) {
            this.trade = trade;
        }

        public List<FilterBean> getFilter() {
            return filter;
        }

        public void setFilter(List<FilterBean> filter) {
            this.filter = filter;
        }

        public List<MerchantBean> getMerchant() {
            return merchant;
        }

        public void setMerchant(List<MerchantBean> merchant) {
            this.merchant = merchant;
        }

        public static class AreaBean {
            private String value;
            private String label;
            /**
             * value : 330102001
             * label : 清波街道
             */

            private List<StreetBean> street;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public List<StreetBean> getStreet() {
                return street;
            }

            public void setStreet(List<StreetBean> street) {
                this.street = street;
            }

            public static class StreetBean {
                private String value;
                private String label;

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }
            }
        }

        public static class DistanceBean {
            private String label;
            private String value;

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class TradeBean {
            private String value;
            private String label;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }

        public static class FilterBean {
            private String label;
            private String name;
            /**
             * label : 不限
             * value : no
             */
            private List<ItemBean> item;

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ItemBean> getItem() {
                return item;
            }

            public void setItem(List<ItemBean> item) {
                this.item = item;
            }

            public static class ItemBean {
                private String label;
                private String value;

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
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
