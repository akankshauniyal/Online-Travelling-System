package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CancelBooking extends JFrame {

    private JTextField usernameField;
    private Conn conn;
    private String username;
    Choice transport;

    public CancelBooking(String username) {
        this.username = username;
        this.conn = new Conn();
        try {
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initialize() throws SQLException {
        setBounds(580, 220, 650, 350);
        setLayout(null);

        JLabel lblName = new JLabel("CANCEL YOUR BOOKING");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblName.setBounds(148, 11, 300, 53);
        add(lblName);

        JLabel lb3 = new JLabel("Enter Username:");
        lb3.setBounds(70, 70, 160, 20);
        lb3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(lb3);

        usernameField = new JTextField();
        usernameField.setBounds(240, 70, 150, 30);
        add(usernameField);

        JLabel del = new JLabel("Transport booked:");
        del.setBounds(50, 120, 180, 25);
        del.setFont(new Font("SAN SERIF", Font.PLAIN, 20));
        add(del);

        transport = new Choice();
        transport.add("Train");
        transport.add("Bus");
        transport.add("Cab");
        transport.setBounds(240, 120, 180, 30);
        add(transport);

        JButton b1 = new JButton("Cancel");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {

                    String enteredUsername = usernameField.getText();
                    String query = "DELETE FROM customer WHERE username = ?";
                    try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                        preparedStatement.setString(1, enteredUsername);
                        preparedStatement.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "Booking has been Cancelled Successfully");

                    new Dashboard(username);
                    setVisible(false);
                } catch (SQLException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        });
        b1.setBounds(235, 170, 100, 30);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        add(b1);

        JButton btnExit = new JButton("Back");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                new Dashboard(username);
                setVisible(false);
            }
        });
        btnExit.setBounds(235, 220, 100, 30);
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.WHITE);
        add(btnExit);

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new CancelBooking("");
    }
}
