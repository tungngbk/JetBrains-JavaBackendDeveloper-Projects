package tictactoe;

import java.util.Scanner;

public class HumanMove implements StrategyMove {

    public Move makeMove(GameBoard gameboard, char piece) {
        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;
        int row = 0;
        int col = 0;
        while (!validInput) {
            System.out.print("Enter the coordinates: ");
            String inputUser = scanner.nextLine();
            if (!inputUser.matches("\\d\\s\\d") || inputUser.equals("")) {
                System.out.println("You should enter numbers!");
                continue;
            }
            String[] coordinates = inputUser.split(" ");
            row = Integer.parseInt(coordinates[0]);
            col = Integer.parseInt(coordinates[1]);
            if (col > 3 || col < 1 || row > 3 || row < 1) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
            col -= 1;
            row -= 1;

            if (gameboard.isCellFree(row, col)) {
                validInput = true;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
        return new Move(row, col, piece);
    }
}