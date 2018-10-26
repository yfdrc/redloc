package com.drc.tools.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.drc.tools.Service.DrcLongRunningService;

public class DrcBootCompleteReceiver extends BroadcastReceiver {
    private final static String TAG = "DrcBootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.i(TAG, "onReceive: ok");
        DrcLongRunningService.Drcstart(context);
    }
}
