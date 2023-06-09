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

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class FoodActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewFood;
    TextView foodRecommendation1, foodRecommendation2, foodRecommendation3;
    EditText foodSearchBox;
    ImageView foodSearchButton;
    ListView foodSearchListView;

    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "eckoBackend_war";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;

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

        loadFoodRecommendations();

        bottomNavigationViewFood.setSelectedItemId(R.id.food);
        bottomNavigationViewFood.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.home:
                    startActivity(new Intent(FoodActivity.this, MainPageActivity.class));
                    return true;
                case R.id.food:
                    return true;
                case R.id.exercise:
                    startActivity(new Intent(FoodActivity.this, ExerciseActivity.class));
                    return true;
                case R.id.sleep:
                    startActivity(new Intent(FoodActivity.this, SleepActivity.class));
                    return true;
            }
            return false;
        });

        foodRecommendation1.setOnClickListener(v -> {
            String foodRecOneName = foodRecommendation1.getText().toString();
            Toast.makeText(this, foodRecOneName, Toast.LENGTH_SHORT).show();
            loadFoodOnAction(foodRecOneName);
        });

        foodRecommendation2.setOnClickListener(v -> {
            String foodRecTwoName = foodRecommendation2.getText().toString();
            Toast.makeText(this, foodRecTwoName, Toast.LENGTH_SHORT).show();
            loadFoodOnAction(foodRecTwoName);
        });

        foodRecommendation3.setOnClickListener(v -> {
            String foodRecThreeName = foodRecommendation3.getText().toString();
            Toast.makeText(this, foodRecThreeName, Toast.LENGTH_SHORT).show();
            loadFoodOnAction(foodRecThreeName);
        });

        foodSearchButton.setOnClickListener(v -> {
            String searchQuery = String.valueOf(foodSearchBox.getText());
            Log.d("SEARCH QUERY", searchQuery);
            loadFoodOnAction(searchQuery);
        });

        Data.foodItems.clear();
        foodSearchListView.setVisibility(View.INVISIBLE);
    }

    public void loadFoodRecommendations() {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        @SuppressLint("SetTextI18n") final StringRequest loadFoodRequest = new StringRequest(
        Request.Method.GET,
        baseURL + "/api/food",
        response -> {
            Log.d("RESPONSE", response);
            Gson gson = new Gson();
            JsonArray js = gson.fromJson(response, JsonArray.class);
            Log.d("FOOD REC JS", js.toString());

            for (int i = 0; i < js.size(); i++) {
                JsonObject obj = js.get(i).getAsJsonObject();
                String food = obj.get("foodName").toString().substring(1, obj.get("foodName").toString().length() - 1);
                if(i == 0) {
                    foodRecommendation1.setText(food);
                    // foodRecommendation1.setText(foodName + "\nCalories: " + cals + "\nProtein: " + protein + "\nCarbs: " + carbs + "g\nFat: " + fat + "g");
                } else if(i == 1) {
                    foodRecommendation2.setText(food);
                } else if(i == 2) {
                    foodRecommendation3.setText(food);
                }
            }
        },
        error -> {
            Log.d("~~~~ SAVE ERROR: ~~~~", error.toString());
        });
        queue.add(loadFoodRequest);
    }

    public void loadFoodOnAction(String foodName) {
        String foodNameEncoded = foodName.replace(" ", "%20");
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        @SuppressLint("SetTextI18n") final StringRequest foodRequestOnClick = new StringRequest(
            Request.Method.GET,
            baseURL + "/api/food?foodName=" + foodNameEncoded,
            response -> {
                Log.d("RESPONSE", response);
                Gson gson = new Gson();
                JsonArray js = gson.fromJson(response, JsonArray.class);
                Log.d("ON CLICK FOOD REC JS", js.toString());
                Data.foodItems.clear();
                for (int i = 0; i < js.size(); i++) {
                    JsonObject obj = js.get(i).getAsJsonObject();
                    String food = obj.get("foodName").toString();
                    float cals = (int) (Float.parseFloat(obj.get("cals").toString()) / 4.2);
                    float protein = Float.parseFloat(obj.get("protein").toString());
                    float fat = Float.parseFloat(obj.get("fat").toString());
                    float carbs = Float.parseFloat(obj.get("carbs").toString());
                    Data.foodItems.add(new FoodItem(food, cals, carbs, protein, fat, 1));
                    FoodItemAdapter foodItemAdapter = new FoodItemAdapter(this, Data.foodItems);
                    foodSearchListView.setAdapter(foodItemAdapter);
                    foodSearchListView.setVisibility(View.VISIBLE);
                }
            },
            error -> {
                Log.d("~~~~ SAVE ERROR: ~~~~", error.toString());
            });
        queue.add(foodRequestOnClick);
    }
}
