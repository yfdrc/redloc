package com.drc.redloc;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.drc.tools.Common.DrcPermissions;
import com.drc.tools.Service.LongRunningService;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "DrcMainActivity";

    private static final int RCODE = 323;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.i(TAG, "onCreate:ok");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] perms = DrcPermissions.getPermissions(this);
        if (perms != null) {
            ActivityCompat.requestPermissions(this, perms, RCODE);
        }

        LongRunningService.Drcstart(this);
        finish();
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
        switch (requestCode) {
            case RCODE:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意才能运行！", Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }
                } else {
                    Toast.makeText(this, "发生未知错误！", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
