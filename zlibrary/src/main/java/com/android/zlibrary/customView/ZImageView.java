package com.android.zlibrary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by win7 on 2016/7/22.
 */
public class ZImageView extends ImageView {
    public ZImageView(Context context) {
        super(context);
        init();
    }

    public ZImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init()
    {
            setScaleType(ScaleType.FIT_CENTER);
            setAdjustViewBounds(true);
        // 设置src  background 无法适配
    }
}
