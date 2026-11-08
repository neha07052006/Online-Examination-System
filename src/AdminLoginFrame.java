
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;

public class AdminLoginFrame extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;

    public AdminLoginFrame() {

        setTitle("Admin Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("ADMIN LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(110, 25, 200, 30);
        panel.add(title);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 90, 100, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 90, 180, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 130, 100, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 130, 180, 25);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(140, 190, 100, 35);
        panel.add(loginButton);

        loginButton.addActionListener(e -> adminLogin());

        add(panel);
        setVisible(true);
    }

    private void adminLogin() {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Admin Login Successful!");
                dispose();
                new AdminDashboardFrame();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new AdminLoginFrame();
    }
}
