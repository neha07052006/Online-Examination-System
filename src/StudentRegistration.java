
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentRegistration {

    public static void registerStudent() {

        System.out.println("\n===== STUDENT REGISTRATION =====");

        System.out.print("Enter Username: ");
        String username = Main.sc.next();

        System.out.print("Enter Password: ");
        String password = Main.sc.next();

        String sql = "INSERT INTO Students (username, password) VALUES (?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("Registration Successful!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Registration Failed!");
            e.printStackTrace();
        }
    }
}
