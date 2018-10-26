package com.drc.tools.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.drc.tools.Common.DrcLocation;

public class DrcLocateService extends Service {
    private final static String TAG = "DrcLocateService";

    private DrcLocation drcLocation = new DrcLocation();

    public static void Drcstart(Context context) {
        //Log.i(TAG, "startService: ok");
        Intent serStart = new Intent(context, DrcLocateService.class);
        context.startService(serStart);
    }

    public static void Drcstop(Context context) {
        //Log.i(TAG, "stopService: ok");
        Intent serStop = new Intent(context, DrcLocateService.class);
        context.stopService(serStop);
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ok");
        super.onCreate();
        drcLocation.start(getBaseContext());
        drcLocation.requestLocation();
        //acquireWakeLock(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Log.i(TAG, "onStartCommand: ok");
//        Notification notification = new NotificationCompat.Builder(this, "default")
//                .setPriority(Notification.PRIORITY_MIN).build();
//        startForeground(1, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ok");
        super.onDestroy();
        //releaseWakeLock();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
