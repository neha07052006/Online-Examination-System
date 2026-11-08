
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginFrame extends JFrame {

    public LoginFrame() {

        setTitle("Online Examination System");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(230, 240, 255));

        JLabel title = new JLabel("STUDENT LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(115, 30, 250, 40);
        panel.add(title);

        JLabel userLabel = new JLabel("Name:");
        userLabel.setBounds(60, 110, 100, 30);
        panel.add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(160, 110, 200, 30);
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(60, 160, 100, 30);
        panel.add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(160, 160, 200, 30);
        panel.add(passwordField);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(160, 220, 120, 35);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please enter Name and Password!"
                );
                return;
            }

            String sql = "SELECT * FROM Students WHERE name = ? AND password = ?";

            try {

                Connection con = DBConnection.getConnection();

                if (con == null) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Database Connection Failed!"
                    );
                    return;
                }

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Login Successful! Welcome " + username
                    );

                    rs.close();
                    ps.close();
                    con.close();

                    dispose();
                    new StudentDashboardFrame(username);

                } else {

                    JOptionPane.showMessageDialog(
                        this,
                        "Invalid Name or Password!"
                    );

                    rs.close();
                    ps.close();
                    con.close();
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                    this,
                    "Login Failed: " + ex.getMessage()
                );
            }
        });

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}
