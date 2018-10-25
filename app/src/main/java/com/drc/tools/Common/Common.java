package com.drc.tools.Common;

import android.Manifest;
import android.annotation.SuppressLint;
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
    private final static String TAG = "Common";

    public static Properties getFSConfigProperties() {
        Properties properties = new Properties();
        InputStream in = Common.class.getResourceAsStream("/assets/App.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
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

}