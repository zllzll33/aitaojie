package com.android.zlibrary.base;

import android.app.Application;

import com.android.zlibrary.R;

/**
 * Created by win7 on 2016/5/19.
 */
public class ZLibrary {
   static ZLibrary zlibrary;

   private Application application;
    public static ZLibrary getInstance()
    {
        if(zlibrary==null)
            zlibrary=new ZLibrary();
        return  zlibrary;
    }

    public void initZlibraryApplicatin(Application application)
    {
        this.application=application;
        ImageLoaderManager.initImageLoader();

    }
    public Application getApplication()
    {
        return application;
    }
}
