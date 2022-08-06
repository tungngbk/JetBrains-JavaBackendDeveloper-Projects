package numbers;


import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
//        write your code here
        System.out.println("Welcome to Amazing Numbers!\n");
        instruction();
        request();
    }

    private static void instruction(){
        System.out.println("\nSupported requests:");
        System.out.println("- enter a natural number to know it's properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println("  * the first parameter represents a starting number;");
        System.out.println("  * the second parameter shows how many consecutive numbers are to be printed;");
        System.out.println("- two natural numbers and a properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate parameters with one space;");
        System.out.println("- enter 0 to exit.");
    }

    private static void request(){
        while (true){
            System.out.print("\nEnter a request: ");
            String request = scanner.nextLine();
            if (request.trim().length() == 0) {
                instruction();
                continue;
            }
            String[] requests = request.split(" ");
            if(requests[0].equals("0")){
                System.out.println("Goodbye!");
                break;
            }
            switch (requests.length){
                case 1:
                    try{
                        Number number = new Number(Long.parseLong(requests[0]));
                        processOneNumber(number);
                    } catch (NumberFormatException nfe){
                        System.out.println("\nThe first parameter should be a natural number or zero.");
                    }
                    break;
                case 2:
                    Number startingNumber = new Number(Long.parseLong(requests[0]));
                    Number consecutiveNumber = new Number(Long.parseLong(requests[1]));
                    processConsecutiveList(startingNumber, consecutiveNumber);
                    break;
                default:
                    try{
                        Number beginningNumber = new Number(Long.parseLong(requests[0]));
                        long quantity = Long.parseLong(requests[1]);
                        processQuery(beginningNumber, quantity, requests);
                    } catch (NumberFormatException nfe){
                        System.out.println("\nThe first parameter should be a natural number or zero.");
                    }
                    break;
            }
        }
    }


    private static void processOneNumber(Number number){
        if(number.isNaturalNumber()){
            System.out.printf("\nProperties of %,d%n\n", number.getNumber());
            System.out.printf("buzz: %b\n", number.isBuzz());
            System.out.printf("duck: %b\n", number.isDuck());
            System.out.printf("palindromic: %b\n", number.isPalindrome());
            System.out.printf("gapful: %b\n", number.isGapful());
            System.out.printf("spy: %b\n", number.isSpy());
            System.out.printf("square: %b\n", number.isSquare(number.getNumber()));
            System.out.printf("sunny: %b\n", number.isSquare(number.getNumber()+1));
            System.out.printf("jumping: %b\n", number.isJumping());
            System.out.printf("happy: %b\n", number.isHappy());
            System.out.printf("sad: %b\n", !number.isHappy());
            System.out.printf("even: %b\n", number.isEven());
            System.out.printf("odd: %b\n", number.isOdd());
        } else {
            System.out.println("The first parameter should be a natural number or zero.");
        }
    }

    private static void processConsecutiveList(Number startingNumber, Number consecutiveNumber) {
        if (ErrorHandler.checkNumbersErrors(startingNumber.getNumber(), consecutiveNumber.getNumber()))
            return;
        for (long i = startingNumber.getNumber(); i < startingNumber.getNumber() + consecutiveNumber.getNumber(); i++){
            StringBuilder numberProperties = checkNumberProperties(i);
            System.out.printf("\t\t\t %,d is %s%n", i, numberProperties.substring(0, numberProperties.length() - 2));
        }
    }

    private static void processQuery(Number beginningNumber, long quantity, String[] properties) {
        String[] propertiesArray = Arrays.copyOfRange(properties, 2, properties.length);
        if(ErrorHandler.checkNumbersErrors(beginningNumber.getNumber(), quantity))
            return;
        if(ErrorHandler.checkProperties(propertiesArray))
            return;
        int howManyNumbers = 0;
        long number = beginningNumber.getNumber();
        while (howManyNumbers < quantity){
            StringBuilder numberProperties = checkNumberProperties(number);

            if (checkParameter(propertiesArray, numberProperties)) { // there is a property / properties in the
                // number
                System.out.printf("\t\t\t %,d is %s%n", number, numberProperties.substring(
                        0,
                        numberProperties.length() - 2
                ));
                howManyNumbers++;
            }
            number++;
        }
    }

    private static boolean checkParameter(String[] properties, StringBuilder numberProperties) {
        boolean flag = true;
        for (String property : properties) {
            if (!property.startsWith("-")) {
                if (!(numberProperties.toString().contains(property.toLowerCase()))) {
                    flag = false;
                    break;
                }
            } else {
                if (numberProperties.toString().contains(property.replace("-", "").toLowerCase())) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    private static StringBuilder checkNumberProperties(long i) {
        Number number = new Number(i);
        StringBuilder numberProperties = new StringBuilder();
        if (number.isBuzz()) numberProperties.append("buzz, ");
        if (number.isDuck()) numberProperties.append("duck, ");
        if (number.isPalindrome()) numberProperties.append("palindromic, ");
        if (number.isGapful()) numberProperties.append("gapful, ");
        if (number.isSpy()) numberProperties.append("spy, ");
        if (number.isSquare(i)) numberProperties.append("square, ");
        if (number.isSquare(i + 1)) numberProperties.append("sunny, ");
        if (number.isJumping()) numberProperties.append("jumping, ");
        if (number.isHappy()) numberProperties.append("happy, ");
        if (!number.isHappy()) numberProperties.append("sad, ");
        if (number.isEven()) numberProperties.append("even, ");
        else numberProperties.append("odd, ");
        return numberProperties;
    }
}
