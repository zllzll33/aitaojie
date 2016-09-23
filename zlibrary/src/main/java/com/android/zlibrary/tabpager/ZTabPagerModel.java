package com.android.zlibrary.tabpager;

import android.support.v4.app.Fragment;

/**
 * Created by win7 on 2016/5/25.
 */
public class ZTabPagerModel {
 private int titleIcon;
    private String titleText;
    private Fragment pagerFragment;

    public int getTitleIcon() {
        return titleIcon;
    }

    public void setTitleIcon(int titleIcon) {
        this.titleIcon = titleIcon;
    }

    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }

    public Fragment getPagerFragment() {
        return pagerFragment;
    }

    public void setPagerFragment(Fragment pagerFragment) {
        this.pagerFragment = pagerFragment;
    }
}
