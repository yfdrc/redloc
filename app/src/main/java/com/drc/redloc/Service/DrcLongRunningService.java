package com.drc.redloc.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.drc.tools.Location.DrcLocation;

public class DrcLongRunningService extends Service {
    private final static String TAG = "DrcLongRunningService";

    private final static int MIN10 = 1000 * 60 * 10;
    private final static int MIN60 = 1000 * 60 * 60;

    private static int sj = 0;
    private static DrcLocation drcLocation = null;
    private static Context context = null;

    public static void Drcstart(Context mContext) {
        context = mContext;
        Intent intent = new Intent(mContext, DrcLongRunningService.class);
        mContext.startService(intent);
    }

    public static void Drcstop(Context mContext) {
        Intent intent = new Intent(mContext, DrcLongRunningService.class);
        mContext.stopService(intent);
    }

    @Override
    public void onCreate() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                drcLocation = new DrcLocation();
//                drcLocation.start(getBaseContext());
//                drcLocation.requestLocation();
//            }
//        }).start();
//
//        Intent intenttem = new Intent(context, DrcLongRunningService.class);
//        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intenttem, 0);
//
//        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//        if (hour < 7) {  sj = MIN60; } else { sj = MIN10; }
//        long triggertime = SystemClock.elapsedRealtime() + sj;
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggertime, pendingIntent);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
