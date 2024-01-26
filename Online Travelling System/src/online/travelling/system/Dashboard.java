package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {

    String username;
    JButton personaldetails, viewpersonaldetails, updatepersonaldetails, date, cancel, delpersonaldetails, about, logout;

    Dashboard(String username) {
        this.username = username;
        //setBounds(0,0,1600,1000);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(null);

        JPanel p1 = new JPanel();
        p1.setLayout(null);
        p1.setBackground(new Color(0, 0, 102));
        p1.setBounds(0, 0, 1600, 65);
        add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/download2.png"));
        Image i2 = i1.getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(5, 0, 70, 70);
        p1.add(image);

        JLabel heading = new JLabel("DASHBOARD");
        heading.setBounds(90, 10, 300, 40);
        heading.setForeground(Color.white);
        heading.setFont(new Font("Tahoma", Font.BOLD, 18));
        p1.add(heading);

        JPanel p2 = new JPanel();
        p2.setLayout(null);
        p2.setBackground(new Color(0, 0, 102));
        p2.setBounds(0, 65, 300, 900);
        add(p2);

        personaldetails = new JButton("Add Personal Details");
        personaldetails.setBounds(0, 0, 300, 50);
        personaldetails.setFont(new Font("Tahoma", Font.BOLD, 17));
        personaldetails.setBackground(new Color(0, 0, 102));
        personaldetails.setForeground(Color.WHITE);
        personaldetails.setMargin(new Insets(0, 0, 0, 60));
        personaldetails.addActionListener(this);
        p2.add(personaldetails);

        updatepersonaldetails = new JButton("Update Personal Details");
        updatepersonaldetails.setBounds(0, 50, 300, 50);
        updatepersonaldetails.setFont(new Font("Tahoma", Font.BOLD, 17));
        updatepersonaldetails.setBackground(new Color(0, 0, 102));
        updatepersonaldetails.setForeground(Color.WHITE);
        updatepersonaldetails.setMargin(new Insets(0, 0, 0, 30));
        updatepersonaldetails.addActionListener(this);
        p2.add(updatepersonaldetails);

        viewpersonaldetails = new JButton(" View Details");
        viewpersonaldetails.setBounds(0, 99, 300, 50);
        viewpersonaldetails.setFont(new Font("Tahoma", Font.BOLD, 17));
        viewpersonaldetails.setBackground(new Color(0, 0, 102));
        viewpersonaldetails.setForeground(Color.WHITE);
        viewpersonaldetails.setMargin(new Insets(0, 0, 0, 130));
        viewpersonaldetails.addActionListener(this);
        p2.add(viewpersonaldetails);

        delpersonaldetails = new JButton(" Delete Personal Details");
        delpersonaldetails.setBounds(0, 148, 300, 50);
        delpersonaldetails.setFont(new Font("Tahoma", Font.BOLD, 17));
        delpersonaldetails.setBackground(new Color(0, 0, 102));
        delpersonaldetails.setForeground(Color.WHITE);
        delpersonaldetails.setMargin(new Insets(0, 0, 0, 41));
        delpersonaldetails.addActionListener(this);
        p2.add(delpersonaldetails);

        date = new JButton(" Enter Departure Details ");
        date.setBounds(0, 197, 400, 50);
        date.setFont(new Font("Tahoma", Font.BOLD, 17));
        date.setBackground(new Color(0, 0, 102));
        date.setForeground(Color.WHITE);
        date.addActionListener(this);
        date.setMargin(new Insets(0, 0, 0, 133));
        p2.add(date);

        cancel = new JButton(" Cancel Booking ");
        cancel.setBounds(0, 246, 300, 50);
        cancel.setFont(new Font("Tahoma", Font.BOLD, 17));
        cancel.setBackground(new Color(0, 0, 102));
        cancel.setForeground(Color.WHITE);
        cancel.addActionListener(this);
        cancel.setMargin(new Insets(0, 0, 0, 100));
        p2.add(cancel);

        about = new JButton(" About ");
        about.setBounds(0, 295, 300, 50);
        about.setFont(new Font("Tahoma", Font.BOLD, 17));
        about.setBackground(new Color(0, 0, 102));
        about.setForeground(Color.WHITE);
        about.setMargin(new Insets(0, 0, 0, 180));
        about.addActionListener(this);
        p2.add(about);
        
        logout = new JButton(" Log Out ");
        logout.setBounds(0, 677, 300, 50);
        logout.setFont(new Font("Tahoma", Font.BOLD, 17));
        logout.setBackground(new Color(0, 0, 102));
        logout.setForeground(Color.WHITE);
        logout.addActionListener(this);
        logout.setMargin(new Insets(0, 0, 0, 180));
        p2.add(logout);


        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/TRAINIMAGE.jpeg"));
        Image i5 = i4.getImage().getScaledInstance(1650, 1000, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image1 = new JLabel(i6);
        image1.setBounds(0, 0, 1650, 1000);
        add(image1);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == personaldetails) {
            new AddCustomer(username);
        } else if (ae.getSource() == viewpersonaldetails) {
            new ViewCustomer(username);
        } else if (ae.getSource() == updatepersonaldetails) {
            new UpdateDetails(username);
        } else if (ae.getSource() == date) {
            new DepartureDetails(username);
        } else if (ae.getSource() == delpersonaldetails) {
            new DeleteCustomer(username);
        } else if (ae.getSource() == cancel) {
            new CancelBooking(username);
        } else if (ae.getSource() == about) {
            new About(username);
        }
        else if (ae.getSource() == logout) {
            setVisible(false);
            new Login();}

    }

    public static void main(String[] args) {
        new Dashboard("");
    }
}
