package com.drc.tools.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.drc.tools.Service.DrcLocateService;
import com.drc.tools.WakeLock.DrcWakeLock;

public class DrcAlarmReceiver extends BroadcastReceiver {
    private static String TAG = "DrcAlarmReceiver";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i(TAG, "onReceive: ok");
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "onReceive: run");
                Log.i(TAG, "    onReceive: run ** start DrcLocateService");
                DrcWakeLock.acquireWakeLock(context,10*1000);
                Intent serStart = new Intent(context, DrcLocateService.class);
                context.startForegroundService(serStart);
                DrcWakeLock.releaseWakeLock();
            }
        }).start();
    }
}
