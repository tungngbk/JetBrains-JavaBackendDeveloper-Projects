package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {
    private static final String DIGITS = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static String baseConversion(String number, String source, String target){
        BigInteger bigInteger = new BigInteger(number, Integer.parseInt(source));
        return bigInteger.toString(Integer.parseInt(target));
    }

    public static String fractionConversion(double fraction, String target){
        StringBuilder output = new StringBuilder(".");
        for (int i = 0; i < 5; i++) {
            fraction *= Integer.parseInt(target);
            output.append(Long.parseLong(Integer.toString((int) fraction)));
            fraction -= Long.parseLong(Integer.toString((int) fraction));
        }
        return output.toString();
    }



    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exitFlag = false;
        while (!exitFlag){
            System.out.println("Enter two numbers in format: {source base} {target base} (To quit type /exit)");
            String input = sc.next();
            if(input.equals("/exit")) return;
            String sourceBase = input;
            String targetBase = sc.next();
            while (true){
                System.out.printf("Enter number in base %s to convert to base %s (To go back type /back)", sourceBase, targetBase);
                String choice = sc.next();
                if(choice.equals("/back")) break;
                else {
                    System.out.println("Conversion result: " + fromToRadix(choice, sourceBase, targetBase));
                }
            }


        }
    }

    private static String fromToRadix(String number, String sourceBase, String targetBase) {
        int sourceBaseInt = Integer.parseInt(sourceBase);
        int targetBaseInt = Integer.parseInt(targetBase);
        final var dotIndex = number.indexOf('.');
        if (dotIndex == -1) {
            return new BigInteger(number, sourceBaseInt).toString(targetBaseInt);
        }
        final var sourceWhole = number.substring(0, dotIndex);
        final var sourceFraction = number.substring(1 + dotIndex);
        final var targetWhole = new BigInteger(sourceWhole, sourceBaseInt).toString(targetBaseInt);
        var decimalFraction = 0.0;
        var divider = (double) sourceBaseInt;

        for (final var digit : sourceFraction.toCharArray()) {
            decimalFraction += DIGITS.indexOf(digit) / divider;
            divider *= sourceBaseInt;
        }
        final var targetFraction = new StringBuilder();
        for (int i = 5; i > 0; --i) {
            decimalFraction *= targetBaseInt;
            final var index = (int) decimalFraction;
            targetFraction.append(DIGITS.charAt(index));
            decimalFraction -= index;
        }

        return targetWhole + "." + targetFraction;
    }
}
