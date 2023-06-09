import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;

import com.univocity.parsers.tsv.TsvParserSettings;

import java.sql.DriverManager;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.HashMap;


import dataStructures.Food;


public class TsvParser {

    private final TsvParserSettings settings;
    private final com.univocity.parsers.tsv.TsvParser parser;
    public BufferedReader reader;
    public TsvParser(){
        this.settings = new TsvParserSettings();
        settings.setMaxCharsPerColumn(200000);
        this.parser = new com.univocity.parsers.tsv.TsvParser(settings);
    }

    //setting up reader
    public void parseTsvFile(String tsvFilePath) throws FileNotFoundException {
        //reeading tsv file
        try {
            this.reader = new BufferedReader(new FileReader(tsvFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public <Auto> void parseReader(int [] ParamColumns, ArrayList<Auto> object, String flag ){
        try{
            //reading first line from file
            String line = this.reader.readLine();
            System.out.println("COLUMN Names *******");
            //parsing the first line of the file
            String[] col_fields = this.parser.parseLine(line);
            //displaying the column names in
            for (int fields : ParamColumns) {
                System.out.print(col_fields[fields]+"\t");
            }
            System.out.println();

            //parsing the contents
            int cnt = 0;
            while ((line = reader.readLine()) != null ) {

                //parsing line with tsv
                String[] row = parser.parseLine(line);
                //Row processing
                ArrayList <String> params = new ArrayList<>();
                //filter out if name is null || non english || if row length is less
                if( row.length < ParamColumns[ParamColumns.length-1] || row[ParamColumns[0]]== null ){
                    continue;
                }
                int nullcnt = 0;
                for(int fields : ParamColumns){
                    String val = row[fields];
                    if(val == null){
                        nullcnt +=1;
                    }
                    params.add(val);
                }

                if ( (row[ParamColumns[3]]== null||  row[ParamColumns[3]].isEmpty())|| nullcnt > ParamColumns.length/2){
                    continue;
                }

                //IF parsing food
                if(flag.equals("food")){
                    Food food = new Food(params);
//                    System.out.print(cnt + " ");
//                    System.out.println(food.toString());
                    object.add((Auto) food);

                }

                cnt +=1;
//                System.out.println(); // Move to the next line
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {

        //data files used to populate db
        String tsvFoodFile = "data-files/en.openfoodfacts.org.products.tsv";



        TsvParser tsvParser = new TsvParser();
        try {
            //created a reader for the tsv file
            tsvParser.parseTsvFile(tsvFoodFile);
            //Arraylist of food
            ArrayList<Food> foodAryList = new ArrayList<>();
            //parsing the food reader
            tsvParser.parseReader(Food.tsvParams, foodAryList, "food" );
            //food list
            System.out.println(foodAryList.size());


            // prebuild sql statement add it all together
            // use BatchInsert Technique
            System.out.println("Adding data to SQL...(ADDING TO TEST TABLE)");

            Connection connection = null;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/healthAppdb?autoReconnect=true&useSSL=false",
                        "root", "dexter5521");
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            }

            PreparedStatement stmt = null;
            String sqlInsertRecord = null;
            int[] iNoRows = null;
            sqlInsertRecord = "INSERT INTO Food (FoodName, ServingSize, Calories, Carbs, Protein, Fat, Sodium, Rating, NumVote) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";;
            try {
                connection.setAutoCommit(false);
                stmt = connection.prepareStatement(sqlInsertRecord);

                for ( Food fooditem : foodAryList) {

                    System.out.print("ADDING : "+ fooditem.toString());
                    stmt.setString(1, fooditem.getProductName() );
                    System.out.println("*");
                    stmt.setString(2, fooditem.getServingSize());
                    System.out.println("*");
                    stmt.setFloat(3, Float.parseFloat(fooditem.getEnergy100g()));
                    System.out.println("*");
                    stmt.setFloat(4, Float.parseFloat(fooditem.getCarbohydrates100g()));
                    System.out.println("*");
                    stmt.setFloat(5, Float.parseFloat(fooditem.getProteins100g()));
                    System.out.println("*");
                    stmt.setFloat(6, Float.parseFloat(fooditem.getFat100g()));
                    System.out.println("*");
                    stmt.setFloat(7, Float.parseFloat(fooditem.getSodium100g()));
                    System.out.println("*");
                    stmt.setString(8, "0");
                    System.out.println("*");
                    stmt.setInt(9, 0);
                    System.out.println("*");
                    stmt.addBatch();

                }

                iNoRows = stmt.executeBatch();
                connection.commit();
                System.out.println("Done");
            }
            catch (Exception e) {
                System.out.println("Exception: " + e);
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }





}




