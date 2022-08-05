package tictactoe;

public class Move {
    private final int row;
    private final int col;
    private final char gamePiece;

    public Move (int row, int col, char gamePiece) {
        this.row = row;
        this.col = col;
        this.gamePiece = gamePiece;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public char getGamePiece() {
        return gamePiece;
    }

}