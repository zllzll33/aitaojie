package com.android.zlibrary.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zlibrary.base.ZLibrary;
import com.android.zlibrary.dialog.ZDialogProgres;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.io.File;

/**
 * Created by win7 on 2016/5/19.
 */
public class ViewUtil {
    private static ZDialogProgres zDialogProgres;
    public void traversalView(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup) {
                traversalView((ViewGroup) view);
            } else {
                doView(view);
            }
        }
    }
    private void doView(View view) {

    }
    public static void showToast(String str)
    {
        Toast.makeText(ZLibrary.getInstance().getApplication(),str,Toast.LENGTH_SHORT).show();
    }
    public static void showHtttpProgress()
    {
         zDialogProgres=new ZDialogProgres();
        zDialogProgres.showHttpProgress();
    }
    public static void showHtttpProgress(String str)
    {
         zDialogProgres=new ZDialogProgres();
        zDialogProgres.showHttpProgress(str);
    }
    public static void hideHtttpProgress()
    {
        zDialogProgres.dismiss();
    }
    public static void setImageView(ImageView imageView, String url)
    {
        setImageView(url, imageView, null, null, null);
    }
    private static void setImageView(String uri, ImageView imageView, DisplayImageOptions options, ImageLoadingListener listener,
                                    ImageLoadingProgressListener progressListener)
    {
        if (!canLoadImageFromUrl(uri))
        {
            return;
        }
        try
        {
            ImageLoader.getInstance().displayImage(uri.trim(), imageView, options, listener, progressListener);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private static boolean canLoadImageFromUrl(String url)
    {
       boolean mCanLoadImageFromUrl = true;
        if (mCanLoadImageFromUrl)// 可以从url加载图片
        {
            return true;
        } else
        {
            File cache = ImageLoader.getInstance().getDiskCache().get(url);
            if (cache != null && cache.exists() && cache.length() > 0)
            {
                return true;
            } else
            {
                return false;
            }
        }
    }
}
