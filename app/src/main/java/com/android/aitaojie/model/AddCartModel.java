package com.android.aitaojie.model;

import java.util.List;

/**
 * Created by win7 on 2016/8/19.
 */
public class AddCartModel {


        /**
         * id : 35
         * level : 1
         * type : group
         * jid : 1
         * goods_id : 10214
         * name : 100g
         * pid : 33
         * pid2 : 0
         * title :
         * price : 0.00
         * stock :
         * sales : 0
         * status : 1
         * img :
         * idx : 0
         */

        private DefaultSpceBean default_spce;
        /**
         * id : 33
         * level : 1
         * type : group
         * jid : 1
         * goods_id : 10214
         * name : 重量
         * pid : 0
         * pid2 : 0
         * title :
         * price : 0.00
         * stock : 0
         * sales : 0
         * status : 1
         * img :
         * idx : 0
         * items : [{"id":"35","level":"1","type":"group","jid":"1","goods_id":"10214","name":"100g","pid":"33","pid2":"0","title":"","price":"0.00","stock":"","sales":"0","status":"1","img":"","idx":"0"},{"id":"38","level":"1","type":"group","jid":"1","goods_id":"10214","name":"200g","pid":"33","pid2":"0","title":"","price":"0.00","stock":"","sales":"0","status":"1","img":"","idx":"0"}]
         */

        private List<SpceBean> spce;

        public DefaultSpceBean getDefault_spce() {
            return default_spce;
        }

        public void setDefault_spce(DefaultSpceBean default_spce) {
            this.default_spce = default_spce;
        }

        public List<SpceBean> getSpce() {
            return spce;
        }

        public void setSpce(List<SpceBean> spce) {
            this.spce = spce;
        }

        public static class DefaultSpceBean {
            private String id;
            private String level;
            private String type;
            private String jid;
            private String goods_id;
            private String name;
            private String pid;
            private String pid2;
            private String title;
            private String price;
            private String stock;
            private String sales;
            private String status;
            private String img;
            private String idx;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getJid() {
                return jid;
            }

            public void setJid(String jid) {
                this.jid = jid;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getPid2() {
                return pid2;
            }

            public void setPid2(String pid2) {
                this.pid2 = pid2;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getIdx() {
                return idx;
            }

            public void setIdx(String idx) {
                this.idx = idx;
            }
        }

        public static class SpceBean {
            private String id;
            private String level;
            private String type;
            private String jid;
            private String goods_id;
            private String name;
            private String pid;
            private String pid2;
            private String title;
            private String price;
            private String stock;
            private String sales;
            private String status;
            private String img;
            private String idx;
            /**
             * id : 35
             * level : 1
             * type : group
             * jid : 1
             * goods_id : 10214
             * name : 100g
             * pid : 33
             * pid2 : 0
             * title :
             * price : 0.00
             * stock :
             * sales : 0
             * status : 1
             * img :
             * idx : 0
             */

            private List<ItemsBean> items;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getJid() {
                return jid;
            }

            public void setJid(String jid) {
                this.jid = jid;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getPid2() {
                return pid2;
            }

            public void setPid2(String pid2) {
                this.pid2 = pid2;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStock() {
                return stock;
            }

            public void setStock(String stock) {
                this.stock = stock;
            }

            public String getSales() {
                return sales;
            }

            public void setSales(String sales) {
                this.sales = sales;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getIdx() {
                return idx;
            }

            public void setIdx(String idx) {
                this.idx = idx;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public static class ItemsBean {
                private String id;
                private String level;
                private String type;
                private String jid;
                private String goods_id;
                private String name;
                private String pid;
                private String pid2;
                private String title;
                private String price;
                private String stock;
                private String sales;
                private String status;
                private String img;
                private String idx;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getLevel() {
                    return level;
                }

                public void setLevel(String level) {
                    this.level = level;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getJid() {
                    return jid;
                }

                public void setJid(String jid) {
                    this.jid = jid;
                }

                public String getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(String goods_id) {
                    this.goods_id = goods_id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }

                public String getPid2() {
                    return pid2;
                }

                public void setPid2(String pid2) {
                    this.pid2 = pid2;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getStock() {
                    return stock;
                }

                public void setStock(String stock) {
                    this.stock = stock;
                }

                public String getSales() {
                    return sales;
                }

                public void setSales(String sales) {
                    this.sales = sales;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getIdx() {
                    return idx;
                }

                public void setIdx(String idx) {
                    this.idx = idx;
                }
            }
        }

}
