package com.android.zlibrary.model;

import java.util.List;

/**
 * Created by win7 on 2016/7/28.
 */
public class ThreePickerModel {
    public static  class FirstModel {
        private String name;
        private List<SecondModel> secondList;
        public List<SecondModel> getSecondList() {
            return secondList;
        }
        public void setSecondList(List<SecondModel> cityList) {
            this.secondList = cityList;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class SecondModel {
        private String name;
        private List<ThirdModel> thirdList;
        public List<ThirdModel> getThirdList() {
            return thirdList;
        }

        public void setThirdList(List<ThirdModel> thirdList) {
            this.thirdList = thirdList;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
    public static class ThirdModel {
        private String name;
        private String code;
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
