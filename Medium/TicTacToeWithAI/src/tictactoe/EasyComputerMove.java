package tictactoe;

import java.util.Random;

public class EasyComputerMove implements StrategyMove {
    public Move makeMove(GameBoard gameboard, char piece) {
        int row;
        int col;
        Random random = new Random();
        System.out.println("Making move level \"easy\"");
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!gameboard.isCellFree(row, col));

        return new Move(row, col, piece);
    }

}