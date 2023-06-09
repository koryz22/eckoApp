package com.eckomobile.ui;

import java.util.ArrayList;

public class Data {
    public static String username = "";
    public static String password = "";
    public static String gender = "";
    public static String age = "";
    public static String height = "";
    public static String weight = "";
    public static String primaryGoal = "";
    public static String foodPreference = "";
    public static String fitnessLevel = "";
    public static String sleepHour = "";

    public static final String[] primaryGoals = {
            "Lose weight",
            "Gain weight",
            "Maintain weight"
    };

    public static final String[] foodPreferences = {
            "Atkins (high protein) diet",
            "Low protein",
            "Keto (high fat) diet",
            "Low fat",
            "High calories",
            "Low calories"
    };

    public static final String[] fitnessLevels = {
            "Light intensity",
            "Moderate intensity",
            "Rigorous intensity"
    };

    public static final String[] sleepHours = {
            "3 hours",
            "4 hours",
            "5 hours",
            "6 hours",
            "7 hours",
            "8 hours",
            "9 hours",
            "10 hours",
            "11 hours",
            "12 hours"
    };
    public static ArrayList<HomeItem> homeItems = new ArrayList<>();
    public static ArrayList<FoodItem> foodItems = new ArrayList<>();
    public static ArrayList<ExerciseItem> exerciseItems = new ArrayList<>();
}

