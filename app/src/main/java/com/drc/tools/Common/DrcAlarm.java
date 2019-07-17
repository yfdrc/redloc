package com.drc.tools.Common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.drc.tools.Broadcast.DrcAlarmReceiver;

public class DrcAlarm {
    private final static String TAG = "DrcAlarm";

    public static void setRepeat(AlarmManager manager, long minutes, Context context) {
        try {
            long times = 60*1000 * minutes;
            long triggerAtTime = SystemClock.elapsedRealtime()+times;
            Intent intent = new Intent(context, DrcAlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, times, pi);
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

    public static void stopRepeat(AlarmManager manager, Context context) {
        try {
            Intent intent = new Intent(context, DrcAlarmReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
            manager.cancel(pi);
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
    }

}
