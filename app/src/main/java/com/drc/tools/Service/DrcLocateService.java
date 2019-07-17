
package com.drc.tools.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.drc.redloc.R;
import com.drc.tools.Location.DrcLocation;
import com.drc.tools.Notification.DrcNotification;

public class DrcLocateService extends Service {
    private final static String TAG = "DrcLocateService";
    private DrcLocation drcLocation = null;

//    private Context context = null;
//    public void Drcstart(Context context) {
//        Log.i(TAG, "startService: ok");
//        if (context != null) {
//            Intent serStart = new Intent(context, DrcLocateService.class);
//            context.startService(serStart);
//        }
//    }
//
//    public void Drcstop() {
//        Log.i(TAG, "stopService: ok");
//        if(context!=null){
//            Intent serStop = new Intent(context, DrcLocateService.class);
//            context.stopService(serStop);
//        }
//    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ok");
        Log.i(TAG, "    onCreate: ok ** start DrcLocation");
        try {
            if (drcLocation == null) drcLocation = new DrcLocation(getBaseContext());
            drcLocation.start();
            startForeground(101, new DrcNotification().createNotification(this.getBaseContext(), "drc", "1", "1"));
        }catch(Exception ex) {
            Log.e(TAG, "onCreate: error:",ex);
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ok");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ok");
        Log.i(TAG, "    onDestroy: ok ** drcLocation.stop");
        if(drcLocation==null) drcLocation = new DrcLocation(getBaseContext());
        drcLocation.stop();

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
