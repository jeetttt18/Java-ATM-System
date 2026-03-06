import javax.swing.*;
import java.awt.*;

public class SignupGUI extends JFrame {

    private JTextField nameField;
    private JTextField pinField;
    private JButton signupButton;

    public SignupGUI() {

        setTitle("ATM Signup");
        setSize(350,250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new GridLayout(3,2,10,10));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel pinLabel = new JLabel("4 Digit PIN:");
        pinField = new JTextField();

        signupButton = new JButton("Create Account");

        add(nameLabel);
        add(nameField);

        add(pinLabel);
        add(pinField);

        add(new JLabel()); // empty space
        add(signupButton);

        UserService service = new UserService();

        signupButton.addActionListener(e -> {

            try {

                String name = nameField.getText();
                int pin = Integer.parseInt(pinField.getText());

                service.signupFromGUI(name,pin);

            } catch(Exception ex){

                JOptionPane.showMessageDialog(this,"Invalid Input");

            }

        });

        setVisible(true);
    }
}