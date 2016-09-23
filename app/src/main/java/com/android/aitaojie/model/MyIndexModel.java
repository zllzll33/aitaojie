package com.android.aitaojie.model;

import java.util.List;

/**
 * Created by win7 on 2016/8/4.
 */
public class MyIndexModel {

    /**
     * user : {"m_uid":"801","m_nickname":"西风烈","m_avatar":"http://q.qlogo.cn/qqapp/1105486565/A016E7CC631DBD0B2546DE9993EEF984/100","m_phone":"18357140881","autograph":""}
     * goods : [{"gid":"10481","gname":"测试商品","gdescription":"测试商品描述","jid":"1","cid":"1","sid":"0","goprice":"100.00","gdprice":"50.00","gstock":"99","gimg":"http://img1.91tutetao.com/2016_07_e7d3e5dc-8392-4fe1-819b-0f39802e357b.jpg","gorder":"999","gtype":"0","gstatus":"1","gvrebate":"1","printid":"0","detail":"","sale_num":"0","color":"","color_name":"","size":"","size_name":"","set_time":"1469672880","detail_url":"http://mobile.ali521.com/goods/view.html?id=10481"},{"gid":"10474","gname":"100M 宽带 两年","gdescription":"","jid":"135","cid":"1172","sid":"336","goprice":"1608.00","gdprice":"1208.00","gstock":"99","gimg":"http://www.luofangyun.com/Public/Upload/goods/2016-07-18/578c72774290b.jpg","gorder":"999","gtype":"0","gstatus":"1","gvrebate":"0","printid":"0","detail":"","sale_num":"0","color":"100M","color_name":"","size":"2年","size_name":"","set_time":null,"detail_url":"http://mobile.ali521.com/goods/view.html?id=10474"},{"gid":"10473","gname":"50M 宽带 两年","gdescription":"","jid":"135","cid":"1172","sid":"336","goprice":"1208.00","gdprice":"968.00","gstock":"99","gimg":"http://www.luofangyun.com/Public/Upload/goods/2016-07-18/578c71efcf12f.jpg","gorder":"999","gtype":"0","gstatus":"1","gvrebate":"0","printid":"0","detail":"","sale_num":"0","color":"","color_name":"50M","size":"","size_name":"2年","set_time":null,"detail_url":"http://mobile.ali521.com/goods/view.html?id=10473"},{"gid":"10472","gname":"20M 宽带 两年","gdescription":"","jid":"135","cid":"1172","sid":"336","goprice":"968.00","gdprice":"728.00","gstock":"99","gimg":"http://www.luofangyun.com/Public/Upload/goods/2016-07-18/578c70cb68d41.jpg","gorder":"999","gtype":"0","gstatus":"1","gvrebate":"0","printid":"0","detail":"","sale_num":"0","color":"","color_name":"20M","size":"","size_name":"2年","set_time":null,"detail_url":"http://mobile.ali521.com/goods/view.html?id=10472"},{"gid":"10471","gname":"酱香名酒","gdescription":"","jid":"67","cid":"836","sid":"349","goprice":"1080.00","gdprice":"720.00","gstock":"99","gimg":"http://www.luofangyun.com/Public/Upload/goods/2016-07-16/578a45df0e026.jpg","gorder":"999","gtype":"0","gstatus":"1","gvrebate":"2","printid":"0","detail":"<p>茅台镇独家秘制洞藏老坛酒简介：酱香型白酒53度，口感相对较柔和，酱味浓，不刺激喉咙，不上头全国独家经营，独一无二 <\/p><p>本酒经过9次蒸煮，8次发酵，7次取酒，7伦次。并经过三年以上窖藏才出厂，装坛，放于洞中窖藏。经过一些年头再取出来。酒坛身上有历史年代沉淀的毛毛，外形复古独特。经营范围：品牌酒，贴牌酒，散酒，基酒等只要你需要，我们就供应。<\/p><p><img src=\"/Public/Upload/ueditor/image/20160716/1468679839837476.jpg\" webTitle=\"1468679839837476.jpg\" _src=\"/Public/Upload/ueditor/image/20160716/1468679839837476.jpg\" alt=\"TB2ogyVpXXXXXahXXXXXXXXXXXX_!!2470846407.jpg\"><img src=\"/Public/Upload/ueditor/image/20160716/1468679855360647.jpg\" webTitle=\"1468679855360647.jpg\" _src=\"/Public/Upload/ueditor/image/20160716/1468679855360647.jpg\" alt=\"TB2txykpXXXXXbOXpXXXXXXXXXX_!!2470846407.jpg\"><img src=\"/Public/Upload/ueditor/image/20160716/1468679865872857.jpg\" webTitle=\"1468679865872857.jpg\" _src=\"/Public/Upload/ueditor/image/20160716/1468679865872857.jpg\" alt=\"TB2Vw5ApXXXXXXbXpXXXXXXXXXX_!!2470846407.jpg\"><img src=\"/Public/Upload/ueditor/image/20160716/1468679871379657.jpg\" webTitle=\"1468679871379657.jpg\" _src=\"/Public/Upload/ueditor/image/20160716/1468679871379657.jpg\" alt=\"TB2zfq0pXXXXXXAXXXXXXXXXXXX_!!2470846407.jpg\"><img src=\"/Public/Upload/ueditor/image/20160716/1468679868714933.jpg\" webTitle=\"1468679868714933.jpg\" _src=\"/Public/Upload/ueditor/image/20160716/1468679868714933.jpg\" alt=\"TB2WweSpXXXXXawXXXXXXXXXXXX_!!2470846407.jpg\"><img src=\"/Public/Upload/ueditor/image/20160716/1468679859737348.jpg\" webTitle=\"1468679859737348.jpg\" _src=\"/Public/Upload/ueditor/image/20160716/1468679859737348.jpg\" alt=\"TB2VdyxpXXXXXcOXXXXXXXXXXXX_!!2470846407.jpg\"><img src=\"/Public/Upload/ueditor/image/20160716/1468679848682470.jpg\" webTitle=\"1468679848682470.jpg\" _src=\"/Public/Upload/ueditor/image/20160716/1468679848682470.jpg\" alt=\"TB2SHX3pXXXXXaNXFXXXXXXXXXX_!!2470846407.jpg\"><img src=\"/Public/Upload/ueditor/image/20160716/1468679834776704.jpg\" webTitle=\"1468679834776704.jpg\" _src=\"/Public/Upload/ueditor/image/20160716/1468679834776704.jpg\" alt=\"TB2hi5fpXXXXXc9XpXXXXXXXXXX_!!2470846407.jpg\"><\/p>","sale_num":"100","color":"53","color_name":"酱香型","size":"12瓶一箱","size_name":"一斤装","set_time":null,"detail_url":"http://mobile.ali521.com/goods/view.html?id=10471"},{"gid":"10470","gname":"","gdescription":"","jid":"0","cid":"0","sid":"0","goprice":"0.00","gdprice":"0.00","gstock":"99","gimg":"http://www.luofangyun.com","gorder":"999","gtype":"0","gstatus":"1","gvrebate":"0","printid":"0","detail":"","sale_num":"0","color":"","color_name":"","size":"","size_name":"","set_time":null,"detail_url":"http://mobile.ali521.com/goods/view.html?id=10470"},{"gid":"10469","gname":"意式咖啡机","gdescription":"意式经典咖啡机","jid":"142","cid":"1221","sid":"348","goprice":"5900.00","gdprice":"5500.00","gstock":"99","gimg":"http://www.luofangyun.com/Public/Upload/goods/2016-07-14/5787603a22bdb.jpg","gorder":"999","gtype":"0","gstatus":"1","gvrebate":"0","printid":"0","detail":"","sale_num":"0","color":"","color_name":"","size":"","size_name":"","set_time":null,"detail_url":"http://mobile.ali521.com/goods/view.html?id=10469"},{"gid":"10468","gname":"滴漏式咖啡机","gdescription":"复古滴漏式咖啡机","jid":"142","cid":"1221","sid":"348","goprice":"2600.00","gdprice":"2000.00","gstock":"99","gimg":"http://www.luofangyun.com/Public/Upload/goods/2016-07-14/578753e624041.jpg","gorder":"999","gtype":"0","gstatus":"1","gvrebate":"0","printid":"0","detail":"","sale_num":"0","color":"透明","color_name":"颜色","size":"58公分","size_name":"尺寸","set_time":null,"detail_url":"http://mobile.ali521.com/goods/view.html?id=10468"},{"gid":"10467","gname":"摩卡","gdescription":"畅销摩卡咖啡","jid":"142","cid":"1222","sid":"348","goprice":"38.00","gdprice":"29.00","gstock":"99","gimg":"http://www.luofangyun.com/Public/Upload/2016-07-14/57874a044d64e.jpg","gorder":"999","gtype":"1","gstatus":"1","gvrebate":"0","printid":"0","detail":"","sale_num":"0","color":"","color_name":"","size":"","size_name":"","set_time":null,"detail_url":"http://mobile.ali521.com/goods/view.html?id=10467"},{"gid":"10466","gname":"拿铁","gdescription":"现场调制奶咖","jid":"142","cid":"1222","sid":"348","goprice":"45.00","gdprice":"30.00","gstock":"99","gimg":"http://www.luofangyun.com/Public/Upload/2016-07-14/578749b9a7b27.png","gorder":"999","gtype":"1","gstatus":"1","gvrebate":"0","printid":"0","detail":"","sale_num":"0","color":"","color_name":"","size":"","size_name":"","set_time":null,"detail_url":"http://mobile.ali521.com/goods/view.html?id=10466"}]
     */


        /**
         * m_uid : 801
         * m_nickname : 西风烈
         * m_avatar : http://q.qlogo.cn/qqapp/1105486565/A016E7CC631DBD0B2546DE9993EEF984/100
         * m_phone : 18357140881
         * autograph :
         */

        private UserBean user;
        /**
         * gid : 10481
         * gname : 测试商品
         * gdescription : 测试商品描述
         * jid : 1
         * cid : 1
         * sid : 0
         * goprice : 100.00
         * gdprice : 50.00
         * gstock : 99
         * gimg : http://img1.91tutetao.com/2016_07_e7d3e5dc-8392-4fe1-819b-0f39802e357b.jpg
         * gorder : 999
         * gtype : 0
         * gstatus : 1
         * gvrebate : 1
         * printid : 0
         * detail :
         * sale_num : 0
         * color :
         * color_name :
         * size :
         * size_name :
         * set_time : 1469672880
         * detail_url : http://mobile.ali521.com/goods/view.html?id=10481
         */

        private List<GoodsBean> goods;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class UserBean {
            private String m_uid;
            private String m_nickname;
            private String m_avatar;
            private String m_phone;
            private String autograph;
            public String getM_uid() {
                return m_uid;
            }

            public void setM_uid(String m_uid) {
                this.m_uid = m_uid;
            }

            public String getM_nickname() {
                return m_nickname;
            }

            public void setM_nickname(String m_nickname) {
                this.m_nickname = m_nickname;
            }

            public String getM_avatar() {
                return m_avatar;
            }

            public void setM_avatar(String m_avatar) {
                this.m_avatar = m_avatar;
            }

            public String getM_phone() {
                return m_phone;
            }

            public void setM_phone(String m_phone) {
                this.m_phone = m_phone;
            }

            public String getAutograph() {
                return autograph;
            }

            public void setAutograph(String autograph) {
                this.autograph = autograph;
            }
        }

        public static class GoodsBean {
            public String getMerchant_logo() {
                return merchant_logo;
            }
            public void setMerchant_logo(String merchant_logo) {
                this.merchant_logo = merchant_logo;
            }

            private String merchant_logo;
            private String gid;
            private String gname;
            private String gdescription;
            private String jid;
            private String cid;
            private String sid;
            private String goprice;
            private String gdprice;
            private String gstock;
            private String gimg;
            private String gorder;
            private String gtype;
            private String gstatus;
            private String gvrebate;
            private String printid;
            private String detail;
            private String sale_num;
            private String color;
            private String color_name;
            private String size;
            private String size_name;
            private String set_time;
            private String detail_url;

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

            public String getJid() {
                return jid;
            }

            public void setJid(String jid) {
                this.jid = jid;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getSid() {
                return sid;
            }

            public void setSid(String sid) {
                this.sid = sid;
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

            public String getGstock() {
                return gstock;
            }

            public void setGstock(String gstock) {
                this.gstock = gstock;
            }

            public String getGimg() {
                return gimg;
            }

            public void setGimg(String gimg) {
                this.gimg = gimg;
            }

            public String getGorder() {
                return gorder;
            }

            public void setGorder(String gorder) {
                this.gorder = gorder;
            }

            public String getGtype() {
                return gtype;
            }

            public void setGtype(String gtype) {
                this.gtype = gtype;
            }

            public String getGstatus() {
                return gstatus;
            }

            public void setGstatus(String gstatus) {
                this.gstatus = gstatus;
            }

            public String getGvrebate() {
                return gvrebate;
            }

            public void setGvrebate(String gvrebate) {
                this.gvrebate = gvrebate;
            }

            public String getPrintid() {
                return printid;
            }

            public void setPrintid(String printid) {
                this.printid = printid;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getSale_num() {
                return sale_num;
            }

            public void setSale_num(String sale_num) {
                this.sale_num = sale_num;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getColor_name() {
                return color_name;
            }

            public void setColor_name(String color_name) {
                this.color_name = color_name;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getSize_name() {
                return size_name;
            }

            public void setSize_name(String size_name) {
                this.size_name = size_name;
            }

            public String getSet_time() {
                return set_time;
            }

            public void setSet_time(String set_time) {
                this.set_time = set_time;
            }

            public String getDetail_url() {
                return detail_url;
            }

            public void setDetail_url(String detail_url) {
                this.detail_url = detail_url;
            }
        }

}
