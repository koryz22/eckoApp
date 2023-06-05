package com.eckomobile.ui;

public class HomeItem {
    private String date;
    private int lifestyleScore, foodScore, exerciseScore, sleepScore;
    private int lifestyleScoreImageID, foodScoreImageID, exerciseScoreImageID, sleepScoreImageID;

    public HomeItem(String dateString, int lifestyleScoreNum, int lifestyleScoreImgID, int foodScoreNum, int foodScoreImgID, int exerciseScoreNum, int exerciseScoreImgID, int sleepScoreNum, int sleepScoreImgID) {
        date = dateString;
        lifestyleScore = lifestyleScoreNum;
        lifestyleScoreImageID = lifestyleScoreImgID;
        foodScore = foodScoreNum;
        foodScoreImageID = foodScoreImgID;
        exerciseScore = exerciseScoreNum;
        exerciseScoreImageID = exerciseScoreImgID;
        sleepScore = sleepScoreNum;
        sleepScoreImageID = sleepScoreImgID;
    }

    public String getDate() {
        return date;
    }

    public int getLifestyleScore() {
        return lifestyleScore;
    }

    public int getLifestyleScoreImageID() {
        return lifestyleScoreImageID;
    }

    public int getFoodScore() {
        return foodScore;
    }

    public int getFoodScoreImageID() {
        return foodScoreImageID;
    }

    public int getExerciseScore() {
        return exerciseScore;
    }

    public int getExerciseScoreImageID() {
        return exerciseScoreImageID;
    }

    public int getSleepScore() {
        return sleepScore;
    }

    public int getSleepScoreImageID() {
        return sleepScoreImageID;
    }
}
