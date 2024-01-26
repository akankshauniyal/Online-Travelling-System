package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDetailsCab extends JFrame {

    private String username;
    private int numberOfPeople;
    private String cabName;
    private String cabNumber;
    private String timings;
    private String fare;

    JLabel labelusername, labelfrom, labelto, cabname, cabno, nopeople, noseats, labelfare, labeltime;

    public TicketDetailsCab(String username, String cabName, String cabNumber, String timings, String fare, int numberOfPeople) {
        this.username = username;
        this.cabName = cabName;
        this.cabNumber = cabNumber;
        this.timings = timings;
        this.fare = fare;
        this.numberOfPeople = numberOfPeople;

        setBounds(400, 150, 750, 600);
        setLayout(null);
        getContentPane().setBackground(Color.white);

        JLabel text = new JLabel("CAB BOOKING DETAILS:");
        text.setBounds(30, 10, 300, 25);
        text.setFont(new Font("Tahoma", Font.PLAIN, 25));
        add(text);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/ticket5.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(270, 180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(450, 50, 270, 180);
        image.setBackground(Color.white);
        add(image);

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/ticket6.png"));
        Image i5 = i4.getImage().getScaledInstance(270, 250, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image1 = new JLabel(i6);
        image1.setBounds(450, 260, 290, 250);
        image1.setBackground(Color.white);
        add(image1);

        JLabel lblusername = new JLabel("Username:");
        lblusername.setBounds(30, 60, 150, 25);
        lblusername.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(lblusername);

        labelusername = new JLabel();
        labelusername.setBounds(200, 60, 150, 25);
        labelusername.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(labelusername);

        JLabel from = new JLabel("From:");
        from.setBounds(30, 110, 300, 25);
        from.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(from);

        labelfrom = new JLabel();
        labelfrom.setBounds(200, 110, 150, 25);
        labelfrom.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(labelfrom);

        JLabel to = new JLabel("To:");
        to.setBounds(30, 160, 300, 25);
        to.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(to);

        labelto = new JLabel();
        labelto.setBounds(200, 160, 150, 25);
        labelto.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(labelto);

        JLabel cab_name = new JLabel("Cab Name:");
        cab_name.setBounds(30, 210, 300, 25);
        cab_name.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(cab_name);

        cabname = new JLabel();
        cabname.setBounds(200, 210, 250, 25);
        cabname.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(cabname);

        JLabel cab_no = new JLabel("Cab Number:");
        cab_no.setBounds(30, 260, 300, 25);
        cab_no.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(cab_no);

        cabno = new JLabel();
        cabno.setBounds(200, 260, 150, 25);
        cabno.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(cabno);

        JLabel time = new JLabel("Timings:");
        time.setBounds(30, 310, 300, 25);
        time.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(time);

        labeltime = new JLabel();
        labeltime.setBounds(200, 310, 150, 25);
        labeltime.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(labeltime);

        JLabel farer = new JLabel("Fare per person:");
        farer.setBounds(30, 360, 300, 25);
        farer.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(farer);

        labelfare = new JLabel();
        labelfare.setBounds(200, 360, 150, 25);
        labelfare.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(labelfare);

        JLabel people = new JLabel("Number of people:");
        people.setBounds(30, 410, 300, 25);
        people.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(people);

        nopeople = new JLabel();
        nopeople.setBounds(200, 410, 150, 25);
        nopeople.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(nopeople);

        JButton back = new JButton("Back");
        back.setBounds(30, 520, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        JButton pay = new JButton("Pay");
        pay.setBounds(200, 520, 100, 30);
        pay.setBackground(Color.BLACK);
        pay.setForeground(Color.WHITE);
        add(pay);

        fetchAndDisplayData();

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookTicketsCab(username, cabName, cabNumber, timings, fare);
                setVisible(false);
            }
        });

        pay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PaymentCab(username, cabName, cabNumber, timings, numberOfPeople, fare);
                setVisible(false);
            }
        });

        setVisible(true);
    }

    private void fetchAndDisplayData() {

        try {
            Conn c = new Conn();
            String query = "SELECT * FROM date WHERE username = '" + username + "'";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                labelusername.setText(rs.getString("username"));
                labelfrom.setText(rs.getString("fromdest"));
                labelto.setText(rs.getString("todest"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        labelusername.setText(username);
        cabname.setText(cabName);
        cabno.setText(cabNumber);
        labeltime.setText(timings);
        labelfare.setText(fare);
        nopeople.setText(Integer.toString(numberOfPeople));

    }

    public static void main(String[] args) {

        new TicketDetailsCab("username", "Red Cab", "12345", "10:00 AM", "$50", 3);
    }
}
