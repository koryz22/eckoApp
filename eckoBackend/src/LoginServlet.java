import com.google.gson.JsonObject;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "LoginServlet", urlPatterns = "/api/login")
public class LoginServlet extends HttpServlet {
    private static DataSource dataSource;
//    init grabbing datasource
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
//    post method of login
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        grabbing username and password
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("USER: " + username);
        System.out.println("PASS: " + password);

        JsonObject responseJsonObject = new JsonObject();

        try (Connection conn = dataSource.getConnection()) {
            String query = "SELECT * from Users where Email = ? and UserPass = ?";
            PreparedStatement pStatement = conn.prepareStatement(query);
            pStatement.setString(1, username);
            pStatement.setString(2, password);
            ResultSet employee_rs = pStatement.executeQuery();
            //if userpassword is correct
            if (employee_rs.next()) {
                int user_id = employee_rs.getInt("UserId");
                System.out.println(user_id);
                //grab stAttribute session
                request.getSession().setAttribute("user", new User(username, user_id));
                responseJsonObject.addProperty("message", "success");
                responseJsonObject.addProperty("UserId", user_id);

            } else {
                responseJsonObject.addProperty("message", "fail");
            }

            response.getWriter().write(responseJsonObject.toString());
            response.setStatus(200);
        } catch (Exception e) {
            responseJsonObject.addProperty("error", e.getMessage());
            response.getWriter().write(responseJsonObject.toString());
            response.setStatus(500);
        }
    }
}