package com.android.zlibrary.util;

	import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

	import com.android.zlibrary.base.ZLibrary;


/**
 * preference工具类，默认都写入string
 * @author Administrator
 *
 */
public class PreferenceUtil {
    public static final String TAG = "PreferenceUtil";
    public static synchronized String getString(String key,String Default) {
        try {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(ZLibrary.getInstance().getApplication());
            return preference.getString(key, Default);
        } catch (Exception e) {
           Log.i(TAG, e.toString());
            return Default;
        }
    }

    public static synchronized void putString(String key, String value) {
        try {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(ZLibrary.getInstance().getApplication());
            preference.edit().putString(key, value).commit();
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    public static synchronized void removePreference( String key) {
        try {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(ZLibrary.getInstance().getApplication());
            preference.edit().remove(key).commit();
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }
    public static synchronized boolean getBoolean( String key, boolean defaut) {
        try {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(ZLibrary.getInstance().getApplication());
            return preference.getBoolean(key, defaut);
        } catch (Exception e) {
           Log.i(TAG, e.toString());
            return defaut;
        }
    }

    public static synchronized void putBoolean( String key, boolean  value) {
        try {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(ZLibrary.getInstance().getApplication());
            preference.edit().putBoolean(key, value).commit();
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }

    public static synchronized void putInt(String key, int value)
    {
        try {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(ZLibrary.getInstance().getApplication());
            preference.edit().putInt(key, value).commit();
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
    }
    public static synchronized int getInt( String key, int defaut) {
        try {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(ZLibrary.getInstance().getApplication());
            return preference.getInt(key, defaut);
        } catch (Exception e) {
            Log.i(TAG, e.toString());
            return defaut;
        }
    }
}


