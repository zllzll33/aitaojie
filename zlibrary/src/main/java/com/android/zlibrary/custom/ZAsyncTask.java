package com.android.zlibrary.custom;

import android.os.AsyncTask;

/**
 * Created by win7 on 2016/6/15.
 */
public class ZAsyncTask extends AsyncTask<Integer, Integer, String> {

    @Override
    protected String doInBackground(Integer... params) {
        //费时操作
//        publishProgress(i);
//        return i + params[0].intValue() + "";
        return null;
    }
    @Override
    protected void onPostExecute(String result) {
//完成
    }
    @Override
    protected void onPreExecute() {
  //
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
//        int vlaue = values[0];
//        progressBar.setProgress(vlaue);
    }
    @Override
    protected void onCancelled() {

    }
public void CancleTask()
{
    cancel(true);
}
}
