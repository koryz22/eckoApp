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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;


@WebServlet(name = "SleepServlet", urlPatterns = "/api/sleepLog")
public class SleepServlet extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Random random = new Random();
        System.out.println("Inside Sleep Page Post");
        HttpSession session = request.getSession();
        String sg = session.getAttribute("sleepGoal").toString();
        int [] foodarrayAtr = {80,70,89,90,72,83,75,89,90,58};

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String currentTime = currentDateTime.format(formatter);

        String sleepTime = request.getParameter("bedTime");
        int [] exerciseAtr = {83,75,80,70,89,90,72,89,90,54};
        System.out.println("Current time: " + currentTime);
        System.out.println("Sleep Goal: " + sg);
        System.out.println("Sleep by time: " + sleepTime);
        int params = exerciseAtr.length;
        int denom =0;
        if (sg.length() == 7) {
            denom = Integer.parseInt(sg.substring(0, 1));
        } else {
            denom = Integer.parseInt(sg.substring(0, 2));
        }
        int currHr = Integer.parseInt(currentTime.substring(0,2));
        int recHr;

        System.out.println(sleepTime.length());

        float score;
        String tag;

        if (sleepTime.length() == 7){
            recHr = Integer.parseInt(sleepTime.substring(0,1));
             tag = sleepTime.substring(5,7);
        } else {
            recHr = Integer.parseInt(sleepTime.substring(0,2));
             tag = sleepTime.substring(6,8);
        }
        System.out.println(recHr);
        System.out.println(currHr);
        System.out.println(tag);
        System.out.println(denom);

        recHr += 12;
        if (recHr >= currHr){
            System.out.println("100%");
            System.out.println(recHr);
            System.out.println(currHr);
            System.out.println(tag);
            System.out.println(denom);
            score = 1;
        }else{
            System.out.println("not 100");
            System.out.println(recHr);
            System.out.println(currHr);
            System.out.println(tag);
            System.out.println(denom);
            score = 1 - (float)(currHr - recHr) / (float) denom;
        }
        int p= random.nextInt(params);

        System.out.println((int) (score * 100));


        int foodScore = foodarrayAtr[p];
        int exerciseScore = exerciseAtr[p];
        int sleepScore = (int) (score * 100);

        try (Connection conn = dataSource.getConnection()) {
            String query = "UPDATE UserRecord SET SleepScore = ?, ExerciseScore = ?, FoodScore = ?,  LifestyleScore = ? WHERE Date = \"06/09/2023\"";
            PreparedStatement pStatement = conn.prepareStatement(query);
            pStatement.setInt(1,sleepScore );
            pStatement.setInt(2, exerciseScore);
            pStatement.setInt(3, foodScore);
            int lsScore = (sleepScore+exerciseScore+foodScore) / 3;
            pStatement.setInt(4, lsScore);
            int rowsAffected = pStatement.executeUpdate();
            System.out.println(rowsAffected + " rows affected.");
            pStatement.close();
        } catch (Exception e) {
            System.out.println("Profile Post Error: " + e);
        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Inside Sleep GEt");
        HttpSession session = request.getSession();

        JsonObject js = new JsonObject();
//        passing sleep goal back
        js.addProperty("sleepGoal",session.getAttribute("sleepGoal").toString());
        response.getWriter().write(js.toString());
    }
}