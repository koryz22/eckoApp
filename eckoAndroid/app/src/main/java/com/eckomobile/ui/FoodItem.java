package com.eckomobile.ui;

public class FoodItem {
    private String name;
    private int calories, carbs, protein, fat, servingSize;

    public FoodItem(String foodName, int foodCalories, int foodCarbs, int foodProtein, int foodFat, int foodServingSize) {
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

    public int getCalories() {
        return calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getServingSize() {
        return servingSize;
    }
}