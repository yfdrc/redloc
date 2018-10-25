package com.drc.tools.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.drc.tools.Base.BaseDatabase;

public class DrcDB {
    private static final String TAG = "DrcDB";
    private BaseDatabase dbhelper = null;
    private SQLiteDatabase db = null;
    private String tablename = null;

    public DrcDB(String DBname, String Tablename, Context context, int ver) {
        if(dbhelper==null || db==null) {
            Log.i(TAG, "DrcDB: init");
            dbhelper = new BaseDatabase(context, DBname, ver);
            db = dbhelper.getWritableDatabase();
            tablename = Tablename;
        }else{
            Log.i(TAG, "DrcDB: ok");
        }
    }

    public void AddTable() {
        ContentValues values = new ContentValues();
        values.put("name", "yfqx");
        values.put("auth", "drc");
        values.put("pages", "323");
        values.put("price", "2.2");
        db.insert(tablename,null, values);
        values.clear();
    }

    public void UpdateTable() {
        ContentValues values = new ContentValues();
        values.put("price", "3.3");
        db.update(tablename, values,"name=?", new String[]{"yfqx"});
    }

    public void DeleteTable() {
        ContentValues values = new ContentValues();
        db.delete(tablename,"price>=?", new String[]{"3"});
    }

    public void ReadTable() {
        Cursor cursor = db.query(tablename,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String name =cursor.getString(cursor.getColumnIndex("name"));
                Float price =cursor.getFloat(cursor.getColumnIndex("price"));
                Log.i(TAG, "ReadTable: name=" + name + " price=" + price.toString());
            }while (cursor.moveToNext());
        }
        cursor.close();
    }
}