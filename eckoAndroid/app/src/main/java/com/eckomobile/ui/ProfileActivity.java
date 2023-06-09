package com.eckomobile.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.eckomobile.R;
import com.eckomobile.data.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    TextView profile_username, profile_genderAge, profile_heightWeight;
    Spinner primaryGoalSpinner, foodPreferenceSpinner, fitnessLevelSpinner, sleepHoursSpinner;
    Button saveButton;
    ImageView backButton;

    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "eckoBackend_war";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;

    public String food_goal ;
    public String sleep_goal;
    public int exercise_goal;
    public String primary_goal;


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

        //post method to grab original gender age, height, and weight
        display_profile();
        backButton.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, MainPageActivity.class)));

    }

    @SuppressLint("SetTextI18n")
    public void display_profile() {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final StringRequest recordsRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/profile",
                response -> {
                    Log.d("~~~~ Profile page: ~~~~","USER INFO" );
                    Gson gson = new Gson();
                    JsonObject js = gson.fromJson(response, JsonObject.class);

                    Log.d("GRABBING RESULT info: ", js.toString());

//                  user info
                    String first_name = js.get("first_name").getAsString();
                    String last_name = js.get("last_name").getAsString();
                    String age = js.get("age").getAsString();
                    String gender = js.get("gender").getAsString();
                    String height = js.get("height").getAsString();
                    String weight = js.get("weight").getAsString();

//                  goals
                    this.food_goal = js.get("food_goal").getAsString().toLowerCase();
                    Log.d("Food Goal:", this.food_goal);

                    this.sleep_goal = js.get("sleep_goal").getAsString().toLowerCase();
                    Log.d("Sleep Goal:", this.sleep_goal);

                    this.exercise_goal = js.get("exercise_goal").getAsInt();
                    Log.d("Exercise Goal:", String.valueOf(this.exercise_goal));

                    this.primary_goal = js.get("primary_goal").getAsString().toLowerCase();
                    Log.d("Primary Goal:", this.primary_goal);


                    profile_username.setText(first_name + " " + last_name);
                    profile_genderAge.setText(gender + ", " + age + " years old");
                    profile_heightWeight.setText("Height: " + height + ", Weight: " + weight);

                    ArrayAdapter<String> primaryGoalsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Data.primaryGoals);
                    primaryGoalsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    primaryGoalSpinner.setAdapter(primaryGoalsAdapter);

                    int primaryGoalIndex = Arrays.asList(Data.primaryGoals).indexOf(primary_goal.toLowerCase());
                    primaryGoalSpinner.setSelection(primaryGoalIndex);

                    String selectedPrimaryGoal;

                    primaryGoalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(0xFFFFFFFF);
                            String selectedPrimaryGoal = Data.primaryGoals[position];
                            // Use the selectedPrimaryGoal variable as needed
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                    ArrayAdapter foodPreferencesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Data.foodPreferences);
                    foodPreferencesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    foodPreferenceSpinner.setAdapter(foodPreferencesAdapter);

                    int foodPreferenceIndex = Arrays.asList(Data.foodPreferences).indexOf(this.food_goal);
                    foodPreferenceSpinner.setSelection(foodPreferenceIndex);

                    foodPreferenceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(0xFFFFFFFF);
                            String selectedFoodPreference = Data.foodPreferences[position];
                            // Use the selectedFoodPreference variable as needed
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                    ArrayAdapter fitnessLevelAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Data.fitnessLevels);
                    fitnessLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    fitnessLevelSpinner.setAdapter(fitnessLevelAdapter);

                    fitnessLevelSpinner.setSelection(exercise_goal - 1);

                    fitnessLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(0xFFFFFFFF);
                            String selectedFitnessLevel = Data.fitnessLevels[position];
                            // Use the selectedFitnessLevel variable as needed
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                    ArrayAdapter sleepHoursAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Data.sleepHours);
                    sleepHoursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sleepHoursSpinner.setAdapter(sleepHoursAdapter);

                    int defaultSleepHours = Arrays.asList(Data.sleepHours).indexOf(this.sleep_goal);
                    sleepHoursSpinner.setSelection(defaultSleepHours);

                    sleepHoursSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(0xFFFFFFFF);
                            String selectedSleepHours = Data.sleepHours[position];
                            // Use the selectedSleepHours variable as needed
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                    saveButton.setOnClickListener(v -> {
                        Toast.makeText(this, "Successfully saved!", Toast.LENGTH_SHORT).show();
                    });
                },
                error -> {
                    Log.d("~~~~ LOGIN ERROR: ~~~~", error.toString());
                });

        queue.add(recordsRequest);
    }




}