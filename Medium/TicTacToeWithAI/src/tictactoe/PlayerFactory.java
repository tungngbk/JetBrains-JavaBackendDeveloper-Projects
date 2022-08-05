package tictactoe;

public class PlayerFactory {

    public static Player create(String playerType, char piece) {
        Player player = null;
        if (playerType.equals("user")) {
            player = new HumanPlayer(piece);
            player.setPlayerBehavior(new HumanMove());
        } else {
            player = new ComputerPlayer(piece);
            switch (playerType) {
                case "easy": {
                    player.setPlayerBehavior(new EasyComputerMove());
                    break;
                }
                case "medium": {
                    player.setPlayerBehavior(new MediumComputerMove());
                    break;
                }
                case "hard": {
                    player.setPlayerBehavior(new HardComputerMove());
                    break;
                }
            }
        }
        return player;
    }
}