
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StudentDashboardFrame extends JFrame {

    String username;

    public StudentDashboardFrame(String username) {

        this.username = username;

        setTitle("Online Examination System - Dashboard");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(230, 240, 255));

        JLabel title = new JLabel("STUDENT DASHBOARD");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(110, 30, 300, 35);
        panel.add(title);

        JLabel welcome = new JLabel("Welcome, " + username);
        welcome.setFont(new Font("Arial", Font.PLAIN, 16));
        welcome.setBounds(170, 80, 250, 30);
        panel.add(welcome);

        JButton profileButton = new JButton("Update Profile");
        profileButton.setBounds(160, 130, 170, 35);
        panel.add(profileButton);

        JButton startButton = new JButton("Start Exam");
        startButton.setBounds(160, 180, 170, 35);
        panel.add(startButton);

        JButton resultButton = new JButton("View Result");
        resultButton.setBounds(160, 230, 170, 35);
        panel.add(resultButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(160, 280, 170, 35);
        panel.add(logoutButton);

        // Update Profile
        profileButton.addActionListener(e -> {
            dispose();
            new ProfileUpdateFrame(username);
        });

        // Start Exam
        startButton.addActionListener(e -> {
            new ExamFrame(username);
        });

        // View Result
        resultButton.addActionListener(e -> {
            viewResult();
        });

        // Logout
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        add(panel);
        setVisible(true);
    }

    // Fetch Result from MySQL
    private void viewResult() {

        String sql = "SELECT score, total_marks, result " +
                     "FROM results WHERE username = ? LIMIT 1";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    int score = rs.getInt("score");
                    int total = rs.getInt("total_marks");
                    String status = rs.getString("result");

                    JOptionPane.showMessageDialog(
                        this,
                        "YOUR EXAM RESULT" +
                        "\nStudent: " + username +
                        "\nScore: " + score + "/" + total +
                        "\nResult: " + status,
                        "Exam Result",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                } else {

                    JOptionPane.showMessageDialog(
                        this,
                        "No Result Found! Please give the exam first."
                    );
                }
            }

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                this,
                "Error: " + ex.getMessage()
            );
        }
    }
}
