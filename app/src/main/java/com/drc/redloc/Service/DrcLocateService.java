
package com.drc.redloc.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.drc.tools.Common.DrcThread;
import com.drc.tools.Location.DrcLocation;

public class DrcLocateService extends Service {
    private final static String TAG = "DrcLocateService";
    private static Context context = null;
    private static DrcLocation drcLocation = null;
    private static DrcThread drcThread = null;

    public static void Drcstart(Context mContext) {
        Log.i(TAG, "startService: ok");
        context = mContext;
        drcThread = new DrcThread();
        Intent serStart = new Intent(context, DrcLocateService.class);
        context.startService(serStart);
    }

    public static void Drcstop() {
        //Log.i(TAG, "stopService: ok");
        Intent serStop = new Intent(context, DrcLocateService.class);
        context.stopService(serStop);
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ok");
        if(drcLocation==null) drcLocation = new DrcLocation(context);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ok");
        drcThread.start(drcLocation);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ok");
        drcLocation.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
