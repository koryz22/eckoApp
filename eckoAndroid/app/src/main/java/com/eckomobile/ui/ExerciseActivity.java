package com.eckomobile.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.eckomobile.R;

public class ExerciseActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewExercise;
    TextView exerciseRecommendation1, exerciseRecommendation2, exerciseRecommendation3;
    EditText exerciseSearchBox;
    ImageView exerciseSearchButton;
    ListView exerciseSearchListView;

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
//                case R.id.sleep:
//                    startActivity(new Intent(ExerciseActivity.this, SleepActivity.class));
//                    return true;
                case R.id.exercise:
                    return true;
            }

            return false;
        });

        exerciseSearchButton.setOnClickListener(v -> {
            String searchQuery = String.valueOf(exerciseSearchBox.getText());

            // Search corresponding exercises

            Data.exerciseItems.clear();
            // Add data
            /* Temporary Data */
            Data.exerciseItems.add(new ExerciseItem("Pec Fly", 200, "1 hour"));
            Data.exerciseItems.add(new ExerciseItem("Lateral Dumbbell Raise", 100, "2 hours"));
            Data.exerciseItems.add(new ExerciseItem("Shoulder Press", 120, "40 minutes"));

            ExerciseItemAdapter exerciseItemAdapter = new ExerciseItemAdapter(this, Data.exerciseItems);
            exerciseSearchListView.setAdapter(exerciseItemAdapter);

            exerciseSearchListView.setVisibility(View.VISIBLE);
        });

        Data.exerciseItems.clear();

        exerciseSearchListView.setVisibility(View.INVISIBLE);
    }
}
