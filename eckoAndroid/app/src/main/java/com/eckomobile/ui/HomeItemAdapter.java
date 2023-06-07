package com.eckomobile.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.eckomobile.R;
import com.eckomobile.ui.HomeItem;

import java.util.ArrayList;

public class HomeItemAdapter extends ArrayAdapter<HomeItem> {
    public HomeItemAdapter(Context context, ArrayList<HomeItem> arrayList) {
        super(context, 0, arrayList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View currentItemView = convertView;

        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.main_list_view, parent, false);
        }

        HomeItem currentPosition = getItem(position);

        TextView date = currentItemView.findViewById(R.id.date);
        date.setText(currentPosition.getDate());

        TextView lifestyleScore = currentItemView.findViewById(R.id.lifestyleScore);
        lifestyleScore.setText(String.valueOf(currentPosition.getLifestyleScore()));
        ImageView lifestyleScoreImage = currentItemView.findViewById(R.id.lifestyleScoreImage);
        lifestyleScoreImage.setImageResource(currentPosition.getLifestyleScoreImageID());

        TextView foodScore = currentItemView.findViewById(R.id.foodScore);
        foodScore.setText(String.valueOf(currentPosition.getFoodScore()));
        ImageView foodScoreImage = currentItemView.findViewById(R.id.foodScoreImage);
        foodScoreImage.setImageResource(currentPosition.getFoodScoreImageID());

        TextView exerciseScore = currentItemView.findViewById(R.id.exerciseScore);
        exerciseScore.setText(String.valueOf(currentPosition.getExerciseScore()));
        ImageView exerciseScoreImage = currentItemView.findViewById(R.id.exerciseScoreImage);
        exerciseScoreImage.setImageResource(currentPosition.getExerciseScoreImageID());

        TextView sleepScore = currentItemView.findViewById(R.id.sleepScore);
        sleepScore.setText(String.valueOf(currentPosition.getSleepScore()));
        ImageView sleepScoreImage = currentItemView.findViewById(R.id.sleepScoreImage);
        sleepScoreImage.setImageResource(currentPosition.getSleepScoreImageID());

        return currentItemView;
    }
}

