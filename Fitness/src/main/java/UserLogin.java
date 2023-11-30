import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;

    
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/data";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        
        if (isValidUser(username, password)) {
            
            response.getWriter().write("success");
        } else {
            
            response.getWriter().write("failure");
        }
    }

    private boolean isValidUser(String username, String password) {
        try {
            
        	Class.forName("com.mysql.jdbc.Driver");

            
            try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
                
                String query = "SELECT * FROM user WHERE username = ? AND password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        return resultSet.next(); 
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }
}
