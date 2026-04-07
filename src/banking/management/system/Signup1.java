package banking.management.system;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class Signup1 extends JFrame implements ActionListener {

    long formno;
    JTextField nameTextField, fnameTextField, mnameTextField, dobTextField, emailTextField, addressTextField, cityTextField, stateTextField, natTextField;
    JButton nextBtn;
    JRadioButton male, female, married, unmarried, otherGender;
    ButtonGroup genderGroup, maritalGroup;

    JPanel formPanel;
    JScrollPane scrollPane;

    public Signup1() {
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Secure Bank - Account Opening (Step 1)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 850);
        setLocationRelativeTo(null);

        formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setPreferredSize(new Dimension(850, 1100));
        formPanel.setBackground(new Color(30, 30, 35)); // Dark bg

        Random ran = new Random();
        formno = Math.abs((ran.nextLong() % 9000L) + 1000L);

        JLabel title = new JLabel("APPLICATION FORM NO: " + formno);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(100, 255, 150));
        title.setBounds(140, 30, 600, 50);
        formPanel.add(title);

        JLabel personal = new JLabel("Personal Details");
        personal.setFont(new Font("Segoe UI", Font.BOLD, 24));
        personal.setForeground(Color.WHITE);
        personal.setBounds(300, 100, 300, 40);
        formPanel.add(personal);

        int y = 160;
        int labelX = 100, fieldX = 280, width = 400;

        addLabel("Full Name:", labelX, y, formPanel);
        nameTextField = addTextField(fieldX, y, formPanel);
        y += 60;

        addLabel("Father's Name:", labelX, y, formPanel);
        fnameTextField = addTextField(fieldX, y, formPanel);
        y += 60;

        addLabel("Mother's Name:", labelX, y, formPanel);
        mnameTextField = addTextField(fieldX, y, formPanel);
        y += 60;

        addLabel("Gender:", labelX, y, formPanel);
        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        male.setBounds(fieldX, y, 80, 35);
        female.setBounds(fieldX + 100, y, 100, 35);
        styleRadio(male); styleRadio(female);
        formPanel.add(male); formPanel.add(female);
        genderGroup = new ButtonGroup();
        genderGroup.add(male); genderGroup.add(female);
        y += 60;

        addLabel("DoB (DD/MM/YYYY):", labelX, y, formPanel);
        dobTextField = addTextField(fieldX, y, formPanel);
        y += 60;

        addLabel("Email Address:", labelX, y, formPanel);
        emailTextField = addTextField(fieldX, y, formPanel);
        y += 60;

        addLabel("Marital Status:", labelX, y, formPanel);
        married = new JRadioButton("Married");
        unmarried = new JRadioButton("Unmarried");
        otherGender = new JRadioButton("Other");
        married.setBounds(fieldX, y, 120, 35);
        unmarried.setBounds(fieldX + 130, y, 140, 35);
        otherGender.setBounds(fieldX + 280, y, 100, 35);
        styleRadio(married); styleRadio(unmarried); styleRadio(otherGender);
        formPanel.add(married); formPanel.add(unmarried); formPanel.add(otherGender);
        maritalGroup = new ButtonGroup();
        maritalGroup.add(married); maritalGroup.add(unmarried); maritalGroup.add(otherGender);
        y += 60;

        addLabel("Address:", labelX, y, formPanel);
        addressTextField = addTextField(fieldX, y, formPanel);
        y += 60;

        addLabel("City:", labelX, y, formPanel);
        cityTextField = addTextField(fieldX, y, formPanel);
        y += 60;

        addLabel("State:", labelX, y, formPanel);
        stateTextField = addTextField(fieldX, y, formPanel);
        y += 60;

        addLabel("Nationality:", labelX, y, formPanel);
        natTextField = addTextField(fieldX, y, formPanel);
        y += 80;

        nextBtn = new JButton("NEXT →");
        nextBtn.setBounds(300, y, 200, 50);
        nextBtn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nextBtn.setBackground(new Color(40, 167, 69));
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setFocusPainted(false);
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

    private void addLabel(String text, int x, int y, JPanel panel) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lbl.setForeground(Color.WHITE);
        lbl.setBounds(x, y, 200, 35);
        panel.add(lbl);
    }

    private JTextField addTextField(int x, int y, JPanel panel) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, 400, 40);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tf.setBackground(new Color(50, 50, 55));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(new Color(100, 255, 150));
        tf.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.add(tf);
        return tf;
    }

    private void styleRadio(JRadioButton rb) {
        rb.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rb.setForeground(Color.WHITE);
        rb.setBackground(new Color(30, 30, 35));
        rb.setFocusPainted(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String form = "" + formno;
        String name = nameTextField.getText().trim();
        String fname = fnameTextField.getText().trim();
        String mname = mnameTextField.getText().trim();
        String gender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "";
        String dob = dobTextField.getText().trim();
        String email = emailTextField.getText().trim();
        String marital = married.isSelected() ? "Married" : unmarried.isSelected() ? "Unmarried" : otherGender.isSelected() ? "Other" : "";
        String address = addressTextField.getText().trim();
        String city = cityTextField.getText().trim();
        String state = stateTextField.getText().trim();
        String nat = natTextField.getText().trim();

        if (name.isEmpty() || fname.isEmpty() || dob.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all required fields", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ConnectionSql db = new ConnectionSql();
            String query = "INSERT INTO signup1 (formno, name, fname, mname, dob, gender, email, marital, address, city, state, nat) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = db.con.prepareStatement(query);
            ps.setString(1, form);
            ps.setString(2, name);
            ps.setString(3, fname);
            ps.setString(4, mname);
            ps.setString(5, dob);
            ps.setString(6, gender);
            ps.setString(7, email);
            ps.setString(8, marital);
            ps.setString(9, address);
            ps.setString(10, city);
            ps.setString(11, state);
            ps.setString(12, nat);
            ps.executeUpdate();

            setVisible(false);
            new Signup2(form).setVisible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving data: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Signup1());
    }
}