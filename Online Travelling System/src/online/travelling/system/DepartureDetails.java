package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.sql.PreparedStatement;

public class DepartureDetails extends JFrame implements ActionListener {

    JTextField tffrom, tfto, tfnumber;
    JButton trains, buses, cabs;
    String username;
    JLabel lusername;

    DepartureDetails(String username) {
        this.username = username;
        setBounds(450, 180, 700, 500);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(0, 0, 102));
        p1.setBounds(0, 0, 700, 40);
        add(p1);

        lusername = new JLabel();
        lusername.setBounds(20, 0, 440, 40);
        lusername.setForeground(Color.white);
        lusername.setFont(new Font("Raleway", Font.PLAIN, 16));
        p1.add(lusername);

        JLabel date = new JLabel("Enter Departure Details");
        date.setBounds(20, 60, 440, 40);
        date.setForeground(Color.BLACK);
        date.setFont(new Font("Raleway", Font.PLAIN, 16));
        add(date);

        JLabel from = new JLabel("From:");
        from.setBounds(20, 110, 150, 25);
        from.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(from);

        tffrom = new JTextField();
        tffrom.setBounds(190, 110, 150, 25);
        add(tffrom);

        JLabel to = new JLabel("To:");
        to.setBounds(20, 160, 150, 25);
        to.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(to);

        tfto = new JTextField();
        tfto.setBounds(190, 160, 150, 25);
        add(tfto);

        trains = new JButton(" Check Trains ");
        trains.setBounds(60, 290, 100, 25);
        trains.setBackground(new Color(0, 0, 102));
        trains.setBorder(BorderFactory.createEmptyBorder());
        trains.setForeground(Color.WHITE);
        trains.addActionListener(this);
        add(trains);

        buses = new JButton(" Check Buses ");
        buses.setBounds(200, 290, 100, 25);
        buses.setBackground(new Color(0, 0, 102));
        buses.setBorder(BorderFactory.createEmptyBorder());
        buses.setForeground(Color.WHITE);
        buses.addActionListener(this);
        add(buses);

        cabs = new JButton(" Check Cabs ");
        cabs.setBounds(130, 340, 100, 25);
        cabs.setBackground(new Color(0, 0, 102));
        cabs.setBorder(BorderFactory.createEmptyBorder());
        cabs.setForeground(Color.WHITE);
        cabs.addActionListener(this);
        add(cabs);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/bus.jpg"));
        Image i2 = i1.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(380, 130, 250, 250);
        image.setBackground(Color.white);
        add(image);

        try {
            Conn c = new Conn();
            String query1 = "select * from account where username= '" + username + "'";
            ResultSet rs = c.s.executeQuery(query1);
            while (rs.next()) {
                lusername.setText(rs.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

   public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == trains || ae.getSource() == buses || ae.getSource() == cabs) {
            String fromdest = tffrom.getText();
            String todest = tfto.getText();

            // Validate if "From" and "To" fields contain only letters
            if (!isValidLetters(fromdest) || !isValidLetters(todest)) {
                JOptionPane.showMessageDialog(this, "Invalid Location!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Conn c = new Conn();
                String query = "INSERT INTO date (username, fromdest, todest) VALUES (?, ?, ?)";

                try (PreparedStatement pstmt = c.prepareStatement(query)) {
                    pstmt.setString(1, username);
                    pstmt.setString(2, fromdest);
                    pstmt.setString(3, todest);
                    pstmt.executeUpdate();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (ae.getSource() == trains) {
            setVisible(false);
            new CheckTrains(username);
        } else if (ae.getSource() == buses) {
            setVisible(false);
            new CheckBuses(username);
        } else if (ae.getSource() == cabs) {
            new CheckCabs(username);
        }
    }

    private boolean isValidLetters(String input) {
        // Check if the input contains only letters
        return input.matches("[a-zA-Z]+");
    }

    public static void main(String[] args) {
        new DepartureDetails("");
    }
}