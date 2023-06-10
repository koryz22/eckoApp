package com.eckomobile.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.HashMap;
import java.util.Map;

public class SleepActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewSleep;
    TimePicker timePicker;
    TextView sleepByTime;
    Button sleepButton;

    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "eckoBackend_war";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        bottomNavigationViewSleep = findViewById(R.id.bottomNavigationViewSleep);
        timePicker = findViewById(R.id.timePicker);
        sleepByTime = findViewById(R.id.sleepByTime);
        sleepButton = findViewById(R.id.sleepButton);

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

            int hour = hourOfDay;
            int min = minute;
            sleepGoal(hour,min);

        });

        sleepButton.setOnClickListener(v -> {
            Log.d("Clicked", "Sleep Button");
            sleepRecord();
        });
    }

    public void sleepGoal(int hour, int min ) {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final StringRequest sleepRequest = new StringRequest(
                Request.Method.GET,
                baseURL + "/api/sleepLog",
                response -> {
                    Log.d("~~~~ Sleep log GET: ~~~~", String.valueOf(hour) +" : "+ String.valueOf(min));
                    int hr = hour;

//                    grabbign sleep goal
                    Gson gson = new Gson();
                    JsonObject jo = gson.fromJson(response, JsonObject.class);
                    String sgoals = jo.get("sleepGoal").toString();
                    Log.d("~~~~ Sleep goal: ~~~~", sgoals);

                    Log.d("~~~~ Sleep goal: ~~~~", sgoals.substring(1,2));
//                    parsing to am pm format
                    if (sgoals.length() == 9) {
                        hr -= Integer.parseInt(sgoals.substring(1, 2));
                    } else {
                        hr -= Integer.parseInt(sgoals.substring(1, 3));
                    }

                    boolean hourAfter = false;
                    if(hr < 0){
                        hr = 24 + hr;
                    }
                    if (hr > 12) {
                        hourAfter = true;
                        hr -= 12;
                    }

                    boolean minuteBefore = false;
                    if (min < 10) {
                        minuteBefore = true;
                    }

                    String selectedTime = hr + ":";

                    if (minuteBefore) {
                        selectedTime += "0" + min;
                    }
                    else {
                        selectedTime += min;
                    }

                    if (hourAfter) {
                        selectedTime += " PM";
                    }
                    else {
                        selectedTime += " AM";
                    }

                    sleepByTime.setText(selectedTime);
                    sleepByTime.setVisibility(View.VISIBLE);
                },
                error -> {
                    Log.d("~~~~ SLEEP ERROR: ~~~~", error.toString());
                });

        queue.add(sleepRequest);

    }

    public void sleepRecord() {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final StringRequest recRequest = new StringRequest(
                Request.Method.POST,
                baseURL + "/api/sleepLog",
                response -> {
                    Log.d("~~~~ Sleep log post: ~~~~", sleepByTime.getText().toString());

                },
                error -> {
                    Log.d("~~~~ SLEEP ERROR: ~~~~", error.toString());
                }){
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("bedTime", sleepByTime.getText().toString());
                return params;
            }
        };
        queue.add(recRequest);
    }


}