package banking.management.system;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BalanceEnquiry extends JFrame implements ActionListener {

    private JLabel balanceLbl, nameLbl, accLbl, typeLbl, ifscLbl, addrLbl, branchLbl, upiLbl;
    private JButton backBtn;
    private String pin, Accountno;

    public BalanceEnquiry(String pin, String Accountno) {
        this.pin = pin;
        this.Accountno = Accountno;

        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Secure Bank - Balance & Account Details");
        setSize(800, 650);  // Compact size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main content panel (scrollable)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(30, 30, 35));
        mainPanel.setPreferredSize(new Dimension(780, 1100));  // Height badi rakhi taaki scroll aaye agar zarurat pade

        // Title
        JLabel title = new JLabel("Account & Balance Details");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(100, 255, 150));
        title.setBounds(150, 30, 500, 50);
        mainPanel.add(title);

        // Logo
        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image logoImg = logoIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImg));
        logo.setBounds(30, 20, 70, 70);
        mainPanel.add(logo);

        int y = 120;
        int labelX = 80, valueX = 280;

        nameLbl = addDetail(mainPanel, "Name:", "Loading...", labelX, y, valueX, y);
        y += 60;
        accLbl = addDetail(mainPanel, "Account Number:", "Loading...", labelX, y, valueX, y);
        y += 60;
        typeLbl = addDetail(mainPanel, "Account Type:", "Loading...", labelX, y, valueX, y);
        y += 60;
        addrLbl = addDetail(mainPanel, "Address:", "Loading...", labelX, y, valueX, y);
        y += 70;  // Address thoda space de
        ifscLbl = addDetail(mainPanel, "IFSC Code:", "Loading...", labelX, y, valueX, y);
        y += 60;
        branchLbl = addDetail(mainPanel, "Branch:", "Loading...", labelX, y, valueX, y);
        y += 60;
        upiLbl = addDetail(mainPanel, "UPI ID:", "Loading...", labelX, y, valueX, y);
        y += 80;

        // Balance (big & highlighted)
        balanceLbl = new JLabel("Available Balance: ₹ Loading...");
        balanceLbl.setFont(new Font("Segoe UI", Font.BOLD, 26));
        balanceLbl.setForeground(new Color(100, 255, 150));
        balanceLbl.setBounds(80, y, 640, 60);
        balanceLbl.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(balanceLbl);
        y += 80;

        // Back button
        backBtn = new JButton("BACK TO DASHBOARD");
        backBtn.setBounds(200, y, 400, 60);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        backBtn.setBackground(new Color(80, 80, 80));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(this);
        mainPanel.add(backBtn);
        y += 80;

        // Footer
        JLabel footer = new JLabel("© 2026 Secure Banking System | Ahmednagar, Maharashtra");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footer.setForeground(new Color(140, 140, 140));
        footer.setBounds(150, y + 20, 500, 30);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(footer);

        // ScrollPane wrap karo
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane);

        // Data load kar
        loadDetails();

        setVisible(true);
    }

    private JLabel addDetail(JPanel panel, String labelText, String value, int lx, int ly, int vx, int vy) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(lx, ly, 200, 40);
        panel.add(lbl);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 18));
        val.setForeground(new Color(200, 255, 200));
        val.setBounds(vx, vy, 500, 40);
        panel.add(val);

        return val;
    }

    private void loadDetails() {
        try {
            ConnectionSql db = new ConnectionSql();

            // Signup1 se name, address, email fetch
            String signupQuery = "SELECT name, address, email FROM signup1 WHERE formno = (SELECT formno FROM login WHERE account_no = ?)";
            PreparedStatement signupPs = db.con.prepareStatement(signupQuery);
            signupPs.setString(1, Accountno);
            ResultSet signupRs = signupPs.executeQuery();

            String name = "Not Found";
            String address = "Not Found";
            String email = "Not Found";
            if (signupRs.next()) {
                name = signupRs.getString("name");
                address = signupRs.getString("address");
                email = signupRs.getString("email");
            }

            // Account type from signup3
            String typeQuery = "SELECT account_type FROM signup3 WHERE account_no = ?";
            PreparedStatement typePs = db.con.prepareStatement(typeQuery);
            typePs.setString(1, Accountno);
            ResultSet typeRs = typePs.executeQuery();
            String accountType = "Not Found";
            if (typeRs.next()) {
                accountType = typeRs.getString("account_type");
            }

            // Fixed bank details
            String fixedIFSC = "SECURE0001234";
            String fixedBranch = "Ahmednagar Main Branch";  // tere location ke hisab se change kar sakta hai

            // Dynamic UPI ID from email
            String upiId = "Not Available";
            if (!email.equals("Not Found") && email.contains("@")) {
                String emailPrefix = email.split("@")[0];
                upiId = emailPrefix + "@securebank";
            }

            // Masked account number
            String maskedAcc = Accountno.substring(0, 4) + "XXXXXXXX" + Accountno.substring(12);

            // Balance from accounts table
            String balQuery = "SELECT balance FROM accounts WHERE account_no = ?";
            PreparedStatement balPs = db.con.prepareStatement(balQuery);
            balPs.setString(1, Accountno);
            ResultSet balRs = balPs.executeQuery();
            double balance = 0;
            if (balRs.next()) {
                balance = balRs.getDouble("balance");
            }

            // Set values
            nameLbl.setText(name);
            accLbl.setText(maskedAcc);
            typeLbl.setText(accountType);
            addrLbl.setText(address);
            ifscLbl.setText(fixedIFSC);
            branchLbl.setText(fixedBranch);
            upiLbl.setText(upiId);
            balanceLbl.setText("Available Balance: ₹ " + String.format("%,.2f", balance));

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading details: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
        new Transactions(pin, Accountno).setVisible(true);
    }

    public static void main(String[] args) {
        new BalanceEnquiry("", "");
    }
}