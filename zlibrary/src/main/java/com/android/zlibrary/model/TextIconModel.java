package com.android.zlibrary.model;

/**
 * Created by win7 on 2016/6/6.
 */
public class TextIconModel {
    private int iconRes;
    private String text;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TextIconModel()
    {}
    public TextIconModel(int iconRes,String text)
    {
        this.iconRes=iconRes;
        this.text=text;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }


}
