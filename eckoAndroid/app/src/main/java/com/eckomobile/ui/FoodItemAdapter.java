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

public class FoodItemAdapter extends ArrayAdapter<FoodItem> {
    public FoodItemAdapter(Context context, ArrayList<FoodItem> arrayList) {
        super(context, 0, arrayList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.food_list_view, parent, false);
        }

        FoodItem currentPosition = getItem(position);

        TextView foodName = currentItemView.findViewById(R.id.foodName);
        foodName.setText(currentPosition.getName());

        TextView foodCalories = currentItemView.findViewById(R.id.foodCalories);
        foodCalories.setText(String.valueOf(currentPosition.getCalories()));

        TextView foodCarbs = currentItemView.findViewById(R.id.foodCarbs);
        foodCarbs.setText(String.valueOf(currentPosition.getCarbs()));

        TextView foodProtein = currentItemView.findViewById(R.id.foodProtein);
        foodProtein.setText(String.valueOf(currentPosition.getProtein()));

        TextView foodFat = currentItemView.findViewById(R.id.foodFat);
        foodFat.setText(String.valueOf(currentPosition.getFat()));

        TextView foodServingSize = currentItemView.findViewById(R.id.foodServingSize);
        foodServingSize.setText(String.valueOf(currentPosition.getServingSize()));

        ImageView foodRatingOne = currentItemView.findViewById(R.id.foodRatingOne);
        foodRatingOne.setOnClickListener(v -> {
            // Log the selection

            Toast.makeText(getContext(), "Selected 1", Toast.LENGTH_SHORT).show();
        });

        ImageView foodRatingTwo = currentItemView.findViewById(R.id.foodRatingTwo);
        foodRatingTwo.setOnClickListener(v -> {
            // Log the selection

            Toast.makeText(getContext(), "Selected 2", Toast.LENGTH_SHORT).show();
        });

        ImageView foodRatingThree = currentItemView.findViewById(R.id.foodRatingThree);
        foodRatingThree.setOnClickListener(v -> {
            // Log the selection

            Toast.makeText(getContext(), "Selected 3", Toast.LENGTH_SHORT).show();
        });

        return currentItemView;
    }
}
