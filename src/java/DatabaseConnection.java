
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 *
 * @author mkobayashi
 */

public class DatabaseConnection {
    
    private HttpServletRequest _request;
    private HttpServletResponse _response;
    private PrintWriter _writer;
    
    // JDBC database URL
    private static final String DB_URL = "jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124grp16";
     
    //  Database credentials
    private static final String USER = "inf124grp16";
    private static final String PASS = "n?yUmap3";
    
    // Constructor
    public DatabaseConnection(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException{
        _request = request;
        _response = response;
        System.err.println("***check for logging***");
        try {
            _writer = response.getWriter();
        }
        catch (IOException e) {
            System.err.println("**writer not initialized");
        }
    }
    
    public Connection connect() 
            throws ServletException, IOException {
        _writer.println("Loading driver...<br/>");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            _writer.println("Driver loaded!<br/>");
        } catch (ClassNotFoundException e) {
            _writer.println("Unable to load driver :(<br/>");
            throw new IllegalStateException("Cannot find the driver in the classpath!", e);
        }

        _writer.println("Connecting database...<br/>");
        try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            _writer.println("Database connected!<br/>");
            return connection;
        } catch (SQLException e) {
            _writer.println(e.getMessage() + "<br/>");
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}