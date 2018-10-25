package com.drc.tools.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.IBinder;
import android.os.SystemClock;

import com.drc.tools.Common.DrcLocation;

public class LongRunningService extends Service {
    private final static String TAG = "LongRunningService";

    private final static int MIN10 = 1000 * 60 * 10;
    private final static int MIN60 = 1000 * 60 * 60;

    private static int sj = 0;
    private static DrcLocation drcLocation = null;

    public static void Drcstart(Context context) {
        Intent intent = new Intent(context, LongRunningService.class);
        context.startService(intent);
    }

    public static void Drcstop(Context context) {
        Intent intent = new Intent(context, LongRunningService.class);
        context.stopService(intent);
    }

    @Override
    public void onCreate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                drcLocation = new DrcLocation();
                drcLocation.start(getBaseContext());
                drcLocation.requestLocation();
            }
        }).start();

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour < 7) {
            sj = MIN60;
        } else {
            sj = MIN10;
        }
        long triggertime = SystemClock.elapsedRealtime() + sj;

        Intent intenttem = new Intent(this, LongRunningService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intenttem, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggertime, pendingIntent);

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
