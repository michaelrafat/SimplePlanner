package com.android.michael.simpleplanner.Utilities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.android.michael.simpleplanner.MainActivity;
import com.android.michael.simpleplanner.NotificationReceiver;
import com.android.michael.simpleplanner.R;

import java.util.Calendar;
import java.util.Random;

public class NotificationUtils {

    private Context context;
    private RemoteViews remoteViews;

    public NotificationUtils(Context context) {
        this.context = context;
    }

    //this method allows push notifications when app not running by a Broadcast Receiver
    public void setTimePicker(Calendar calendar, Calendar currentCalendar, int selectedHour, int selectedMinute) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        calendar.add(Calendar.DAY_OF_YEAR, currentCalendar.get(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR, selectedHour);
        calendar.set(Calendar.MINUTE, selectedMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.DATE, currentCalendar.get(Calendar.DATE));
        calendar.set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH));

        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
        notificationIntent.setFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, new Random().nextInt(), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);
    }

    public void pushNotification(String title, String message, int NOTIFICATION_ID) {

        String CHANNEL_ID = "Michael_CHANNEL_ID";
        String CHANNEL_NAME = "New Notification";
        String CHANNEL_DESCRIPTION = "Description";

        //RemoteView is a custom Notification Layout
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        remoteViews.setTextViewText(R.id.tv_notification_title, title);
        remoteViews.setTextViewText(R.id.tv_notification_message, message);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            mChannel.setDescription(CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setDefaults(Notification.DEFAULT_ALL)
                .setCustomBigContentView(remoteViews)
                .setFullScreenIntent(resultPendingIntent, true)
                .setContentText(message);

        builder.setContentIntent(resultPendingIntent);
        builder.setFullScreenIntent(resultPendingIntent, true);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
