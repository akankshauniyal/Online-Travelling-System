package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookTicketsBus extends JFrame {

    public BookTicketsBus(String username, String busName, String busNumber, String timings, String fare) {
        setBounds(400, 150, 700, 500);
        setLayout(null);
        getContentPane().setBackground(Color.white);

        JLabel titleLabel = new JLabel("Please Enter The Desired Details");
        titleLabel.setBounds(200, 20, 350, 25);
        titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(titleLabel);

        JLabel trainNameLabel = new JLabel("Bus Name: " + busName);
        trainNameLabel.setBounds(50, 70, 500, 25);
        trainNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(trainNameLabel);

        JLabel trainNumberLabel = new JLabel("Bus Number: " + busNumber);
        trainNumberLabel.setBounds(50, 110, 500, 25);
        trainNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(trainNumberLabel);

        JLabel timingsLabel = new JLabel("Timings: " + timings);
        timingsLabel.setBounds(50, 150, 500, 25);
        timingsLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(timingsLabel);

        JLabel fareLabel = new JLabel("Fare: " + fare);
        fareLabel.setBounds(50, 190, 500, 25);
        fareLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(fareLabel);

        JLabel numberOfPeopleLabel = new JLabel("Number of People:");
        numberOfPeopleLabel.setBounds(50, 230, 150, 25);
        numberOfPeopleLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(numberOfPeopleLabel);

        JTextField numberOfPeopleField = new JTextField();
        numberOfPeopleField.setBounds(200, 230, 50, 25);
        add(numberOfPeopleField);

        JLabel view = new JLabel("View Ticket Details:");
        view.setBounds(50, 290, 300, 25);
        view.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(view);

        JButton bookButton = new JButton("Enter");
        bookButton.setBounds(300, 230, 100, 30);
        bookButton.setBackground(Color.BLACK);
        bookButton.setForeground(Color.WHITE);
        add(bookButton);

        JButton viewb = new JButton("View Details");
        viewb.setBounds(200, 290, 150, 30);
        viewb.setBackground(Color.BLACK);
        viewb.setForeground(Color.WHITE);
        add(viewb);

        JButton back = new JButton("Back");
        back.setBounds(400, 290, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        add(back);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dashboard(username);
                setVisible(false);
            }
        });

        viewb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numberOfPeopleText = numberOfPeopleField.getText();
                if (!numberOfPeopleText.isEmpty()) {
                    int numberOfPeople = Integer.parseInt(numberOfPeopleText);
                    new TicketDetailsBus(username, busName, busNumber, timings, fare, numberOfPeople);
                } else {
                    JOptionPane.showMessageDialog(BookTicketsBus.this, "Please enter the number of people.");
                }
            }
        });

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEnterButtonClick(username, busName, busNumber, timings, fare, numberOfPeopleField);
            }
        });

        setVisible(true);
    }

    private void handleEnterButtonClick(String username, String busName, String busNumber, String timings, String fare, JTextField numberOfPeopleField) {
        String numberOfPeopleText = numberOfPeopleField.getText();
        if (!numberOfPeopleText.isEmpty()) {
            int numberOfPeople = Integer.parseInt(numberOfPeopleText);
            new PassengerDetailsBus(numberOfPeople, username, busName, busNumber, timings, fare);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter the number of people.");
        }
    }

    public static void main(String[] args) {
        new BookTicketsBus("username", "Red Bus", "12345", "10:00 AM", "$50");
    }
}
