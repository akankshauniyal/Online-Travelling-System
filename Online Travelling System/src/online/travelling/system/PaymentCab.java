package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentCab extends JFrame {

    private String username;
    private int numberOfPeople;
    private String fare;
    private double totalPayment;

    public PaymentCab(String username, String cabName, String cabNumber, String timings, int numberOfPeople, String fare) {
        this.username = username;
        this.numberOfPeople = numberOfPeople;
        this.fare = fare;

        totalPayment = calculateTotalPayment(numberOfPeople, fare);

        setBounds(400, 150, 700, 507);
        setLayout(null);
        getContentPane().setBackground(Color.white);

        JLabel text = new JLabel("PAYMENT DETAILS:");
        text.setBounds(230, 10, 300, 25);
        text.setFont(new Font("Tahoma", Font.PLAIN, 25));
        add(text);

        JLabel lblUsername = new JLabel("Username: " + username);
        lblUsername.setBounds(230, 60, 300, 25);
        lblUsername.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(lblUsername);

        JLabel lblNumberOfPeople = new JLabel("Number of People: " + numberOfPeople);
        lblNumberOfPeople.setBounds(230, 100, 300, 25);
        lblNumberOfPeople.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(lblNumberOfPeople);

        JLabel lblFare = new JLabel("Fare per Person: Rs " + fare);
        lblFare.setBounds(230, 140, 300, 25);
        lblFare.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(lblFare);

        JLabel lblTotalPayment = new JLabel("Total Payment: Rs " + totalPayment);
        lblTotalPayment.setBounds(230, 180, 300, 25);
        lblTotalPayment.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(lblTotalPayment);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/pay.png"));
        Image i2 = i1.getImage().getScaledInstance(700, 190, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 280, 700, 190);
        image.setBackground(Color.white);
        add(image);

        JButton back = new JButton("Back");
        back.setBounds(150, 220, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        JButton pay = new JButton("Pay");
        pay.setBounds(390, 220, 100, 30);
        pay.setBackground(Color.BLACK);
        pay.setForeground(Color.WHITE);
        add(pay);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TicketDetails(username, cabName, cabNumber, timings, fare, numberOfPeople);
                setVisible(false);
            }
        });

        pay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dashboard(username);
                setVisible(false);
            }
        });

        setVisible(true);
    }

    private double calculateTotalPayment(int numberOfPeople, String fare) {

        String fareWithoutDollarSign = fare.replaceAll("\\$", "");

        double farePerTicket = Double.parseDouble(fareWithoutDollarSign);

        return numberOfPeople * farePerTicket;
    }

    public static void main(String[] args) {

        new PaymentCab("username", "Red Cab", "12345", "10:00 AM", 3, "50.0");
    }
}
