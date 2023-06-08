package com.eckomobile.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.eckomobile.R;

import java.util.Arrays;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    TextView profile_username, profile_genderAge, profile_heightWeight;
    Spinner primaryGoalSpinner, foodPreferenceSpinner, fitnessLevelSpinner, sleepHoursSpinner;
    Button saveButton;
    ImageView backButton;

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_username = findViewById(R.id.profile_username);
        profile_genderAge = findViewById(R.id.profile_genderAge);
        profile_heightWeight = findViewById(R.id.profile_heightWeight);
        primaryGoalSpinner = findViewById(R.id.primaryGoalSpinner);
        foodPreferenceSpinner = findViewById(R.id.foodPreferenceSpinner);
        fitnessLevelSpinner = findViewById(R.id.fitnessLevelSpinner);
        sleepHoursSpinner = findViewById(R.id.sleepHoursSpinner);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backButton);

//        profile_genderAge.setText(Data.gender + ", " + Data.age);
        profile_genderAge.setText("Male" + ", " + "20");
//        profile_heightWeight.setText(Data.height + " / " + Data.weight);
        profile_heightWeight.setText("5'9\"" + ", " + "170 lbs.");

        ArrayAdapter primaryGoalsAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Data.primaryGoals);
        primaryGoalsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        primaryGoalSpinner.setAdapter(primaryGoalsAdapter);
        if (Objects.equals(Data.primaryGoal, "")) {
            primaryGoalSpinner.setSelection(0);
        }
        else {
            int primaryGoalIndex = Arrays.asList(Data.primaryGoals).indexOf(Data.primaryGoal);
            primaryGoalSpinner.setSelection(primaryGoalIndex);
        }
        primaryGoalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(0xFFFFFFFF);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ArrayAdapter foodPreferencesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Data.foodPreferences);
        foodPreferencesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodPreferenceSpinner.setAdapter(foodPreferencesAdapter);
        if (Objects.equals(Data.foodPreference, "")) {
            foodPreferenceSpinner.setSelection(0);
        }
        else {
            int foodPreferenceIndex = Arrays.asList(Data.foodPreferences).indexOf(Data.foodPreference);
            foodPreferenceSpinner.setSelection(foodPreferenceIndex);
        }
        foodPreferenceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(0xFFFFFFFF);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ArrayAdapter fitnessLevelAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Data.fitnessLevels);
        fitnessLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fitnessLevelSpinner.setAdapter(fitnessLevelAdapter);
        if (Objects.equals(Data.fitnessLevel, "")) {
            fitnessLevelSpinner.setSelection(0);
        }
        else {
            int fitnessLevelInt = Arrays.asList(Data.fitnessLevels).indexOf(Data.fitnessLevel);
            fitnessLevelSpinner.setSelection(fitnessLevelInt);
        }
        fitnessLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(0xFFFFFFFF);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ArrayAdapter sleepHoursAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Data.sleepHours);
        sleepHoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sleepHoursSpinner.setAdapter(sleepHoursAdapter);
        if (Objects.equals(Data.sleepHour, "")) {
            int defaultSleepHours = Arrays.asList(Data.sleepHours).indexOf("8 hours");
            sleepHoursSpinner.setSelection(defaultSleepHours);
        }
        else {
            int sleepHoursInt = Arrays.asList(Data.sleepHours).indexOf(Data.sleepHour);
            sleepHoursSpinner.setSelection(sleepHoursInt);
        }
        sleepHoursSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(0xFFFFFFFF);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        saveButton.setOnClickListener(v -> {
            // Save data to the DB

            Toast.makeText(this, "Successfully saved!", Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, MainPageActivity.class)));
    }
}