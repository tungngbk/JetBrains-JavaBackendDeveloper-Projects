package tictactoe;

public class GameBoard {

    char[][] grid;
    int numOfXs;
    int numOfOs;
    int numEmpty;

    GameBoard() {
        grid = new char[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                grid[row][col] = ' ';
            }
        }
        numOfXs = 0;
        numOfOs = 0;
        numEmpty = 9;
    }

    public void processMove(Move move) {
        numEmpty--;
        if (move.getGamePiece() == 'X') {
            numOfXs++;
        } else {
            numOfOs++;
        }
        grid[move.getRow()][move.getCol()] = move.getGamePiece();
    }
    public boolean winnerCheck(char piece) {
        boolean didWin = false;

        for (int i = 0; i < 3; i++) {
            if (grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2] && grid[i][2] == piece) {
                didWin = true;
                break;
            }
            if (grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i] && grid[2][i] == piece) {
                didWin = true;
                break;
            }
        }
        if (!didWin) {
            if ((grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2] && grid[2][2] == piece) ||
                    (grid[2][0] == grid[1][1] && grid[1][1] == grid[0][2] && grid[0][2] == piece))
                didWin = true;
        }
        return didWin;
    }
    public boolean checkForDraw(){
        return numEmpty == 0;
    }

    public boolean isCellFree(int row, int col) {
        return grid[row][col] == ' ';
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    public char[][] getGrid() {
        return grid;
    }

    @Override
    public String toString() {
        StringBuilder holder = new StringBuilder();
        holder.append("---------%n");
        for (int i = 0; i < 3; i++) {
            holder.append("| ");
            for (int j = 0; j < 3; j++) {
                holder.append(grid[i][j]).append(" ");
            }
            holder.append("|%n");
        }
        holder.append("---------%n");

        return holder.toString();
    }
}