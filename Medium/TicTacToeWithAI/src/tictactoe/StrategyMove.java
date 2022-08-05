package tictactoe;

public interface StrategyMove {
    Move makeMove(GameBoard gameboard, char piece);
}