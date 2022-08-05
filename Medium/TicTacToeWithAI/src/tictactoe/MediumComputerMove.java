package tictactoe;

import java.util.Random;

public class MediumComputerMove implements StrategyMove {
    int[] rowAndCol = new int[2];
    int[][] shadowBoard = new int[3][3];
    Random random = new Random();
    final int ONE_PLAYER_PIECE = 5;
    final int ONE_OPPONENT_PIECE = -1;
    final int TWO_PLAYER_PIECES_TO_WIN = 10;
    final int TWO_OPPONENT_PIECES_TO_LOSE = -2;

    public Move makeMove(GameBoard gameboard, char piece) {
        rowAndCol[0] = -1;
        rowAndCol[1] = -1;
        System.out.println("Making move level \"medium\"");

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (!gameboard.isCellFree(row, col)) {
                    shadowBoard[row][col] = gameboard.getCell(row, col) == piece ? ONE_PLAYER_PIECE : ONE_OPPONENT_PIECE;
                }
            }
        }

        findOpenNextToTwo(gameboard, TWO_PLAYER_PIECES_TO_WIN);

        if (rowAndCol[0] == -1) {
            findOpenNextToTwo(gameboard, TWO_OPPONENT_PIECES_TO_LOSE);
        }
        if (rowAndCol[0] == -1) {
            do {
                rowAndCol[0] = random.nextInt(3);
                rowAndCol[1] = random.nextInt(3);
            } while (!gameboard.isCellFree(rowAndCol[0], rowAndCol[1]));
        }

        return new Move(rowAndCol[0], rowAndCol[1], piece);
    }

    void findOpenNextToTwo(GameBoard gameBoard, int numberOfTwoAndOpen) {
        int checkFigure;
        for (int variableRowCol = 0; variableRowCol < 3; variableRowCol++) {
            // check rows
            checkFigure = shadowBoard[variableRowCol][0] + shadowBoard[variableRowCol][1] + shadowBoard[variableRowCol][2];
            if (checkFigure == numberOfTwoAndOpen) {
                for (int i = 0; i < 3; i++) {
                    if (findOpenCellMakeMove(gameBoard, variableRowCol, i)) {
                        return;
                    }
                }
            }
            // check columns
            checkFigure = shadowBoard[0][variableRowCol] + shadowBoard[1][variableRowCol] + shadowBoard[2][variableRowCol];
            if (checkFigure == numberOfTwoAndOpen) {
                for (int i = 0; i < 3; i++) {
                    if (findOpenCellMakeMove(gameBoard, i, variableRowCol)) {
                        return;
                    }
                }
            }
        }
        //check diagonals
        checkFigure = shadowBoard[0][0] + shadowBoard[1][1] + shadowBoard[2][2];
        if (checkFigure == numberOfTwoAndOpen) {
            for (int i = 0; i < 3; i++) {
                if (findOpenCellMakeMove(gameBoard, i, i)) {
                    return;
                }
            }
        }
        checkFigure = shadowBoard[2][0] + shadowBoard[1][1] + shadowBoard[0][2];
        if (checkFigure == numberOfTwoAndOpen) {
            for (int i = 0; i < 3; i++) {
                if (findOpenCellMakeMove(gameBoard,2 - i, i)) {
                    return;
                }
            }
        }
    }
    boolean findOpenCellMakeMove(GameBoard gameBoard, int row, int col) {
        boolean isCellFree = gameBoard.isCellFree(row, col);
        if (isCellFree) {
            rowAndCol[0] = row;
            rowAndCol[1] = col;
        }
        return isCellFree;
    }

}