package com.android.zlibrary.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.Base64;


import com.android.zlibrary.base.ZLibrary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by win7 on 2016/5/18.
 */
public class TypeUtil {
    final static int BUFFER_SIZE = 4096;
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    //可逆加密
    public   static  String KL(String inStr) {
        // String s = new String(inStr);
        char [] a = inStr.toCharArray();
        for  ( int  i =  0 ; i < a.length; i++) {
            a[i] = (char ) (a[i] ^  't' );
        }
        String s = new  String(a);
        return  s;
    }

    public static String Base64(String str)
    {
        String strBase64 = new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
        return strBase64;
    }
   public static String JBase64(String base64)
   {
       String str=new String(Base64.decode(base64.getBytes(), Base64.DEFAULT));
       return str;
   }

    public static byte[] InputStream2Byte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte databtyes[] = bytestream.toByteArray();
        bytestream.close();
        return databtyes;
    }
  public String Bytes2String(byte[] bytes)
  {
      return new String(bytes);
  }
    public String InputStreamToStr(InputStream is)throws IOException
    {
      byte[] bytes= InputStream2Byte(is);
        return Bytes2String(bytes);
    }
    public static InputStream String2InputStream(String in) throws Exception{

        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("utf-8"));
        return is;
    }

    public static byte[] String2Byte(String str)
    {
        return str.getBytes();
    }
    public static  InputStream ByteToInputStream(byte[] bytes)
    {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        return is;
    }
    public static OutputStream StrToOutputStream(String str)throws IOException
    {
        OutputStream os = System.out;
        os.write(str.getBytes());
        return  os;
    }
    public  OutputStream Byte2OutputStream(byte[] bytes)throws IOException
    {
        return StrToOutputStream(new String(bytes));
    }

 public String OutputStream2Str(OutputStream os)
 {
     String str = os.toString();
     return  str;
 }
    public byte[] OutputStreamToByte(OutputStream os)
    {
        return OutputStream2Str(os).getBytes();
    }

    public  static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }
    public static Bitmap Drawable2Bitmap( Resources res,int id)
    {
        Bitmap bmp = BitmapFactory.decodeResource(res, id);
        return bmp;
    }

     public static   Drawable bitmap2Drawable(Bitmap bitmap) {
         return new BitmapDrawable(ZLibrary.getInstance().getApplication().getResources(),bitmap);
    }
   public static   byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
  public  static   Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
    public static int  dp2px(int dp)
    {
        float  scale= ZLibrary.getInstance().getApplication().getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
    public static int px2dp(int px)
    {
        float  scale= ZLibrary.getInstance().getApplication().getResources().getDisplayMetrics().density;
        return (int)(px / scale + 0.5f);
    }
    public static String Map2JsonStr(Map map){
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (Map.Entry)iterator.next();
            sb.append("\"").append(entry.getKey().toString()).append("\"").append( ":" ).append("\"").append(null==entry.getValue()?"":
                    entry.getValue().toString()).append("\"").append (iterator.hasNext() ? "," : "");
        }
        sb.append("}");
        return sb.toString();
    }
    public static String Map2String(Map map){
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();

        for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
        {
            entry = (Map.Entry)iterator.next();
            sb.append(entry.getKey().toString()).append( "=" ).append("\"").append(null==entry.getValue()?"":
                    entry.getValue().toString()).append("\"").append (iterator.hasNext() ? "&" : "");
        }

        return sb.toString();
    }
    public static Map String2Map(String mapString){
        Map map = new HashMap();
        StringTokenizer items;
        for(StringTokenizer entrys = new StringTokenizer(mapString, ":"); entrys.hasMoreTokens();
            map.put(items.nextToken(), items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
            items = new StringTokenizer(entrys.nextToken(), ",");
        return map;
    }
  public static  String Map2Xml(Map map)
  {
      Map.Entry entry;
      StringBuffer sb = new StringBuffer();
      sb.append("<xml>");
      for(Iterator iterator = map.entrySet().iterator(); iterator.hasNext();)
      {
          entry = (Map.Entry)iterator.next();
          sb.append("<").append(entry.getKey().toString()).append(">").append(null==entry.getValue()?"":
                  entry.getValue().toString()).append("</").append(entry.getKey().toString()).append(">");
      }
      sb.append("</xml>");
      return sb.toString();
  }
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }
}


