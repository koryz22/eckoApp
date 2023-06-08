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

public class FoodActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewFood;
    TextView foodRecommendation1, foodRecommendation2, foodRecommendation3;
    EditText foodSearchBox;
    ImageView foodSearchButton;
    ListView foodSearchListView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        bottomNavigationViewFood = findViewById(R.id.bottomNavigationViewFood);
        foodRecommendation1 = findViewById(R.id.foodRecommendation1);
        foodRecommendation2 = findViewById(R.id.foodRecommendation2);
        foodRecommendation3 = findViewById(R.id.foodRecommendation3);
        foodSearchBox = findViewById(R.id.foodSearchBox);
        foodSearchButton = findViewById(R.id.foodSearchButton);
        foodSearchListView = findViewById(R.id.foodSearchListView);

        bottomNavigationViewFood.setSelectedItemId(R.id.food);
        bottomNavigationViewFood.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.home:
                    startActivity(new Intent(FoodActivity.this, MainPageActivity.class));
                    return true;
                case R.id.food:
                    return true;
//                case R.id.sleep:
//                    startActivity(new Intent(FoodActivity.this, SleepActivity.class));
//                    return true;
//                case R.id.exercise:
//                    startActivity(new Intent(FoodActivity.this, ExerciseActivity.class));
//                    return true;
            }

            return false;
        });

        foodSearchButton.setOnClickListener(v -> {
            String searchQuery = String.valueOf(foodSearchBox.getText());

            // Search corresponding foods

            Data.foodItems.clear();
            // Add data
            /* Temporary Data */
            Data.foodItems.add(new FoodItem("Chicken Tenders", 300, 100, 20, 6, 2));
            Data.foodItems.add(new FoodItem("Smoked Salmon", 400, 20, 60, 12, 1));
            Data.foodItems.add(new FoodItem("Protein Shake", 50, 30, 120, 5, 3));

            FoodItemAdapter foodItemAdapter = new FoodItemAdapter(this, Data.foodItems);
            foodSearchListView.setAdapter(foodItemAdapter);

            foodSearchListView.setVisibility(View.VISIBLE);
        });

        Data.foodItems.clear();

        foodSearchListView.setVisibility(View.INVISIBLE);
    }
}
