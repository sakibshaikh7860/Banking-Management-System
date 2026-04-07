package banking.management.system;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class Signup3 extends JFrame implements ActionListener {

  JRadioButton saving, fd, current, rd;
  JCheckBox atm, internet, mobile, emailAlert, cheque, eStmt, declaration;
  JButton submitBtn, cancelBtn;
  String formno;

  JPanel formPanel;
  JScrollPane scrollPane;

  public Signup3(String formno) {
    this.formno = formno;

    try {
      FlatDarkLaf.setup();
    } catch (Exception e) {
      e.printStackTrace();
    }

    setTitle("Secure Bank - Account Opening (Step 3)");
    setSize(900, 850);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    formPanel = new JPanel();
    formPanel.setLayout(null);
    formPanel.setPreferredSize(new Dimension(850, 900));
    formPanel.setBackground(new Color(30, 30, 35));



    JLabel title = new JLabel("Account Details");
    title.setFont(new Font("Segoe UI", Font.BOLD, 28));
    title.setForeground(Color.WHITE);
    title.setBounds(320, 40, 300, 40);
    formPanel.add(title);

    JLabel formLbl = new JLabel("Form No: " + formno);
    formLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
    formLbl.setForeground(new Color(140, 140, 140));
    formLbl.setBounds(700, 20, 150, 30);
    formPanel.add(formLbl);

    int y = 140;

    addLabel("Account Type:", 100, y, formPanel);
    y += 40;

    saving = new JRadioButton("Savings Account");
    fd = new JRadioButton("Fixed Deposit Account");
    current = new JRadioButton("Current Account");
    rd = new JRadioButton("Recurring Deposit Account");

    styleRadio(saving); styleRadio(fd); styleRadio(current); styleRadio(rd);

    saving.setBounds(100, y, 250, 40);
    fd.setBounds(380, y, 300, 40);
    y += 50;
    current.setBounds(100, y, 250, 40);
    rd.setBounds(380, y, 300, 40);
    y += 60;

    formPanel.add(saving); formPanel.add(fd); formPanel.add(current); formPanel.add(rd);

    ButtonGroup accGroup = new ButtonGroup();
    accGroup.add(saving); accGroup.add(fd); accGroup.add(current); accGroup.add(rd);

    addLabel("Your Account Number will be:", 100, y, formPanel);
    JLabel accHint = new JLabel("XXXX-XXXX-XXXX-XXXX (16-digit)");
    accHint.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    accHint.setForeground(new Color(180, 180, 180));
    accHint.setBounds(100, y + 30, 500, 30);
    formPanel.add(accHint);
    y += 80;

    addLabel("Your PIN will be:", 100, y, formPanel);
    JLabel pinHint = new JLabel("XXXX (4-digit)");
    pinHint.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    pinHint.setForeground(new Color(180, 180, 180));
    pinHint.setBounds(100, y + 30, 300, 30);
    formPanel.add(pinHint);
    y += 80;

    addLabel("Services Required:", 100, y, formPanel);
    y += 40;

    atm = new JCheckBox("ATM Card");
    internet = new JCheckBox("Internet Banking");
    mobile = new JCheckBox("Mobile Banking");
    emailAlert = new JCheckBox("Email Alerts");
    cheque = new JCheckBox("Cheque Book");
    eStmt = new JCheckBox("E-Statement");

    styleCheck(atm); styleCheck(internet); styleCheck(mobile);
    styleCheck(emailAlert); styleCheck(cheque); styleCheck(eStmt);

    atm.setBounds(100, y, 200, 40);
    internet.setBounds(380, y, 200, 40);
    y += 50;
    mobile.setBounds(100, y, 200, 40);
    emailAlert.setBounds(380, y, 200, 40);
    y += 50;
    cheque.setBounds(100, y, 200, 40);
    eStmt.setBounds(380, y, 200, 40);
    y += 70;

    formPanel.add(atm); formPanel.add(internet); formPanel.add(mobile);
    formPanel.add(emailAlert); formPanel.add(cheque); formPanel.add(eStmt);

    declaration = new JCheckBox("I declare that the above details are correct to the best of my knowledge.");
    declaration.setSelected(true);
    declaration.setFont(new Font("Segoe UI", Font.PLAIN, 15));
    declaration.setForeground(Color.WHITE);
    declaration.setBackground(new Color(30, 30, 35));
    declaration.setBounds(100, y, 600, 40);
    formPanel.add(declaration);
    y += 80;

    submitBtn = new JButton("SUBMIT");
    submitBtn.setBounds(250, y, 150, 50);
    submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
    submitBtn.setBackground(new Color(40, 167, 69));
    submitBtn.setForeground(Color.WHITE);
    submitBtn.addActionListener(this);
    formPanel.add(submitBtn);

    cancelBtn = new JButton("CANCEL");
    cancelBtn.setBounds(450, y, 150, 50);
    cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
    cancelBtn.setBackground(new Color(220, 53, 69));
    cancelBtn.setForeground(Color.WHITE);
    cancelBtn.addActionListener(this);
    formPanel.add(cancelBtn);

    JLabel footer = new JLabel("© 2026 Secure Banking System | Ahmednagar");
    footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    footer.setForeground(new Color(140, 140, 140));
    footer.setBounds(250, 720 + 80, 400, 30);
    footer.setHorizontalAlignment(SwingConstants.CENTER);
    formPanel.add(footer);

    // ScrollPane wrap karo
    JScrollPane scrollPane = new JScrollPane(formPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    scrollPane.setBorder(null);
    add(scrollPane);
    setVisible(true);
  }

  private void addLabel(String text, int x, int y, JPanel p) {
    JLabel l = new JLabel(text);
    l.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    l.setForeground(Color.WHITE);
    l.setBounds(x, y, 300, 40);
    p.add(l);
  }

  private void styleRadio(JRadioButton rb) {
    rb.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    rb.setForeground(Color.WHITE);
    rb.setBackground(new Color(30, 30, 35));
    rb.setFocusPainted(false);
  }

  private void styleCheck(JCheckBox cb) {
    cb.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    cb.setForeground(Color.WHITE);
    cb.setBackground(new Color(30, 30, 35));
    cb.setFocusPainted(false);
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == cancelBtn) {
      setVisible(false);
      new Login1().setVisible(true);
      return;
    }

    if (!declaration.isSelected()) {
      JOptionPane.showMessageDialog(null, "Please accept the declaration");
      return;
    }

    String accountType = null;
    if (saving.isSelected()) accountType = "Saving Account";
    else if (fd.isSelected()) accountType = "Fixed Deposit Account";
    else if (current.isSelected()) accountType = "Current Account";
    else if (rd.isSelected()) accountType = "Recurring Deposit Account";

    if (accountType == null) {
      JOptionPane.showMessageDialog(null, "Please select Account Type");
      return;
    }

    Random ran = new Random();
    String accNo = "" + Math.abs(ran.nextLong() % 9000000000000000L + 1000000000000000L); // 16 digit
    String newPin = "" + Math.abs(ran.nextLong() % 9000L + 1000L); // 4 digit

    String facility = "";
    if (atm.isSelected()) facility += "ATM Card, ";
    if (internet.isSelected()) facility += "Internet Banking, ";
    if (mobile.isSelected()) facility += "Mobile Banking, ";
    if (emailAlert.isSelected()) facility += "Email Alerts, ";
    if (cheque.isSelected()) facility += "Cheque Book, ";
    if (eStmt.isSelected()) facility += "E-Statement, ";

    try {
      ConnectionSql db = new ConnectionSql();

      // 1. signup3 insert
      String q1 = "INSERT INTO signup3 (formno, account_type, account_no, pin, services) VALUES (?, ?, ?, ?, ?)";
      PreparedStatement ps1 = db.con.prepareStatement(q1);
      ps1.setString(1, formno);
      ps1.setString(2, accountType);
      ps1.setString(3, accNo);
      ps1.setString(4, newPin);
      ps1.setString(5, facility);
      ps1.executeUpdate();

      // 2. login insert (tere code mein Account_No tha, sahi kar diya)
      String q2 = "INSERT INTO login (formno, Account_No, pin) VALUES (?, ?, ?)";
      PreparedStatement ps2 = db.con.prepareStatement(q2);
      ps2.setString(1, formno);
      ps2.setString(2, accNo);
      ps2.setString(3, newPin);
      ps2.executeUpdate();


      // Signup1 se name fetch kar le (formno se)
      String nameQuery = "SELECT name FROM signup1 WHERE formno = ?";
      PreparedStatement namePs = db.con.prepareStatement(nameQuery);
      namePs.setString(1, formno);
      ResultSet nameRs = namePs.executeQuery();
      String userName = "New User";  // default agar nahi mila
      if (nameRs.next()) {
        userName = nameRs.getString("name");
      }

      // accounts table mein entry (balance 0 + name daal do)
      String accInsert = "INSERT INTO accounts (account_no, pin, balance, name) VALUES (?, ?, ?, ?)";
      PreparedStatement accPs = db.con.prepareStatement(accInsert);
      accPs.setString(1, accNo);
      accPs.setString(2, newPin);
      accPs.setDouble(3, 0.00);
      accPs.setString(4, userName);  // yeh line add ki
      accPs.executeUpdate();

      JOptionPane.showMessageDialog(null,
              "Congratulations!\nYour Account is Successfully Opened.\n\n" +
                      "Account Number: " + accNo + "\n" +
                      "PIN: " + newPin + "\n\n" +
                      "Please keep them safe.\nThank you for choosing Secure Bank!",
              "Account Created", JOptionPane.INFORMATION_MESSAGE);

      setVisible(false);
      new Login1().setVisible(true);

    } catch (SQLException ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new Signup3("1234"));
  }
}