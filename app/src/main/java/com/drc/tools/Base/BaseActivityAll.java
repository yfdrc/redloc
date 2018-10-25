package com.drc.tools.Base;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

public class BaseActivityAll {
    private static final String TAG = "BaseActivityAll";

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        //Log.i(TAG, "addActivity: ok");
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        //Log.i(TAG, "removeActivity: ok");
        activities.remove(activity);
    }

    public static void finishAll() {
        //Log.i(TAG, "finishAll: ok");
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
