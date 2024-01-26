package online.travelling.system;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class Loading extends JFrame implements Runnable {

    Thread t;
    JProgressBar bar;
    String username;

    @Override
    public void run() {
        try {
            for (int i = 1; i <= 101; i++) {
                int max = bar.getMaximum();
                int value = bar.getValue();

                if (value < max) {
                    bar.setValue(bar.getValue() + 1);
                } else {
                    setVisible(false);
                    new Dashboard(username);
                }
                Thread.sleep(60);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Loading(String username) {
        this.username = username;
        t = new Thread(this);
        setBounds(500, 200, 650, 400);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel text = new JLabel("Online Travelling System ");
        text.setBounds(120, 20, 600, 40);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Raleway", Font.BOLD, 32));
        add(text);

        bar = new JProgressBar();
        bar.setBounds(150, 100, 300, 32);
        bar.setStringPainted(true);
        add(bar);

        JLabel loading = new JLabel("This may take some time!");
        loading.setBounds(195, 140, 500, 40);
        loading.setForeground(Color.RED);
        loading.setFont(new Font("Raleway", Font.BOLD, 16));
        add(loading);

        JLabel lusername = new JLabel("Welcome" + " " + username);
        lusername.setBounds(20, 310, 440, 40);
        lusername.setForeground(Color.BLACK);
        lusername.setFont(new Font("Raleway", Font.BOLD, 16));
        add(lusername);

        // Set default close operation to EXIT_ON_CLOSE
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add a window listener to handle the window closing event
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0); // Terminate the program when the window is closed
            }
        });

        t.start();
        setVisible(true);
    }

    public static void main(String[] args) {
        new Loading("");
    }
}
