package tictactoe;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class Main {

    static ArrayList<Integer> playerOPositions = new ArrayList<Integer>();
    static ArrayList<Integer> playerXPositions = new ArrayList<Integer>();
    public static boolean isNumber(String first, String second) {
        return first.matches("\\d+") && second.matches("\\d+");
    }

    public static int convertPosition(int xPos, int yPos){
        if(xPos == 1 && yPos == 1) return 1;
        if(xPos == 1 && yPos == 2) return 2;
        if(xPos == 1 && yPos == 3) return 3;

        if(xPos == 2 && yPos == 1) return 4;
        if(xPos == 2 && yPos == 2) return 5;
        if(xPos == 2 && yPos == 3) return 6;

        if(xPos == 3 && yPos == 1) return 7;
        if(xPos == 3 && yPos == 2) return 8;
        if(xPos == 3 && yPos == 3) return 9;

        return 0;
    }
    public static void printField(char[][] field) {
        System.out.println("---------");
        for (int i = 0; i < 3; ++i) {
            System.out.print("| ");
            for (int j = 0; j < 3; ++j) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public static char playerTurn(String player){
        char symbol = ' ';
        if(player.equals("playerX")){
            symbol = 'X';
        } else if (player.equals("playerO")) {
            symbol = 'O';
        }
        return symbol;
    }

    public static String checkWinner(){
        List topRow = Arrays.asList(1, 2, 3);
        List midRow = Arrays.asList(4, 5, 6);
        List botRow = Arrays.asList(7, 8, 9);
        List leftCol = Arrays.asList(1, 4, 7);
        List midCol = Arrays.asList(2, 5, 8);
        List rightCol = Arrays.asList(3, 6, 9);
        List cross1 = Arrays.asList(1, 5, 9);
        List cross2 = Arrays.asList(7, 5, 3);

        List<List> winning = new ArrayList<List>();
        winning.add(topRow);
        winning.add(midRow);
        winning.add(botRow);
        winning.add(leftCol);
        winning.add(midCol);
        winning.add(rightCol);
        winning.add(cross1);
        winning.add(cross2);

        boolean checkXWin = false;
        boolean checkOWin = false;

        for (List l : winning) {
            if(playerXPositions.containsAll(l)) checkXWin = true;
            if(playerOPositions.containsAll(l)) checkOWin = true;
        }
        if(checkXWin) return "X wins";
        if(checkOWin) return "O wins";
        if(playerXPositions.size() + playerOPositions.size() == 9) return "Draw";
        return "";
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        char[][] field = new char[3][3];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                field[i][j] = ' ';
            }
        }
        printField(field);
        boolean xTurn = true;
        while (true) {
            System.out.println("Enter the coordinates: ");
            String x1, x2;
            x1 = s.next();
            x2 = s.next();
            if (!isNumber(x1, x2)) {
                System.out.println("You should enter numbers!");
                continue;
            }
            int xPos = Integer.parseInt(x1);
            int yPos = Integer.parseInt(x2);
            if (xPos < 1 || xPos > 3 || yPos < 1 || yPos > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else {
                if (field[xPos - 1][yPos - 1] != ' ') {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    if(xTurn) {
                        field[xPos - 1][yPos - 1] = playerTurn("playerX");
                        playerXPositions.add(convertPosition(xPos, yPos));
                        xTurn = false;
                    }
                    else {
                        field[xPos - 1][yPos - 1] = playerTurn("playerO");
                        playerOPositions.add(convertPosition(xPos, yPos));
                        xTurn = true;
                    }

                }
            }
            printField(field);
            String result = checkWinner();
            if(result.length() > 0){
                System.out.println(result);
                break;
            }

        }

    }
}