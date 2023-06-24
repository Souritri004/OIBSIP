import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ATMInterface1 {
    private static int userId = 1234;
    private static int userPin = 5678;
    private static double accountBalance = 1000.0;
    private static List<String> transactionHistory = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("ATM Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel idLabel = new JLabel("User ID:");
        JTextField idField = new JTextField(10);

        JLabel pinLabel = new JLabel("User PIN:");
        JPasswordField pinField = new JPasswordField(10);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            int enteredUserId = Integer.parseInt(idField.getText());
            int enteredPin = Integer.parseInt(new String(pinField.getPassword()));

            if (enteredUserId == userId && enteredPin == userPin) {
                showMainMenu(frame);
            } else {
                JOptionPane.showMessageDialog(frame, "Authentication Failed. Exiting...");
                frame.dispose();
            }
        });

        panel.add(idLabel);
        panel.add(idField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(loginButton);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void showMainMenu(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JButton transactionHistoryButton = new JButton("Transactions History");
        transactionHistoryButton.addActionListener(e -> displayTransactionHistory());

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(e -> performWithdraw(frame));

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(e -> performDeposit(frame));

        JButton transferButton = new JButton("Transfer");
        transferButton.addActionListener(e -> performTransfer(frame));

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Thank you for using the ATM Interface. Exiting...");
            frame.dispose();
        });

        panel.add(transactionHistoryButton);
        panel.add(withdrawButton);
        panel.add(depositButton);
        panel.add(transferButton);
        panel.add(quitButton);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    private static void displayTransactionHistory() {
        StringBuilder history = new StringBuilder();
        if (transactionHistory.isEmpty()) {
            history.append("No transaction history available.");
        } else {
            for (String transaction : transactionHistory) {
                history.append(transaction).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, history.toString(), "Transaction History", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void performWithdraw(JFrame frame) {
        String amount = JOptionPane.showInputDialog(frame, "Enter the amount to withdraw:");
        if (amount != null && !amount.isEmpty()) {
            double withdrawAmount = Double.parseDouble(amount);
            if (withdrawAmount <= accountBalance && withdrawAmount > 0) {
                accountBalance -= withdrawAmount;
                String transaction = "Withdrawal: -" + withdrawAmount + " | New Balance: " + accountBalance;
                transactionHistory.add(transaction);
                JOptionPane.showMessageDialog(frame, "Withdrawal successful. Current balance: " + accountBalance);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid withdrawal amount or insufficient funds.", "Withdrawal Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void performDeposit(JFrame frame) {
        String amount = JOptionPane.showInputDialog(frame, "Enter the amount to deposit:");
        if (amount != null && !amount.isEmpty()) {
            double depositAmount = Double.parseDouble(amount);
            if (depositAmount > 0) {
                accountBalance += depositAmount;
                String transaction = "Deposit: +" + depositAmount + " | New Balance: " + accountBalance;
                transactionHistory.add(transaction);
                JOptionPane.showMessageDialog(frame, "Deposit successful. Current balance: " + accountBalance);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid deposit amount.", "Deposit Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void performTransfer(JFrame frame) {
        String amount = JOptionPane.showInputDialog(frame, "Enter the amount to transfer:");
        if (amount != null && !amount.isEmpty()) {
            double transferAmount = Double.parseDouble(amount);
            if (transferAmount <= accountBalance && transferAmount > 0) {
                String recipientAccount = JOptionPane.showInputDialog(frame, "Enter the recipient's account number:");
                if (recipientAccount != null && !recipientAccount.isEmpty()) {
                    // Implement code to perform the transfer here
                    accountBalance -= transferAmount;
                    String transaction = "Transfer: -" + transferAmount + " | New Balance: " + accountBalance + " | Recipient Account: " + recipientAccount;
                    transactionHistory.add(transaction);
                    JOptionPane.showMessageDialog(frame, "Transfer successful. Current balance: " + accountBalance);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid recipient account number.", "Transfer Failed", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid transfer amount or insufficient funds.", "Transfer Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
