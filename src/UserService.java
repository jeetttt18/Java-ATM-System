import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {

    // ==========================
    // LOGIN FOR GUI
    // ==========================
    public int loginFromGUI(int accountNumber, int pin) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM users WHERE account_number = ? AND pin = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, accountNumber);
            pst.setInt(2, pin);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                con.close();
                return accountNumber;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // ==========================
    // GET BALANCE
    // ==========================
    public double getBalance(int accountNumber) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT balance FROM users WHERE account_number = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, accountNumber);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                con.close();
                return balance;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    // ==========================
    // DEPOSIT (GUI)
    // ==========================
    public void depositFromGUI(int accountNumber, double amount) {

        if (amount <= 0) return;

        try {
            Connection con = DBConnection.getConnection();

            // Update balance
            String updateQuery = "UPDATE users SET balance = balance + ? WHERE account_number = ?";
            PreparedStatement pst1 = con.prepareStatement(updateQuery);
            pst1.setDouble(1, amount);
            pst1.setInt(2, accountNumber);
            pst1.executeUpdate();

            // Insert transaction
            String insertQuery = "INSERT INTO transactions (account_number, type, amount) VALUES (?, 'DEPOSIT', ?)";
            PreparedStatement pst2 = con.prepareStatement(insertQuery);
            pst2.setInt(1, accountNumber);
            pst2.setDouble(2, amount);
            pst2.executeUpdate();

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==========================
    // WITHDRAW (GUI)
    // ==========================
    public boolean withdrawFromGUI(int accountNumber, double amount) {

        if (amount <= 0) return false;

        try {
            Connection con = DBConnection.getConnection();

            // Check balance
            String balanceQuery = "SELECT balance FROM users WHERE account_number = ?";
            PreparedStatement pst1 = con.prepareStatement(balanceQuery);
            pst1.setInt(1, accountNumber);
            ResultSet rs = pst1.executeQuery();

            if (rs.next()) {

                double currentBalance = rs.getDouble("balance");

                if (currentBalance < amount) {
                    con.close();
                    return false;
                }

                // Deduct balance
                String updateQuery = "UPDATE users SET balance = balance - ? WHERE account_number = ?";
                PreparedStatement pst2 = con.prepareStatement(updateQuery);
                pst2.setDouble(1, amount);
                pst2.setInt(2, accountNumber);
                pst2.executeUpdate();

                // Insert transaction
                String insertQuery = "INSERT INTO transactions (account_number, type, amount) VALUES (?, 'WITHDRAW', ?)";
                PreparedStatement pst3 = con.prepareStatement(insertQuery);
                pst3.setInt(1, accountNumber);
                pst3.setDouble(2, amount);
                pst3.executeUpdate();

                con.close();
                return true;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public String getUserName(int accountNumber) {

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT name FROM users WHERE account_number = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, accountNumber);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                con.close();
                return name;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "User";
    }
    public ResultSet getTransactions(int accountNumber) {

        try {

            Connection con = DBConnection.getConnection();

            String query = "SELECT type, amount, transaction_date FROM transactions WHERE account_number = ?";

            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, accountNumber);

            return pst.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public void signupFromGUI(String name, int pin) {

        try {

            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO users (name, pin, balance) VALUES (?, ?, 0)";

            PreparedStatement pst = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setString(1, name);
            pst.setInt(2, pin);

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();

            if (rs.next()) {

                int acc = rs.getInt(1);

                JOptionPane.showMessageDialog(null,
                        "Signup Successful!\nYour Account Number: " + acc);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}