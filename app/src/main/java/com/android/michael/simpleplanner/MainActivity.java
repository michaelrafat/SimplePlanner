package com.android.michael.simpleplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.michael.simpleplanner.Utilities.DateTimeUtils;
import com.android.michael.simpleplanner.Utilities.NotificationUtils;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button pushNotificationButton, nextPageButton;
    private TextView timeDateTextView;

    private DateTimeUtils dateTimeUtils;
    private NotificationUtils notificationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        dateTimeUtils = new DateTimeUtils(this);
        notificationUtils = new NotificationUtils(this);

        dateTimeUtils.startDateTime(timeDateTextView, this);

        pushNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationUtils.pushNotification(
                        "New Notification",
                        "Time: " + dateTimeUtils.getCurrentDateTime(),
                        new Random().nextInt()
                );
            }
        });

        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this, SecondActivity.class));

            }
        });
    }

    private void initView() {

        pushNotificationButton = findViewById(R.id.btn_push_notification);
        nextPageButton = findViewById(R.id.btn_next_page2);
        timeDateTextView = findViewById(R.id.tv_time_date);
    }
}