package com.android.zlibrary.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.zlibrary.base.ZLibrary;
import com.google.gson.Gson;


/**
 * Created by win7 on 2016/5/13.
 */
public class JsonDaoManager {
    private static JsonDaoManager Instance=null;
    SQLiteDatabase db=null;
    String qjson;
    public JsonDaoManager()
    {
        JsonDaoHelper jsonDaoHelper = new JsonDaoHelper(ZLibrary.getInstance().getApplication(),"JsonDao_db",null,1);
//得到一个可写的数据库
       db = jsonDaoHelper.getWritableDatabase();
    }
   public static JsonDaoManager getInstance()
   {
       if(Instance==null)
           Instance=new JsonDaoManager();
       return  Instance;
   }
    public <T> void insertOrUpdateJsonDbModel(T model)
    {
      String modelName=  model.getClass().getName();
        Gson gson = new Gson();
        String json=gson.toJson(model);
//        Log.e("modelName",modelName);
        Cursor cursor = db.query("jsonTable", new String[]{"json_object","json_content"}, "json_object=?", new String[]{modelName}, null, null, null);
        if(cursor.getColumnCount()>=1)
        {
            String  deletemodel="delete from jsonTable where json_object ="+"\""+modelName+"\"";
//            Log.e("deletesql",deletemodel);
            exeSql(deletemodel);
        }
        cursor.close();
       /* String insertmodel="insert into jsonTable(json_object,json_content) values(modelName,json)";
        exeSql(insertmodel);*/
        ContentValues cv = new ContentValues();
        cv.put("json_object",modelName);
        cv.put("json_content", json);
        db.insert("jsonTable", null, cv);

    }
    public <T> T queryJsonDbModel(Class<T> clazz)
    {
        String clazzname=clazz.getName();
//      Log.e("queryname",clazzname);
        Cursor cursor = db.query("jsonTable", new String[]{"json_object","json_content"}, "json_object=?", new String[]{clazzname}, null, null, null);
        if(cursor.moveToFirst())
        {
            qjson=cursor.getString(1);
//            Log.e("json content",qjson);
        }
        Gson gson=new Gson();
        T object=gson.fromJson(qjson,clazz);
        cursor.close();
       return object;
    }
    public void creatTable()
    {
        String creattable="create table if not exists jsonTable(json_object varchar(50),json_content TEXT)";
        db.execSQL(creattable);
    }
    public void exeSql(String sql)
    {
        db.execSQL(sql);
    }
}
