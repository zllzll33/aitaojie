package com.android.zlibrary.base;

import java.io.File;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import com.android.zlibrary.R;
import com.android.zlibrary.util.FileUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoaderManager
{
	static Bitmap iamge_bitmap=null;
	public static void initImageLoader()
	{
		if (!ImageLoader.getInstance().isInited())
		{
			ImageLoaderConfiguration config = getConfigurationDefault();
			ImageLoader.getInstance().init(config);
		}
	}

	private static ImageLoaderConfiguration getConfigurationDefault()
	{
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ZLibrary.getInstance().getApplication()).memoryCacheSize(2 * 1024 * 1024)
				.denyCacheImageMultipleSizesInMemory().defaultDisplayImageOptions(getOptionsDefault()).build();
		return config;
	}

	private static DisplayImageOptions getOptionsDefault()
	{
		DisplayImageOptions options = getBuilderDefault().build();
		return options;
	}

	private static DisplayImageOptions.Builder getBuilderDefault()
	{
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder().showImageForEmptyUri(R.mipmap.nopic)
				.showImageOnFail(R.mipmap.nopic).resetViewBeforeLoading(true).cacheOnDisk(true).cacheInMemory(false)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true);
		return builder;
	}

	public static DisplayImageOptions getOptionsNoCache()
	{
		DisplayImageOptions options = getBuilderDefault().cacheOnDisk(false).cacheInMemory(false).build();
		return options;
	}

	public static DisplayImageOptions getOptionsNopicSmall()
	{
		DisplayImageOptions options = getBuilderDefault().showImageForEmptyUri(R.mipmap.nopic_expression)
				.showImageOnFail(R.mipmap.nopic_expression).build();
		return options;
	}

	public static DisplayImageOptions getOptionsNoCacheNoResetViewBeforeLoading()
	{
		DisplayImageOptions options = getBuilderDefault().cacheOnDisk(false).cacheInMemory(false).resetViewBeforeLoading(false).build();
		return options;
	}

	public static DisplayImageOptions getOptionsNoResetViewBeforeLoading()
	{
		DisplayImageOptions options = getBuilderDefault().resetViewBeforeLoading(false).build();
		return options;
	}

	public static boolean isCacheExistOnDisk(String url)
	{
		if (!TextUtils.isEmpty(url))
		{
			File file = ImageLoader.getInstance().getDiskCache().get(url);
			if (file != null && file.exists()) // 缓存存在
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isCacheExistInMemory(String url)
	{
		if (!TextUtils.isEmpty(url))
		{
			Bitmap bmp = ImageLoader.getInstance().getMemoryCache().get(url);
			if (bmp != null) // 缓存存在
			{
				return true;
			}
		}
		return false;
	}
  public static void ClearImageCache()
  {
	  ImageLoader.getInstance().clearDiskCache();
  }
	public static String getCacheSize()
	{
		File cacheDir = ImageLoader.getInstance().getDiskCache().getDirectory();
		if (cacheDir != null)
		{
			long cacheSize = FileUtil.getFileSize(cacheDir);
			return	FileUtil.formatFileSize(cacheSize);

		}
		else
			return "";

	}
	public static long getImageCacheSize()
	{
		long cacheSize=0;
		File cacheDir = ImageLoader.getInstance().getDiskCache().getDirectory();
		if (cacheDir != null)
		{
			cacheSize = FileUtil.getFileSize(cacheDir);


		}
		return cacheSize;
	}
	public static Bitmap loadImageBitmap(String url) {

		try {
			ImageLoader.getInstance().loadImage(url,
					new ImageLoadingListener() {
						public void onLoadingStarted(String s, View view) {
						}

						@Override
						public void onLoadingFailed(String s, View view,
													FailReason failReason) {
						}

						public void onLoadingComplete(String s, View view,
													  Bitmap bitmap) {
							iamge_bitmap=bitmap;

						}

						public void onLoadingCancelled(String s, View view) {

						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iamge_bitmap;
	}
}
