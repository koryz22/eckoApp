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
//                case R.id.food:
//                    startActivity(new Intent(MainPageActivity.this, FoodActivity.class));
//                    return true;
//                case R.id.sleep:
//                    startActivity(new Intent(MainPageActivity.this, SleepActivity.class));
//                    return true;
//                case R.id.exercise:
//                    startActivity(new Intent(MainPageActivity.this, ExerciseActivity.class));
//                    return true;
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

                    for (int i = 0; i < js.size(); i++) {
                        JsonObject obj = js.get(i).getAsJsonObject();

//                        JsonElement property1 = object.get("property1");
//                        JsonElement property2 = object.get("property2");
//                      add to data like this based on json obj
//                    Data.homeItems.add(new HomeItem("6/3/2023", 80, R.drawable.lifestyle1, 82, R.drawable.food2, 78, R.drawable.exercise2, 80, R.drawable.sleep1));
//
                    }
                    // loop through array

                },
                error -> {
                    Log.d("~~~~ LOGIN ERROR: ~~~~", error.toString());
                });

        queue.add(recordsRequest);
    }





}