package com.android.aitaojie.model;

import java.util.List;

/**
 * Created by win7 on 2016/8/11.
 */
public class MessageModel {
        private String logo;
        private String title;
        private String type;
        private String info;
        private String set_time;
        private String status;
      private String mnickname;

    public String getMnickname() {
        return mnickname;
    }

    public void setMnickname(String mnickname) {
        this.mnickname = mnickname;
    }

    public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getSet_time() {
            return set_time;
        }

        public void setSet_time(String set_time) {
            this.set_time = set_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

}
