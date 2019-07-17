package com.drc.tools.Broadcast;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.drc.tools.Common.DrcAlarm;

import static android.content.Context.ALARM_SERVICE;

public class DrcBootCompleteReceiver extends BroadcastReceiver {
    private final static String TAG = "DrcBootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: ok");
        AlarmManager manager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        DrcAlarm.stopRepeat(manager, context);
    }
}
