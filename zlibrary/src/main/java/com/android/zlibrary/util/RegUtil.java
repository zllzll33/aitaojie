package com.android.zlibrary.util;

/**
 * Created by win7 on 2016/7/27.
 */
public class RegUtil {
    public static final String reg_mobile = "[1][358]\\d{9}";
    public static final String reg_user = "^\\w{5,}";
    public static final String reg_email="^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
    public static final String reg_zipcode = "^[1-9]\\d{5}$";
    public static final String reg_cz = "^[\\u4e00-\\u9fa5]{0,}$";
    public static final String reg_url = "^[http://|www]([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";
    public  static boolean isMobile(String str)
    {
      return   str.matches(reg_mobile);
    }
    public static boolean isEmail(String str)
    {
        return   str.matches(reg_email);
    }
    public static boolean isZipcode(String str)
    {
        return   str.matches(reg_zipcode);
    }
    public static boolean isLetterUser(String str)
    {
        return   str.matches(reg_user);
    }
    public static boolean isCZ(String str)
    {
        return   str.matches(reg_cz);
    }
    public static boolean isURL(String str)
    {
        return   str.matches(reg_url);
    }
}
