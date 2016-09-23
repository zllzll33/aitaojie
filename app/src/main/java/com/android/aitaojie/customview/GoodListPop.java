package com.android.aitaojie.customview;

import android.content.Context;

import com.android.aitaojie.R;
import com.android.zlibrary.customView.ZBasePopupWindow;


/**
 * Created by win7 on 2016/7/20.
 */
public class GoodListPop extends ZBasePopupWindow {
    public GoodListPop(Context context) {
        super(context);
        setOutSideDismiss();
        setBGColor(0xffffffff);
    }
    @Override
    protected void init() {
        Mode = Match_parent;
        super.init();
    }

    @Override
    protected int layoutId() {
        return R.layout.pop_good_list;
    }
}
