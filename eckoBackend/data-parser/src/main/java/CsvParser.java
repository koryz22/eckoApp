import com.univocity.parsers.csv.CsvParserSettings;
import dataStructures.Exercise;
import dataStructures.Food;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;


public class CsvParser {

    private final CsvParserSettings settings;
    private final com.univocity.parsers.csv.CsvParser parser;
    public BufferedReader reader;
    public CsvParser(){
        this.settings = new CsvParserSettings();
        settings.setMaxCharsPerColumn(200000);
        this.parser = new com.univocity.parsers.csv.CsvParser(settings);
    }

    //setting up reader
    public void parseCsvFile(String csvFilePath) throws FileNotFoundException {
        //reeading csv file
        try {
            this.reader = new BufferedReader(new FileReader(csvFilePath));
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

                //parsing line with csv
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



                //IF parsing food
                if(flag.equals("exercise")){
                    Exercise exercise = new Exercise(params);
//                    System.out.print(cnt + " ");
//                    System.out.println(exercise.toString());
                    object.add((Auto) exercise);

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

        String csvExerciseFile = "data-files/exercise_dataset.csv";


        CsvParser csvParser = new CsvParser();

        try {
            //created a reader for the csv file exercise
            csvParser.parseCsvFile(csvExerciseFile);
            //Arraylist of Exercises
            ArrayList<Exercise> exerciseArrayList = new ArrayList<>();
            //parsing the Exercise reader
            csvParser.parseReader(Exercise.csvParams, exerciseArrayList, "exercise" );
            //exercise list
            System.out.println(exerciseArrayList.size());


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
            sqlInsertRecord = "INSERT INTO Exercise (ExerciseName, CaloriesBurned, Rating, NumVotes) VALUES (?, ?, ?, ?)";
            try {
                connection.setAutoCommit(false);
                stmt = connection.prepareStatement(sqlInsertRecord);

                for ( Exercise exerciseItems : exerciseArrayList) {

                    System.out.print("ADDING : "+ exerciseItems.toString());
                    stmt.setString(1, exerciseItems.getExerciseName());
                    System.out.println("*");
                    stmt.setFloat(2, Float.parseFloat(exerciseItems.getCalBurnedPerKg()));
                    System.out.println("*");
                    stmt.setFloat(3, 0);
                    System.out.println("*");
                    stmt.setInt(4, 0);
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




