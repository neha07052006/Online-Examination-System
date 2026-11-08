
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ProfileUpdateFrame extends JFrame {

    private String oldName;

    public ProfileUpdateFrame(String username) {

        oldName = username;

        setTitle("Student Profile Update");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(230, 240, 255));

        JLabel title = new JLabel("UPDATE PROFILE");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(105, 30, 250, 40);
        panel.add(title);

        JLabel nameLabel = new JLabel("Display Name:");
        nameLabel.setBounds(50, 110, 120, 30);
        panel.add(nameLabel);

        JTextField nameField = new JTextField(oldName);
        nameField.setBounds(180, 110, 200, 30);
        panel.add(nameField);

        JLabel passLabel = new JLabel("New Password:");
        passLabel.setBounds(50, 170, 120, 30);
        panel.add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(180, 170, 200, 30);
        panel.add(passwordField);

        JButton updateButton = new JButton("UPDATE");
        updateButton.setBounds(85, 250, 120, 35);
        panel.add(updateButton);

        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(230, 250, 120, 35);
        panel.add(cancelButton);

        updateButton.addActionListener(e -> {

            String newName = nameField.getText().trim();
            String newPassword =
                new String(passwordField.getPassword());

            if (newName.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please enter Name and Password!"
                );
                return;
            }

            String sql =
                "UPDATE Students SET name = ?, password = ? WHERE name = ?";

            try {

                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(sql);

                ps.setString(1, newName);
                ps.setString(2, newPassword);
                ps.setString(3, oldName);

                int result = ps.executeUpdate();

                ps.close();
                con.close();

                if (result > 0) {

                    JOptionPane.showMessageDialog(
                        this,
                        "Profile Updated Successfully!"
                    );

                    dispose();
                    new StudentDashboardFrame(newName);

                } else {

                    JOptionPane.showMessageDialog(
                        this,
                        "Profile Update Failed!"
                    );
                }

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage()
                );
            }
        });

        cancelButton.addActionListener(e -> {
            dispose();
            new StudentDashboardFrame(oldName);
        });

        add(panel);
        setVisible(true);
    }
}
