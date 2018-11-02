package com.drc.redloc;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.drc.tools.Common.DrcPermissions;
import com.drc.tools.Crypto.TestCrypto;
import com.drc.tools.Service.DrcLongRunningService;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "DrcMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.i(TAG, "onCreate:ok");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrcPermissions.requestPermissions(this, this);
        //DrcLongRunningService.Drcstart(this);
        //finish();

        TestCrypto.Test();
    }

    @Override
    protected void onDestroy() {
        //Log.i(TAG, "onDestroy: ok");
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Log.i(TAG, "onRequestPermissionsResult: ok");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        DrcPermissions.onRequestPermissions(this, this, grantResults, requestCode);

    }
}
