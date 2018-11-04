package com.drc.tools.Notification;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.drc.redloc.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class DrcNotification extends Application {

    private final static String TAG = "DrcNotification";
    private static NotificationManager notificationManager = null;

    private static NotificationManager getNotificationManager(Context context) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public static void createNotification(Context context, String channelId, int notifyId, String notifyTitle, String notifyText) {
       Log.d(TAG, "notification create");
        try {
            NotificationManager notificationManager = getNotificationManager(context);
            NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle().bigText(notifyText);
            Notification notification = new NotificationCompat.Builder(context, channelId)
                    .setTicker(notifyTitle)                                    //在状态栏显示的标题
                    .setWhen(java.lang.System.currentTimeMillis())             //设置显示的时间，默认就是currentTimeMillis()
                    .setContentTitle(notifyTitle)                              //设置标题
                    .setContentText(notifyText)                                //设置内容
                    .setSmallIcon(R.drawable.ic_launcher_foreground)        //设置状态栏显示时的图标
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground))  //设置显示的大图标
                    .setAutoCancel(false)                          //设置是否自动按下过后取消
                    .setOngoing(true)                              //设置为true时就不能删除  除非使用notificationManager.cancel(1)方法
                    .setStyle(style)
                    .build();                                      //创建Notification
            notificationManager.notify(notifyId, notification);    //管理器通知
        } catch (Exception ex) {
            Log.e(TAG, "createNotification error" + ex.toString());
        }
    }

    public static void deleteNotification(Context context, int notifyId) {
        try {
            NotificationManager notificationManager = getNotificationManager(context);
            notificationManager.cancel(notifyId);
        } catch (Exception ex) {
            Log.e(TAG, "deleteNotification error" + ex.toString());
        }
    }

    public static void romoveNotification(Context context) {
        try {
            NotificationManager notificationManager = getNotificationManager(context);
            notificationManager.cancelAll();
        } catch (Exception ex) {
            Log.e(TAG, "romoveNotification error" + ex.toString());
        }
    }

    public static void createNotificationChannel(Context context, String channelId, int importance) {
        try {
            String channelName = channelId;
            String description = channelId;
            NotificationManager notificationManager = getNotificationManager(context);
            NotificationChannel notificationChannel;
            if (notificationManager.getNotificationChannel(channelId) != null) {
                notificationChannel = notificationManager.getNotificationChannel(channelId);
            } else {
                notificationChannel = new NotificationChannel(channelId, channelName, importance);
            }
            notificationChannel.enableLights(false);        //闪灯
            notificationChannel.setLightColor(Color.RED);   //闪灯的颜色

            notificationChannel.enableVibration(false);      //震动
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400});  //设置震动频率

            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE); //锁屏可见
            notificationChannel.setShowBadge(false);  //设置是否显示角标
            notificationChannel.setBypassDnd(true);  //设置绕过免打扰模式
            notificationChannel.setDescription(description);

            notificationManager.createNotificationChannel(notificationChannel);
        } catch (Exception ex) {
            Log.e(TAG, "createNotificationChannel error" + ex.toString());
        }
    }


    public static void deleteNotificationChannel(Context context, String channelId) {
        try {
            NotificationManager notificationManager = getNotificationManager(context);
            if (notificationManager.getNotificationChannel(channelId) != null) {
                notificationManager.deleteNotificationChannel(channelId);
            }
        } catch (Exception ex) {
            Log.e(TAG, "deleteNotificationChannel error" + ex.toString());
        }
    }

}
