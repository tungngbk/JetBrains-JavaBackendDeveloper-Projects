package tictactoe;

public abstract class Player {

    StrategyMove playersMove;
    char piece;

    public Player(char piece) {
        this.piece = piece;
    }
    public Move executeMove(GameBoard gameBoard, char piece) {
        return playersMove.makeMove(gameBoard, piece);
    }

    public void setPlayerBehavior(StrategyMove makeMove) {
        playersMove = makeMove;
    }
    public char getPiece() {
        return piece;
    }
}