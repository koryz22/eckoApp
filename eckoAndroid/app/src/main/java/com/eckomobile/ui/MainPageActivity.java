package com.eckomobile.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.eckomobile.R;
import com.eckomobile.data.NetworkManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;


public class MainPageActivity extends AppCompatActivity {
    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "eckoBackend_war";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;

    private int userId;

    BottomNavigationView bottomNavigationViewHome;
    ListView listViewHome;
    ImageView profileButton;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.userId = getIntent().getIntExtra("UserId",0);
        setContentView(R.layout.activity_main_page);
//        check if today's record is created
        check_record();

//        display updated record
        display_records();

        bottomNavigationViewHome = findViewById(R.id.bottomNavigationViewHome);
        listViewHome = findViewById(R.id.listViewHome);
        profileButton = findViewById(R.id.profileButton);
        bottomNavigationViewHome.setSelectedItemId(R.id.home);
        bottomNavigationViewHome.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.home:
                    return true;
                case R.id.food:
                    startActivity(new Intent(MainPageActivity.this, FoodActivity.class));
                    return true;
                case R.id.exercise:
                    startActivity(new Intent(MainPageActivity.this, ExerciseActivity.class));
                    return true;
                case R.id.sleep:
                    startActivity(new Intent(MainPageActivity.this, SleepActivity.class));
                    return true;
            }

            return false;
        });
        profileButton.setOnClickListener(v -> startActivity(new Intent(MainPageActivity.this, ProfileActivity.class)));
        HomeItemAdapter homeItemAdapter = new HomeItemAdapter(this, Data.homeItems);
        listViewHome.setAdapter(homeItemAdapter);
    }
    @SuppressLint("SetTextI18n")
    public void display_records() {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final StringRequest recordsRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/mainPage?UserId=100000008",
                response -> {
                    Log.d("~~~~ Main Page record: ~~~~", response);
                    Log.d("USER ID: ", Integer.toString(this.userId) );

                    Gson gson = new Gson();
                    JsonArray js = gson.fromJson(response, JsonArray.class);
                    Log.d("JS", js.toString());

                    Data.homeItems.clear();
                    for (int i = 0; i < js.size(); i++) {
                        JsonObject obj = js.get(i).getAsJsonObject();
                        String date = obj.get("date").toString();
                        date = date.substring(1, date.length() - 1);
                        int ls_score = obj.get("ls_score").getAsInt();
                        int food_score = obj.get("food_score").getAsInt();
                        int exercise_score = obj.get("exercise_score").getAsInt();
                        int sleep_score = obj.get("sleep_score").getAsInt();
                        Data.homeItems.add(new HomeItem(date, ls_score, R.drawable.lifestyle4, food_score, R.drawable.food7, exercise_score, R.drawable.exercise5, sleep_score, R.drawable.sleep3));
                    }
                },
                error -> {
                    Log.d("~~~~ LOGIN ERROR: ~~~~", error.toString());
                });

        queue.add(recordsRequest);
    }

    public void check_record() {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final StringRequest addTodaysDateRequest = new StringRequest(
                Request.Method.POST,
                baseURL + "/api/mainPage",
                response -> {
                    Log.d("~~~~ Main Page post: ~~~~", response);
                    Log.d("USER ID: ", Integer.toString(this.userId) );
                },
                error -> {
                    Log.d("~~~~ LOGIN ERROR: ~~~~", error.toString());
                });
        queue.add(addTodaysDateRequest);
    }
}