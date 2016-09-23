package com.android.aitaojie.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by win7 on 2016/8/11.
 */
public class ClassifyGoodListModel {

        /**
         * cid : 1246
         * cname : 进口食品
         */

        @SerializedName("class")
        private List<ClassBean> classX;
        private List<GoodsBean> Goods;

        public List<ClassBean> getClassX() {
            return classX;
        }

        public void setClassX(List<ClassBean> classX) {
            this.classX = classX;
        }

        public List<GoodsBean> getGoods() {
            return Goods;
        }

        public void setGoods(List<GoodsBean> Goods) {
            this.Goods = Goods;
        }

        public static class ClassBean {
            private String cid;
            private String cname;

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getCname() {
                return cname;
            }

            public void setCname(String cname) {
                this.cname = cname;
            }
        }

        public static class GoodsBean {
            private String gid;
            private String gdescription;
            private String goprice;
            private String gdprice;
            private String gimg;

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getGdescription() {
                return gdescription;
            }

            public void setGdescription(String gdescription) {
                this.gdescription = gdescription;
            }

            public String getGoprice() {
                return goprice;
            }

            public void setGoprice(String goprice) {
                this.goprice = goprice;
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

}
