package banking.management.system;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class Withdrawl extends JFrame implements ActionListener {

    JTextField amountField;
    JButton withdrawBtn, backBtn, exitBtn;
    String pin, Accountno;

    public Withdrawl(String pin, String Accountno) {
        this.pin = pin;
        this.Accountno = Accountno;

        // Modern dark theme
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Secure Bank - Withdrawal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(30, 30, 35)); // Dark bg

        // Logo top-left
        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image logoImg = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImg));
        logo.setBounds(40, 30, 80, 80);
        add(logo);

        // Title
        JLabel title = new JLabel("Withdraw Funds");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(255, 100, 100)); // Red accent for withdrawal
        title.setBounds(180, 60, 400, 50);
        add(title);

        // Amount label
        JLabel lblAmount = new JLabel("Enter Amount to Withdraw (₹)");
        lblAmount.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblAmount.setForeground(Color.WHITE);
        lblAmount.setBounds(150, 180, 400, 40);
        add(lblAmount);

        // Amount field
        amountField = new JTextField();
        amountField.setBounds(150, 230, 500, 60);
        amountField.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        amountField.setBackground(new Color(50, 50, 55));
        amountField.setForeground(Color.WHITE);
        amountField.setCaretColor(new Color(255, 100, 100));
        amountField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        add(amountField);

        // Withdraw button
        withdrawBtn = new JButton("WITHDRAW");
        withdrawBtn.setBounds(150, 340, 220, 60);
        withdrawBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        withdrawBtn.setBackground(new Color(220, 53, 69)); // Red for withdrawal
        withdrawBtn.setForeground(Color.WHITE);
        withdrawBtn.setFocusPainted(false);
        withdrawBtn.addActionListener(this);
        add(withdrawBtn);

        // Back button
        backBtn = new JButton("BACK");
        backBtn.setBounds(430, 340, 220, 60);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backBtn.setBackground(new Color(80, 80, 80));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(this);
        add(backBtn);

        // Exit button (optional, agar chahiye to rakh, warna remove kar sakta hai)
//        exitBtn = new JButton("EXIT");
//        exitBtn.setBounds(150, 420, 500, 60);
//        exitBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
//        exitBtn.setBackground(new Color(100, 100, 100));
//        exitBtn.setForeground(Color.WHITE);
//        exitBtn.setFocusPainted(false);
//        exitBtn.addActionListener(this);
//        add(exitBtn);

        // Footer
        JLabel footer = new JLabel("© 2026 Secure Banking System | Ahmednagar, Maharashtra");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footer.setForeground(new Color(140, 140, 140));
        footer.setBounds(200, 520, 500, 30);
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

        if (ae.getSource() == exitBtn) {
            System.exit(0);
        }

        if (ae.getSource() == withdrawBtn) {
            String input = amountField.getText().trim();

            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter amount to withdraw");
                return;
            }

            String cleanInput = input.replace(",", "").replace(" ", "");
            try {
                double amount = Double.parseDouble(cleanInput);

                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Amount must be greater than 0");
                    return;
                }

                ConnectionSql c = new ConnectionSql();
                Date date = new Date();

                // Balance check (tere original logic, double mein)
                ResultSet rs = c.s.executeQuery("SELECT * FROM bank WHERE Account_No = '" + Accountno + "' AND pin = '" + pin + "'");  // pin ko apne column se match kar
                double balance = 0;
                while (rs.next()) {
                    if (rs.getString("type").equals("Deposit")) {
                        balance += Double.parseDouble(rs.getString("amount"));
                    } else {
                        balance -= Double.parseDouble(rs.getString("amount"));
                    }
                }

                if (balance < amount) {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance", "Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Safe transaction insert
                String insertQuery = "INSERT INTO bank (pin, account_no, date, type, amount) VALUES (?, ?, ?, 'Withdrawl', ?)";
                PreparedStatement ps = c.con.prepareStatement(insertQuery);
                ps.setString(1, pin);
                ps.setString(2, Accountno);
                ps.setString(3, date.toString());
                ps.setDouble(4, amount);
                int rows = ps.executeUpdate();

                if (rows == 0) {
                    JOptionPane.showMessageDialog(null, "Withdrawal failed - transaction not recorded", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Balance update in accounts table
                String updateQuery = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";
                PreparedStatement updatePs = c.con.prepareStatement(updateQuery);
                updatePs.setDouble(1, amount);
                updatePs.setString(2, Accountno);
                updatePs.executeUpdate();

                JOptionPane.showMessageDialog(null, "₹ " + amount + " Withdrawal Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                setVisible(false);
                new Transactions(pin, Accountno).setVisible(true);

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter valid amount (numbers only)", "Invalid Input", JOptionPane.ERROR_MESSAGE);
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
        SwingUtilities.invokeLater(() -> new Withdrawl("", ""));
    }
}