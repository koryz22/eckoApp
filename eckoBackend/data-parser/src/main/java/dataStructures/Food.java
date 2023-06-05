package dataStructures;

import java.util.ArrayList;

public class Food {

    /* THIS IS IMPORTANT
    THE NUMBERS REPRESENT THE INDEX NUMBERS FOR FIELDS
    DATA FIlE we using "data-files/en.openfoodfacts.org.products.tsv"
    product_name
    traces_en
    serving_size
    nutrition_grade_fr
    energy_100g
    fat_100g
    carbohydrates_100g
    proteins_100g
    sodium_100g
     */
    public static int [] tsvParams =  {7,40,63,65,101,112,117};
    private final String product_name;
    private final String serving_size;
    private final String energy_100g;
    private final String fat_100g;
    private final String carbohydrates_100g;
    private final String proteins_100g;
    private final String sodium_100g;

    private final String Rating = "0";
    private final String Num_vote = "0";

    public Food(ArrayList<String> params) {
        if (params.size() != 7) {
            throw new IllegalArgumentException("Invalid number of parameters");
        }
        this.product_name = (String) params.get(0);
        this.serving_size = (String) params.get(1);
        this.energy_100g = (String) params.get(2);
        this.fat_100g = (String) params.get(3);
        this.carbohydrates_100g = (String) params.get(4);
        this.proteins_100g = (String) params.get(5);
        this.sodium_100g = (String) params.get(6);
    }


    public int [] getTsvParams(){
        return tsvParams;
    }
    public String getProductName() {
        return this.product_name;
    }


    public String getServingSize() {
        return this.serving_size;
    }


    public String getEnergy100g() {
        return this.energy_100g;
    }

    public String getFat100g() {
        return this.fat_100g;
    }

    public String getCarbohydrates100g() {
        return this.carbohydrates_100g;
    }

    public String getProteins100g() {
        return this.proteins_100g;
    }

    public String getSodium100g() {
        return this.sodium_100g;
    }



    @Override
    public String toString() {
        return "Product Name: " + product_name +
                "\nServing Size: " + serving_size +
                "\nEnergy (per 100g): " + energy_100g +
                "\nFat (per 100g): " + fat_100g +
                "\nCarbohydrates (per 100g): " + carbohydrates_100g +
                "\nProteins (per 100g): " + proteins_100g +
                "\nSodium (per 100g): " + sodium_100g;
    }


}