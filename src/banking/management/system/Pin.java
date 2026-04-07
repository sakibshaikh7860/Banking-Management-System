package banking.management.system;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Pin extends JFrame implements ActionListener {

    JPasswordField newPinField, rePinField;
    JButton changeBtn, backBtn;
    String pin, Accountno;

    public Pin(String pin, String Accountno) {
        this.pin = pin;
        this.Accountno = Accountno;

        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Secure Bank - Change PIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(30, 30, 35));

        // Logo
        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image logoImg = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImg));
        logo.setBounds(40, 30, 80, 80);
        add(logo);

        // Title
        JLabel title = new JLabel("Change Your PIN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(100, 255, 150));
        title.setBounds(180, 60, 400, 50);
        add(title);

        // New PIN label & field
        JLabel lblNew = new JLabel("New PIN");
        lblNew.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblNew.setForeground(Color.WHITE);
        lblNew.setBounds(150, 180, 200, 40);
        add(lblNew);

        newPinField = new JPasswordField();
        newPinField.setBounds(150, 230, 500, 60);
        newPinField.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        newPinField.setBackground(new Color(50, 50, 55));
        newPinField.setForeground(Color.WHITE);
        newPinField.setCaretColor(new Color(100, 255, 150));
        newPinField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        add(newPinField);

        // Re-enter label & field
        JLabel lblRe = new JLabel("Re-enter New PIN");
        lblRe.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblRe.setForeground(Color.WHITE);
        lblRe.setBounds(150, 320, 200, 40);
        add(lblRe);

        rePinField = new JPasswordField();
        rePinField.setBounds(150, 370, 500, 60);
        rePinField.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        rePinField.setBackground(new Color(50, 50, 55));
        rePinField.setForeground(Color.WHITE);
        rePinField.setCaretColor(new Color(100, 255, 150));
        rePinField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        add(rePinField);

        // Change button
        changeBtn = new JButton("CHANGE PIN");
        changeBtn.setBounds(150, 470, 220, 60);
        changeBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        changeBtn.setBackground(new Color(40, 167, 69));
        changeBtn.setForeground(Color.WHITE);
        changeBtn.setFocusPainted(false);
        changeBtn.addActionListener(this);
        add(changeBtn);

        // Back button
        backBtn = new JButton("BACK");
        backBtn.setBounds(430, 470, 220, 60);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backBtn.setBackground(new Color(80, 80, 80));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(this);
        add(backBtn);

        // Footer
        JLabel footer = new JLabel("© 2026 Secure Banking System | Ahmednagar, Maharashtra");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footer.setForeground(new Color(140, 140, 140));
        footer.setBounds(200, 570, 500, 30);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        add(footer);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backBtn) {
            setVisible(false);
            new Transactions(pin, Accountno).setVisible(true);
            return;
        }

        if (ae.getSource() == changeBtn) {
            String newPin = new String(newPinField.getPassword());
            String rePin = new String(rePinField.getPassword());

            if (newPin.isEmpty() || rePin.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter and re-enter new PIN");
                return;
            }

            if (!newPin.equals(rePin)) {
                JOptionPane.showMessageDialog(null, "PINs do not match", "Mismatch", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (newPin.length() != 4) {
                JOptionPane.showMessageDialog(null, "PIN must be 4 digits", "Invalid PIN", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                ConnectionSql c = new ConnectionSql();

                // Update bank table (pin column use kar, agar Login_Password hai to change kar)
                String q1 = "UPDATE bank SET pin = ? WHERE account_no = ? AND pin = ?";
                PreparedStatement ps1 = c.con.prepareStatement(q1);
                ps1.setString(1, newPin);
                ps1.setString(2, Accountno);
                ps1.setString(3, pin);
                ps1.executeUpdate();

                // Update login table
                String q2 = "UPDATE login SET pin = ? WHERE account_no = ? AND pin = ?";
                PreparedStatement ps2 = c.con.prepareStatement(q2);
                ps2.setString(1, newPin);
                ps2.setString(2, Accountno);
                ps2.setString(3, pin);
                ps2.executeUpdate();

                // Update signup3 table (agar PIN wahan bhi hai)
                String q3 = "UPDATE signup3 SET pin = ? WHERE account_no = ? AND pin = ?";
                PreparedStatement ps3 = c.con.prepareStatement(q3);
                ps3.setString(1, newPin);
                ps3.setString(2, Accountno);
                ps3.setString(3, pin);
                ps3.executeUpdate();

                JOptionPane.showMessageDialog(null, "PIN changed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                setVisible(false);
                new Transactions(newPin, Accountno).setVisible(true);

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Unexpected error", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Pin("", ""));
    }
}