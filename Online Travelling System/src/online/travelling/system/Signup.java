package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Signup extends JFrame implements ActionListener {

    JButton create, back;
    JTextField tfusername, tfname, tfpassword, tfans;
    Choice securityq;

    Signup() {
        setBounds(350, 200, 900, 360);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(133, 193, 233));
        p1.setBounds(0, 0, 500, 400);
        p1.setLayout(null);
        add(p1);

        JLabel lblusername = new JLabel("Username:");
        lblusername.setBounds(50, 20, 125, 25);
        lblusername.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        p1.add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(190, 20, 180, 25);
        tfusername.setBorder(BorderFactory.createEmptyBorder());
        p1.add(tfusername);

        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(50, 60, 125, 25);
        lblname.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        p1.add(lblname);

        tfname = new JTextField();
        tfname.setBounds(190, 60, 180, 25);
        tfname.setBorder(BorderFactory.createEmptyBorder());
        p1.add(tfname);

        JLabel password = new JLabel("Password:");
        password.setBounds(50, 100, 125, 25);
        password.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        p1.add(password);

        tfpassword = new JPasswordField(); // Use JPasswordField for password input
        tfpassword.setBounds(190, 100, 180, 25);
        tfpassword.setBorder(BorderFactory.createEmptyBorder());
        p1.add(tfpassword);

        JLabel security = new JLabel("Security question:");
        security.setBounds(50, 140, 125, 25);
        security.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        p1.add(security);

        securityq = new Choice();
        securityq.add("What is the name of your first pet?");
        securityq.add("In which city were you born?");
        securityq.add("What is your favorite book?");
        securityq.add("Your favourite meal?");
        securityq.add("Your lucky number?");
        securityq.setBounds(190, 140, 180, 25);
        p1.add(securityq);

        JLabel ans = new JLabel("Answer:");
        ans.setBounds(50, 180, 125, 25);
        ans.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        p1.add(ans);

        tfans = new JTextField();
        tfans.setBounds(190, 180, 180, 25);
        tfans.setBorder(BorderFactory.createEmptyBorder());
        p1.add(tfans);

        create = new JButton("Create");
        create.setBounds(80, 250, 100, 30);
        create.setBackground(Color.WHITE);
        create.setForeground(new Color(133, 193, 233));
        create.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        create.setBorder(BorderFactory.createEmptyBorder());
        create.addActionListener(this);
        p1.add(create);

        back = new JButton("Back");
        back.setBounds(250, 250, 100, 30);
        back.setBackground(Color.WHITE);
        back.setForeground(new Color(133, 193, 233));
        back.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        back.setBorder(BorderFactory.createEmptyBorder());
        back.addActionListener(this);
        p1.add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/sign.png"));
        Image i2 = i1.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(570, 45, 250, 250);
        add(image);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == create) {
            try {
                String username = tfusername.getText();
                String name = tfname.getText();
                String passwordInput = tfpassword.getText();

                // Check if username and password are empty
                if (username.trim().isEmpty() || passwordInput.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username and password are mandatory fields.");
                } else {
                    // Check if the username already exists
                    String checkQuery = "SELECT * FROM account WHERE username = ?";
                    try (Conn checkConn = new Conn();
                         PreparedStatement checkStmt = checkConn.prepareStatement(checkQuery)) {

                        checkStmt.setString(1, username);
                        ResultSet checkRs = checkStmt.executeQuery();

                        if (checkRs.next()) {
                            // Username already exists, show an error message
                            JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different username.");
                        } else {
                            // Hash the password
                            String password = hashPassword(passwordInput);

                            // Username is unique, proceed with signup
                            String insertQuery = "INSERT INTO account (username, name, password, security, answer) VALUES (?, ?, ?, ?, ?)";
                            try (Conn insertConn = new Conn();
                                 PreparedStatement insertStmt = insertConn.prepareStatement(insertQuery)) {

                                insertStmt.setString(1, username);
                                insertStmt.setString(2, name);
                                insertStmt.setString(3, password.substring(0, Math.min(password.length(), 256))); // Limit to 256 characters
                                insertStmt.setString(4, securityq.getSelectedItem());
                                insertStmt.setString(5, tfans.getText());

                                insertStmt.executeUpdate();
                                JOptionPane.showMessageDialog(null, "Account created successfully!");

                                setVisible(false);
                                new Login();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login();
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
        new Signup();
    }
}
