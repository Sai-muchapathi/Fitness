
/**
 * Servlet implementation class MyServlet
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyServlet() {
        super();
        System.out.print("Successful");
        
        // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
        }
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	System.out.print("Successful");
    	String[] array = request.getParameterValues("data");
    	System.out.println(array);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection = null;
        try {
        	
            
            Class.forName("com.mysql.jdbc.Driver");
            System.out.print("Successful inside tryyyy");
            
            String url = "jdbc:mysql://localhost:3306/data";
            String username = "";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);

            System.out.print("Successful after connections");
            
            String insertQuery = "INSERT INTO fitness (workout, date) VALUES (?, ?)";
            System.out.print("Successful after insertQuery");
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                
                for (String data : array) {
                    preparedStatement.setString(1, data);
                    preparedStatement.setDate(2, new java.sql.Date(System.currentTimeMillis()));
                    preparedStatement.executeUpdate();
                }
                System.out.print("Successful after stmt exe");
            }
            out.println("Connected to the database successfully!");

        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error: " + e.getMessage());
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
}