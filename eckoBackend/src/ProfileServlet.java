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
import java.util.Date;

/**
 * This profileServlet is declared in the web annotation below,
 * which is mapped to the URL pattern /api/Profile.
 */
@WebServlet(name = "Profile", urlPatterns = "/api/profile")
public class ProfileServlet extends HttpServlet {
    private static DataSource dataSource;

    public void init(ServletConfig config) {
        try {
            dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/healthAppdb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles GET requests to store session information
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        System.out.println("Inside Profile Page GET");

        //grabbing user from session
        User user = (User) session.getAttribute("user");



        System.out.println("USER NAme PASSED: " + user.toString());

        int user_id = user.getUserid();


        try (Connection conn = dataSource.getConnection()) {
            JsonObject userJso = new JsonObject();
//            grabbing everything from users
            String query = "SELECT * from Users WHERE UserId = ?";
            PreparedStatement pStatement = conn.prepareStatement(query);
            pStatement.setInt(1, user_id);
            ResultSet record_rs = pStatement.executeQuery();
            System.out.println("Inside Profile Page GET 2");
            while(record_rs.next()) {
                System.out.println ("Inside Profile Page GET 3");

                System.out.println(record_rs.toString());

                // GRABBING DATA
                String first_name = record_rs.getString("FirstName");
                String last_name = record_rs.getString("LastName");
                String age = record_rs.getString("Age");
                String dob = record_rs.getString("Dob");
                String gender = record_rs.getString("Gender");
                String height = record_rs.getString("Height");
                String sleep_hours = record_rs.getString("SleepHours");
                String food_goal = record_rs.getString("FoodGoal");
                String sleep_goal = record_rs.getString("SleepGoal");
                String exercise_goal = record_rs.getString("ExerciseGoal");
                String primary_goal = record_rs.getString("PrimaryGoal");
                String weight = record_rs.getString("Weight");

                userJso.addProperty("first_name", first_name);
                userJso.addProperty("last_name", last_name);
                userJso.addProperty("age",age);
                userJso.addProperty("dob",dob);
                userJso.addProperty("gender",gender);
                userJso.addProperty("height",height);
                userJso.addProperty("sleep_hours",sleep_hours);
                userJso.addProperty("food_goal",food_goal);
                userJso.addProperty("sleep_goal",sleep_goal);
                userJso.addProperty("exercise_goal",exercise_goal);
                userJso.addProperty("primary_goal",primary_goal);
                userJso.addProperty("weight",weight);

            }
            record_rs.close();
            pStatement.close();
            response.getWriter().write(userJso.toString());
        } catch (Exception e) {
            System.out.println("Inside Main Page GET 5");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("error", e.getMessage());
            response.getWriter().write(jsonObject.toString());
            response.setStatus(500);
        }
    }


    /**
     * handles POST requests to add and show the item list information
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String item = request.getParameter("item");
        System.out.println(item);
        HttpSession session = request.getSession();

        // get the previous items in a ArrayList
        ArrayList<String> previousItems = (ArrayList<String>) session.getAttribute("previousItems");
        if (previousItems == null) {
            previousItems = new ArrayList<String>();
            previousItems.add(item);
            session.setAttribute("previousItems", previousItems);
        } else {
            // prevent corrupted states through sharing under multi-threads
            // will only be executed by one thread at a time
            synchronized (previousItems) {
                previousItems.add(item);
            }
        }

        JsonObject responseJsonObject = new JsonObject();

        JsonArray previousItemsJsonArray = new JsonArray();
        previousItems.forEach(previousItemsJsonArray::add);
        responseJsonObject.add("previousItems", previousItemsJsonArray);

        response.getWriter().write(responseJsonObject.toString());
    }
}
