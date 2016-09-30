package com.android.zlibrary.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by win7 on 2016/5/13.
 */
public class JsonDaoHelper extends SQLiteOpenHelper {
    private static final String TAG = "JsonDaoHelper";
    public JsonDaoHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                         int version) {
        super(context, name, factory, version);
    }
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table jsonTable(json_object varchar(100),json_content Text)";
//输出创建数据库的日志信息
//execSQL函数用于执行SQL语句
        db.execSQL(sql);
    }
    //当更新数据库的时候执行该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//输出更新数据库的日志信息
    }
}
