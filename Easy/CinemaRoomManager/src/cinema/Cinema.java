package cinema;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    static final int MAX_SEATS = 60;
    static final int TICKET_NORMAL_PRICE = 10;
    static final int TICKET_LOW_PRICE = 8;
    static int purchasedTickets = 0;
    static int currentIncome = 0;

    public static void printRoom(char[][] room, int rows, int seats){
        System.out.print("Cinema:\n ");
        for (int i = 1; i <= seats; i++) {
            System.out.print(" " + i);
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < seats; ++j) {
                System.out.print(" " + room[i][j]);
            }
            System.out.println();
        }
    }

    public static void buyTicket(char[][] room, int rows, int seats){
        Scanner scanner = new Scanner(System.in);
        boolean buyingSuccessful = false;
        while (!buyingSuccessful){
            System.out.println("Enter a row number:");
            int rowNum = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seatNum = scanner.nextInt();

            if(rowNum > rows || seatNum > seats){
                System.out.println("Wrong input!");
            } else {
                if(room[rowNum-1][seatNum-1] != 'B'){
                    room[rowNum-1][seatNum-1] = 'B';
                    purchasedTickets++;
                    int numOfSeats = rows*seats;
                    if(numOfSeats <= MAX_SEATS){
                        System.out.println(String.format("Ticket price: $%d", TICKET_NORMAL_PRICE));
                        currentIncome+=TICKET_NORMAL_PRICE;
                    } else {
                        if(rowNum <= Math.floor(rows/2)){
                            System.out.println(String.format("Ticket price: $%d", TICKET_NORMAL_PRICE));
                            currentIncome+=TICKET_NORMAL_PRICE;
                        } else {
                            System.out.println(String.format("Ticket price: $%d", TICKET_LOW_PRICE));
                            currentIncome+=TICKET_LOW_PRICE;
                        }
                    }
                    buyingSuccessful = true;
                } else {
                    System.out.println("That ticket has already been purchased!");
                }
            }
        }
    }

    public static void statistics(char[][] room, int rows, int seats){
        final DecimalFormat df = new DecimalFormat("0.00");
        int totalSeats = rows*seats;
        int firstHalf = (int) Math.floor(rows/2);
        int secondHalf =  rows - firstHalf;

        double percentages = purchasedTickets*100.0/totalSeats;
        int maxIncome = 0;
        if(totalSeats <= MAX_SEATS){
            maxIncome = totalSeats*TICKET_NORMAL_PRICE;
        } else {
            maxIncome = seats*(firstHalf*TICKET_NORMAL_PRICE + secondHalf*TICKET_LOW_PRICE);
        }
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.println(String.format("Percentage: %.2f", percentages) +"%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + maxIncome);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int rows, seats;
        System.out.println("Enter the number of rows:");
        rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = scanner.nextInt();

        char[][] room = new char[rows][seats];
        for(char[] row : room){
            Arrays.fill(row, 'S');
        }
        boolean exitFlag = false;
        while (true && !exitFlag){
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int choice = scanner.nextInt();
            switch (choice){
                case 0:
                    exitFlag = true;
                    break;
                case 1:
                    printRoom(room, rows, seats);
                    break;
                case 2:
                    buyTicket(room, rows, seats);
                    break;
                case 3:
                    statistics(room, rows, seats);
                    break;
            }
        }

    }
}