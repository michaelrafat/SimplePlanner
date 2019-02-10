package com.android.michael.simpleplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.michael.simpleplanner.Utilities.DateTimeUtils;
import com.android.michael.simpleplanner.Utilities.NotificationUtils;

import java.util.Date;
import java.util.Random;

public class NotificationReceiver extends BroadcastReceiver {

    private DateTimeUtils dateTimeUtils;
    private NotificationUtils notificationUtils;

    @Override
    public void onReceive(Context context, Intent intent) {

        dateTimeUtils = new DateTimeUtils(context);
        notificationUtils = new NotificationUtils(context);

        String currentDate = dateTimeUtils.formatDate(new Date());

        notificationUtils.pushNotification("New Notification", "Time: " + currentDate, new Random().nextInt());
    }
}