package dataStructures;

import java.util.ArrayList;

public class Exercise {
    /* THIS IS IMPORTANT
    THE NUMBERS REPRESENT THE INDEX NUMBERS FOR FIELDS
    DATA FIlE we are using "data-files/exercise_dataset.csv"
    Exercise_name = 0
    cal per kg 5
     */

    public static int [] csvParams =  {0,5};

    private final String exercise_name;

    private final String calBurned_kg;

    private final String Rating = "0";
    private final String Num_vote = "0";


    public Exercise(ArrayList<String> params){
        if(params.size()!=2){
            throw new IllegalArgumentException("invalid number of param");
        }
        this.exercise_name = (String) params.get(0);
        this.calBurned_kg  = (String) params.get(1);

    }
    public String getExerciseName() {
        return exercise_name;
    }

    public String getCalBurnedPerKg() {
        return calBurned_kg;
    }

    public String getRating() {
        return Rating;
    }

    public String getNumVotes() {
        return Num_vote;
    }

    @Override
    public String toString() {
        return "Exercise Name: " + exercise_name +
                "\nCalories Burned per kg: " + calBurned_kg +
                "\nRating: " + Rating +
                "\nNumber of Votes: " + Num_vote;
    }




}
