package banking.management.system;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login1 extends JFrame implements ActionListener {
    JButton login, signup, reset;
    JTextField AccountTextField;
    JPasswordField pinTextField;

    public Login1() {
        // Apply modern FlatLaf dark theme
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Secure Banking System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(1000, 750);
        setLocationRelativeTo(null); // Center on screen
        getContentPane().setBackground(new Color(30, 30, 35)); // Dark bg

        // Logo (top-left, smaller)
        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icons/bankinglogo1.jpg"));
        Image logoImg = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
        logoLabel.setBounds(40, 30, 80, 80);
        add(logoLabel);

        // Welcome text
        JLabel welcome = new JLabel("Welcome to Secure Bank");
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcome.setForeground(new Color(100, 255, 150)); // Light green
        welcome.setBounds(180, 60, 500, 50);
        add(welcome);

        // Login card-like section
        JLabel loginTitle = new JLabel("Secure Login");
        loginTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        loginTitle.setForeground(Color.WHITE);
        loginTitle.setBounds(380, 160, 300, 40);
        add(loginTitle);

        // Account Number
        JLabel lblAccount = new JLabel("Account Number");
        lblAccount.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblAccount.setForeground(Color.WHITE);
        lblAccount.setBounds(220, 240, 200, 30);
        add(lblAccount);

        AccountTextField = new JTextField();
        AccountTextField.setBounds(380, 240, 320, 45);
        AccountTextField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        AccountTextField.setBackground(new Color(50, 50, 55));
        AccountTextField.setForeground(Color.WHITE);
        AccountTextField.setCaretColor(new Color(100, 255, 150));
        AccountTextField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(AccountTextField);

        // Password
        JLabel lblPin = new JLabel("Password");
        lblPin.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblPin.setForeground(Color.WHITE);
        lblPin.setBounds(220, 320, 200, 30);
        add(lblPin);

        pinTextField = new JPasswordField();
        pinTextField.setBounds(380, 320, 320, 45);
        pinTextField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        pinTextField.setBackground(new Color(50, 50, 55));
        pinTextField.setForeground(Color.WHITE);
        pinTextField.setCaretColor(new Color(100, 255, 150));
        pinTextField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(pinTextField);

        // Buttons with modern styling
        login = new JButton("LOGIN");
        login.setBounds(380, 400, 150, 50);
        login.setFont(new Font("Segoe UI", Font.BOLD, 18));
        login.setBackground(new Color(40, 167, 69)); // Banking green
        login.setForeground(Color.WHITE);
        login.setFocusPainted(false);
        login.addActionListener(this);
        add(login);

        signup = new JButton("SIGN UP");
        signup.setBounds(550, 400, 150, 50);
        signup.setFont(new Font("Segoe UI", Font.BOLD, 18));
        signup.setBackground(new Color(80, 80, 80));
        signup.setForeground(Color.WHITE);
        signup.setFocusPainted(false);
        signup.addActionListener(this);
        add(signup);

        reset = new JButton("RESET");
        reset.setBounds(380, 470, 320, 50);
        reset.setFont(new Font("Segoe UI", Font.BOLD, 18));
        reset.setBackground(new Color(220, 53, 69)); // Red
        reset.setForeground(Color.WHITE);
        reset.setFocusPainted(false);
        reset.addActionListener(this);
        add(reset);

        // Footer
        JLabel footer = new JLabel("© 2026 Secure Banking System | Ahmednagar, Maharashtra");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footer.setForeground(new Color(140, 140, 140));
        footer.setBounds(290, 550, 500, 30);
        footer.setHorizontalAlignment(SwingConstants.CENTER);


        setVisible(true);
        add(footer);

        // Optional background images (comment out if you want cleaner look)
        // ImageIcon bgIcon = new ImageIcon(ClassLoader.getSystemResource("icons/loggn.jpg"));
        // ... adjust if keeping

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == reset) {
            AccountTextField.setText("");
            pinTextField.setText("");
        } else if (ae.getSource() == login) {
            String accountNo = AccountTextField.getText().trim();
            String password = new String(pinTextField.getPassword()).trim();

            if (accountNo.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter Account Number and Password", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ConnectionSql db = new ConnectionSql();
            String query = "SELECT * FROM login WHERE Account_No = ? AND pin = ?";
            try (PreparedStatement pstmt = db.con.prepareStatement(query)) {
                pstmt.setString(1, accountNo);
                pstmt.setString(2, password);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    setVisible(false);
                    new Transactions(password, accountNo).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Account Number or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else if (ae.getSource() == signup) {
            setVisible(false);
            new Signup1().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login1());
    }
}