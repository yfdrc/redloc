package com.drc.tools.Base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        //Log.d(TAG, "onCreate: ok");
    }

    public static Context getContext() {
        //Log.d(TAG, "getContext: ok");
        return context;
    }
}
