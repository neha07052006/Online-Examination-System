
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AdminDashboardFrame extends JFrame {

    public AdminDashboardFrame() {

        setTitle("Admin Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("ADMIN DASHBOARD");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBounds(210, 30, 300, 40);
        panel.add(title);

        JButton studentsButton = new JButton("View Students");
        studentsButton.setBounds(230, 120, 220, 45);
        panel.add(studentsButton);

        JButton resultsButton = new JButton("View All Results");
        resultsButton.setBounds(230, 190, 220, 45);
        panel.add(resultsButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(230, 260, 220, 45);
        panel.add(logoutButton);

        studentsButton.addActionListener(e -> viewStudents());
        resultsButton.addActionListener(e -> viewResults());

        logoutButton.addActionListener(e -> {
            dispose();
            new AdminLoginFrame();
        });

        add(panel);
        setVisible(true);
    }

    private void viewStudents() {

        String[] columns = {"Student ID", "Name", "Email"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT student_id, name, email FROM students";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("email")
                });
            }

            rs.close();
            ps.close();
            con.close();

            showTable("All Students", model);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void viewResults() {

        String[] columns = {
            "ID", "Username", "Score", "Total Marks", "Result"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT id, username, score, total_marks, result FROM results";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("score"),
                    rs.getInt("total_marks"),
                    rs.getString("result")
                });
            }

            rs.close();
            ps.close();
            con.close();

            showTable("All Exam Results", model);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void showTable(String title, DefaultTableModel model) {

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame(title);
        frame.add(scrollPane);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }
}
