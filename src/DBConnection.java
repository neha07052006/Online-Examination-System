
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {

        Connection con = null;

        try {

            String url = "jdbc:mysql://localhost:3306/online_exam_db";
            String username = "root";
            String password = "YOUR_MYSQL_PASSWORD\"";

            con = DriverManager.getConnection(url, username, password);

            System.out.println("Database Connected Successfully!");

            System.out.println("Connected Database: " +
                con.getCatalog());

        } catch (Exception e) {

            System.out.println("Database Connection Failed!");
            e.printStackTrace();

        }

        return con;
    }
}
