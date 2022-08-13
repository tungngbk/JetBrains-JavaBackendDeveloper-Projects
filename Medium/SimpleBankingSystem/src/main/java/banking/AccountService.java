package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Scanner;

public class AccountService {

    private Scanner scanner = new Scanner(System.in);
    public void saveAccount(Account account, SQLiteDataSource sqLiteDataSource){
        String query = "INSERT INTO card(id,number,pin,balance) VALUES(?,?,?,?);";
        try(Connection connection = sqLiteDataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setNull(1, Types.NULL);
            statement.setString(2, account.getCardNumber());
            statement.setString(3, account.getCardPin());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Exception occurred while connecting to database.");
        }
    }

    public boolean checkAccount(Account account, SQLiteDataSource sqLiteDataSource){
        String query = "SELECT * FROM card WHERE number = ? AND pin = ?";
        boolean recordExist = true;
        try(Connection connection = sqLiteDataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, account.getCardNumber());
                preparedStatement.setString(2, account.getCardPin());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (!resultSet.next()){
                    recordExist = false;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Check fail!");
        }
        return recordExist;
    }

    public boolean checkValidCardNumber(String cardNumber, SQLiteDataSource sqLiteDataSource){
        String query = "SELECT * FROM card WHERE number = ?";
        boolean recordExist = true;
        try(Connection connection = sqLiteDataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, cardNumber);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (!resultSet.next()){
                    recordExist = false;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Check fail!");
        }
        return recordExist;
    }

    private boolean checkLuhn(String cardNumber){
        int nDigits = cardNumber.length() - 1;
        int sumOfDigits = 0;
        for(int i = 0; i < nDigits; i++){
            int valueOfDigit = cardNumber.charAt(i) - '0';
            if(i%2 == 0){
                valueOfDigit *= 2;
                if(valueOfDigit > 9){
                    valueOfDigit -= 9;
                }
            }
            sumOfDigits += valueOfDigit;
        }
        sumOfDigits += cardNumber.charAt(nDigits) - '0';
        return (sumOfDigits % 10 == 0);
    }

    public int getBalance(Account account, SQLiteDataSource sqLiteDataSource){
        String query = "SELECT * FROM card WHERE number = " +
                "'" + account.getCardNumber() +"';";
        try(Connection connection = sqLiteDataSource.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                return resultSet.getInt("balance");
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred while connecting to database.");
        }
        return 0;
    }

    public void addIncome(Account account, SQLiteDataSource sqLiteDataSource, int income) {
        int currentIncome = getBalance(account, sqLiteDataSource);
        int newIncome = currentIncome + income;
        updateBalance(account, sqLiteDataSource, newIncome);
    }

    public void closeAccount(Account account, SQLiteDataSource sqLiteDataSource) {
        try (Connection connection = sqLiteDataSource.getConnection()){
            try (Statement statement = connection.createStatement()) {
                String query = "DELETE FROM card WHERE number = " + account.getCardNumber() + ";";
                statement.executeUpdate(query);
            }

        } catch (SQLException e) {
            System.out.println("Fail to close account");
        }
    }

    public void transferMoney(Account account, SQLiteDataSource sqLiteDataSource, String cardNumber) {
        if(!checkLuhn(cardNumber)){
            System.out.println("Probably you made a mistake in the card number.");
            return;
        }
        if(!checkValidCardNumber(cardNumber, sqLiteDataSource)){
            System.out.println("Such a card does not exist.");
            return;
        }
        String query = "UPDATE card SET balance = ? WHERE number = ?";
        String select = "SELECT * FROM card WHERE number = ?";
        try(Connection connection = sqLiteDataSource.getConnection()) {
            connection.setAutoCommit(false);
            try(PreparedStatement selectAccount = connection.prepareStatement(select);
                PreparedStatement selectAccount2 = connection.prepareStatement(select);
                    PreparedStatement updateSenderBalance = connection.prepareStatement(query);
                PreparedStatement updateReceiverBalance = connection.prepareStatement(query)) {

                selectAccount.setString(1, account.getCardNumber());
                ResultSet resultSet = selectAccount.executeQuery();
                int currentIncome = 0;
                while (resultSet.next()){
                    if(resultSet.getString(2).equals(cardNumber)){
                        System.out.println("You can't transfer money to the same account!");
                        return;
                    }
                    currentIncome = resultSet.getInt(4);
                }
                System.out.println("Enter how much money you want to transfer:");
                int amount = scanner.nextInt();
                if(amount > currentIncome){
                    System.out.println("Not enough money!");
                    return;
                }
                // Update balance of sender
                int newIncome = currentIncome - amount;
                updateSenderBalance.setInt(1, newIncome);
                updateSenderBalance.setString(2, account.getCardNumber());
                updateSenderBalance.executeUpdate();

                // Update balance of receiver
                selectAccount2.setString(1, cardNumber);
                ResultSet resultSet1 = selectAccount2.executeQuery();
                int receiverIncome = 0;
                while (resultSet1.next()){
                    receiverIncome = resultSet1.getInt(4);
                }
                int newReceiverIncome = receiverIncome + amount;
                updateReceiverBalance.setInt(1, newReceiverIncome);
                updateReceiverBalance.setString(2, cardNumber);
                updateReceiverBalance.executeUpdate();

                connection.commit();
                System.out.println("Success!");
            }
        } catch (SQLException e) {
            System.out.println("Fail.");
            return;
        }
    }

    public void updateBalance(Account account, SQLiteDataSource sqLiteDataSource, int balance){
        try(Connection connection = sqLiteDataSource.getConnection()) {
            try(Statement statement = connection.createStatement()){
                String query = "UPDATE card " +
                        "SET balance = " + balance +
                        " WHERE number = '" + account.getCardNumber() + "';";
                statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            System.out.println("Fail to update balance!.");
        }
    }
}
