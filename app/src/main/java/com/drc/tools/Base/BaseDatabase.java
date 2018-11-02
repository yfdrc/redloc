package com.drc.tools.Base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDatabase extends SQLiteOpenHelper {
    private static final String CREATEBOOK = "create table system("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "neirong text,"
            + "memo text,"
            + "ver real)";

    private static final String TAG = "BaseDatabase";

    public BaseDatabase(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.i(TAG, "onCreate: ok");
        db.execSQL(CREATEBOOK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Log.i(TAG, "onUpgrade: ok");
        db.execSQL("drop table if exists system");
        onCreate(db);
    }
}
