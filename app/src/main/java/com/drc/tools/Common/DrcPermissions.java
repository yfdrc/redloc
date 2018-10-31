package com.drc.tools.Common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DrcPermissions {
    public static int RCODE = 323;

    private final static String TAG = "DrcPermissions";

    public static void onRequestPermissions(Context context, Activity activity, int[] grantResults, int requestCode) {
        if (requestCode == RCODE) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, "必须同意才能运行！", Toast.LENGTH_LONG).show();
                        activity.finish();
                        return;
                    }
                }
            } else {
                Toast.makeText(context, "发生未知错误！", Toast.LENGTH_LONG).show();
                activity.finish();
            }
        }
    }

    public static void requestPermissions(Context context, Activity activity) {
        String[] perms = getPermissions(context);
        if (perms != null) {
            ActivityCompat.requestPermissions(activity, perms,RCODE);
        }
    }

    private static String[] getPermissions(Context context) {
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

}