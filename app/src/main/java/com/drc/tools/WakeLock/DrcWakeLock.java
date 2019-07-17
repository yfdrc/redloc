package com.drc.tools.WakeLock;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

public class DrcWakeLock {
    private final static String TAG = "DrcWakeLock";
    private static PowerManager.WakeLock wakeLock = null;

    //获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
    public static void acquireWakeLock(Context context, long timeout)
    {
        if (null == wakeLock)
        {
            PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK|PowerManager.ON_AFTER_RELEASE, "drcapp:mywakelocktag");
            if (null != wakeLock)
            {
                Log.i(TAG,"null != wakeLock");
                wakeLock.acquire(timeout);
            }else{
                Log.i(TAG,"null == wakeLock");
            }
        }
    }

    //释放设备电源锁
    public static void releaseWakeLock()
    {
        if (null != wakeLock)
        {
            wakeLock.release();
            wakeLock = null;
        }
    }
}
