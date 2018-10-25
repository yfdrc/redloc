package com.drc.tools.Base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDatabase extends SQLiteOpenHelper {
    private static final String CREATEBOOK = "create table Book("
            + "id integer primary key autoincrement,"
            + "auth text,"
            + "price real,"
            + "pages integer,"
            + "name text)";
    private static final String CREATECATEGORY = "create table Category("
            + "id integer primary key autoincrement,"
            + "CategoryName text,"
            + "CategoryCode integer)";
    private static final String CREATETEST = "create table Test("
            + "id integer primary key autoincrement,"
            + "CategoryName text,"
            + "CategoryCode integer)";

    private static final String TAG = "BaseDatabase";

    public BaseDatabase(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate: ok");
        db.execSQL(CREATEBOOK);
        db.execSQL(CREATECATEGORY);
        db.execSQL(CREATETEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade: ok");
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        db.execSQL("drop table if exists Test");
        onCreate(db);
    }
}
