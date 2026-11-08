
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {

    private static String currentUsername;

    public static String getLoggedInUsername() {
        return currentUsername;
    }

    public static boolean login() {

        System.out.println("\n===== STUDENT LOGIN =====");

        System.out.print("Enter Username: ");
        String username = Main.sc.next();

        System.out.print("Enter Password: ");
        String password = Main.sc.next();

        String sql = "SELECT * FROM Students WHERE username = ? AND password = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                currentUsername = username;

                System.out.println("\nLogin Successful!");
                System.out.println("Welcome, " + username);

                rs.close();
                ps.close();
                con.close();

                return true;

            } else {

                System.out.println("Invalid Username or Password!");

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println("Login Failed!");
            e.printStackTrace();

        }

        return false;
    }
}
