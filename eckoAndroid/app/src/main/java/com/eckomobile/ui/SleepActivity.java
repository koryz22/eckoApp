package com.eckomobile.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.eckomobile.R;

public class SleepActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewSleep;
    TimePicker timePicker;
    TextView sleepByTime;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        bottomNavigationViewSleep = findViewById(R.id.bottomNavigationViewSleep);
        timePicker = findViewById(R.id.timePicker);
        sleepByTime = findViewById(R.id.sleepByTime);

        bottomNavigationViewSleep.setSelectedItemId(R.id.sleep);
        bottomNavigationViewSleep.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.home:
                    startActivity(new Intent(SleepActivity.this, MainPageActivity.class));
                    return true;
                case R.id.food:
                    startActivity(new Intent(SleepActivity.this, FoodActivity.class));
                    return true;
                case R.id.exercise:
                    startActivity(new Intent(SleepActivity.this, ExerciseActivity.class));
                    return true;
                case R.id.sleep:
                    return true;
            }

            return false;
        });

        sleepByTime.setVisibility(View.INVISIBLE);

        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            boolean hourAfter = false;
            if (hourOfDay > 12) {
                hourAfter = true;
                hourOfDay -= 12;
            }
            boolean minuteBefore = false;
            if (minute < 10) {
                minuteBefore = true;
            }

            String selectedTime = hourOfDay + ":";

            if (minuteBefore) {
                selectedTime += "0" + minute;
            }
            else {
                selectedTime += minute;
            }

            if (hourAfter) {
                selectedTime += " PM";
            }
            else {
                selectedTime += " AM";
            }

            sleepByTime.setText(selectedTime);
            sleepByTime.setVisibility(View.VISIBLE);
        });
    }
}