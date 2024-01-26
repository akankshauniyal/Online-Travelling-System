package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.security.MessageDigest;
import java.sql.PreparedStatement;


public class Login extends JFrame implements ActionListener {
  
    JButton login, signup, forgot;
    JTextField tfusername;
    JPasswordField  tfpass;
    
    Login() {
    setSize(900,400);
    setLocation(350,200);
    setLayout(null);
    
    getContentPane().setBackground(Color.WHITE);
    
    JPanel p1= new JPanel();
    p1.setBackground(new Color(131, 200, 233));
    p1.setBounds(0,0,400,400);
    p1.setLayout(null);
    add(p1);
    
    ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("icons/login1.jpg"));
    Image i2= i1.getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT);
    ImageIcon i3= new ImageIcon(i2);
    JLabel image= new JLabel(i3);
    image.setBounds(70,60,250,250);
    p1.add(image);
    
    JPanel p2= new JPanel();
    p2.setLayout(null);
    p2.setBounds(400,30,450,300);
    add(p2);
    
    JLabel lblusername= new JLabel("Username:");
    lblusername.setBounds(60,20,100,25);
    lblusername.setFont(new Font ("SAN SERIF", Font.PLAIN, 18));
    p2.add(lblusername);
    
    tfusername= new JTextField();
    tfusername.setBounds(60,53,300,30);
    tfusername.setBorder(BorderFactory.createEmptyBorder());
    p2.add(tfusername);
    
    JLabel lblpassword= new JLabel("Password:");
    lblpassword.setBounds(60,100,100,25);
    lblpassword.setFont(new Font ("SAN SERIF", Font.PLAIN, 18));
    p2.add(lblpassword);
    
     tfpass = new JPasswordField(); // Use JPasswordField for password input
     tfpass.setBounds(60, 135, 300, 30);
     tfpass.setBorder(BorderFactory.createEmptyBorder());
     p2.add(tfpass);
    
    login = new JButton("Login");
    login.setBounds(60,200,130,30);
    login.setBackground(new Color(133, 193,233));
    login.setForeground(Color.WHITE);
    login.setBorder(BorderFactory.createEmptyBorder());
    login.addActionListener(this);
    p2.add(login);
    
    signup = new JButton("SignUp");
    signup.setBounds(210,200,130,30);
    signup.setBackground(new Color(133, 193,233));
    signup.setForeground(Color.WHITE);
    signup.setBorder(BorderFactory.createEmptyBorder());
    signup.addActionListener(this);
    p2.add(signup);
    
    forgot = new JButton("Forgot Password");
    forgot.setBounds(135,250,130,30);
    forgot.setBackground(new Color(133, 193,233));
    forgot.setForeground(Color.WHITE);
    forgot.setBorder(BorderFactory.createEmptyBorder());
    forgot.addActionListener(this);
    p2.add(forgot);
    
    JLabel text= new JLabel("Trouble logging in?");
    text.setBounds(280,250,150,20);
    p2.add(text);
    
    setVisible(true);
    }
    
  @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            try {
                String username = tfusername.getText();
                char[] passwordInput = tfpass.getPassword(); // Use getPassword to get char[] from JPasswordField

                // Check if username and password are empty
                if (username.trim().isEmpty() || passwordInput.length == 0) {
                    JOptionPane.showMessageDialog(null, "Username and password are mandatory fields.");
                } else {
                    // Hash the password
                    String hashedPassword = hashPassword(new String(passwordInput));

                    // Check if the username and hashed password match in the database
                    String query = "SELECT * FROM account WHERE username = ? AND password = ?";
                    try (Conn c = new Conn();
                         PreparedStatement stmt = c.prepareStatement(query)) {

                        stmt.setString(1, username);
                        stmt.setString(2, hashedPassword);

                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            setVisible(false);
                            new Loading(username);
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect username or password!");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == signup) {
            setVisible(false);
            new Signup();
        } else {
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
    
    public static void main(String[] args)
    {
        new Login();
    }
}
