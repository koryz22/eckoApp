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

@WebServlet(name = "MainPageServlet", urlPatterns = "/api/mainPage")
public class MainPageServlet extends HttpServlet {
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
        System.out.println("Inside Main Page GET");
        String userId = request.getParameter("UserId");
        System.out.println("USER_ID PASSED: " + userId);

        int user_id = Integer.parseInt(userId);
        System.out.println(userId);

        try (Connection conn = dataSource.getConnection()) {
            JsonArray jsonArray = new JsonArray();
            String query = "SELECT * from UserRecord WHERE UserId = ? ORDER BY Date DESC";
            PreparedStatement pStatement = conn.prepareStatement(query);
            pStatement.setInt(1, user_id);
            ResultSet record_rs = pStatement.executeQuery();
            System.out.println("Inside Main Page GET 2");
            while(record_rs.next()) {
                String date = record_rs.getString("Date");
                int ls_score = record_rs.getInt("LifestyleScore");
                int food_score = record_rs.getInt("FoodScore");
                int exercise_score = record_rs.getInt("ExerciseScore");
                int sleep_score = record_rs.getInt("SleepScore");
                // GRABBING DATA
                System.out.println("USER : " + user_id);
                System.out.println(date);
                System.out.println(ls_score);
                System.out.println(food_score);
                System.out.println(exercise_score);
                System.out.println(sleep_score);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("date", date);
                jsonObject.addProperty("ls_score", ls_score);
                jsonObject.addProperty("food_score", food_score);
                jsonObject.addProperty("exercise_score", exercise_score);
                jsonObject.addProperty("sleep_score", sleep_score);
                jsonArray.add(jsonObject);
            }
            record_rs.close();
            pStatement.close();
            response.getWriter().write(jsonArray.toString());
        } catch (Exception e) {
            System.out.println("Inside Main Page GET 5");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("error", e.getMessage());
            response.getWriter().write(jsonObject.toString());
            response.setStatus(500);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Inside Main Page Post");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int user_id = user.getUserid();

        Boolean create = false;
        System.out.println(user_id);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = currentDate.format(formatter);
        System.out.println("Current date: " + formattedDate);

        try (Connection conn = dataSource.getConnection()) {
//            seeing if today is already in db
            String query = "SELECT * from UserRecord WHERE UserId = ? and Date = ?";
            PreparedStatement pStatement = conn.prepareStatement(query);
            pStatement.setInt(1, user_id);
            pStatement.setString(2, formattedDate);
            ResultSet record_rs = pStatement.executeQuery();
            System.out.println("Inside Main Page Post 2");
            if (record_rs.next()) {
                System.out.println("create = false");
            } else {
                System.out.println("create = true");
                create = true;
            }
            record_rs.close();
            pStatement.close();

            if (create) {
                //            seeing if today is already in db
                String query2 = "INSERT INTO UserRecord (Date, UserId, LifestyleScore, FoodScore, ExerciseScore, SleepScore) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pStatement2 = conn.prepareStatement(query2);
                pStatement2.setString(1, formattedDate);
                pStatement2.setInt(2, user_id);
                pStatement2.setInt(3, 0);
                pStatement2.setInt(4, 0);
                pStatement2.setInt(5, 0);
                pStatement2.setInt(6, 0);
                int rowsAffected = pStatement2.executeUpdate();
                System.out.println(rowsAffected + " rows affected.");
                pStatement2.close();
            }
        } catch(Exception e){
            System.out.println("Inside Main Page GET 5");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("error", e.getMessage());
            response.getWriter().write(jsonObject.toString());
            response.setStatus(500);
        }
    }
}