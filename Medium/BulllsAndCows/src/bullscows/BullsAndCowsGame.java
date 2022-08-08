package bullscows;

import java.util.*;

public class BullsAndCowsGame {
    private int codeLength;
    private static Scanner scanner = new Scanner(System.in);
    private Code code;
    private Grade grade;
    private boolean gameDone;

    BullsAndCowsGame(){
        code = new Code();
        grade = new Grade();
    }

    public void requestInput(){
        System.out.println("Input the length of the secret code:");
        try {
            this.codeLength = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Error: \"abc 0 -7\" isn't a valid number.");
        }
        System.out.println("Input the number of possible symbols in the code:");
        int numOfSymbols = 0;
        try {
            numOfSymbols = scanner.nextInt();
        } catch (Exception e){
            System.out.println("Error: \"abc 0 -7\" isn't a valid number.");
        }
        if(numOfSymbols < codeLength){
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", codeLength, numOfSymbols);
            gameDone = true;
            return;
        }
        if(numOfSymbols > 36){
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            gameDone = true;
            return;
        }
        // 'a' = 97, 'z' = 122
        if(numOfSymbols <= 10){
            System.out.print("The secret is prepared: ");
            for (int i = 0; i < codeLength; i++){
                System.out.print("*");
            }
            System.out.printf(" (%d-%d).\n",0, numOfSymbols-1);
            this.code.setSecretCode(randomGenerator(codeLength, numOfSymbols));
        } else {
            int rangeCharacter = numOfSymbols - 10;
            char startSymbol = 'a';
            char endSymbol = (char) ((int)startSymbol + rangeCharacter - 1);
            System.out.print("The secret is prepared: ");
            for (int i = 0; i < codeLength; i++){
                System.out.print("*");
            }
            if(rangeCharacter == 1){
                System.out.printf(" (%d-%d, %c).\n",0, 9, startSymbol);
            } else {
                System.out.printf(" (%d-%d, %c-%c).\n",0, 9, startSymbol, endSymbol);
            }
            this.code.setSecretCode(randomGenerator(this.codeLength, numOfSymbols));
        }
    }
    public void play(){
        requestInput();
        if(!gameDone) System.out.println("Okay, let's start a game!");
        int turn = 1;
        while (!gameDone){
            grade.resetGrade();
            System.out.println("Turn " + turn + ":");
            String guessCode = scanner.next();
            String secretCode = this.code.getSecretCode();
            if(guessCode.equals(secretCode)){
                grade.setBulls(codeLength);
                gameDone = true;
            } else {
                for(int i = 0; i < code.getSecretCode().length(); i++){
                    if(guessCode.charAt(i) == secretCode.charAt(i)){
                        grade.setBulls(grade.getBulls() + 1);
                    } else if (secretCode.contains(String.valueOf(guessCode.charAt(i)))){
                        grade.setCows(grade.getCows() + 1);
                    }
                }
            }
            printResult();
            turn++;
        }
    }
    public void printResult(){
        System.out.println(grade);
        if(gameDone){
            System.out.println("Congratulations! You guessed the secret code.");
        }
    }

    public static String randomGenerator(int length, int symbols) {
        if (length <= 0 || length > 36) {
            String error = String.format("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", length);
            System.out.println(error);
            System.exit(0);
        }
        List<Character> list = new ArrayList<>(List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't','u',
                'v',  'w',  'x', 'y', 'z'));
        List<Character> randomList = list.subList(0, symbols);
        Collections.shuffle(randomList);
        StringBuilder result = new StringBuilder();
        for (var ch : randomList.subList(0, length)) {
            result.append(ch);
        }
        return result.toString();
    }
}
