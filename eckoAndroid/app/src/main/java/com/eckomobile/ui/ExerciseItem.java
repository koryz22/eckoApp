package com.eckomobile.ui;

public class ExerciseItem {
    private String name, time;
    private int calories;

    public ExerciseItem(String exercise, int exerciseCalories, String exerciseTime) {
        name = exercise;
        calories = exerciseCalories;
        time = exerciseTime;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public String getTime() {
        return time;
    }
}

