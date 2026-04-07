package banking.management.system;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;

public class FastCash extends JFrame implements ActionListener {

    JButton btn100, btn500, btn1000, btn2000, btn5000, btn10000, backBtn;
    String pin, Accountno;

    public FastCash(String pin, String Accountno) {
        this.pin = pin;
        this.Accountno = Accountno;

        // Modern dark theme
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Secure Bank - Fast Cash");
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
        JLabel title = new JLabel("Fast Cash Withdrawal");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(255, 100, 100)); // Red accent for withdrawal
        title.setBounds(180, 60, 500, 50);
        add(title);

        // Instruction
        JLabel lbl = new JLabel("Select Amount to Withdraw (₹)");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(150, 140, 400, 40);
        add(lbl);

        // Buttons grid (3x2)
        int btnWidth = 220, btnHeight = 80, gap = 20;
        int x = 150, y = 200;

        btn100 = createButton("₹ 100", x, y);
        x += btnWidth + gap;
        btn500 = createButton("₹ 500", x, y);
        x += btnWidth + gap;
        btn1000 = createButton("₹ 1000", x, y);

        x = 150;
        y += btnHeight + gap;
        btn2000 = createButton("₹ 2000", x, y);
        x += btnWidth + gap;
        btn5000 = createButton("₹ 5000", x, y);
        x += btnWidth + gap;
        btn10000 = createButton("₹ 10000", x, y);

        // Back button
        backBtn = new JButton("BACK");
        backBtn.setBounds(300, y + btnHeight + gap + 30, 300, 60);
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

        setVisible(true);
    }

    private JButton createButton(String text, int x, int y) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 220, 80);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setBackground(new Color(220, 53, 69)); // Red for withdrawal
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.addActionListener(this);
        add(btn);
        return btn;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backBtn) {  // BACK button
            setVisible(false);
            new Transactions(pin, Accountno).setVisible(true);
            return;
        }

        // Fast cash amount buttons
        String text = ((JButton) ae.getSource()).getText();
        String amountStr = text.replace("₹ ", "").replace(",", "").trim();

        if (amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No amount selected");
            return;
        }

        try {
            double amount = Double.parseDouble(amountStr);
            System.out.println("FastCash: Attempting withdrawal of ₹ " + amount);  // Debug

            ConnectionSql c = new ConnectionSql();
            Date date = new Date();

            // Balance check
            String checkQuery = "SELECT * FROM bank WHERE account_no = ? AND pin = ?";
            PreparedStatement checkPs = c.con.prepareStatement(checkQuery);
            checkPs.setString(1, Accountno);
            checkPs.setString(2, pin);
            ResultSet rs = checkPs.executeQuery();

            double balance = 0;
            while (rs.next()) {
                String type = rs.getString("type");
                double amt = Double.parseDouble(rs.getString("amount"));
                if ("Deposit".equals(type)) {
                    balance += amt;
                } else {
                    balance -= amt;
                }
            }

            System.out.println("Current balance: ₹ " + balance);  // Debug

            if (balance < amount) {
                JOptionPane.showMessageDialog(null, "Insufficient Balance", "Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Withdrawal insert
            String insertQuery = "INSERT INTO bank (pin, account_no, date, type, amount) VALUES (?, ?, ?, 'Withdrawl', ?)";
            PreparedStatement ps = c.con.prepareStatement(insertQuery);
            ps.setString(1, pin);
            ps.setString(2, Accountno);
            ps.setString(3, date.toString());
            ps.setDouble(4, amount);
            int rows = ps.executeUpdate();

            System.out.println("Insert rows affected: " + rows);  // Debug

            if (rows > 0) {
                // Balance update in accounts table
                String updateQuery = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";
                PreparedStatement updatePs = c.con.prepareStatement(updateQuery);
                updatePs.setDouble(1, amount);
                updatePs.setString(2, Accountno);
                updatePs.executeUpdate();

                JOptionPane.showMessageDialog(null,
                        "₹ " + amount + " Withdrawn Successfully via Fast Cash!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                setVisible(false);
                new Transactions(pin, Accountno).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Withdrawal failed - no rows inserted", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount format", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FastCash("", ""));
    }
}