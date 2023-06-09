package com.eckomobile.ui;

public class FoodItem {
    private String name;
    private float calories, carbs, protein, fat;
    private int servingSize;

    public FoodItem(String foodName, float foodCalories, float foodCarbs, float foodProtein, float foodFat, int foodServingSize) {
        name = foodName;
        calories = foodCalories;
        carbs = foodCarbs;
        protein = foodProtein;
        fat = foodFat;
        servingSize = foodServingSize;
    }

    public String getName() {
        return name;
    }

    public float getCalories() {
        return calories;
    }

    public float getCarbs() {
        return carbs;
    }

    public float getProtein() {
        return protein;
    }

    public float getFat() {
        return fat;
    }

    public int getServingSize() {
        return servingSize;
    }
}
