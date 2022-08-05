package tictactoe;


public class HardComputerMove implements StrategyMove {


    @Override
    public Move makeMove(GameBoard gameboard, char piece) {
        char[][] board = gameboard.getGrid();
        Hard hard = new Hard(piece);
        Hard.HardMove bestMove = hard.findBestMove(board);
        return new Move(bestMove.row,bestMove.col,piece);

    }
}