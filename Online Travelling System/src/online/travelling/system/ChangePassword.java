package online.travelling.system;


import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.security.MessageDigest;

public class ChangePassword extends JFrame implements ActionListener {

    JTextField tfusername, tfnewpass;
    JButton update, back;

    ChangePassword(String username, String hashedPassword) {
        setBounds(550, 250, 450, 250);
        getContentPane().setBackground(new Color(133, 193,233));
        setLayout(null);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(40, 20, 100, 25);
        lblUsername.setFont(new Font("SAN SERIF", Font.PLAIN, 15));
        add(lblUsername);

        tfusername = new JTextField();
        tfusername.setBounds(190, 20, 180, 25);
        tfusername.setBorder(BorderFactory.createEmptyBorder());
        tfusername.setEditable(false);
        add(tfusername);

        tfusername.setText(username);

        JLabel lblnewpass = new JLabel("New Password:");
        lblnewpass.setBounds(40, 60, 125, 25);
        lblnewpass.setFont(new Font("SAN SERIF", Font.PLAIN, 15));
        add(lblnewpass);

        tfnewpass = new JPasswordField();
        tfnewpass.setBounds(190, 60, 180, 25);
        tfnewpass.setBorder(BorderFactory.createEmptyBorder());
        add(tfnewpass);

        update = new JButton(" UPDATE");
        update.setBackground(Color.white);
        update.setForeground(Color.BLACK);
        update.setBounds(80, 120, 100, 25);
        update.setBorder(BorderFactory.createEmptyBorder());
        update.addActionListener(this);
        add(update);

        back = new JButton(" BACK");
        back.setBackground(Color.white);
        back.setForeground(Color.BLACK);
        back.setBounds(200, 120, 100, 25);
        back.setBorder(BorderFactory.createEmptyBorder());
        back.addActionListener(this);
        add(back);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == update) {
            try {
                String newPassInput = tfnewpass.getText();

                // Check if the new password is not empty
                if (newPassInput.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "New password cannot be empty.");
                } else {
                    // Hash the new password
                    String newHashedPassword = hashPassword(newPassInput);

                    // Update the password in the database
                    String updateQuery = "UPDATE account SET password = ? WHERE username = ?";
                    try (Conn updateConn = new Conn();
                         PreparedStatement updateStmt = updateConn.prepareStatement(updateQuery)) {

                        updateStmt.setString(1, newHashedPassword);
                        updateStmt.setString(2, tfusername.getText());

                        int rowsUpdated = updateStmt.executeUpdate();

                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(this, "Password updated successfully!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to update password.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    // Close the current frame
                    setVisible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new ForgetPassword();
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte hashedByte : hashedBytes) {
                String hex = Integer.toHexString(0xff & hashedByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args) {
        new ChangePassword("SampleUser", "SampleHashedPassword");
    }
}

