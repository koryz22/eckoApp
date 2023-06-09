import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "ExerciseServlet", urlPatterns = "/api/exercise")
public class ExerciseServlet extends HttpServlet {
    private static DataSource dataSource;

    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/healthAppdb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("EXERCISE SERVLET GET CALLED");
        String exerciseQuery = request.getParameter("exerciseName");
        System.out.println("1");

        HttpSession session = request.getSession();
        System.out.println("1");

        User user = (User) session.getAttribute("user");
        System.out.println("1");

        int user_id = user.getUserid();
        System.out.println("1");

        String primaryGoal = (String) session.getAttribute("primaryGoal");
        System.out.println("1");

        String exerciseGoal = (String) session.getAttribute("exerciseGoal");
        System.out.println("1");

        String weight = (String) session.getAttribute("weight");
        System.out.println("1");


        System.out.println("primary goal: " + primaryGoal);
        System.out.println("e goal: " + exerciseGoal);

        try (Connection conn = dataSource.getConnection()) {
            JsonArray jsonArray = new JsonArray();
            String query = "";
            if(exerciseQuery == null || exerciseQuery.isEmpty()) {
                if (primaryGoal.equals("Lose weight")) {
                    if (exerciseGoal.equals("Light intensity")) {
                        query = "SELECT * from exercise ORDER BY CaloriesBurned LIMIT 3 OFFSET 120;";
                    } else if (exerciseGoal.equals("Moderate intensity")) {
                        query = "SELECT * from exercise ORDER BY CaloriesBurned DESC LIMIT 3 OFFSET 20;";
                    } else if (exerciseGoal.equals("Rigorous intensity")) {
                        query = "SELECT * from exercise ORDER BY CaloriesBurned DESC LIMIT 3;";
                    }
                } else if  (primaryGoal.equals("Maintain weight")) {
                    if (exerciseGoal.equals("Light intensity")) {
                        query = "SELECT * from exercise ORDER BY CaloriesBurned DESC LIMIT 3 OFFSET 100;";
                    } else if (exerciseGoal.equals("Moderate intensity")) {
                        query = "SELECT * from exercise ORDER BY CaloriesBurned DESC LIMIT 3 OFFSET 75;";
                    } else if (exerciseGoal.equals("Rigorous intensity")) {
                        query = "SELECT * from exercise ORDER BY CaloriesBurned DESC LIMIT 3 OFFSET 40;";
                    }
                } else if (primaryGoal.equals("Gain weight")) {
                    if (exerciseGoal.equals("Light intensity")) {
                        query = "SELECT * from exercise ORDER BY CaloriesBurned LIMIT 3;";
                    } else if (exerciseGoal.equals("Moderate intensity")) {
                        query = "SELECT * from exercise ORDER BY CaloriesBurned LIMIT 3 OFFSET 10;";
                    } else if (exerciseGoal.equals("Rigorous intensity")) {
                        query = "SELECT * from exercise ORDER BY CaloriesBurned LIMIT 3 OFFSET 20;";
                    }
                }

                PreparedStatement pStatement = conn.prepareStatement(query);
                ResultSet rs = pStatement.executeQuery();
                while (rs.next()) {
                    String exerciseName = rs.getString("ExerciseName");
                    float cals = rs.getFloat("CaloriesBurned");
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("exerciseName", exerciseName);
                    jsonObject.addProperty("calsBurned", cals);
                    jsonArray.add(jsonObject);
                }
                rs.close();
                pStatement.close();
            } else {
                System.out.println("Exercise Query: " + exerciseQuery);
                String query2 = "";
                if (primaryGoal.equals("Lose weight")) {
                    query2 = "SELECT * from exercise WHERE ExerciseName LIKE '" + exerciseQuery + "%' ORDER BY CaloriesBurned DESC LIMIT 10";
                } else if  (primaryGoal.equals("Maintain weight")) {
                    query2 = "SELECT * from exercise WHERE ExerciseName LIKE '" + exerciseQuery + "%' ORDER BY CaloriesBurned LIMIT 10 OFFSET 124";
                } else if (primaryGoal.equals("Gain weight")) {
                    query2 = "SELECT * from exercise WHERE ExerciseName LIKE '" + exerciseQuery + "%' ORDER BY CaloriesBurned LIMIT 10";
                }
                PreparedStatement pStatement2 = conn.prepareStatement(query2);
                ResultSet rs2 = pStatement2.executeQuery();
                while (rs2.next()) {
                    String exerciseName = rs2.getString("ExerciseName");
                    float cals = rs2.getFloat("CaloriesBurned");

                    System.out.println("test");
                    System.out.println(cals);

                    int weightToInt = Integer.parseInt(weight);

                    System.out.println(weightToInt);

                    float convertedCals = (float) ((weightToInt / 2.0) * cals);

                    System.out.println(convertedCals);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("exerciseName", exerciseName);
                    jsonObject.addProperty("calsBurned", convertedCals);
                    jsonArray.add(jsonObject);
                }
                rs2.close();
                pStatement2.close();
            }
            response.getWriter().write(jsonArray.toString());
        } catch(Exception e) {
            System.out.println(e);
            response.setStatus(500);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}