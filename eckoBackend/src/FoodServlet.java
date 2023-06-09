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

@WebServlet(name = "FoodServlet", urlPatterns = "/api/food")
public class FoodServlet extends HttpServlet {
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
        System.out.println("FOOD SERVLET GET CALLED");
        String foodNamePassed = request.getParameter("foodName");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int user_id = user.getUserid();
        String primaryGoal = (String) session.getAttribute("primaryGoal");
        String foodGoal = (String) session.getAttribute("foodGoal");
        System.out.println("primary goal: " + primaryGoal);
        System.out.println("f goal: " + foodGoal);

        try (Connection conn = dataSource.getConnection()) {
            JsonArray jsonArray = new JsonArray();
            String query = "";

            if(foodNamePassed == null || foodNamePassed.isEmpty()) {
                if(foodGoal.contains("Atkins")) {
                    if (primaryGoal.equals("Lose weight")) {
                        query = "SELECT DISTINCT * from food WHERE Protein < 30 AND Calories > 1000 ORDER BY Protein DESC, Calories ASC LIMIT 3;";
                    } else if  (primaryGoal.equals("Maintain weight")) {
                        query = "SELECT DISTINCT * from food WHERE Protein < 30 AND Calories < 2000 ORDER BY Protein DESC LIMIT 3;";
                    } else if (primaryGoal.equals("Gain weight")) {
                        query = "SELECT DISTINCT * from food WHERE Protein > 30 AND Protein < 60 AND Calories < 4000 ORDER BY Protein DESC, Calories DESC LIMIT 3;";
                    }
                } else if(foodGoal.contains("Keto")) {
                    if (primaryGoal.equals("Lose weight")) {
                        query = "SELECT DISTINCT * from food WHERE Fat < 40 AND Calories < 1500 ORDER BY Fat DESC LIMIT 3;";
                    } else if  (primaryGoal.equals("Maintain weight")) {
                        query = "SELECT DISTINCT * from food WHERE Fat < 50 AND Calories < 2200 ORDER BY Fat DESC LIMIT 3;";
                    } else if (primaryGoal.equals("Gain weight")) {
                        query = "SELECT DISTINCT * from food WHERE Fat < 60 AND Calories < 4000 ORDER BY Fat DESC LIMIT 3;";
                    }
                } else if(foodGoal.contains("Balanced")) {
                    if (primaryGoal.equals("Lose weight")) {
                        query = "SELECT DISTINCT * from food WHERE Fat < 30 AND Protein < 30 AND Calories < 1500 ORDER BY Fat DESC LIMIT 3;";
                    } else if  (primaryGoal.equals("Maintain weight")) {
                        query = "SELECT DISTINCT * from food WHERE Fat < 30 AND Protein < 30 AND Calories < 2200 ORDER BY Fat DESC LIMIT 3;";
                    } else if (primaryGoal.equals("Gain weight")) {
                        query = "SELECT DISTINCT * from food WHERE Fat < 30 AND Protein < 30 AND Calories < 4000 ORDER BY Fat DESC LIMIT 3;";
                    }
                }

                PreparedStatement pStatement = conn.prepareStatement(query);
                ResultSet rs = pStatement.executeQuery();
                while (rs.next()) {
                    String foodName = rs.getString("FoodName");
                    float cals = rs.getFloat("Calories");
                    float protein = rs.getFloat("Protein");
                    float fat = rs.getFloat("Fat");
                    float carbs = rs.getFloat("Carbs");
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("foodName", foodName);
                    jsonObject.addProperty("cals", cals);
                    jsonObject.addProperty("protein", protein);
                    jsonObject.addProperty("fat", fat);
                    jsonObject.addProperty("carbs", carbs);
                    jsonArray.add(jsonObject);
                }
                rs.close();
                pStatement.close();
            } else {
                System.out.println("Food name passed: " + foodNamePassed);
                String query2 = "";
                if (primaryGoal.equals("Lose weight")) {
                    query2 = "SELECT * from food WHERE FoodName LIKE '" + foodNamePassed + "%' ORDER BY Fat ASC LIMIT 10";
                } else if (primaryGoal.equals("Maintain weight")) {
                    query2 = "SELECT * from food WHERE FoodName LIKE '" + foodNamePassed + "%' ORDER BY Calories DESC LIMIT 10 OFFSET 500";
                } else if (primaryGoal.equals("Gain weight")) {
                    query2 = "SELECT * from food WHERE FoodName LIKE '" + foodNamePassed + "%' ORDER BY Fat DESC LIMIT 10";
                }
                PreparedStatement pStatement2 = conn.prepareStatement(query2);
                ResultSet rs2 = pStatement2.executeQuery();
                while (rs2.next()) {
                    String foodName = rs2.getString("FoodName");
                    System.out.println(foodName);
                    float cals = rs2.getFloat("Calories");
                    float protein = rs2.getFloat("Protein");
                    float fat = rs2.getFloat("Fat");
                    float carbs = rs2.getFloat("Carbs");
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("foodName", foodName);
                    jsonObject.addProperty("cals", cals);
                    jsonObject.addProperty("protein", protein);
                    jsonObject.addProperty("fat", fat);
                    jsonObject.addProperty("carbs", carbs);
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