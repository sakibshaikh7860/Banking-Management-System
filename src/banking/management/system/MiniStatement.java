package banking.management.system;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MiniStatement extends JFrame implements ActionListener {

    JLabel accountLabel, balanceLabel;
    JTable txnTable;
    DefaultTableModel tableModel;
    JButton closeBtn;
    String pin, Accountno;

    public MiniStatement(String pin, String Accountno) {
        this.pin = pin;
        this.Accountno = Accountno;

        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Secure Bank - Mini Statement");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(30, 30, 35));

        // Logo top-left
        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image logoImg = logoIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImg));
        logo.setBounds(20, 20, 60, 60);
        add(logo);

        // Title
        JLabel title = new JLabel("Mini Statement");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(100, 255, 150));
        title.setBounds(100, 30, 300, 40);
        add(title);

        // Account Number (masked)
        accountLabel = new JLabel("Account: XXXX-XXXX-XXXX-XXXX");
        accountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        accountLabel.setForeground(Color.WHITE);
        accountLabel.setBounds(100, 80, 400, 30);
        add(accountLabel);

        // Transactions Table
        String[] columns = {"Date", "Type", "Amount"};
        tableModel = new DefaultTableModel(columns, 0);
        txnTable = new JTable(tableModel);
        txnTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txnTable.setRowHeight(30);
        txnTable.setBackground(new Color(40, 40, 45));
        txnTable.setForeground(Color.WHITE);
        txnTable.setGridColor(new Color(60, 60, 60));
        txnTable.getTableHeader().setBackground(new Color(50, 50, 55));
        txnTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(txnTable);
        scroll.setBounds(50, 130, 600, 300);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll);

        // Total Balance
        balanceLabel = new JLabel("Total Balance: ₹ 0.00");
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        balanceLabel.setForeground(new Color(100, 255, 150));
        balanceLabel.setBounds(50, 450, 600, 50);
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        // Close button
        closeBtn = new JButton("CLOSE");
        closeBtn.setBounds(250, 520, 200, 50);
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        closeBtn.setBackground(new Color(80, 80, 80));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(this);
        add(closeBtn);

        // Footer
        JLabel footer = new JLabel("© 2026 Secure Banking System | Ahmednagar, Maharashtra");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footer.setForeground(new Color(140, 140, 140));
        footer.setBounds(150, 580, 400, 30);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        add(footer);

        // Load data
        loadMiniStatement();

        setVisible(true);
    }

    private void loadMiniStatement() {
        try {
            ConnectionSql c = new ConnectionSql();

            // Masked account
            String masked = Accountno.substring(0, 4) + "XXXXXXXX" + Accountno.substring(12);
            accountLabel.setText("Account: " + masked);

            // Balance direct accounts table se (dashboard se match karega)
            String balQuery = "SELECT balance FROM accounts WHERE account_no = ?";
            PreparedStatement balPs = c.con.prepareStatement(balQuery);
            balPs.setString(1, Accountno);
            ResultSet balRs = balPs.executeQuery();
            double balance = 0;
            if (balRs.next()) {
                balance = balRs.getDouble("balance");
            }
            balanceLabel.setText("Total Balance: ₹ " + String.format("%,.2f", balance));

            // Transactions last 10 from bank table (Login_Password hata diya)
            String txnQuery = "SELECT date, type, amount FROM bank WHERE account_no = ? ORDER BY date DESC LIMIT 10";
            PreparedStatement txnPs = c.con.prepareStatement(txnQuery);
            txnPs.setString(1, Accountno);
            ResultSet rs = txnPs.executeQuery();

            while (rs.next()) {
                String date = rs.getString("date");
                String type = rs.getString("type");
                double amt = rs.getDouble("amount");

                tableModel.addRow(new Object[]{date, type, String.format("₹ %,.2f", amt)});
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading statement: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == closeBtn) {
            setVisible(false);
            new Transactions(pin, Accountno).setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniStatement("", ""));
    }
}