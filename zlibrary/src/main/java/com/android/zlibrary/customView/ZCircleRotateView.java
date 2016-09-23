package com.android.zlibrary.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.android.zlibrary.R;

/**
 * Created by win7 on 2016/5/26.
 */
public class ZCircleRotateView extends View {
    public int resId= R.mipmap.circle_progress;
    public ZCircleRotateView(Context context)
    {super(context);
        init();
    }
    public ZCircleRotateView(Context context, AttributeSet attributeSet)
    {super(context,attributeSet);
        init();
    }
    private void init()
    {
        setBackgroundResource(R.mipmap.circle_progress);
        RotateAnimation anim= new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(1000);
        startAnimation(anim);
    }
    public ZCircleRotateView setBackgroundRes(int resId)
    {
        this.resId=resId;
        return this;
    }
}
