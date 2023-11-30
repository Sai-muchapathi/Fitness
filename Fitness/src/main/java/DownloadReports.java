import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/DownloadReports")
public class DownloadReports extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        String jdbcUrl = "jdbc:mysql://localhost:3306/";
        String dbUser = "";
        String dbPassword = "";

        
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=data.csv");

        try (PrintWriter writer = response.getWriter()) {
            
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {

                
            	String query = "SELECT reports.report_id, fitness.workout, goals.goal, fitness.date "
                        + "FROM reports "
                        + "JOIN fitness ON reports.fitness_id = fitness.fitness_id "
                        + "JOIN goals ON reports.goal_id = goals.goal_id";

                try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    
                    writer.println("Report ID,Workout,Goal, Date"); // Adjust column names

                    
                    while (resultSet.next()) {
                        String reportID = resultSet.getString("report_id"); // Replace with actual column name
                        String workout = resultSet.getString("workout");
                        String goal = resultSet.getString("goal");
                        String date = resultSet.getString("date");

                        writer.println(reportID + "," + workout + "," + goal + "," + date); // Adjust column order
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                writer.println("Error retrieving data from the database");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
