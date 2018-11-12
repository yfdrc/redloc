package com.drc.tools.Common;

import android.util.Log;

import com.drc.tools.Location.DrcLocation;

public class DrcThread extends Thread implements Runnable {
   private final static String TAG = "DrcThread";
   private DrcLocation location = null;

    @Override
    public void run() {
        Log.d(TAG, "run: ok");
        super.run();
        this.location.start();
    }

    public void start(DrcLocation drcLocation) {
        Log.d(TAG, "start: ok");
        this.location = drcLocation;
        super.start();
    }
}
