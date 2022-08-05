package tictactoe;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        while (true) {
            String usersInput = getInputFromUser();
            if (usersInput.startsWith("exit")) {
                System.exit(0);
            }
            String[] userChoices = usersInput.split(" ");
            Player player1 = PlayerFactory.create(userChoices[1], 'X');
            Player player2 = PlayerFactory.create(userChoices[2], 'O');

            String resultText;
            GameBoard gameBoard = new GameBoard();
            int turn = 1;

            while (true) {
                System.out.printf(gameBoard.toString());

                Move currentMove = turn % 2 != 0 ? player1.executeMove(gameBoard, player1.getPiece()) : player2.executeMove(gameBoard, player2.getPiece());
                gameBoard.processMove(currentMove);

                if (gameBoard.winnerCheck('X')) {
                    resultText = "X wins";
                    break;
                }
                if (gameBoard.winnerCheck('O')) {
                    resultText = "O wins";
                    break;
                }
                if (gameBoard.checkForDraw()) {
                    resultText = "Draw";
                    break;
                }
                turn++;
            }
            System.out.printf(gameBoard.toString());
            System.out.println(resultText);
        }
    }

    public static String getInputFromUser() {
        Scanner input = new Scanner(System.in);
        String usersInput;
        while (true) {
            System.out.println("Input command:");
            usersInput = input.nextLine();
            if (usersInput.matches("(start)\\s(user|easy|medium|hard)\\s(user|easy|medium|hard)") || usersInput.startsWith("exit")) {
                break;
            } else {
                System.out.println("Bad parameters!");
            }
        }
        return usersInput;
    }

}