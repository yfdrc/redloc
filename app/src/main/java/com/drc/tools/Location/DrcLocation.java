package com.drc.tools.Location;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.drc.tools.Base.BaseApplication;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import java.util.List;

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
            Log.d(TAG,"start");
            if (locationClient != null && !locationClient.isStarted()) {
                registerListener(drcLocationlistener);
                locationClient.start();
            }
        }
    }

    public void stop() {
        synchronized (objLock) {
            if (locationClient != null && locationClient.isStarted()) {
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
            mOption.setLocationMode(LocationMode.Hight_Accuracy);  //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");           //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(6000);                //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true);          //可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true); //可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false);      //可选，设置是否需要设备方向结果
            mOption.setLocationNotify(true);      //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true);      //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationDescribe(true); //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setIsNeedLocationPoiList(true);  //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);  //可选，默认false，设置是否收集CRASH信息，默认收集
            mOption.setIsNeedAltitude(false);        //可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        }
        return mOption;
    }

    public boolean requestHotSpotState() {
        return locationClient.requestHotSpotState();
    }

}
