package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PassengerDetailsCab extends JFrame {

    private int numberOfPeople;
    private int currentPassengerIndex = 1;

    private JTextField firstNameField, lastNameField, ageField;
    private JLabel seatNumberLabel, passengerNumberLabel;

    private Conn conn;

    public PassengerDetailsCab(int numberOfPeople, String username, String cabName, String cabNumber, String timings, String fare) {
        this.numberOfPeople = numberOfPeople;
        this.conn = new Conn();
        setBounds(400, 150, 700, 500);
        setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 204));

        passengerNumberLabel = new JLabel("Enter Details for Passenger " + currentPassengerIndex);
        passengerNumberLabel.setBounds(200, 20, 350, 25);
        passengerNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(passengerNumberLabel);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(50, 70, 150, 25);
        firstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(firstNameLabel);

        firstNameField = new JTextField();
        firstNameField.setBounds(200, 70, 150, 25);
        add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(50, 110, 150, 25);
        lastNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(200, 110, 150, 25);
        add(lastNameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 150, 150, 25);
        ageLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(ageLabel);

        ageField = new JTextField();
        ageField.setBounds(200, 150, 50, 25);
        add(ageField);

        JButton nextPassengerButton = new JButton("Next Passenger");
        nextPassengerButton.setBounds(200, 230, 150, 30);
        nextPassengerButton.setBackground(Color.BLACK);
        nextPassengerButton.setForeground(Color.WHITE);
        add(nextPassengerButton);

        nextPassengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNextPassengerButtonClick(username, cabName, cabNumber, timings, fare);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBackToBookingPage(username, cabName, cabNumber, timings, fare);
            }
        });

        if (currentPassengerIndex <= numberOfPeople) {
            updateSeatNumberLabel();
        } else {
            JOptionPane.showMessageDialog(this, "All passengers details entered.");
            goBackToBookingPage(username, cabName, cabNumber, timings, fare);
        }

        setVisible(true);
    }

    private void updateSeatNumberLabel() {
        passengerNumberLabel.setText("Enter Details for Passenger " + currentPassengerIndex);

    }

    private void handleNextPassengerButtonClick(String username, String cabName, String cabNumber, String timings, String fare) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String age = ageField.getText();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !age.isEmpty()) {

            savePassengerDetailsToDatabase(username, cabName, cabNumber, timings, fare, firstName, lastName, age);

            currentPassengerIndex++;
            System.out.println("Passenger " + (currentPassengerIndex - 1) + " - Name: " + firstName + " " + lastName
                    + ", Age: " + age);

            if (currentPassengerIndex <= numberOfPeople) {
                updateSeatNumberLabel();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "All passengers details entered.");
                goBackToBookingPage(username, cabName, cabNumber, timings, fare);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all details.");
        }
    }

    private void savePassengerDetailsToDatabase(String username, String cabName, String cabNumber, String timings, String fare,
            String firstName, String lastName, String age) {
        // Establish database connection and insert data
        try {
            String sql = "INSERT INTO CabDetails (username, cab_name, cab_number, timings, fare, people) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, cabName);
                preparedStatement.setString(3, cabNumber);
                preparedStatement.setString(4, timings);
                preparedStatement.setString(5, fare);
                preparedStatement.setString(6, numberOfPeople + "");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        ageField.setText("");
    }

    private void goBackToBookingPage(String username, String cabName, String cabNumber, String timings, String fare) {
        new BookTicketsCab(username, cabName, cabNumber, timings, fare);
        setVisible(false);
    }

    public static void main(String[] args) {
        new PassengerDetailsCab(3, "username", "Red Cab", "12345", "10:00 AM", "$50"); // Example with 3 passengers
    }
}
