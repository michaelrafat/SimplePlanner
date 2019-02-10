package com.android.michael.simpleplanner.Utilities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    private Context context;
    private SimpleDateFormat simpleDateFormat;
    private String currentDateTime;
    private Calendar currentCalender, calendar;
    private NotificationUtils notificationUtils;

    public DateTimeUtils(Context context) {
        this.context = context;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    //a method that update a TextView with a TimeDate
    public void startDateTime(final TextView timeDateTextView, final Activity activity) {

        new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(50);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDateTime(timeDateTextView);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void updateDateTime(TextView timeDateTextView) {

        currentDateTime = formatDate(new Date());
        timeDateTextView.setText(currentDateTime);
    }

    public String formatDate(Date date) {

        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }

    public void setTimePicker() {

        initCalendar();
        notificationUtils = new NotificationUtils(context);

        final int hour = currentCalender.get(Calendar.HOUR);
        final int minute = currentCalender.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                if (selectedHour >= hour) {
                    if (selectedMinute > minute) {
                        notificationUtils.setTimePicker(calendar, currentCalender, selectedHour, selectedMinute);
                        showToast("Push a Notification at: " + selectedHour + ":" + selectedMinute);
                    } else {
                        showToast("invalid old time!");
                    }
                } else {
                    showToast("invalid old time!");
                }
            }
        }, hour, minute, true);
        mTimePicker.show();
    }

    private void initCalendar() {
        currentCalender = Calendar.getInstance();
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}