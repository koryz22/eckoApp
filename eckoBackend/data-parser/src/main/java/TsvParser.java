import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.univocity.parsers.tsv.TsvParserSettings;


public class TsvParser {

    private final TsvParserSettings settings;
    private final com.univocity.parsers.tsv.TsvParser parser;
    public final Set<String> allergySet = new HashSet<>();

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

                if (nullcnt > ParamColumns.length/2){
                    continue;
                }

                //IF parsing food
                if(flag.equals("food")){
                    Food food = new Food(params);
                    String allergies = food.getTracesEn();
                    //Handling allergies
                    if (allergies !=null &&!allergies.isEmpty() ){
                        String[] allergyList = food.getTracesEn().split(",");
                        for(String allergy : allergyList){
                            if (allergy.length()>=3 && allergy.charAt(2) == (':') || !allergy.matches("^[a-zA-Z]+$")){
                            }else{
                                this.allergySet.add(allergy);
                            }
                        }
                    }
                    System.out.print(cnt + " ");
                    System.out.println(food.toString());
                    object.add((Auto) food);

                }
                cnt +=1;
                System.out.println(); // Move to the next line

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        String tsvFoodFile = "data-files/en.openfoodfacts.org.products.tsv";
        TsvParser tsvParser = new TsvParser();
        try {
            //created a reader for the tsv file
            tsvParser.parseTsvFile(tsvFoodFile);

            //Arraylist of food
            ArrayList<Food> foodAryList = new ArrayList<>();
            //parsing the food reader
            tsvParser.parseReader(Food.tsvParams, foodAryList, "food" );
            // at the end should have allergy list and foodAry list
            System.out.println(tsvParser.allergySet);
            //food list
            System.out.println(foodAryList.size());


//        // prebuild sql statement add it all together
//        // use BatchInsert Technique
//        System.out.println("Adding data to SQL...(ADDING TO TEST TABLE)");
//        Connection connection = null;
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:mysql:///moviedb?autoReconnect=true&useSSL=false",
//                    "mytestuser", "My6$Password");
//        } catch (Exception e) {
//            System.out.println("Exception: " + e);
//        }
//
//        PreparedStatement psInsertRecord = null;
//        String sqlInsertRecord = null;
//        int[] iNoRows = null;
//        sqlInsertRecord = "CALL add_star_test_version(?, ?)";
//        try {
//            connection.setAutoCommit(false);
//            psInsertRecord = connection.prepareStatement(sqlInsertRecord);
//
//            for (Map.Entry<String, Actor> entry : actorsMap.entrySet()) {
//                String key = entry.getKey();
//                String value = entry.getValue().getDob();
//                psInsertRecord.setString(1, key);
//                psInsertRecord.setString(2, value);
//                psInsertRecord.addBatch();
//            }
//
//            iNoRows = psInsertRecord.executeBatch();
//            connection.commit();
//            System.out.println("Done");
//        }
//        catch (Exception e) {
//            System.out.println("Exception: " + e);
//        }







        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
