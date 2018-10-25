package com.drc.tools.Base;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivityAll.addActivity(this);
        //Log.i(TAG, new StringBuilder().append("onCreate:").append( getClass().getSimpleName()).toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseActivityAll.removeActivity(this);
        //Log.i(TAG, new StringBuilder().append("onDestroy:").append( getClass().getSimpleName()).toString());
    }
}
