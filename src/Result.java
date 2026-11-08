
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Result {

    public static void viewResult(String username) {

        System.out.println("\n===== VIEW RESULT =====");
        System.out.println("Student: " + username);

        String sql = "SELECT * FROM Results WHERE username = ? ORDER BY exam_date DESC";

        try {

            Connection con = DBConnection.getConnection();

            if (con == null) {
                System.out.println("Database Connection Failed!");
                return;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                do {
                    System.out.println("\nStudent Name: " + rs.getString("username"));
                    System.out.println("Score: " + rs.getInt("score"));
                    System.out.println("Total Marks: " + rs.getInt("total_marks"));
                    System.out.println("Result: " + rs.getString("result"));
                    System.out.println("Exam Date: " + rs.getTimestamp("exam_date"));
                    System.out.println("--------------------------------");
                } while (rs.next());

            } else {

                System.out.println("No Result Found!");

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println("Unable to View Result!");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();

        }
    }
}
