package com.eckomobile.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.eckomobile.R;

import java.util.ArrayList;

public class ExerciseItemAdapter extends ArrayAdapter<ExerciseItem> {
    public ExerciseItemAdapter(Context context, ArrayList<ExerciseItem> arrayList) {
        super(context, 0, arrayList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_list_view, parent, false);
        }

        ExerciseItem currentPosition = getItem(position);

        TextView exerciseName = currentItemView.findViewById(R.id.exerciseName);
        exerciseName.setText(currentPosition.getName());

        TextView exerciseCalories = currentItemView.findViewById(R.id.exerciseCalories);
        exerciseCalories.setText(String.valueOf(currentPosition.getCalories()));

        TextView exerciseTime = currentItemView.findViewById(R.id.exerciseTime);
        exerciseTime.setText(currentPosition.getTime());

        ImageView exerciseRatingOne = currentItemView.findViewById(R.id.exerciseRatingOne);
        exerciseRatingOne.setOnClickListener(v -> {
            // Log the selection

            Toast.makeText(getContext(), "Selected 1", Toast.LENGTH_SHORT).show();
        });

        ImageView exerciseRatingTwo = currentItemView.findViewById(R.id.exerciseRatingTwo);
        exerciseRatingTwo.setOnClickListener(v -> {
            // Log the selection

            Toast.makeText(getContext(), "Selected 2", Toast.LENGTH_SHORT).show();
        });

        ImageView exerciseRatingThree = currentItemView.findViewById(R.id.exerciseRatingThree);
        exerciseRatingThree.setOnClickListener(v -> {
            // Log the selection

            Toast.makeText(getContext(), "Selected 3", Toast.LENGTH_SHORT).show();
        });

        return currentItemView;
    }
}

