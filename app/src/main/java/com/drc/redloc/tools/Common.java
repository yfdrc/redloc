package com.drc.redloc.tools;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Common {
    private final static String TAG = "DrcCommon";

    private static PowerManager.WakeLock mWakeLock;

    public static Properties getFSConfigProperties() {
        Properties props = new Properties();
        InputStream in = Common.class.getResourceAsStream("/assets/App.properties");
        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public static String[] getPermissions(Context context) {
        String[] perms;
        List<String> listsrc = new ArrayList<>();
        String[] listdec = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        for (String list : listdec) {
            if (ContextCompat.checkSelfPermission(context, list) != PackageManager.PERMISSION_GRANTED) {
                listsrc.add(list);
            }
        }
        if (listsrc.isEmpty()) {
            perms = null;
        } else {
            perms = listsrc.toArray(new String[listsrc.size()]);
        }
        return perms;
    }

    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
            //Log.i(TAG, "isServiceRunning: " + serviceInfo.service.getClassName());
            if (serviceInfo.service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    public static void acquireWakeLock(Context context) {
        if (null == mWakeLock) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "WakeLock");
            if (null != mWakeLock) {
                mWakeLock.acquire();
            }
        }
    }

    public static void releaseWakeLock() {
        if (null != mWakeLock) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

}