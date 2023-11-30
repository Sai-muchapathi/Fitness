
/**
 * Servlet implementation class MyServlet
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Reports")
public class Reports extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Reports() {
        super();
        System.out.print("Successful");
        
        // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		System.out.print("Successful");
    	String[] array = request.getParameterValues("data");
    	System.out.println(array);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Connection connection = null;
        try {
            
        	Class.forName("com.mysql.jdbc.Driver");
            
            String url = "jdbc:mysql://localhost:3306/data";
            String username = "";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);

            String sql = "SELECT reports.report_id, fitness.workout, goals.goal, fitness.date, goals.date "
                    + "FROM reports "
                    + "JOIN fitness ON reports.fitness_id = fitness.fitness_id "
                    + "JOIN goals ON reports.goal_id = goals.goal_id";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                
            	out.println("<table class='table table-striped table-bordered'>");
            	out.println("<thead class='thead-dark'><tr><th>ID</th><th>Workout</th><th>Goals</th><th>Workout Date</th><th>Goals Date</th></tr></thead>");
            	out.println("<tbody>");

                while (resultSet.next()) {
                	int reportId = resultSet.getInt("report_id");
                    String workout = resultSet.getString("workout");
                    String goal = resultSet.getString("goal");
                    Date workoutDate = resultSet.getDate("fitness.date");
                    Date goalsDate = resultSet.getDate("goals.date");

                    out.println("<tr><td>" + reportId + "</td><td>" + workout + "</td><td>" + goal + "</td></tr>" + workoutDate + "</td></tr>" + goalsDate + "</td></tr>");
                }

                //out.println("</table>");
                out.println("</tbody></table>");
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("An error occurred while processing the request.");
        } finally {
            
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
		
        }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	 
}
}