import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

public class DBController {
    Connection conn;
    Statement stmt;

    public DBController(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");
            return;
        }

        try {
            Locale.setDefault(Locale.ENGLISH);
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "movies", "root");
            stmt = conn.createStatement();
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            return;
        }
    }

    public Connection getConnection(){
        if (conn!=null) return conn;
        return null;
    }

    public Statement getStatement(){
        if (stmt!=null) return stmt;
        return null;
    }


}
