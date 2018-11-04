package com.drc.tools.Location;

import android.content.Context;
import android.icu.util.Calendar;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.drc.redloc.MainActivity;
import com.drc.tools.Notification.DrcNotification;

class DrcLocationlistener extends BDAbstractLocationListener {
    private final static String TAG = "DrcLocationlistener";
    private Context context = null;

    DrcLocationlistener(Context mContext){
        this.context = mContext;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        StringBuilder sb = new StringBuilder();
        sb.append("更新时间：").append(Calendar.getInstance().getTime().toString()).append("\n");
        sb.append("维度：").append(bdLocation.getLatitude()).append("\n");
        sb.append("经度：").append(bdLocation.getLongitude()).append("\n");
        sb.append("地址：").append(bdLocation.getProvince()).append(bdLocation.getCity()).append(bdLocation.getDistrict()).append(bdLocation.getStreet()).append("\n");
        sb.append("定位方式：");
        if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
            sb.append("GPS");
        } else {
            sb.append("NETWARE");
        }
        sb.append("\n");
        //DrcFilesystem.SaveAndSend(context, sb.toString());

        DrcNotification.createNotification(this.context, "drc", 1, "Location", sb.toString());
    }
}
