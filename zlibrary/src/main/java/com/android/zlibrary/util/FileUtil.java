package com.android.zlibrary.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.android.zlibrary.base.ZLibrary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by win7 on 2016/5/12.
 */
public class FileUtil {

    /*<!-- SDCard中创建与删除文件权限 -->
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
    <!-- 向SDCard写入数据权限 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>*/
    private static LoadPrgrssListener loadPrgrssListener=null;
    public static String sdpath = "";
    public static String innerStoragepath="";
    public static URL url;
    public static Context context;
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    /**
     * 获取SD卡根目录路径
     *
     * @return
     */
    public static String getSdCardPath() {
        boolean exist = isSdCardExist();

        if (exist) {
            sdpath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            sdpath = getInnerStoragepath();
        }
        return sdpath;

    }
    public static String getInnerStoragepath()
    {
        innerStoragepath = ZLibrary.getInstance().getApplication().getFilesDir().getPath();
        return innerStoragepath;
    }
    public static String  getFilepath(String path,String filename )
    {
        if(path.isEmpty())
            return getSdCardPath()+"/"+filename;
        else
            return getSdCardPath()+"/"+path+"/"+filename;
    }
    public static long getFileSize(File dirFile)
    {
        long size = 0;
        File flist[] = dirFile.listFiles();
        for (int i = 0; i < flist.length; i++)
        {
            if (flist[i].isDirectory())
            {
                size = size + getFileSize(flist[i]);
            } else
            {
                size = size + flist[i].length();
            }
        }
        return size;
    }
    public static String formatFileSize(long fileLength)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileLength <= 0)
        {
            fileSizeString = "0.00B";
        } else if (fileLength < 1024)
        {
            fileSizeString = df.format((double) fileLength) + "B";
        } else if (fileLength < 1048576)
        {
            fileSizeString = df.format((double) fileLength / 1024) + "KB";
        } else if (fileLength < 1073741824)
        {
            fileSizeString = df.format((double) fileLength / 1048576) + "MB";
        } else
        {
            fileSizeString = df.format((double) fileLength / 1073741824) + "GB";
        }
        return fileSizeString;
    }
    public  static boolean creatMkdirs(String Mkdirsname)
    {
        File file =new File(getSdCardPath()+"/"+Mkdirsname);
        if (!file.exists()) {
            file.mkdirs();
            return  true;
        }
        else
            return false;
    }
    public  static boolean creatMkdirs(String path,String Mkdirsname)
    {
        File file;
        if(path.isEmpty())
            file =new File(getSdCardPath()+"/"+Mkdirsname);
        else
           file =new File(getSdCardPath()+"/"+path+"/"+Mkdirsname);
        if (!file.exists()) {
            file.mkdirs();
            return  true;
        }
        else
            return false;
    }
    public static boolean creatFile(String path,String filename,String filecontent)
    {
        File file;
        try {
            if(path.isEmpty())
                file =new File(getSdCardPath()+"/"+filename);
                else
                   file =new File(getSdCardPath()+"/"+path+"/"+filename);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(filecontent.getBytes());
            fos.close();
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

 public static void saveHttpBitmap(String path,String filename,String fileUrl)
 {
    new BitmapHttpThread(path,filename,fileUrl).start();

 }
public static void loadHttpFile(String path,String filename,String fileUrl)
{
    new fileHttpLoad(path,filename,fileUrl).start();
}
    public static void updateAPK(Context actcontext,String path,String filename,String fileUrl)
    {
        context=actcontext;
        new apkLoadInstall(context,path,filename,fileUrl).start();
    }
    public static String getFilePath(String filename) {
        String filepath = "";
        File file = new File(Environment.getExternalStorageDirectory(),
                filename);
        if (file.exists()) {
            filepath = file.getAbsolutePath();
        } else {
            filepath = "不适用";
        }
        return filepath;
    }
    public static class BitmapHttpThread extends Thread{
        String path,filename,fileUrl;
        File file;
        public BitmapHttpThread(String path,String filename,String fileUrl)
        {
           this.path= path;
            this.filename=filename;
            this.fileUrl=fileUrl;
        }
        public void run()
        {
            try {
                url = new URL(fileUrl);
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
               Bitmap bitmap = BitmapFactory.decodeStream(is);
                if(path.isEmpty())
                    file =new File(getSdCardPath()+"/"+filename);
                else
                    file =new File(getSdCardPath()+"/"+path+"/"+filename);
                if(file.exists())
                    file.delete();
                FileOutputStream fos = new FileOutputStream(file);
                if(filename.contains("jpg"))
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                else if(filename.contains("png"))
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();

            }
            catch (IOException e)
            {
                e.printStackTrace();

            }
        }
    }

    public static class fileHttpLoad extends Thread{
        String path,filename,fileUrl;
        File file;
        public fileHttpLoad(String path,String filename,String fileUrl)
        {
            this.path= path;
            this.filename=filename;
            this.fileUrl=fileUrl;
        }
        public void run()
        {
            try {
                url = new URL(fileUrl);
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                if(path.isEmpty())
                    file =new File(getSdCardPath()+"/"+filename);
                else
                    file =new File(getSdCardPath()+"/"+path+"/"+filename);
                if(file.exists())
                    file.delete();
                FileOutputStream fos = new FileOutputStream(file);
                long fileLeght=conn.getContentLength();
                byte[] buf = new byte[1024];
                int ch = -1;
                int count = 0;
                while ((ch = is.read(buf)) != -1) {
                    fos.write(buf, 0, ch);
                    count += ch;
                    if(loadPrgrssListener!=null)
                        loadPrgrssListener.loadprogress(fileLeght,count);
                }
                fos.flush();
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }
    public static class apkLoadInstall extends Thread{
        String path,filename,fileUrl;
        File file;
        public apkLoadInstall(Context context,String path,String filename,String fileUrl)
        {
            this.path= path;
            this.filename=filename;
            this.fileUrl=fileUrl;
        }
        public void run()
        {
            try {
                url = new URL(fileUrl);
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                long fileLeght=conn.getContentLength();
                if(path.isEmpty())
                    file =new File(getSdCardPath()+"/"+filename);
                else
                    file =new File(getSdCardPath()+"/"+path+"/"+filename);
                if(file.exists())
                    file.delete();
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int ch = -1;
                long count = 0;
                while ((ch = is.read(buf)) != -1) {
                    fos.write(buf, 0, ch);
                    count += ch;
                    if(loadPrgrssListener!=null)
                        loadPrgrssListener.loadprogress(fileLeght,count);
                }
                fos.flush();
                if (fos != null) {
                    fos.close();
                }
                if (is != null) {
                    is.close();
                }
                installAPK(context,getFilepath(path,filename));
            } catch (MalformedURLException e) {
                e.printStackTrace();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }
    public static void installAPK(Context context,String apkpath)
    {
        Uri uri = Uri.parse(apkpath);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setClassName("com.android.packageinstaller",
                "com.android.packageinstaller.PackageInstallerActivity");

        context.startActivity(intent);
    }
    public static void  setLoadPrgrssListener(LoadPrgrssListener loadPrgrssListenerr)
    {
        loadPrgrssListener=loadPrgrssListenerr;
    }
    public static interface  LoadPrgrssListener{
        public void loadprogress(long fileLeght, long count);
    }
    public static ArrayList<FileInfo> queryAllImage()
    {
        ArrayList<FileInfo> images = new ArrayList<FileInfo>();
        ContentResolver resolver = ZLibrary.getInstance().getApplication().getContentResolver();
        Cursor cursor = null;
        try {
            //查询数据库，参数分别为（路径，要查询的列名，条件语句，条件参数，排序）
            cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null ,null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    FileInfo image = new FileInfo();
                    image.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID))); //获取唯一id
                    image.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))); //文件路径
                    image.setFileName(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME))); //文件名
                    //...   还有很多属性可以设置
                    //可以通过下一行查看属性名，然后在Images.Media.里寻找对应常量名
//                    Log.i(TAG, "queryAllImage --- all column name --- " + cursor.getColumnName(cursor.getPosition()));

                    //获取缩略图（如果数据量大的话，会很耗时——需要考虑如何开辟子线程加载）
                /*
                 * 可以访问android.provider.MediaStore.Images.Thumbnails查询图片缩略图
                 * Thumbnails下的getThumbnail方法可以获得图片缩略图，其中第三个参数类型还可以选择MINI_KIND
                 */
                    Bitmap thumbnail = MediaStore.Images.Thumbnails.getThumbnail(resolver, image.getId(), MediaStore.Images.Thumbnails.MICRO_KIND, null);
                    image.setThumbnail(thumbnail);

                    images.add(image);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return images;
    }
    public static ArrayList<FileInfo> querryAllVideo()
    {
        ArrayList<FileInfo> videos = new ArrayList<FileInfo>();
        ContentResolver resolver = ZLibrary.getInstance().getApplication().getContentResolver();
        Cursor cursor = null;
        try {
            //查询数据库，参数分别为（路径，要查询的列名，条件语句，条件参数，排序）
            cursor = resolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null ,null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    FileInfo video = new FileInfo();
                    video.setId(cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID))); //获取唯一id
                    video.setFilePath(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))); //文件路径
                    video.setFileName(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME))); //文件名
                    //...   还有很多属性可以设置
                    //可以通过下一行查看属性名，然后在Video.Media.里寻找对应常量名
//                    Log.i(TAG, "queryAllImage --- all column name --- " + cursor.getColumnName(cursor.getPosition()));

                    //获取缩略图（如果数据量大的话，会很耗时——需要考虑如何开辟子线程加载）
                /*
                 * 可以访问android.provider.MediaStore.Video.Thumbnails查询图片缩略图
                 * Thumbnails下的getThumbnail方法可以获得图片缩略图，其中第三个参数类型还可以选择MINI_KIND
                 */
                    Bitmap thumbnail = MediaStore.Video.Thumbnails.getThumbnail(resolver, video.getId(), MediaStore.Video.Thumbnails.MICRO_KIND, null);
                    video.setThumbnail(thumbnail);

                    videos.add(video);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return videos;
    }
    public static class FileInfo{
        private int id;
        private String filePath;
        private String fileName;
        private Bitmap thumbnail;
        public Bitmap getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(Bitmap thumbnail) {
            this.thumbnail = thumbnail;
        }


        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }


    }

}
