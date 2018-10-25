package com.drc.tools.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.drc.tools.Service.LongRunningService;

public class BootCompleteReceiver extends BroadcastReceiver {
    private final static String TAG = "BootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: ok");
        LongRunningService.Drcstart(context);
    }
}
