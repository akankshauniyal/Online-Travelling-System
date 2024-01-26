package online.travelling.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class AddCustomer extends JFrame implements ActionListener {

    JLabel labelusername, labelname;
    JComboBox comboid;
    JTextField tfnumber, tfaddress, tfcountry, tfemail, tfphone;
    JRadioButton male, female, other;
    JButton add, back;
    String username;

    AddCustomer(String username) {
        this.username = username;
        setBounds(450, 200, 850, 550);
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

        JLabel ibid = new JLabel("ID: ");
        ibid.setBounds(30, 90, 150, 25);
        ibid.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        add(ibid);

        comboid = new JComboBox(new String[]{"Aadhar Card", "Pan Card", "Ration Card"});
        comboid.setBounds(220, 90, 150, 25);
        comboid.setBackground(Color.WHITE);
        add(comboid);

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

        male = new JRadioButton("Male");
        male.setBounds(220, 210, 70, 25);
        male.setBackground(Color.white);
        add(male);

        female = new JRadioButton("Female");
        female.setBounds(300, 210, 70, 25);
        female.setBackground(Color.white);
        add(female);

        other = new JRadioButton("Other");
        other.setBounds(380, 210, 70, 25);
        other.setBackground(Color.white);
        add(other);

        ButtonGroup bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);
        bg.add(other);

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

        add = new JButton("Add");
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

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/newcustomer.jpg"));
        Image i2 = i1.getImage().getScaledInstance(400, 500, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400, 40, 450, 420);
        add(image);

        try {
            Conn c = new Conn();
            String query1 = "select * from account where username= '" + username + "'";
            ResultSet rs = c.s.executeQuery(query1);
            while (rs.next()) {
                labelusername.setText(rs.getString("username"));
                labelname.setText(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true);

    }

public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == add) {
        String username = labelusername.getText();
        String id = (String) comboid.getSelectedItem();
        String number = tfnumber.getText();
        String name = labelname.getText();
        String gender = null;

        // Validate if ID, Number, email, and contact Number are not empty
        if (id.isEmpty() || number.isEmpty() || tfemail.getText().isEmpty() || tfphone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID, Number, Email, and Contact Number are mandatory fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (male.isSelected()) {
            gender = " Male";
        } else if (female.isSelected()) {
            gender = "Female";
        } else {
            gender = "Other";
        }

        String country = tfcountry.getText();
        String address = tfaddress.getText();
        String email = tfemail.getText();
        String phone = tfphone.getText();
        
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
            String query = "insert into customer values('" + username + "','" + id + "','" + number + "','" + name + "', '" + gender + "','" + country + "','" + address + "','" + email + "','" + phone + "')";
            c.s.executeUpdate(query);

            JOptionPane.showMessageDialog(null, " Customer Details Added Successfully!");
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
        new AddCustomer("");
    }
}