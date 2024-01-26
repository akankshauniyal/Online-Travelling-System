package online.travelling.system;

import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ForgetPassword extends JFrame implements ActionListener {

    JTextField tfusername, tfname, tfquestion, tfans;
    JButton search, changepass, back;

    ForgetPassword() {
        setBounds(350, 200, 850, 380);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/forgotpass.png"));
        Image i2 = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(580, 70, 200, 200);
        add(image);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBounds(30, 30, 500, 280);
        add(p1);

        JLabel lblusername = new JLabel("Username:");
        lblusername.setBounds(40, 20, 100, 25);
        lblusername.setFont(new Font("SAN SERIF", Font.PLAIN, 15));
        p1.add(lblusername);

        tfusername = new JTextField();
        tfusername.setBounds(190, 20, 180, 25);
        tfusername.setBorder(BorderFactory.createEmptyBorder());
        p1.add(tfusername);

        search = new JButton(" SEARCH");
        search.setBackground(new Color(133, 193, 233));
        search.setForeground(Color.BLACK);
        search.setBounds(380, 20, 100, 25);
        search.addActionListener(this);
        p1.add(search);

        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(40, 60, 125, 25);
        lblname.setFont(new Font("SAN SERIF", Font.PLAIN, 15));
        p1.add(lblname);

        tfname = new JTextField();
        tfname.setBounds(190, 60, 180, 25);
        tfname.setBorder(BorderFactory.createEmptyBorder());
        p1.add(tfname);

        JLabel lbquestion = new JLabel("Security Question:");
        lbquestion.setBounds(40, 100, 150, 25);
        lbquestion.setFont(new Font("SAN SERIF", Font.PLAIN, 15));
        p1.add(lbquestion);

        tfquestion = new JTextField();
        tfquestion.setBounds(190, 100, 180, 25);
        tfquestion.setBorder(BorderFactory.createEmptyBorder());
        p1.add(tfquestion);

        JLabel lbans = new JLabel("Answer:");
        lbans.setBounds(40, 140, 150, 25);
        lbans.setFont(new Font("SAN SERIF", Font.PLAIN, 15));
        p1.add(lbans);

        tfans = new JTextField();
        tfans.setBounds(190, 140, 180, 25);
        tfans.setBorder(BorderFactory.createEmptyBorder());
        p1.add(tfans);

        changepass = new JButton(" CHANGE PASSWORD ");
        changepass.setBackground(new Color(133, 193, 233));
        changepass.setForeground(Color.BLACK);
        changepass.setBounds(120, 190, 200, 25);
        changepass.addActionListener(this);
        p1.add(changepass);


        back = new JButton(" BACK");
        back.setBackground(new Color(133, 193, 233));
        back.setForeground(Color.BLACK);
        back.setBounds(150, 230, 100, 25);
        back.addActionListener(this);
        p1.add(back);

        setVisible(true);
    }

   public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            try {
                String query = " SELECT * FROM account WHERE username = '" + tfusername.getText() + "'";

                Conn c = new Conn();

                ResultSet rs = c.s.executeQuery(query);
                while (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    tfquestion.setText(rs.getString("security"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (ae.getSource() == changepass) {
            try {
                String query = " SELECT * FROM account WHERE answer = '" + tfans.getText() + "' AND username = '" + tfusername.getText() + "'";

                Conn c = new Conn();

                ResultSet rs = c.s.executeQuery(query);
                if (rs.next()) {
                    // Retrieve the hashed password
                    String hashedPassword = rs.getString("password");

                    // Open a new frame for changing the password
                    new ChangePassword(tfusername.getText(), hashedPassword);
                    
                    // Close the current frame
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid answer or username", "Password Retrieval", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new ForgetPassword();
    }
}
