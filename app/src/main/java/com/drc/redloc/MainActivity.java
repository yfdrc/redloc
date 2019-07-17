package com.drc.redloc;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.drc.tools.Common.DrcAlarm;
import com.drc.tools.Notification.DrcNotification;
import com.drc.tools.Common.DrcPermissions;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "DrcMainActivity";

    private void init(Context context){
        DrcNotification drcNotification= new DrcNotification();
        drcNotification.createNotificationChannel(context,"drc",3);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        DrcAlarm.setRepeat(manager,1,context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate:ok");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrcPermissions.requestPermissions(this, this);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);

        final DrcNotification drcNotification= new DrcNotification();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "b1 onClick: createNotificationChannel");
                drcNotification.createNotificationChannel(v.getContext(),"drc",3);
                TextView tv=(TextView) findViewById(R.id.tvlocate);
                tv.setText("b1");
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drcNotification.deleteNotificationChannel(v.getContext(),"drc");
                TextView tv=(TextView) findViewById(R.id.tvlocate);
                tv.setText("b2");
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drcNotification.createNotificationNotify(v.getContext(),"drc",3,"test title","test notifytext");
                TextView tv=(TextView) findViewById(R.id.tvlocate);
                tv.setText("b3");
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "b4 onClick: DrcAlarm.setRepeat");
                AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                DrcAlarm.setRepeat(manager,1,v.getContext());
                TextView tv=(TextView) findViewById(R.id.tvlocate);
                tv.setText("b4");
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "b5 onClick: DrcAlarm.stopRepeat");
                AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                DrcAlarm.stopRepeat(manager, v.getContext());
                TextView tv=(TextView) findViewById(R.id.tvlocate);
                tv.setText("b5");
            }
        });

        init(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ok");
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        DrcPermissions.onRequestPermissions(this, this, grantResults, requestCode);

    }
}
