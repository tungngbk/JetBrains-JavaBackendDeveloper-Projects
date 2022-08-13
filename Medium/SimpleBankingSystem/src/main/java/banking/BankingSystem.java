package banking;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BankingSystem {
    private Scanner scanner;
    private boolean exitSystem;
    private SQLiteDataSource sqLiteDataSource;
    private boolean dbCreated;
    private AccountService accountService;

    public BankingSystem(String pathToDb) {
        this.scanner = new Scanner(System.in);
        this.sqLiteDataSource = new SQLiteDataSource();
        this.sqLiteDataSource.setUrl("jdbc:sqlite:" + pathToDb);
        this.dbCreated = false;
        this.exitSystem = false;
        this.accountService = new AccountService();
    }

    public void run(){
        if (!dbCreated) {
            createDatabase();
            dbCreated = true;
        }
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                createAccount();
                break;
            case 2:
                logInAccount();
                break;
            case 0:
                exitSystem = true;
                System.out.println("Bye!");
                break;
        }
    }
    private void createAccount(){
        String bin = "400000";
        String identifier = "";
        String cardPin ="";
        Random random = new Random();
        // Create identifier part without checksum
        for(int i = 0; i < 9; i++){
            identifier += random.nextInt(10);
        }
        String cardNumber = bin + identifier;
        for(int i = 0; i < 4; i++){
            cardPin += random.nextInt(10);
        }
        // Create checksum
        int sumOfDigits = 0;
        for(int i = 0; i < 15; i++){
            // i mod 2 = 0 => odd digit (i start with 0)
            int valueOfDigit = cardNumber.charAt(i) - '0';
            if(i%2 == 0){
                valueOfDigit *= 2;
                if(valueOfDigit > 9){
                    valueOfDigit -= 9;
                }
            }
            sumOfDigits += valueOfDigit;
        }
        if(sumOfDigits % 10 == 0){
            cardNumber += "0";
        } else {
            cardNumber += String.valueOf(10 - sumOfDigits%10);
        }
        Account account = new Account(cardNumber, cardPin);
//        saveAccount(account);
        accountService.saveAccount(account, this.sqLiteDataSource);
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(cardPin);
    }

    private void logInAccount(){
        System.out.println("Enter your card number:");
        String cardNumber = scanner.next();
        System.out.println("Enter your PIN:");
        String cardPin = scanner.next();
        Account currentAccount = new Account(cardNumber, cardPin);
        Boolean logInSuccessful = accountService.checkAccount(currentAccount, this.sqLiteDataSource);
        if(logInSuccessful){
            System.out.println("You have successfully logged in!");
            logInSuccessful(currentAccount);
        } else {
            System.out.println("Wrong card number or PIN!");
        }
    }



    private void printLoginMenu(){
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }

    private void logInSuccessful(Account account){
        boolean logOut = false;
        while (!logOut){
            printLoginMenu();
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    int balance = accountService.getBalance(account, this.sqLiteDataSource);
                    System.out.println("Balance: " + balance);
                    break;
                case 2:
                    System.out.println("Enter income:");
                    int income = scanner.nextInt();
                    accountService.addIncome(account, this.sqLiteDataSource, income);
                    System.out.println("Income was added!");
                    break;
                case 3:
                    System.out.println("Transfer");
                    System.out.println("Enter card number:");
                    String cardNumber = scanner.next();
                    accountService.transferMoney(account, this.sqLiteDataSource, cardNumber);
                    break;
                case 4:
                    accountService.closeAccount(account, this.sqLiteDataSource);
                    System.out.println("The account has been closed!");
                    break;
                case 5:
                    logOut = true;
                    logOut();
                    break;
                case 0:
                    exitSystem = true;
                    logOut = true;
                    System.out.println("Bye!");
                    break;
            }
        }
    }

    private void logOut(){
        System.out.println("You have successfully logged out!");
    }

    public boolean isExitSystem() {
        return exitSystem;
    }

    private void createDatabase(){
        String query = "CREATE TABLE IF NOT EXISTS card ("
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "	number TEXT,"
                + "	pin TEXT,"
                + " balance INTEGER DEFAULT 0"
                + ");";
        try(Connection connection = this.sqLiteDataSource.getConnection()) {
            try(Statement statement = connection.createStatement()) {
                statement.execute(query);
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred while connecting to database.");
        }
    }

}
