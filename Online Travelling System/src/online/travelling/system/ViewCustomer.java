package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class ViewCustomer extends JFrame implements ActionListener {

    JButton back;
    JLabel labelusername, labelid, labelnumber, labelname, labelgender, labelcountry, labeladd, labelemail, labelphone;

    ViewCustomer(String username) {

        setBounds(450, 180, 870, 625);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblusername = new JLabel("Username:");
        lblusername.setBounds(30, 50, 150, 25);
        lblusername.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(lblusername);

        labelusername = new JLabel();
        labelusername.setBounds(220, 50, 150, 25);
        labelusername.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(labelusername);

        JLabel lblid = new JLabel("ID:");
        lblid.setBounds(30, 110, 150, 25);
        lblid.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(lblid);

        labelid = new JLabel();
        labelid.setBounds(220, 110, 150, 25);
        labelid.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(labelid);

        JLabel lblnumber = new JLabel("Number:");
        lblnumber.setBounds(30, 170, 150, 25);
        lblnumber.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(lblnumber);

        labelnumber = new JLabel();
        labelnumber.setBounds(220, 170, 150, 25);
        labelnumber.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(labelnumber);

        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(30, 230, 150, 25);
        lblname.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(lblname);

        labelname = new JLabel();
        labelname.setBounds(220, 230, 150, 25);
        labelname.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(labelname);

        JLabel lblgender = new JLabel("Gender:");
        lblgender.setBounds(30, 290, 150, 25);
        lblgender.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(lblgender);

        labelgender = new JLabel();
        labelgender.setBounds(220, 290, 150, 25);
        labelgender.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(labelgender);

        JLabel lblcountry = new JLabel("Country:");
        lblcountry.setBounds(450, 50, 150, 25);
        lblcountry.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(lblcountry);

        labelcountry = new JLabel();
        labelcountry.setBounds(640, 50, 150, 25);
        labelcountry.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(labelcountry);

        JLabel lbladd = new JLabel("Address:");
        lbladd.setBounds(450, 110, 150, 25);
        lbladd.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(lbladd);

        labeladd = new JLabel();
        labeladd.setBounds(640, 110, 150, 25);
        labeladd.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(labeladd);

        JLabel lblemail = new JLabel("Email:");
        lblemail.setBounds(450, 170, 150, 25);
        lblemail.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(lblemail);

        labelemail = new JLabel();
        labelemail.setBounds(640, 170, 190, 25);
        labelemail.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(labelemail);

        JLabel lblphone = new JLabel("Contact Number:");
        lblphone.setBounds(450, 230, 150, 25);
        lblphone.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(lblphone);

        labelphone = new JLabel();
        labelphone.setBounds(640, 230, 150, 25);
        labelphone.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(labelphone);

        back = new JButton("Back");
        back.setBounds(350, 350, 100, 25);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBorder(BorderFactory.createEmptyBorder());
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/customer.jpeg"));
        Image i2 = i1.getImage().getScaledInstance(470, 200, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(10, 400, 470, 200);
        add(image);

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/customer.jpeg"));
        Image i5 = i4.getImage().getScaledInstance(400, 200, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel image1 = new JLabel(i6);
        image1.setBounds(450, 400, 400, 200);
        add(image1);

        try {
            Conn c = new Conn();
            String query = "select * from customer where username= '" + username + "'";
            ResultSet rs = c.s.executeQuery(query);
            while (rs.next()) {
                labelusername.setText(rs.getString("username"));
                labelid.setText(rs.getString("id"));
                labelnumber.setText(rs.getString("number"));
                labelname.setText(rs.getString("name"));
                labelgender.setText(rs.getString("gender"));
                labelcountry.setText(rs.getString("country"));
                labeladd.setText(rs.getString("address"));
                labelemail.setText(rs.getString("email"));
                labelphone.setText(rs.getString("phone"));
            }
        } catch (Exception e) {

        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new ViewCustomer("akku");
    }
}
