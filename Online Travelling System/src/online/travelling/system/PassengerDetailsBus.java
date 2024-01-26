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

public class PassengerDetailsBus extends JFrame {

    private int numberOfPeople;
    private int currentPassengerIndex = 1;

    private JTextField firstNameField, lastNameField, ageField;
    private JLabel seatNumberLabel, passengerNumberLabel;

    private Conn conn;  

    public PassengerDetailsBus(int numberOfPeople, String username, String busName, String busNumber, String timings, String fare) {
        this.numberOfPeople = numberOfPeople;
        this.conn = new Conn();  

        setBounds(400, 150, 700, 500);
        setLayout(null);
        getContentPane().setBackground(new Color(131, 200, 233));

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

        seatNumberLabel = new JLabel("Seat Number: ");
        seatNumberLabel.setBounds(50, 190, 300, 25);
        seatNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(seatNumberLabel);

        JButton nextPassengerButton = new JButton("Next Passenger");
        nextPassengerButton.setBounds(200, 230, 150, 30);
        nextPassengerButton.setBackground(Color.BLACK);
        nextPassengerButton.setForeground(Color.WHITE);
        add(nextPassengerButton);

        nextPassengerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleNextPassengerButtonClick(username, busName, busNumber, timings, fare);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBackToBookingPage(username, busName, busNumber, timings, fare);
            }
        });

        if (currentPassengerIndex <= numberOfPeople) {
            updateSeatNumberLabel();
        } else {
            JOptionPane.showMessageDialog(this, "All passengers details entered.");
            goBackToBookingPage(username, busName, busNumber, timings, fare);
        }

        setVisible(true);
    }

    private void updateSeatNumberLabel() {
        passengerNumberLabel.setText("Enter Details for Passenger " + currentPassengerIndex);
        seatNumberLabel.setText("Seat Number: " + generateSeatNumber());
    }

    private void handleNextPassengerButtonClick(String username, String busName, String busNumber, String timings, String fare) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String age = ageField.getText();

        if (!firstName.isEmpty() && !lastName.isEmpty() && !age.isEmpty()) {
            // Save the details
            String seatNumber = generateSeatNumber();
            savePassengerDetailsToDatabase(username, busName, busNumber, timings, fare, firstName, lastName, age, seatNumber);

            currentPassengerIndex++;
            System.out.println("Passenger " + (currentPassengerIndex - 1) + " - Name: " + firstName + " " + lastName
                    + ", Age: " + age + ", Seat Number: " + seatNumber);

            if (currentPassengerIndex <= numberOfPeople) {
                updateSeatNumberLabel();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "All passengers details entered.");
                goBackToBookingPage(username, busName, busNumber, timings, fare);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all details.");
        }
    }

    private void savePassengerDetailsToDatabase(String username, String busName, String busNumber, String timings, String fare,
            String firstName, String lastName, String age, String seatNumber) {
        // Establish database connection and insert data
        try {
            String sql = "INSERT INTO BusDetails (username, bus_name, bus_number, timings, fare, people, seat_numbers) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, busName);
                preparedStatement.setString(3, busNumber);
                preparedStatement.setString(4, timings);
                preparedStatement.setString(5, fare);
                preparedStatement.setString(6, numberOfPeople + "");
                preparedStatement.setString(7, seatNumber);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
private String generateSeatNumber() {
        return "L-" + currentPassengerIndex;
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        ageField.setText("");
    }

    private void goBackToBookingPage(String username, String busName, String busNumber, String timings, String fare) {
        new BookTicketsBus(username, busName, busNumber, timings, fare);
        setVisible(false); }

    public static void main(String[] args) {
        new PassengerDetailsBus(3, "username", "Red Bus", "12345", "10:00 AM", "$50"); 
    }
}

