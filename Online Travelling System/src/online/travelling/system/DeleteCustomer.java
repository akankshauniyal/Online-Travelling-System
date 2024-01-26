package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DeleteCustomer extends JFrame {

    private Choice c1;
    private Conn conn;
    private String username;

    public DeleteCustomer(String username) {
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

        JLabel lblName = new JLabel("DELETE CUSTOMER DETAILS");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblName.setBounds(148, 11, 300, 53);
        add(lblName);

        JLabel lb3 = new JLabel("Username :");
        lb3.setBounds(120, 70, 160, 20);
        lb3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(lb3);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/delete.jpg"));
        Image i2 = i1.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(460, 150, 150, 150);
        image.setBackground(Color.white);
        add(image);

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/del.png"));
        Image i5 = i4.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image1 = new JLabel(i6);
        image1.setBounds(0, 150, 150, 150);
        image1.setBackground(Color.white);
        add(image1);

        c1 = new Choice();
        try {
            // Populate the Choice component with usernames
            String query = "SELECT * FROM customer";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                c1.add(rs.getString("username"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        c1.setBounds(300, 70, 150, 30);
        add(c1);

        JButton b1 = new JButton("Delete Customer Details");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // Delete details of the selected username
                    String selectedUsername = c1.getSelectedItem();
                    String query = "DELETE FROM customer WHERE username = ?";
                    try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                        preparedStatement.setString(1, selectedUsername);
                        preparedStatement.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "Customer Details Deleted Successfully");

                    new Dashboard(username);
                    setVisible(false);
                } catch (SQLException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        });
        b1.setBounds(170, 120, 250, 30);
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
        btnExit.setBounds(235, 170, 100, 30);
        btnExit.setBackground(Color.BLACK);
        btnExit.setForeground(Color.WHITE);
        add(btnExit);

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new DeleteCustomer("");
    }
}
