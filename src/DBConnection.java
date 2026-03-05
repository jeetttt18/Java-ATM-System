import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {

        String url = "jdbc:mysql://localhost:3306/atmsystem";
        String username = "root";
        String password = "YourNewPassword123!";

        return DriverManager.getConnection(url, username, password);
    }
}