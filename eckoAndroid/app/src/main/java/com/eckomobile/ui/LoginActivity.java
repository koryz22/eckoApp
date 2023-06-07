package com.eckomobile.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.eckomobile.databinding.ActivityLoginBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.eckomobile.data.NetworkManager;
import com.eckomobile.R;


import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private int userId;
    private final String host = "10.0.2.2";
    private final String port = "8080";
    private final String domain = "eckoBackend_war";
    private final String baseURL = "http://" + host + ":" + port + "/" + domain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        // upon creation, inflate and initialize the layout
        setContentView(binding.getRoot());

        username = binding.username;
        password = binding.password;
        final Button loginButton = binding.login;

        //assign a listener to call a function to handle the user request when clicking a button
        loginButton.setOnClickListener(view -> login());
    }

    @SuppressLint("SetTextI18n")
    public void login() {
        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
        final StringRequest loginRequest = new StringRequest(
                Request.Method.POST,
                baseURL + "/api/login",
                response -> {
                    Log.d("~~~~ LOGIN SUCCESS: ~~~~", response);
                    Log.d("USER", username.getText().toString());
                    Log.d("pass", password.getText().toString());

                    Gson gson = new Gson();
                    JsonObject js = gson.fromJson(response, JsonObject.class);
                    String status = js.get("message").getAsString();
                    this.userId = js.get("UserId").getAsInt();
                    Log.d("ID", Integer.toString(this.userId));

                    if (status.equals("success")){
                        finish();
                        Intent MainPageActivity = new Intent(LoginActivity.this, MainPageActivity.class);
                        MainPageActivity.putExtra("UserId", this.userId);
                        startActivity(MainPageActivity);
                    } else {
                        Log.d("~~~~ LOGIN ERROR: ~~~~", status);
                    }
                },
                error -> {
                    // error
                    Log.d("~~~~ LOGIN ERROR: ~~~~", error.toString());
                }) {
            @Override
            protected Map<String, String> getParams() {
                final Map<String, String> params = new HashMap<>();
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        queue.add(loginRequest);
    }
}