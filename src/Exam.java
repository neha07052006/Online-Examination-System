
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Exam {

    public static void startExam(String username) {

        int score = 0;
        int answer;

        System.out.println("\n===== ONLINE EXAM =====");

        System.out.println("\nQ1. Which language is used for Android Development?");
        System.out.println("1. HTML\n2. Java\n3. CSS\n4. SQL");
        System.out.print("Enter Answer: ");
        answer = Main.sc.nextInt();
        if (answer == 2) score++;

        System.out.println("\nQ2. Which one is a Database?");
        System.out.println("1. MySQL\n2. HTML\n3. CSS\n4. Java");
        System.out.print("Enter Answer: ");
        answer = Main.sc.nextInt();
        if (answer == 1) score++;

        System.out.println("\nQ3. Which keyword is used to create a class in Java?");
        System.out.println("1. function\n2. define\n3. class\n4. create");
        System.out.print("Enter Answer: ");
        answer = Main.sc.nextInt();
        if (answer == 3) score++;

        String status = score >= 2 ? "PASS" : "FAIL";

        System.out.println("\n===== EXAM RESULT =====");
        System.out.println("Student: " + username);
        System.out.println("Your Score: " + score + "/3");
        System.out.println("Result: " + status);

        String sql = "INSERT INTO Results (username, score, total_marks, result) VALUES (?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setInt(2, score);
            ps.setInt(3, 3);
            ps.setString(4, status);

            ps.executeUpdate();

            System.out.println("Result Saved Successfully!");

            ps.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Result Saving Failed!");
            e.printStackTrace();
        }
    }
}
