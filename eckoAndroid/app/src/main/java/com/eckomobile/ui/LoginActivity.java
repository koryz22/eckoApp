package com.eckomobile.ui;


import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.eckomobile.R;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button login, create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        create = findViewById(R.id.create);

        login.setOnClickListener(v -> {
            // Check the login credentials here

            // Add items to the "Data.homeItems" array-list (to display items in the "Home" page)

            /* Temporary Data (for testing) */
            Data.homeItems.add(new HomeItem("6/3/2023", 80, R.drawable.lifestyle1, 82, R.drawable.food2, 78, R.drawable.exercise2, 80, R.drawable.sleep1));
            Data.homeItems.add(new HomeItem("6/2/2023", 70, R.drawable.lifestyle2, 72, R.drawable.food4, 88, R.drawable.exercise5, 70, R.drawable.sleep4));
            Data.homeItems.add(new HomeItem("6/1/2023", 85, R.drawable.lifestyle6, 62, R.drawable.food3, 68, R.drawable.exercise1, 60, R.drawable.sleep2));
            Data.homeItems.add(new HomeItem("6/5/2023", 75, R.drawable.lifestyle4, 89, R.drawable.food1, 72, R.drawable.exercise6, 76, R.drawable.sleep5));
            Data.homeItems.add(new HomeItem("6/6/2023", 90, R.drawable.lifestyle3, 64, R.drawable.food3, 69, R.drawable.exercise4, 82, R.drawable.sleep7));

            startActivity(new Intent(LoginActivity.this, MainPageActivity.class));
        });

        create.setOnClickListener(v -> {
            // Create an account

            Data.username = String.valueOf(username.getText());
            Data.password = String.valueOf(password.getText());
            startActivity(new Intent(LoginActivity.this, MainPageActivity.class));
        });
    }
}

//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.StringRequest;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.eckomobile.data.NetworkManager;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class LoginActivity extends AppCompatActivity {
//
//    private EditText username;
//    private EditText password;
//    private TextView message;
//    private final String host = "10.0.2.2";
//    private final String port = "8080";
//    private final String domain = "eckoBackend_war";
//    private final String baseURL = "http://" + host + ":" + port + "/" + domain;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
////        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
////        // upon creation, inflate and initialize the layout
////        setContentView(binding.getRoot());
////
////        username = binding.username;
////        password = binding.password;
////        message = binding.message;
////        final Button loginButton = binding.login;
//
//        //assign a listener to call a function to handle the user request when clicking a button
////        loginButton.setOnClickListener(view -> login());
//    }
//
//    @SuppressLint("SetTextI18n")
//    public void login() {
//        message.setText("Logging in...");
//        // use the same network queue across our application
//        final RequestQueue queue = NetworkManager.sharedManager(this).queue;
//        // request type is POST
//        final StringRequest loginRequest = new StringRequest(
//                // Grab post request from post method
//                Request.Method.POST,
//                baseURL + "/api/login",
//                response -> {
//                    // TODO: should parse the json response to redirect to appropriate functions
//                    //  upon different response value.
//                    Log.d("~~~~ LOGIN SUCCESS: ~~~~", response);
//                    Log.d("USER", username.toString());
//                    Log.d("pass", password.toString());
//
//                    Gson gson = new Gson();
//                    JsonObject js = gson.fromJson(response, JsonObject.class);
//                    String status = js.get("status").getAsString();
//                    Log.d("status", status);
//
//                    if (status.equals("success")){
//                        //Complete and destroy login activity once successful
//                        finish();
//                        // initialize the activity(page)/destination
//                        Intent MainPageActivity = new Intent(LoginActivity.this, com.eckomobile.ui.MainPageActivity.class);
//                        // activate the list page.
//                        startActivity(MainPageActivity);
//                    } else {
//                        Log.d("~~~~ LOGIN ERROR: ~~~~", status);
//                        message.setText("Incorrect Username/Password");
//                    }
//                },
//                error -> {
//                    // error
//                    Log.d("~~~~ LOGIN ERROR: ~~~~", error.toString());
//                }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // POST request form data
//                final Map<String, String> params = new HashMap<>();
//                params.put("username", username.getText().toString());
//                params.put("password", password.getText().toString());
//                params.put("android", "true");
//
//                return params;
//            }
//        };
//        // important: queue.add is where the login request is actually sent
//        queue.add(loginRequest);
//    }
//}