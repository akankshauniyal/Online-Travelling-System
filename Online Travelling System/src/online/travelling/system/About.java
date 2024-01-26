package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class About extends JFrame implements ActionListener {

    String username;

    About(String username) {
        this.username = username;
        setSize(650, 400);
        setLocation(400, 200);
        setLayout(null);
        getContentPane().setBackground(new Color(131, 200, 233));

        JLabel heading = new JLabel("We Welcome You to our Online Travelling System!");
        heading.setBounds(50, 20, 600, 30);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 22));
        add(heading);

        JTextArea aboutText = new JTextArea();
        aboutText.setText("Our platform provides a convenient and easy way to book tickets for cabs, buses, and trains. "
                + "Whether you're planning a short trip or a long journey, we've got you covered. "
                + "Enjoy the comfort of hassle-free booking from the comfort of your home. "
                + "Explore our user-friendly interface and experience a seamless travel booking process.\n\n\n"
                + "Thank you for choosing our Online Traveling System!");

        aboutText.setBounds(53, 70, 557, 200);
        aboutText.setFont(new Font("Tahoma", Font.PLAIN, 17));
        aboutText.setEditable(false);
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);
        add(aboutText);

        JButton backButton = new JButton("Back");
        backButton.setBounds(250, 300, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JButton) {
            new Dashboard(username);
            setVisible(false);

        }
    }

    public static void main(String[] args) {
        new About("");
    }
}
