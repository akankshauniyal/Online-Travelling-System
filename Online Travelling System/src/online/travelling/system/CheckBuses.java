package online.travelling.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.Random;

public class CheckBuses extends JFrame {

    JLabel labelusername, labelfrom, labelto;
    String username;
    DefaultTableModel model;

    CheckBuses(String username) {
        this.username = username;
        setBounds(300, 50, 1000, 600);
        setLayout(null);
        getContentPane().setBackground(Color.white);

        JLabel text = new JLabel("BUS DETAILS:");
        text.setBounds(30, 10, 300, 25);
        text.setFont(new Font("Tahoma", Font.PLAIN, 25));
        add(text);

        JLabel lblusername = new JLabel("Username:");
        lblusername.setBounds(30, 60, 150, 25);
        lblusername.setFont(new Font("SAN SERIF", Font.PLAIN, 23));
        add(lblusername);

        labelusername = new JLabel();
        labelusername.setBounds(160, 60, 150, 25);
        labelusername.setFont(new Font("SAN SERIF", Font.PLAIN, 23));
        add(labelusername);

        JLabel from = new JLabel("From");
        from.setBounds(30, 110, 300, 25);
        from.setFont(new Font("Tahoma", Font.PLAIN, 23));
        add(from);

        labelfrom = new JLabel();
        labelfrom.setBounds(30, 160, 150, 25);
        labelfrom.setFont(new Font("SAN SERIF", Font.PLAIN, 23));
        add(labelfrom);

        JLabel to = new JLabel("To");
        to.setBounds(150, 110, 300, 25);
        to.setFont(new Font("Tahoma", Font.PLAIN, 23));
        add(to);

        labelto = new JLabel();
        labelto.setBounds(150, 160, 150, 25);
        labelto.setFont(new Font("SAN SERIF", Font.PLAIN, 23));
        add(labelto);

        JLabel name = new JLabel("Bus Name");
        name.setBounds(30, 240, 300, 25);
        name.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(name);

        JLabel number = new JLabel("Bus Number");
        number.setBounds(260, 240, 300, 25);
        number.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(number);

        JLabel time = new JLabel("Timings");
        time.setBounds(480, 240, 300, 25);
        time.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(time);

        JLabel fare = new JLabel("Fare");
        fare.setBounds(710, 240, 300, 25);
        fare.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(fare);

        JLabel note = new JLabel("Note: Click on the row for which you would like to book the ticket!");
        note.setBounds(30, 500, 800, 25);
        note.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(note);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/bustickets.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(250, 180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(660, 20, 250, 180);
        image.setBackground(Color.white);
        add(image);

        model = new DefaultTableModel();
        JTable table = new JTable(model);

        table.setFont(new Font("Tahoma", Font.PLAIN, 20));
        table.setRowHeight(30);

        model.addColumn("");
        model.addColumn("");
        model.addColumn("");
        model.addColumn("");

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(30, 270, 900, 160);
        add(pane);

        JButton back = new JButton("Back");
        back.setBounds(30, 450, 100, 30);
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

        fetchDataFromDatabase();

        generateRandomData();

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {

                        String busName = (String) model.getValueAt(selectedRow, 0);
                        String busNumber = (String) model.getValueAt(selectedRow, 1);
                        String timings = (String) model.getValueAt(selectedRow, 2);
                        String fare = (String) model.getValueAt(selectedRow, 3);

                        storeDetailsInDatabase(username, busName, busNumber, timings, fare);

                        new BookTicketsBus(username, busName, busNumber, timings, fare);
                    }
                }
            }
        });
        setVisible(true);
    }

    private void storeDetailsInDatabase(String username, String busName, String busNumber, String timings, String fare) {
        try {
            Conn c = new Conn();
            String query = "CREATE TABLE IF NOT EXISTS bus_tickets ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "username VARCHAR(20),"
                    + "bus_name VARCHAR(50),"
                    + "bus_number VARCHAR(20),"
                    + "timings VARCHAR(20),"
                    + "fare VARCHAR(20))";
            c.s.executeUpdate(query);

            query = "INSERT INTO bus_tickets (username, bus_name, bus_number, timings, fare) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = c.c.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, busName);
            ps.setString(3, busNumber);
            ps.setString(4, timings);
            ps.setString(5, fare);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchDataFromDatabase() {
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
    }

    private void generateRandomData() {
        String[] busNames = {"Red Bus", "MakeMyTrip", "SRTC", "Volvo", "Prasanna Purple"};
        Random random = new Random();

        for (int i = 0; i < busNames.length; i++) {
            model.addRow(new Object[]{
                busNames[i],
                String.valueOf(1000 + random.nextInt(9000)),
                generateRandomTime(),
                String.valueOf(300 + (i * 10) + random.nextInt(350)),});
        }
    }

    private String generateRandomTime() {
        Random random = new Random();
        int hour = random.nextInt(12) + 1;
        int minute = random.nextInt(60);
        String ampm = (random.nextBoolean()) ? "AM" : "PM";
        return String.format("%02d:%02d %s", hour, minute, ampm);
    }

    public static void main(String[] args) {
        new CheckBuses("");
    }
}
