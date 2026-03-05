import javax.swing.*;
import java.awt.*;

public class ATMDashboardGUI extends JFrame {

    public ATMDashboardGUI(int accountNumber) {

        UserService service = new UserService();

        setTitle("ATM Machine Dashboard");
        setSize(400,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        // ===== TOP PANEL =====
        JPanel topPanel = new JPanel();
        JLabel title = new JLabel("Welcome to ATM");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(title);

        add(topPanel, BorderLayout.NORTH);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5,1,10,10));

        JButton balanceBtn = new JButton("Check Balance");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton statementBtn = new JButton("Account Statement");
        JButton logoutBtn = new JButton("Logout");

        buttonPanel.add(balanceBtn);
        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(statementBtn);
        buttonPanel.add(logoutBtn);

        add(buttonPanel, BorderLayout.CENTER);

        // ===== CHECK BALANCE =====
        balanceBtn.addActionListener(e -> {

            double balance = service.getBalance(accountNumber);

            JOptionPane.showMessageDialog(this,
                    "Your Balance: ₹ " + balance);
        });

        // ===== DEPOSIT =====
        depositBtn.addActionListener(e -> {

            String input = JOptionPane.showInputDialog("Enter deposit amount:");

            if(input == null) return;

            try {

                double amount = Double.parseDouble(input);

                if(amount <= 0){
                    JOptionPane.showMessageDialog(this,
                            "Amount must be greater than 0");
                    return;
                }

                service.depositFromGUI(accountNumber, amount);

                JOptionPane.showMessageDialog(this,
                        "Deposit Successful!");

            }
            catch(NumberFormatException ex){

                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number!");
            }

        });
        // ===== WITHDRAW =====
        withdrawBtn.addActionListener(e -> {

            String input = JOptionPane.showInputDialog("Enter withdraw amount:");

            if(input == null) return;

            try {

                double amount = Double.parseDouble(input);

                if(amount <= 0){
                    JOptionPane.showMessageDialog(this,
                            "Amount must be greater than 0");
                    return;
                }

                boolean success = service.withdrawFromGUI(accountNumber, amount);

                if(success)
                    JOptionPane.showMessageDialog(this,
                            "Withdraw Successful!");
                else
                    JOptionPane.showMessageDialog(this,
                            "Insufficient Balance!");

            }
            catch(NumberFormatException ex){

                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number!");
            }

        });

        // ===== ACCOUNT STATEMENT =====
        statementBtn.addActionListener(e -> {

            new ATMStatementGUI(accountNumber);

        });

        // ===== LOGOUT =====
        logoutBtn.addActionListener(e -> {

            dispose(); // close dashboard

            new ATMLoginGUI(); // back to login

        });

        setVisible(true);
    }
}