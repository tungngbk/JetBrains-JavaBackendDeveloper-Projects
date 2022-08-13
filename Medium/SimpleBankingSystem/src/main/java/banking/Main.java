package banking;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        BankingSystem bankingSystem = new BankingSystem(args[1]);
        while (!bankingSystem.isExitSystem()){
            bankingSystem.run();
        }
    }
}