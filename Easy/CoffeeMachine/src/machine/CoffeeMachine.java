package machine;

import java.util.Scanner;
import java.util.stream.Stream;

public class CoffeeMachine {
    private final static int REQUIRED_WATER = 200;
    private final static int REQUIRED_MILK = 50;
    private final static int REQUIRED_BEANS = 15;
    public static final String NO_I_CAN = "No, I can make only %d cup(s) of coffee";
    public static final String YES_I_CAN = "Yes, I can make that amount of coffee";
    public static final String YES_I_CAN_AND_EVER = "Yes, I can make that amount of coffee (and even %d more than that)";

    public static final String MISSING_WATER = "Sorry, not enough water!";
    public static final String MISSING_MILK = "Sorry, not enough milk!";
    public static final String MISSING_COFFEE_BEANS = "Sorry, not enough coffee beans!";
    public static final String MISSING_DISPOSABLE_CUPS = "Sorry, not enough disposable cups!";
    public static final String ENOUGH_RESOURCES = "I have enough resources, making you a coffee!";
    public static int MONEY = 550;
    public static int WATER = 400;
    public static  int MILK = 540;
    public static int COFFEE_BEANS = 120;
    public static int DISPOSABLE_CUPS = 9;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exitFlag = false;
        while (!exitFlag){
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String choice = scanner.next();
            switch (choice){
                case "buy":
                    buyCoffee();
                    break;
                case "fill":
                    fillMachine();
                    break;
                case "take":
                    takeMoney();
                    break;
                case "remaining":
                    printCoffeeMachine(WATER, MILK, COFFEE_BEANS, DISPOSABLE_CUPS, MONEY);
                    break;
                case "exit":
                    exitFlag = true;
                    break;
                default:
                    break;
            }
        }
//        howManyCups();
    }

    public static void printCoffeeMachine(int water, int milk, int coffeeBeans, int disposableCups, int money){
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(coffeeBeans + " g of coffee beans");
        System.out.println(disposableCups + " disposable cups");
        System.out.println("$" + money + " of money");
    }

    public static void howManyCups() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water the coffee machine has:");
        int water = scanner.nextInt() / REQUIRED_WATER;
        System.out.println("Write how many ml of milk the coffee machine has:");
        int milk = scanner.nextInt() / REQUIRED_MILK;
        System.out.println("Write how many grams of coffee beans the coffee machine has:");
        int beans = scanner.nextInt() / REQUIRED_BEANS;
        System.out.println("Write how many cups of coffee you will need:");
        int cups = scanner.nextInt();

        int makeCups = Math.min(Math.min(water,milk),beans);

        if (makeCups == cups) {
            System.out.println(YES_I_CAN);
        } else if (makeCups < cups) {
            System.out.printf(NO_I_CAN, makeCups);
        } else {
            System.out.printf(YES_I_CAN_AND_EVER, makeCups - cups);
        }
    }

    public static void buyCoffee(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String choice = scanner.next();
        switch (choice){
            case "1":
                if(WATER < 250) {
                    System.out.println(MISSING_WATER);
                    break;
                } else if (COFFEE_BEANS < 16) {
                    System.out.println(MISSING_COFFEE_BEANS);
                    break;
                } else if (DISPOSABLE_CUPS < 1) {
                    System.out.println(MISSING_DISPOSABLE_CUPS);
                    break;
                }
                WATER -= 250;
                COFFEE_BEANS -= 16;
                MONEY += 4;
                DISPOSABLE_CUPS -= 1;
                System.out.println(ENOUGH_RESOURCES);
                break;
            case "2":
                if(WATER < 350) {
                    System.out.println(MISSING_WATER);
                    break;
                } else if (COFFEE_BEANS < 20) {
                    System.out.println(MISSING_COFFEE_BEANS);
                    break;
                } else if (DISPOSABLE_CUPS < 1) {
                    System.out.println(MISSING_DISPOSABLE_CUPS);
                    break;
                } else if (MILK < 75) {
                    System.out.println(MISSING_MILK);
                    break;
                }
                WATER -= 350;
                MILK -= 75;
                COFFEE_BEANS -= 20;
                MONEY += 7;
                DISPOSABLE_CUPS -= 1;
                System.out.println(ENOUGH_RESOURCES);
                break;
            case "3":
                if(WATER < 200) {
                    System.out.println(MISSING_WATER);
                    break;
                } else if (COFFEE_BEANS < 12) {
                    System.out.println(MISSING_COFFEE_BEANS);
                    break;
                } else if (DISPOSABLE_CUPS < 1) {
                    System.out.println(MISSING_DISPOSABLE_CUPS);
                    break;
                } else if (MILK < 100) {
                    System.out.println(MISSING_MILK);
                    break;
                }
                WATER -= 200;
                MILK -= 100;
                COFFEE_BEANS -= 12;
                MONEY += 6;
                DISPOSABLE_CUPS -= 1;
                System.out.println(ENOUGH_RESOURCES);
                break;
            case "back":
                return;
        }
    }
    public static void fillMachine(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water you want to add:");
        WATER += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        MILK += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        COFFEE_BEANS += scanner.nextInt();
        System.out.println("Write how many disposable cups of coffee you want to add:");
        DISPOSABLE_CUPS += scanner.nextInt();
    }

    public static void takeMoney(){
        System.out.println("I gave you $" + MONEY);
        MONEY = 0;
    }
}