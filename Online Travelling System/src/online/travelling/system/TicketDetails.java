package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketDetails extends JFrame {

    private String username;
    private int numberOfPeople;
    private String trainName;
    private String trainNumber;
    private String timings;
    private String fare;

    JLabel labelusername, labelfrom, labelto, trainname, trainno, nopeople, noseats, labelfare, labeltime;

    public TicketDetails(String username, String trainName, String trainNumber, String timings, String fare, int numberOfPeople) {
        this.username = username;
        this.trainName = trainName;
        this.trainNumber = trainNumber;
        this.timings = timings;
        this.fare = fare;
        this.numberOfPeople = numberOfPeople;

        setBounds(400, 150, 750, 600);
        setLayout(null);
        getContentPane().setBackground(Color.white);

        JLabel text = new JLabel("TICKET DETAILS:");
        text.setBounds(30, 10, 300, 25);
        text.setFont(new Font("Tahoma", Font.PLAIN, 25));
        add(text);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/ticket1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(250, 180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(450, 50, 250, 180);
        image.setBackground(Color.white);
        add(image);

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/ticket2.jpg"));
        Image i5 = i4.getImage().getScaledInstance(250, 180, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image1 = new JLabel(i6);
        image1.setBounds(450, 260, 280, 190);
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

        JLabel train_name = new JLabel("Train Name:");
        train_name.setBounds(30, 210, 300, 25);
        train_name.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(train_name);

        trainname = new JLabel();
        trainname.setBounds(200, 210, 250, 25);
        trainname.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(trainname);

        JLabel train_no = new JLabel("Train Number:");
        train_no.setBounds(30, 260, 300, 25);
        train_no.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(train_no);

        trainno = new JLabel();
        trainno.setBounds(200, 260, 150, 25);
        trainno.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(trainno);

        JLabel time = new JLabel("Timings:");
        time.setBounds(30, 310, 300, 25);
        time.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(time);

        labeltime = new JLabel();
        labeltime.setBounds(200, 310, 150, 25);
        labeltime.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(labeltime);

        JLabel farer = new JLabel("Fare per ticket:");
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

        JLabel seats = new JLabel("Seats:");
        seats.setBounds(30, 460, 300, 25);
        seats.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(seats);

        noseats = new JLabel();
        noseats.setBounds(200, 460, 150, 25);
        noseats.setFont(new Font("SAN SERIF", Font.PLAIN, 17));
        add(noseats);

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
                new BookTicketsTrain(username, trainName, trainNumber, timings, fare);
                setVisible(false);
            }
        });

        pay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PaymentTrain(username, trainName, trainNumber, timings, numberOfPeople, fare);
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
        trainname.setText(trainName);
        trainno.setText(trainNumber);
        labeltime.setText(timings);
        labelfare.setText(fare);
        nopeople.setText(Integer.toString(numberOfPeople));

        StringBuilder noseatsValue = new StringBuilder();
        for (int i = 1; i <= numberOfPeople; i++) {
            noseatsValue.append("A-").append(i);
            if (i < numberOfPeople) {
                noseatsValue.append(", ");
            }
        }
        noseats.setText(noseatsValue.toString());

    }

    public static void main(String[] args) {

        new TicketDetails("username", "Rajdhani Express", "12345", "10:00 AM", "$50", 3);
    }
}
