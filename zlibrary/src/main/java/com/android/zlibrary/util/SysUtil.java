package com.android.zlibrary.util;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.android.zlibrary.base.ZActivityManager;
import com.android.zlibrary.base.ZLibrary;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by win7 on 2016/5/20.
 */
public class SysUtil {
    public static int mNotificationId=5555;
    public long getSysTime()
    {
        long systemTime=System.currentTimeMillis();
        return systemTime;
    }
    public static  int[] getSysTimeArray()
    {
        int[] timeByte=new int[6];
        Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow(); // 取得系统时间。
        timeByte[0]=t.year;
        timeByte[1]=t.month;
        timeByte[2]=t.monthDay;
        timeByte[3]=t.hour; // 0-23
        timeByte[4]=t.minute;
        timeByte[5]=t.second;
        return timeByte;
    }
    public  static String  getSysTimeFormatSecond()
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
    public static String getSysTimeFormatDay()
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
    public  static String  getSysTimeFormatHour()
    {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
    public static int[] getSreeenParam()
    {
        int[] Param=new int[2];
        WindowManager wm = (WindowManager) ZLibrary.getInstance().getApplication()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Param[0]=width;
        Param[1]=height;
        return  Param;
    }
    public static float getScreenDensity()
    {
        float  scale= ZLibrary.getInstance().getApplication().getResources().getDisplayMetrics().density;
        return scale;
    }
    public static void sendNotification(int appNameRes,int logoRes,int small_logo,String title ,String content, Class<?> goClass,boolean isCover)
    {
        Context Rcontext=ZLibrary.getInstance().getApplication();
        NotificationManager mNotificationManager = (NotificationManager) Rcontext.getSystemService(Rcontext.NOTIFICATION_SERVICE);
        Notification mNotification = new Notification();
        Bitmap icon = BitmapFactory.decodeResource(Rcontext.getResources(), logoRes);
        NotificationCompat.Builder m_builder = new NotificationCompat.Builder(Rcontext);
        m_builder.setAutoCancel(true);
        m_builder.setLargeIcon(icon);
//		  m_builder.setContentInfo("?");
        m_builder.setSmallIcon(small_logo);
        m_builder.setWhen(System.currentTimeMillis());
        m_builder.setContentTitle(title);
        m_builder.setContentText(content);
        m_builder.setTicker(title);
        m_builder.setDefaults(Notification.DEFAULT_SOUND);
        if(!isCover)
        mNotificationId++;
        Intent completingIntent = new Intent(Rcontext,goClass);
        PendingIntent mPendingIntent = PendingIntent.getActivity(Rcontext,appNameRes, completingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotification.contentIntent = mPendingIntent;
        m_builder.setContentIntent(mPendingIntent);
        mNotificationManager.notify(mNotificationId, m_builder.build());
    }
    public static String[] getPackageInfo()
    {
        String[] packageInfo=new String[3];
        try {
            String pkName = ZLibrary.getInstance().getApplication().getPackageName();
            String versionName =  ZLibrary.getInstance().getApplication().getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode =  ZLibrary.getInstance().getApplication().getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            packageInfo[0]=pkName;
            packageInfo[1]=versionName;
            packageInfo[2]=String.valueOf(versionCode);
        } catch (Exception e) {
        }
        return packageInfo;
    }
    public static boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ZLibrary.getInstance().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
    public static boolean isNetworkAvailable() {
       /* <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
        <uses-permission android:name="android.permission.INTERNET"/>*/
                ConnectivityManager connectivity = (ConnectivityManager) ZLibrary.getInstance().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isAppBackground() {
        ActivityManager activityManager = (ActivityManager)  ZLibrary.getInstance().getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(ZLibrary.getInstance().getApplication().getPackageName()))
            {
                if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                {
//                        Log.e("后台", appProcess.processName);
                        return true;
                }else{
//                        Log.e("前台", appProcess.processName);
                        return  false;
                }
            }
        }
     return false;
    }
    public static long getBitmapsize(Bitmap bitmap){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();

    }
    public static boolean isInstallAPK(String packageName)
    {
        return new File("/data/data/" + packageName).exists();
    }
    public static void clearWebCache()
    {
        String cache_path="/data/data/"+getPackageInfo()[0]+"/app_webview";
        File web_cache=new File(cache_path);
//        Log.e("cache_path",cache_path);
        if(web_cache.exists())
         deleteDirectory(cache_path);
    }
    public static long getWebCacheSize()
    {
        long size=0;
        String cache_path="/data/data/"+getPackageInfo()[0]+"/app_webview";
        File web_cache=new File(cache_path);
        size=getFolderSize(web_cache);
//        Log.e("web_cache_size",String.valueOf(web_cache));
        return size;
    }
    public static long getFolderSize(java.io.File file){

        long size = 0;
        try {
            if(!file.exists()) {
             return 0;
            }
            if(file.isDirectory()) {
                java.io.File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);

                    } else {
                        size = size + fileList[i].length();

                    }
                }
            }
            else
                size=file.length();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return size;
    }
    public static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前空目录
        return dirFile.delete();
    }
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }
    public static void hideKeyBoard(View view)
    {
        InputMethodManager imm = (InputMethodManager)ZLibrary.getInstance().getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),0); //强制隐藏键盘
    }
    public static  String getLocalIpAddress()
    {
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()&&inetAddress.getAddress().length==4)
                    {
//                        Log.e("ip",inetAddress.getHostAddress().toString());
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException ex)
        {

        }
        return "192.169.0.33";
    }
}
