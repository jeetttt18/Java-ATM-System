import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ATMLoginGUI extends JFrame {

    private JTextField accountField;
    private JPasswordField pinField;
    private JButton loginButton;
    private JButton signupButton;

    private UserService userService;

    public ATMLoginGUI() {

        userService = new UserService();

        setTitle("ATM Login");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Labels and Fields
        JLabel accountLabel = new JLabel("Account Number:");
        accountField = new JTextField();

        JLabel pinLabel = new JLabel("PIN:");
        pinField = new JPasswordField();

        loginButton = new JButton("Login");
        signupButton = new JButton("Signup");

        // Add components
        add(accountLabel);
        add(accountField);
        add(pinLabel);
        add(pinField);
        add(loginButton);
        add(signupButton);

        // Login Button Action
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginAction();
            }
        });

        // Signup Button Action (optional)
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Use Console Signup for now.");
            }
        });

        setVisible(true);
    }

    private void loginAction() {

        try {
            int accountNumber = Integer.parseInt(accountField.getText());
            int pin = Integer.parseInt(new String(pinField.getPassword()));

            int result = userService.loginFromGUI(accountNumber, pin);

            if (result != -1) {
                JOptionPane.showMessageDialog(this, "Login Successful!");

                dispose(); // close login window
                new ATMDashboardGUI(accountNumber); // open dashboard

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Account Number or PIN!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers only!");
        }
    }
}