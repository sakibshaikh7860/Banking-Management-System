package banking.management.system;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Transactions extends JFrame implements ActionListener {
    String pin, Accountno;
    JButton depositBtn, withdrawBtn, fastCashBtn, miniStmtBtn, changePinBtn, balanceBtn, logoutBtn;
    JLabel balanceLabel, welcomeLabel;
    JTable recentTable;
    DefaultTableModel tableModel;

    public Transactions(String pin, String Accountno) {
        this.pin = pin;
        this.Accountno = Accountno;

        // Apply modern dark theme
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Secure Bank - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 750);  // size same rakha
        setLocationRelativeTo(null);

        // Main content panel (sab kuch isme jayega)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(20, 25, 30));
        mainPanel.setPreferredSize(new Dimension(1100, 850));  // height badi rakhi taaki scroll test ho

        // Header / Welcome
        welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcomeLabel.setForeground(new Color(100, 255, 150));
        welcomeLabel.setBounds(130, 30, 400, 50);
        mainPanel.add(welcomeLabel);

        // Logo
        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icons/logo.jpg"));
        Image logoImg = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel logo = new JLabel(new ImageIcon(logoImg));
        logo.setBounds(30, 20, 80, 80);
        mainPanel.add(logo);

        // Balance Card (top-right)
        JPanel balanceCard = new JPanel();
        balanceCard.setBounds(650, 40, 400, 120);
        balanceCard.setBackground(new Color(40, 167, 69));
        balanceCard.setBorder(BorderFactory.createLineBorder(new Color(60, 187, 89), 3));
        balanceCard.setLayout(null);
        mainPanel.add(balanceCard);

        JLabel balTitle = new JLabel("Available Balance");
        balTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        balTitle.setForeground(Color.WHITE);
        balTitle.setBounds(20, 20, 200, 30);
        balanceCard.add(balTitle);

        balanceLabel = new JLabel("₹ 0.00");
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setBounds(20, 50, 300, 50);
        balanceCard.add(balanceLabel);

        fetchBalance();

        // Quick Actions Panel (buttons grid)
        JPanel actionsPanel = new JPanel();
        actionsPanel.setBounds(100, 180, 900, 350);
        actionsPanel.setBackground(new Color(20, 25, 30));
        actionsPanel.setLayout(null);
        mainPanel.add(actionsPanel);

        int btnWidth = 220, btnHeight = 80, gap = 40;
        int x = 0, y = 0;

        depositBtn = createButton("DEPOSIT", x, y, actionsPanel);
        x += btnWidth + gap;
        withdrawBtn = createButton("WITHDRAWAL", x, y, actionsPanel);
        x += btnWidth + gap;
        fastCashBtn = createButton("FAST CASH", x, y, actionsPanel);

        x = 0;
        y += btnHeight + gap;

        miniStmtBtn = createButton("MINI STATEMENT", x, y, actionsPanel);
        x += btnWidth + gap;
        changePinBtn = createButton("CHANGE PIN", x, y, actionsPanel);
        x += btnWidth + gap;
        balanceBtn = createButton("ACCOUNT DETAILS", x, y, actionsPanel);

        x = 0;
        y += btnHeight + gap;
        x += btnWidth + gap;
        logoutBtn = createButton("LOGOUT", x, y, actionsPanel);
        logoutBtn.setBackground(new Color(220, 53, 69));

        // Logout button
//        logoutBtn = new JButton("LOGOUT");
//        logoutBtn.setBounds(400, y + btnHeight + gap + 20, 300, 60);
//        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
//        logoutBtn.setBackground(new Color(220, 53, 69));
//        logoutBtn.setForeground(Color.WHITE);
//        logoutBtn.setFocusPainted(false);
//        logoutBtn.addActionListener(this);
//        mainPanel.add(logoutBtn);

        // Footer
        JLabel footer = new JLabel("© 2026 Secure Banking System | Ahmednagar, Maharashtra");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footer.setForeground(new Color(140, 140, 140));
        footer.setBounds(350, 550, 400, 30);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(footer);

        // ScrollPane wrap karo (sab content isme jayega)
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(null);
        add(scrollPane);

        setVisible(true);
    }

    private JButton createButton(String text, int x, int y, JPanel panel) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 220, 80);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(50, 50, 55));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 2));
        btn.addActionListener(this);
        panel.add(btn);
        return btn;
    }

    private void fetchBalance() {
        try {
            ConnectionSql db = new ConnectionSql();
            String query = "SELECT balance FROM accounts WHERE account_no = ?";
            PreparedStatement ps = db.con.prepareStatement(query);
            ps.setString(1, Accountno);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double bal = rs.getDouble("balance");
                balanceLabel.setText("₹ " + String.format("%,.2f", bal));
            } else {
                balanceLabel.setText("₹ 0.00 (Account not found)");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            balanceLabel.setText("Error fetching balance");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == depositBtn) {
            setVisible(false);
            new Deposit(pin, Accountno).setVisible(true);
        } else if (e.getSource() == withdrawBtn) {
            setVisible(false);
            new Withdrawl(pin, Accountno).setVisible(true);
        } else if (e.getSource() == fastCashBtn) {
            setVisible(false);
            new FastCash(pin, Accountno).setVisible(true);
        } else if (e.getSource() == miniStmtBtn) {
            new MiniStatement(pin, Accountno).setVisible(true);
        } else if (e.getSource() == changePinBtn) {
            setVisible(false);
            new Pin(pin, Accountno).setVisible(true);
        } else if (e.getSource() == balanceBtn) {
            setVisible(false);
            new BalanceEnquiry(pin, Accountno).setVisible(true);
        } else if (e.getSource() == logoutBtn) {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                setVisible(false);
                new Login1().setVisible(true);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Transactions("", ""));
    }
}