package com.drc.redloc;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.drc.redloc.Service.DrcLocateService;
import com.drc.tools.Common.DrcPermissions;
import com.drc.tools.Database.DrcDB;
import com.drc.tools.Location.DrcLocation;
import com.drc.tools.Notification.DrcNotification;
import com.drc.redloc.Service.DrcLongRunningService;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "DrcMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.i(TAG, "onCreate:ok");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrcPermissions.requestPermissions(this, this);
        //DrcLongRunningService.Drcstart(this);

        //DrcNotification.create(this, "drc", 5, 1, "title", "text");
        //finish();

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);

//        final DrcDB db = new DrcDB(this, "drc", "system", 1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrcNotification.createNotificationChannel(v.getContext(),"drc",3);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrcNotification.createNotification(v.getContext(), "drc", 1, "title", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrcLocation drcLocation = new DrcLocation(v.getContext());
                drcLocation.start();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

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
