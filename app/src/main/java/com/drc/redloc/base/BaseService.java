package com.drc.redloc.base;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BaseService extends Service {
    private static final String TAG = "BaseService";
    private BaseBinder basebinder = new BaseBinder();

    public BaseService() {
    }

    protected class BaseBinder extends Binder {
        public void startDownload() {
            Log.i(TAG, "startDownload: ok");
        }

        public int getProgress() {
            Log.i(TAG, "getProgress: ok");
            return 0;
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ok");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ok");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ok");
        super.onCreate();
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind: ok");
        super.onRebind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind: ok");
        return basebinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind: ok");
        return super.onUnbind(intent);
    }
}
