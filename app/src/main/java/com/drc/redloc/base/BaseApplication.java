package com.drc.redloc.base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
