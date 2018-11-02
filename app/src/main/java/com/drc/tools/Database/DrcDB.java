package com.drc.tools.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.TimeUtils;

import com.drc.tools.Base.BaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class DrcDB {
    private static final String TAG = "DrcDB";
    private BaseDatabase dbhelper = null;
    private SQLiteDatabase db = null;
    private String tablename = null;

    public DrcDB(Context context, String dbName, String tableName, int version) {
        if(dbhelper==null || db==null) {
            Log.i(TAG, "DrcDB: init");
            dbhelper = new BaseDatabase(context, dbName, version);
            db = dbhelper.getWritableDatabase();
            tablename = tableName;
        }else{
            Log.i(TAG, "DrcDB: ok");
        }
    }

    public void AddTable() {
        ContentValues values = new ContentValues();
        values.put("name", "drc");
        values.put("neirong", "i am a man!");
        values.put("memo", "memo");
        values.put("ver", "1.0");
        db.insert(tablename,null, values);
        values.clear();
    }

    public void UpdateTable() {
        ContentValues values = new ContentValues();
        values.put("name", "red");
        values.put("neirong", "I'm a man!");
        db.update(tablename, values,"name=?", new String[]{"drc"});
    }

    public void DeleteTable() {
        ContentValues values = new ContentValues();
        db.delete(tablename,"ver>=?", new String[]{"1"});
    }

    public void ReadTable() {
        Cursor cursor = db.query(tablename,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String name =cursor.getString(cursor.getColumnIndex("name"));
                String neirong =cursor.getString(cursor.getColumnIndex("neirong"));
                Log.i(TAG, "ReadTable: name=" + name + " neirong=" + neirong.toString());
            }while (cursor.moveToNext());
        }
        cursor.close();
    }
}