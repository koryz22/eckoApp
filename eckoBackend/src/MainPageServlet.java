//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import jakarta.servlet.ServletConfig;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
//
//@WebServlet(name = "LoginServlet", urlPatterns = "/api/main-page")
//public class MainPageServlet extends HttpServlet {
//    private static DataSource dataSource;
//    public void init(ServletConfig config) {
//        try {
//            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/healthAppdb");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//    }
//    /**
//     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//     */
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String userId = request.getParameter("userId");
//
//        System.out.println(userId);
//        try (Connection conn = dataSource.getConnection()) {
//            JsonArray jsonArray = new JsonArray();
//            String query = "SELECT * from UserRecord = ?";
//            PreparedStatement pStatement = conn.prepareStatement(query);
//            pStatement.setString(1, userId);
//            ResultSet record_rs = pStatement.executeQuery();
//
//            while(record_rs.next()) {
//                String date = record_rs.getString("Date");
//                int ls_score = record_rs.getInt("LifestyleScore");
//                int food_score = record_rs.getInt("FoodScore");
//                int exercise_score = record_rs.getInt("ExerciseScore");
//                int sleep_score = record_rs.getInt("SleepScore");
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.addProperty("date", date);
//                jsonObject.addProperty("ls_score", ls_score);
//                jsonObject.addProperty("food_score", food_score);
//                jsonObject.addProperty("exercise_score", exercise_score);
//                jsonObject.addProperty("sleep_score", sleep_score);
//                jsonArray.add(jsonObject);
//            }
//
//            record_rs.close();
//            pStatement.close();
//            response.getWriter().write(jsonArray.toString());
//
//        } catch (Exception e) {
//            JsonObject jsonObject = new JsonObject();
//            jsonObject.addProperty("error", e.getMessage());
//            response.getWriter().write(jsonObject.toString());
//            response.setStatus(500);
//        }
//    }
//}