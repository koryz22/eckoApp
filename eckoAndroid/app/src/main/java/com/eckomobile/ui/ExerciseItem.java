package com.eckomobile.ui;

public class ExerciseItem {
    private String name, time;
    private float calories;

    public ExerciseItem(String exercise, float exerciseCalories, String exerciseTime) {
        name = exercise;
        calories = exerciseCalories;
        time = exerciseTime;
    }

    public String getName() {
        return name;
    }

    public float getCalories() {
        return calories;
    }

    public String getTime() {
        return time;
    }
}

