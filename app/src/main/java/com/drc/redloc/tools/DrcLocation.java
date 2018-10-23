package com.drc.redloc.tools;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.drc.redloc.base.BaseApplication;

import java.util.Date;

public class DrcLocation extends BaseApplication {
    private final static String TAG = "DrcLocation";

    private final static int MIN10 = 1000 * 60 * 10;
    private final static int MIN60 = 1000 * 60 * 60;
    private static int sj = 0;
    private LocationClient locationClient;
    private Context context;

    public void start(Context context) {
        this.context = context;
        locationClient = new LocationClient(context);
        locationClient.registerLocationListener(new DrcLocationlistener());
    }

    public void requestLocation() {
        initLocation();
        locationClient.start();
    }

    public void stop() {
        locationClient.stop();
    }

    protected class DrcLocationlistener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            Log.i(TAG, "onReceiveLocation: ok");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            StringBuilder sb = new StringBuilder();
            sb.append("更新时间：").append(df.format(new Date())).append("\n");
            sb.append("维度：").append(bdLocation.getLatitude()).append("\n");
            sb.append("经度：").append(bdLocation.getLongitude()).append("\n");
            sb.append("地址：").append(bdLocation.getProvince()).append(bdLocation.getCity())
                    .append(bdLocation.getDistrict()).append(bdLocation.getStreet()).append("\n");
            sb.append("定位方式：");
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                sb.append("GPS");
            } else {
                sb.append("NETWARE");
            }
            sb.append("\n");
            DrcFilesystem.SaveAndSend(context, sb.toString());
        }
    }

    private void initLocation() {
        LocationClientOption lop = new LocationClientOption();

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour < 7) {
            sj = MIN60;
        } else {
            sj = MIN10;
        }
        lop.setIsNeedAddress(true);
        locationClient.setLocOption(lop);
    }
}
