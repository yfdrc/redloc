package com.drc.redloc.BAK;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.drc.redloc.base.BaseApplication;

import java.util.List;

public class DrcCommon extends BaseApplication {
    private final static String TAG = "DrcCommon";

    public static boolean isAppRunning(Context context, String packageName) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        if (list.size() <= 0) {
            //Log.i(TAG, "isAppRunning: no");
            isAppRunning = false;
        }
        for (ActivityManager.RunningAppProcessInfo info : list) {
            //Log.i(TAG, "isAppRunning: " + info.processName);
            if (info.processName.equals(packageName)) {
                //Log.i(TAG, "isAppRunning: yes");
                isAppRunning = true;
            }
        }
        return isAppRunning;
    }

    public static BroadcastReceiver DrcBroadCastReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                DrcLocateService.Drcstart(context);
                //Log.i(TAG, "Screen went OFF");
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                DrcLocateService.Drcstop(context);
                //Log.i(TAG, "Screen went ON");
            }
        }
    };

    public static void startBroadCast() {
        //Log.i(TAG, "startBroadCast: ok");
        IntentFilter screenStateFilter = new IntentFilter();
        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
        getContext().registerReceiver(DrcBroadCastReciever, screenStateFilter);
    }
}
