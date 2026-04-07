package banking.management.system;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;



public class Deposit extends JFrame implements ActionListener {

    JTextField amountField;
    JButton depositBtn, backBtn;
    String pin, Accountno;

    public Deposit(String pin, String Accountno) {
        this.pin = pin;
        this.Accountno = Accountno;

        // Modern dark theme
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Secure Bank - Deposit");
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

        // Welcome text
        JLabel welcome = new JLabel("Deposit Funds");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcome.setForeground(new Color(100, 255, 150));
        welcome.setBounds(180, 60, 400, 50);
        add(welcome);

        // Amount label
        JLabel lblAmount = new JLabel("Enter Amount to Deposit (₹)");
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
        amountField.setCaretColor(new Color(100, 255, 150));
        amountField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        add(amountField);

        // Deposit button
        depositBtn = new JButton("DEPOSIT");
        depositBtn.setBounds(150, 340, 220, 60);
        depositBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        depositBtn.setBackground(new Color(40, 167, 69)); // Banking green
        depositBtn.setForeground(Color.WHITE);
        depositBtn.setFocusPainted(false);
        depositBtn.addActionListener(this);
        add(depositBtn);

        // Back button
        backBtn = new JButton("BACK");
        backBtn.setBounds(430, 340, 220, 60);
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
        footer.setBounds(200, 520, 500, 30);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        add(footer);

        // Optional background image (comment out if you want clean look)
        // ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/deposit1.jpg"));
        // ... add if needed

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backBtn) {
            setVisible(false);
            new Transactions(pin, Accountno).setVisible(true);
            return;
        }

        if (ae.getSource() == depositBtn) {
            String amountStr = amountField.getText().trim();

            if (amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter amount to deposit", "Input Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);

                if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Amount must be greater than 0");
                    return;
                }

                ConnectionSql c = new ConnectionSql();
                Date date = new Date();

                // 1. Transaction log insert (bank table)
                String logQuery = "INSERT INTO bank (pin, account_no, date, type, amount) VALUES (?, ?, ?, 'Deposit', ?)";
                PreparedStatement logPs = c.con.prepareStatement(logQuery);
                logPs.setString(1, pin);
                logPs.setString(2, Accountno);
                logPs.setString(3, date.toString());
                logPs.setDouble(4, amount);
                logPs.executeUpdate();

                // 2. Balance update in accounts table
                String updateQuery = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";
                PreparedStatement updatePs = c.con.prepareStatement(updateQuery);
                updatePs.setDouble(1, amount);
                updatePs.setString(2, Accountno);
                int rows = updatePs.executeUpdate();

                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "₹ " + amount + " Deposited Successfully!");
                    setVisible(false);
                    new Transactions(pin, Accountno).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Account not found - Deposit failed");
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter valid amount (numbers only)");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Deposit("", ""));
    }
}