package com.drc.tools.Location;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.location.LocationManager;
import android.util.Log;
import java.util.List;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import com.drc.tools.Base.BaseApplication;
import com.drc.tools.Notification.DrcNotification;
import com.drc.tools.Service.DrcLocateService;

public class DrcLocation extends BaseApplication {

    private static String TAG = "DrcLocation";
    private LocationClient locationClient = null;
    private LocationClientOption mOption, DIYoption;
    private Object objLock = new Object();
    private DrcLocationlistener drcLocationlistener = null;

    public DrcLocation(Context context) {
        synchronized (objLock) {
            Log.d(TAG,"DrcLocation");
            if (locationClient == null) {
                locationClient = new LocationClient(context);
                drcLocationlistener = new DrcLocationlistener(context);
                setLocationOption(getDefaultLocationClientOption());
            }
        }
    }

    public void start() {
        synchronized (objLock) {
            Log.d(TAG,"DrcLocation start");
            Log.d(TAG,"    DrcLocation start ** registerListener-locationClient.start");
            if (locationClient != null && !locationClient.isStarted()) {
                registerListener(drcLocationlistener);
                locationClient.start();
            }
        }
    }

    public void stop() {
        synchronized (objLock) {
            Log.d(TAG,"stop");
            if (locationClient != null && locationClient.isStarted()) {
                Log.d(TAG,"    DrcLocation stop ** unregisterListener-locationClient.stop");
                unregisterListener(drcLocationlistener);
                locationClient.stop();
            }
        }
    }

    public String getLocationProvider() {
        String provider = "";
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            List<String> providerList = locationManager.getProviders(true);
            if (providerList.contains(LocationManager.GPS_PROVIDER)) {
                provider = LocationManager.GPS_PROVIDER;
            } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                provider = LocationManager.NETWORK_PROVIDER;
            }
        } catch (Exception ex) {
            Log.e(TAG, "getLocationProvider error");
        }
        return provider;
    }

    public boolean registerListener(BDAbstractLocationListener listener) {
        Log.d(TAG,"registerListener");
        boolean isSuccess = false;
        if (listener != null) {
            locationClient.registerLocationListener(listener);
            isSuccess = true;
        }
        return isSuccess;
    }

    public void unregisterListener(BDAbstractLocationListener listener) {
        if (listener != null) {
            Log.d(TAG,"unregisterListener");
            locationClient.unRegisterLocationListener(listener);
        }
    }

    public boolean setLocationOption(LocationClientOption option) {
        Log.d(TAG,"setLocationOption");
        boolean isSuccess = false;
        if (option != null) {
            if (locationClient.isStarted())
                locationClient.stop();
            DIYoption = option;
            locationClient.setLocOption(option);
        }
        return isSuccess;
    }

    public LocationClientOption getOption() {
        return DIYoption;
    }

    public LocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            mOption = new LocationClientOption();
            mOption.setLocationMode(LocationMode.Device_Sensors);  //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");           //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(60*1000);            //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true);          //可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true); //可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false);      //可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false);        //可选，默认false，设置是否当gps有效时按照1秒1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true);      //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationDescribe(true); //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setIsNeedLocationPoiList(true);  //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);  //可选，默认false，设置是否收集CRASH信息，默认收集
            mOption.setIsNeedAltitude(true);         //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        }
        return mOption;
    }

    public boolean requestHotSpotState() {
        return locationClient.requestHotSpotState();
    }

}

class DrcLocationlistener extends BDAbstractLocationListener {
    private final static String TAG = "DrcLocationlistener";
    private Context context = null;

    DrcLocationlistener(Context mContext){
        this.context = mContext;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        Log.d(TAG, "onReceiveLocation");
        Log.d(TAG, "    onReceiveLocation ** stopService DrcLocateService");
        StringBuilder sb = new StringBuilder();
        sb.append("时间：").append(Calendar.getInstance().getTime().toString()).append("\n");
        sb.append("维度：").append(bdLocation.getLatitude()).append("\n");
        sb.append("经度：").append(bdLocation.getLongitude()).append("\n");
        sb.append("地址：").append(bdLocation.getAddrStr()).append("\n");
        sb.append("移速：").append( bdLocation.getSpeed()).append("\n");
        //DrcFilesystem.SaveAndSend(context, sb.toString());

        new DrcNotification().createNotificationNotify(this.context, "drc", 1, "Location", sb.toString());

        Intent serStop = new Intent(context.getApplicationContext(), DrcLocateService.class);
        context.getApplicationContext().stopService(serStop);
    }
}
