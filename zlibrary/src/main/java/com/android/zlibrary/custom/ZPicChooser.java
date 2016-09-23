package com.android.zlibrary.custom;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.android.zlibrary.util.TypeUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by win7 on 2016/7/26.
 */
public class ZPicChooser  {
    private  int CROP = 200;
    private final static String FILE_SAVEPATH = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + "/pic/Portrait/";
    private Uri origUri;
    private Uri cropUri;
    private File protraitFile;
    private String protraitPath;
    public final static String SDCARD = "/sdcard";
    public final static String SDCARD_MNT = "/mnt/sdcard";
    public static final int PicGallery=111;
    public static final int PicCamera=112;
    public static final int PicCrop=113;
    Activity activity;
    private  Bitmap corpBitmap;
    public ZPicChooser(Activity activity)
    {
        this.activity=activity;
    }
    public void setCrop(int crop)
    {
        this.CROP=crop;
    }
    public void startImagePick() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        activity.startActivityForResult(Intent.createChooser(intent, "选择图片"), PicGallery);
    }
    public  void startActionCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getCameraTempFile());
        activity.startActivityForResult(intent, PicCamera);
    }
    private Uri getCameraTempFile() {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        // 照片命名
        String cropFileName = "osc_camera_" + timeStamp + ".jpg";
        // 裁剪头像的绝对路径
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);

        cropUri = Uri.fromFile(protraitFile);
        this.origUri = this.cropUri;
        return this.cropUri;
    }

    public String getCorpPicPath()
    {
        return  "file://" + protraitPath;
    }
    public Bitmap getCorpBitmap()
    {
        corpBitmap= BitmapFactory.decodeFile(protraitPath);
        return corpBitmap;
    }
    public byte[] getCorpBytes()
    {
                   getCorpBitmap();
                    ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
                    if (getCorpPicPath().contains(".png"))
                        corpBitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
                    else if (getCorpPicPath().contains(".jpg"))
                        corpBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
                    byte[]    result = output.toByteArray();
                    corpBitmap.recycle();
                    try {
                        output.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


        return result;
    }
    public void startActionCrop()
    {
        startActionCrop(origUri);
    }
    public void startActionCrop(Uri data) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(data, "image/*");
        intent.putExtra("output", getUploadTempFile(data));
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", CROP);// 输出图片大小
        intent.putExtra("outputY", CROP);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        activity.startActivityForResult(intent, PicCrop);
    }
    private Uri getUploadTempFile(Uri uri) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(FILE_SAVEPATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String thePath = getAbsolutePathFromNoStandardUri(uri);

        // 如果是标准Uri
        if (TypeUtil.isEmpty(thePath)) {
            thePath = getAbsoluteImagePath(activity, uri);
        }
        String ext = getFileFormat(thePath);
        ext = TypeUtil.isEmpty(ext) ? "jpg" : ext;
        // 照片命名
        String cropFileName = "quanmin_crop_" + timeStamp + "." + ext;
        // 裁剪头像的绝对路径
        protraitPath = FILE_SAVEPATH + cropFileName;
        protraitFile = new File(protraitPath);
        cropUri = Uri.fromFile(protraitFile);
        return cropUri;
    }
    public static String getAbsoluteImagePath(Activity context, Uri uri) {
        String imagePath = "";
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, proj, // Which columns to
                // return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)

        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
            }
        }

        return imagePath;
    }
    private   String getAbsolutePathFromNoStandardUri(Uri mUri) {
        String filePath = null;

        String mUriString = mUri.toString();
        mUriString = Uri.decode(mUriString);

        String pre0 = "file://" + SDCARD + File.separator;
        String pre1 = "file://" + SDCARD + "1" + File.separator;
        String pre2 = "file://" + SDCARD_MNT + File.separator;

        if (mUriString.startsWith(pre1)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre1.length());
        } else if (mUriString.startsWith(pre2)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre2.length());
        } else if (mUriString.startsWith(pre0)) {
            filePath = Environment.getExternalStorageDirectory().getPath()
                    + File.separator + mUriString.substring(pre0.length());
        }
        return filePath;
    }
    private    String getFileFormat(String fileName) {
        if (TypeUtil.isEmpty(fileName))
            return "";
        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }
}
