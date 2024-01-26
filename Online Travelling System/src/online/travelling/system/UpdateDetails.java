package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateDetails extends JFrame implements ActionListener {

    JLabel labelusername, labelname;
    JComboBox comboid;
    JTextField tfnumber, tfaddress, tfcountry, tfemail, tfphone, tfid, tfgender;
    JRadioButton male, female, other;
    JButton add, back;
    String username;

    UpdateDetails(String username) {
        this.username = username;
        setBounds(500, 200, 850, 550);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel text = new JLabel("UPDATE CUSTOMER DETAILS:");
        text.setBounds(50, 0, 300, 25);
        text.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(text);

        JLabel lblusername = new JLabel("Username:");
        lblusername.setBounds(30, 50, 150, 25);
        lblusername.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(lblusername);

        labelusername = new JLabel();
        labelusername.setBounds(220, 50, 150, 25);
        labelusername.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(labelusername);

        JLabel ibid = new JLabel("ID: ");
        ibid.setBounds(30, 90, 150, 25);
        ibid.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(ibid);

        tfid = new JTextField();
        tfid.setBounds(220, 90, 150, 25);
        add(tfid);

        JLabel lblnumber = new JLabel("Number: ");
        lblnumber.setBounds(30, 130, 150, 25);
        lblnumber.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(lblnumber);

        tfnumber = new JTextField();
        tfnumber.setBounds(220, 130, 150, 25);
        add(tfnumber);

        JLabel lblname = new JLabel("Name:");
        lblname.setBounds(30, 170, 150, 25);
        lblname.setFont(new Font("SAN SERIF", Font.PLAIN, 18));
        add(lblname);

        labelname = new JLabel();
        labelname.setBounds(220, 170, 150, 25);
        labelname.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(labelname);

        JLabel lblgender = new JLabel("Gender:");
        lblgender.setBounds(30, 210, 150, 25);
        lblgender.setFont(new Font("SAN SERIF", Font.PLAIN, 18));
        add(lblgender);

        tfgender = new JTextField();
        tfgender.setBounds(220, 210, 150, 25);
        add(tfgender);

        JLabel country = new JLabel("Country:");
        country.setBounds(30, 250, 150, 25);
        country.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(country);

        tfcountry = new JTextField();
        tfcountry.setBounds(220, 250, 150, 25);
        add(tfcountry);

        JLabel lbladdress = new JLabel("Address:");
        lbladdress.setBounds(30, 290, 150, 25);
        lbladdress.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(lbladdress);

        tfaddress = new JTextField();
        tfaddress.setBounds(220, 290, 150, 25);
        add(tfaddress);

        JLabel email = new JLabel("Email ID: ");
        email.setBounds(30, 330, 150, 25);
        email.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(email);

        tfemail = new JTextField();
        tfemail.setBounds(220, 330, 150, 25);
        add(tfemail);

        JLabel phone = new JLabel("Contact Number: ");
        phone.setBounds(30, 370, 150, 25);
        phone.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(phone);

        tfphone = new JTextField();
        tfphone.setBounds(220, 370, 150, 25);
        add(tfphone);

        add = new JButton("Update");
        add.setBounds(70, 430, 100, 25);
        add.setBackground(Color.BLACK);
        add.setForeground(Color.WHITE);
        add.setBorder(BorderFactory.createEmptyBorder());
        add.addActionListener(this);
        add(add);

        back = new JButton("Back");
        back.setBounds(220, 430, 100, 25);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.setBorder(BorderFactory.createEmptyBorder());
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/update.png"));
        Image i2 = i1.getImage().getScaledInstance(310, 360, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(440, 60, 310, 360);
        add(image);

        try {
            Conn c = new Conn();
            String query1 = "select * from customer where username= '" + username + "'";
            ResultSet rs = c.s.executeQuery(query1);
            while (rs.next()) {
                labelusername.setText(rs.getString("username"));
                labelname.setText(rs.getString("name"));
                tfid.setText(rs.getString("id"));
                tfnumber.setText(rs.getString("number"));
                tfgender.setText(rs.getString("gender"));
                tfcountry.setText(rs.getString("country"));
                tfaddress.setText(rs.getString("address"));
                tfemail.setText(rs.getString("email"));
                tfphone.setText(rs.getString("phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == add) {
            String username = labelusername.getText();
            String id = tfid.getText();
            String number = tfnumber.getText();
            String name = labelname.getText();
            String gender = tfgender.getText();
            String country = tfcountry.getText();
            String address = tfaddress.getText();
            String email = tfemail.getText();
            String phone = tfphone.getText();

            // Validate if ID, Number, email, and contact Number are not empty
            if (id.isEmpty() || number.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID, Number, Email, and Contact Number are mandatory fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate PAN card number
if (id.equals("Pan Card") && !isValidPanNumber(number)) {
    JOptionPane.showMessageDialog(this, "Invalid PAN Card Number. Check Format!", "Validation Error", JOptionPane.ERROR_MESSAGE);
    return;
}
        // Validate Ration Card number
if (id.equals("Ration Card") && !isValidRationCardNumber(number)) {
    JOptionPane.showMessageDialog(this, "Invalid Ration Card Number. Check Format!", "Validation Error", JOptionPane.ERROR_MESSAGE);
    return;
}


        // Validate email format
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid Email Format!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate phone number format
        if (!isValidPhoneNumber(phone)) {
            JOptionPane.showMessageDialog(this, "Invalid Phone Number!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate Aadhar card number
        if (id.equals("Aadhar Card") && !isValidAadharNumber(number)) {
            JOptionPane.showMessageDialog(this, "Invalid Aadhar Card Number. Check Format!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

            try {
                Conn c = new Conn();
                String query = "update customer set username= '" + username + "', id= '" + id + "', number='" + number + "',name='" + name + "',gender= '" + gender + "',country='" + country + "',address='" + address + "',email='" + email + "',phone='" + phone + "'";
                c.s.executeUpdate(query);

                JOptionPane.showMessageDialog(null, " Customer Details Updated Successfully!");
                setVisible(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setVisible(false);
        }
    }

   private boolean isValidEmail(String email) {
    // Regular expression for a valid email address
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    // Compile the regex pattern
    Pattern pattern = Pattern.compile(emailRegex);

    // Match the given email with the pattern
    Matcher matcher = pattern.matcher(email);

    // Return true if the email matches the pattern, else false
    return matcher.matches();
}

private boolean isValidAadharNumber(String aadharNumber) {
    // Check if it's a 12-digit number
    if (aadharNumber.matches("\\d{12}")) {
        return true;
    } else {
       
        return false;
    }
}
    
private boolean isValidPanNumber(String panNumber) {
    // Check if it's in the format ABCD1234X
    return panNumber.matches("[A-Z]{4}[0-9]{4}[A-Z]");
}

    
private boolean isValidPhoneNumber(String phoneNumber) {
    // Check if it's a 10-digit number
    return phoneNumber.matches("\\d{10}");
}

private boolean isValidRationCardNumber(String rationCardNumber) {
    // Check if it's a 10-digit number
    return rationCardNumber.matches("\\d{10}");
}



    public static void main(String[] args) {
        new UpdateDetails("");
    }
}