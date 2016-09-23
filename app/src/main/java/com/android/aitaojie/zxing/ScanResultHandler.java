package com.android.aitaojie.zxing;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.zlibrary.util.ViewUtil;


public class ScanResultHandler extends OnActivityResultHandler
{
	private static final int REQUEST_CODE = 100;
	private static final int RESULT_CODE = MyCaptureActivity.RESULT_CODE_SCAN_SUCCESS;
	private static final String EXTRA_RESULT_SUCCESS_STRING = MyCaptureActivity.EXTRA_RESULT_SUCCESS_STRING;
	private int mRequestCode = REQUEST_CODE;
	private ScanResultDealerListener mListener;

	public void setmListener(ScanResultDealerListener mListener)
	{
		this.mListener = mListener;
	}

	public ScanResultHandler(Fragment mFragment)
	{
		super(mFragment);
	}

	public ScanResultHandler(FragmentActivity mActivity)
	{
		super(mActivity);
	}

	public void startScan(Intent intent)
	{
		startActivityForResult(intent, mRequestCode);
	}

	public void startScan(Intent intent, int requestCode)
	{
		this.mRequestCode = requestCode;
		startActivityForResult(intent, mRequestCode);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == mRequestCode)
		{
			if (resultCode == RESULT_CODE)
			{
				String scanResult = data.getStringExtra(EXTRA_RESULT_SUCCESS_STRING);
				if (mListener != null)
				{
					mListener.onResult(scanResult);
				}
				requestScanResult(scanResult);
			}
		}
	}
	 private void requestScanResult( String scanResult) {
       Log.e("ZxingCode",scanResult);
		ViewUtil.showToast(scanResult);
	}

	public interface ScanResultDealerListener
	{
		public void onResult(String result);
	}

}
