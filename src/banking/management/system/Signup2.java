package banking.management.system;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Signup2 extends JFrame implements ActionListener {

  String formno;
  JComboBox<String> religionCB, categoryCB, incomeCB, educationCB, occupationCB;
  JTextField panField, aadharField;
  JRadioButton seniorYes, seniorNo, existingYes, existingNo;
  JButton nextBtn;
  ButtonGroup seniorGroup, existingGroup;

  JPanel formPanel;
  JScrollPane scrollPane;

  public Signup2(String formno) {
    this.formno = formno;

    try {
      FlatDarkLaf.setup();
    } catch (Exception e) {
      e.printStackTrace();
    }

    setTitle("Secure Bank - Account Opening (Step 2)");
    setSize(900, 800);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    formPanel = new JPanel();
    formPanel.setLayout(null);
    formPanel.setPreferredSize(new Dimension(850, 900));
    formPanel.setBackground(new Color(30, 30, 35));



    JLabel title = new JLabel("Additional Details");
    title.setFont(new Font("Segoe UI", Font.BOLD, 28));
    title.setForeground(Color.WHITE);
    title.setBounds(300, 40, 400, 40);
    formPanel.add(title);

    JLabel formLbl = new JLabel("Form No: " + formno);
    formLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
    formLbl.setForeground(new Color(140, 140, 140));
    formLbl.setBounds(700, 20, 150, 30);
    formPanel.add(formLbl);

    int y = 120;
    int labelX = 100, fieldX = 320;

    addLabel("Religion:", labelX, y, formPanel);
    religionCB = new JComboBox<>(new String[]{"Hindu", "Muslim", "Sikh", "Christian", "Other"});
    styleCombo(religionCB);
    religionCB.setBounds(fieldX, y, 350, 40);
    formPanel.add(religionCB);
    y += 70;

    addLabel("Category:", labelX, y, formPanel);
    categoryCB = new JComboBox<>(new String[]{"General", "OBC", "SC", "ST", "Other"});
    styleCombo(categoryCB);
    categoryCB.setBounds(fieldX, y, 350, 40);
    formPanel.add(categoryCB);
    y += 70;

    addLabel("Income:", labelX, y, formPanel);
    incomeCB = new JComboBox<>(new String[]{"< ₹1,50,000", "< ₹2,50,000", "< ₹5,00,000", "₹5,00,001 - ₹10,00,000", "> ₹10,00,000"});
    styleCombo(incomeCB);
    incomeCB.setBounds(fieldX, y, 350, 40);
    formPanel.add(incomeCB);
    y += 70;

    addLabel("Educational Qualification:", labelX, y, formPanel);
    educationCB = new JComboBox<>(new String[]{"Non-Graduate", "Graduate", "Post-Graduate", "Doctorate", "Others"});
    styleCombo(educationCB);
    educationCB.setBounds(fieldX, y, 350, 40);
    formPanel.add(educationCB);
    y += 70;

    addLabel("Occupation:", labelX, y, formPanel);
    occupationCB = new JComboBox<>(new String[]{"Salaried", "Self-Employed", "Business", "Student", "Retired", "Others"});
    styleCombo(occupationCB);
    occupationCB.setBounds(fieldX, y, 350, 40);
    formPanel.add(occupationCB);
    y += 70;

    addLabel("PAN Number:", labelX, y, formPanel);
    panField = addTextField(fieldX, y, formPanel);
    y += 70;

    addLabel("Aadhaar Number:", labelX, y, formPanel);
    aadharField = addTextField(fieldX, y, formPanel);
    y += 70;

    addLabel("Senior Citizen:", labelX, y, formPanel);
    seniorYes = new JRadioButton("Yes");
    seniorNo = new JRadioButton("No");
    styleRadio(seniorYes); styleRadio(seniorNo);
    seniorYes.setBounds(fieldX, y, 100, 40);
    seniorNo.setBounds(fieldX + 120, y, 100, 40);
    formPanel.add(seniorYes); formPanel.add(seniorNo);
    seniorGroup = new ButtonGroup();
    seniorGroup.add(seniorYes); seniorGroup.add(seniorNo);
    y += 70;

    addLabel("Existing Account:", labelX, y, formPanel);
    existingYes = new JRadioButton("Yes");
    existingNo = new JRadioButton("No");
    styleRadio(existingYes); styleRadio(existingNo);
    existingYes.setBounds(fieldX, y, 100, 40);
    existingNo.setBounds(fieldX + 120, y, 100, 40);
    formPanel.add(existingYes); formPanel.add(existingNo);
    existingGroup = new ButtonGroup();
    existingGroup.add(existingYes); existingGroup.add(existingNo);
    y += 80;

    nextBtn = new JButton("NEXT →");
    nextBtn.setBounds(320, y, 200, 50);
    nextBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
    nextBtn.setBackground(new Color(40, 167, 69));
    nextBtn.setForeground(Color.WHITE);
    nextBtn.addActionListener(this);
    formPanel.add(nextBtn);

    JLabel footer = new JLabel("© 2026 Secure Banking System | Ahmednagar");
    footer.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    footer.setForeground(new Color(140, 140, 140));
    footer.setBounds(250, y + 80, 400, 30);
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
    l.setBounds(x, y, 200, 40);
    p.add(l);
  }

  private JTextField addTextField(int x, int y, JPanel p) {
    JTextField tf = new JTextField();
    tf.setBounds(x, y, 350, 40);
    tf.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    tf.setBackground(new Color(50, 50, 55));
    tf.setForeground(Color.WHITE);
    tf.setCaretColor(new Color(100, 255, 150));
    tf.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    p.add(tf);
    return tf;
  }

  private void styleCombo(JComboBox<String> cb) {
    cb.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    cb.setBackground(new Color(50, 50, 55));
    cb.setForeground(Color.WHITE);
  }

  private void styleRadio(JRadioButton rb) {
    rb.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    rb.setForeground(Color.WHITE);
    rb.setBackground(new Color(30, 30, 35));
    rb.setFocusPainted(false);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String religion = (String) religionCB.getSelectedItem();
    String category = (String) categoryCB.getSelectedItem();
    String income = (String) incomeCB.getSelectedItem();
    String education = (String) educationCB.getSelectedItem();
    String occupation = (String) occupationCB.getSelectedItem();
    String pan = panField.getText().trim();
    String aadhar = aadharField.getText().trim();
    String senior = seniorYes.isSelected() ? "Yes" : "No";
    String existing = existingYes.isSelected() ? "Yes" : "No";

    if (aadhar.isEmpty() || pan.isEmpty()) {
      JOptionPane.showMessageDialog(null, "PAN and Aadhaar are required");
      return;
    }

    try {
      ConnectionSql db = new ConnectionSql();
      String query = "INSERT INTO signup2 (formno, religion, category, income, education, occupation, pan, aadhar, senior, existing) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement ps = db.con.prepareStatement(query);
      ps.setString(1, formno);
      ps.setString(2, religion);
      ps.setString(3, category);
      ps.setString(4, income);
      ps.setString(5, education);
      ps.setString(6, occupation);
      ps.setString(7, pan);
      ps.setString(8, aadhar);
      ps.setString(9, senior);
      ps.setString(10, existing);
      ps.executeUpdate();

      setVisible(false);
      new Signup3(formno).setVisible(true);
    } catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new Signup2("1234"));
  }
}