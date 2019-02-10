package com.android.michael.simpleplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.michael.simpleplanner.Database.Plan;
import com.android.michael.simpleplanner.Utilities.DatabaseUtils;
import com.android.michael.simpleplanner.Utilities.DateTimeUtils;

public class SecondActivity extends AppCompatActivity {

    private Button saveToDatabaseButton, selectLastDatabaseRecordButton, timePickerButton, backButton;
    private EditText planNumberEditText;
    private DateTimeUtils dateTimeUtils;
    private DatabaseUtils databaseUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initView();

        dateTimeUtils = new DateTimeUtils(this);
        databaseUtils = new DatabaseUtils(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SecondActivity.this, MainActivity.class));

            }
        });

        saveToDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String planNumber = planNumberEditText.getText().toString();

                if (!planNumber.isEmpty()) {
                    Plan plan = new Plan();
                    plan.setPlanNumber(planNumber);
                    databaseUtils.insertPlan(plan);
                } else {
                    planNumberEditText.setError("Empty Number... !");
                    showToast("Please add Number to save!");
                }
            }
        });

        selectLastDatabaseRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseUtils.getLastPlan();
            }
        });

        timePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeUtils.setTimePicker();
            }
        });
    }

    private void initView() {

        saveToDatabaseButton = findViewById(R.id.btn_save_notify_database);
        selectLastDatabaseRecordButton = findViewById(R.id.btn_return_last_database_notify);
        timePickerButton = findViewById(R.id.btn_time_picker);
        backButton = findViewById(R.id.btn_back_to_first_page);
        planNumberEditText = findViewById(R.id.editText);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }
}