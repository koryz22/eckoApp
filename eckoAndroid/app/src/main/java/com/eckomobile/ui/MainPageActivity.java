package com.eckomobile.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.eckomobile.R;


public class MainPageActivity extends AppCompatActivity {
    private EditText query;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set layout view
        setContentView(R.layout.activity_main_page);

//        this.button = findViewById(R.id.searchButton);
//        this.query = findViewById(R.id.search);

        // assign button listener to start querying
        button.setOnClickListener(view -> handleMovieSearch());
    }

    @SuppressLint("SetTextI18n")
    public void handleMovieSearch() {
        Log.d("Going to movie list activity", query.getText().toString());

        // initialize the activity(page)/destination
        Intent movieListActivity = new Intent(MainPageActivity.this, MovieListActivity.class);
        movieListActivity.putExtra("query", query.getText().toString());
        startActivity(movieListActivity);

    }
}