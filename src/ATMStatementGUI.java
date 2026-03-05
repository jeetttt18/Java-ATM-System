import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ATMStatementGUI extends JFrame {

    public ATMStatementGUI(int accountNumber) {

        setTitle("Account Statement");
        setSize(500,400);
        setLocationRelativeTo(null);

        String[] columns = {"Type", "Amount", "Date"};

        DefaultTableModel model = new DefaultTableModel(columns,0);

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        UserService service = new UserService();

        try {

            ResultSet rs = service.getTransactions(accountNumber);

            while(rs.next()){

                String type = rs.getString("type");
                double amount = rs.getDouble("amount");
                String date = rs.getString("transaction_date");

                model.addRow(new Object[]{type, amount, date});
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        setVisible(true);
    }
}