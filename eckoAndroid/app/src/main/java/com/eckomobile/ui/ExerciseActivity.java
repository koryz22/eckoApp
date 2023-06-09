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
import com.eckomobile.data.NetworkManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.eckomobile.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ExerciseActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewExercise;
    TextView exerciseRecommendation1, exerciseRecommendation2, exerciseRecommendation3;
    EditText exerciseSearchBox;
    ImageView exerciseSearchButton;
    ListView exerciseSearchListView;

    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "eckoBackend_war";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        bottomNavigationViewExercise = findViewById(R.id.bottomNavigationViewExercise);
        exerciseRecommendation1 = findViewById(R.id.exerciseRecommendation1);
        exerciseRecommendation2 = findViewById(R.id.exerciseRecommendation2);
        exerciseRecommendation3 = findViewById(R.id.exerciseRecommendation3);
        exerciseSearchBox = findViewById(R.id.exerciseSearchBox);
        exerciseSearchButton = findViewById(R.id.exerciseSearchButton);
        exerciseSearchListView = findViewById(R.id.exerciseSearchListView);

        loadExerciseRecommendations();

        bottomNavigationViewExercise.setSelectedItemId(R.id.exercise);
        bottomNavigationViewExercise.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.home:
                    startActivity(new Intent(ExerciseActivity.this, MainPageActivity.class));
                    return true;
                case R.id.food:
                    startActivity(new Intent(ExerciseActivity.this, FoodActivity.class));
                    return true;
                case R.id.exercise:
                    return true;
                case R.id.sleep:
                    startActivity(new Intent(ExerciseActivity.this, SleepActivity.class));
                    return true;
            }

            return false;
        });

        exerciseRecommendation1.setOnClickListener(v -> {
            String exerciseRecOne = exerciseRecommendation1.getText().toString();
            Toast.makeText(this, exerciseRecOne, Toast.LENGTH_SHORT).show();
            loadExerciseOnAction(exerciseRecOne);
        });

        exerciseRecommendation2.setOnClickListener(v -> {
            String exerciseRecTwo = exerciseRecommendation2.getText().toString();
            Toast.makeText(this, exerciseRecTwo, Toast.LENGTH_SHORT).show();
            loadExerciseOnAction(exerciseRecTwo);
        });

        exerciseRecommendation3.setOnClickListener(v -> {
            String exerciseRecThree = exerciseRecommendation3.getText().toString();
            Toast.makeText(this, exerciseRecThree, Toast.LENGTH_SHORT).show();
            loadExerciseOnAction(exerciseRecThree);
        });

        exerciseSearchButton.setOnClickListener(v -> {
            String searchQuery = String.valueOf(exerciseSearchBox.getText());
            loadExerciseOnAction(searchQuery);
        });

        Data.exerciseItems.clear();
        exerciseSearchListView.setVisibility(View.INVISIBLE);
    }
    public void loadExerciseRecommendations() {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        @SuppressLint("SetTextI18n") final StringRequest loadExerciseRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/exercise",
                response -> {
                    Gson gson = new Gson();
                    JsonArray js = gson.fromJson(response, JsonArray.class);
                    Log.d("EXERCISE RES JS", js.toString());
                    for (int i = 0; i < js.size(); i++) {
                        JsonObject obj = js.get(i).getAsJsonObject();
                        String exercise = obj.get("exerciseName").toString().substring(1, obj.get("exerciseName").toString().length() - 1);
                        if(i == 0) {
                            exerciseRecommendation1.setText(exercise);
                        } else if(i == 1) {
                            exerciseRecommendation2.setText(exercise);
                        } else if(i == 2) {
                            exerciseRecommendation3.setText(exercise);
                        }
                    }
                },
                error -> {
                    Log.d("~~~~ SAVE ERROR: ~~~~", error.toString());
                });
        queue.add(loadExerciseRequest);
    }

    public void loadExerciseOnAction(String exerciseName) {
        String exerciseNameEncoded = exerciseName.replace(" ", "%20");
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        @SuppressLint("SetTextI18n") final StringRequest exerciseRequestOnAction = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/exercise?exerciseName=" + exerciseNameEncoded,
                response -> {
                    Log.d("RESPONSE", response);
                    Gson gson = new Gson();
                    JsonArray js = gson.fromJson(response, JsonArray.class);
                    Log.d("ON CLICK FOOD REC JS", js.toString());
                    Data.exerciseItems.clear();
                    for (int i = 0; i < js.size(); i++) {
                        JsonObject obj = js.get(i).getAsJsonObject();
                        String exercise = obj.get("exerciseName").toString();
                        float calsBurned = Float.parseFloat(obj.get("calsBurned").toString());
                        Data.exerciseItems.add(new ExerciseItem(exercise, calsBurned, "1 hour"));
                        ExerciseItemAdapter exerciseItemAdapter = new ExerciseItemAdapter(this, Data.exerciseItems);
                        exerciseSearchListView.setAdapter(exerciseItemAdapter);
                        exerciseSearchListView.setVisibility(View.VISIBLE);
                    }
                },
                error -> {
                    Log.d("~~~~ SAVE ERROR: ~~~~", error.toString());
                });
        queue.add(exerciseRequestOnAction);
    }
}
