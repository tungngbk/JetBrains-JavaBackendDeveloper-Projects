package numbers;

import java.util.ArrayList;

public class Number {
    private long number;

    public Number(long number) {
        this.number = number;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public boolean isEven(){
        if(number % 2 == 0) return true;
        else return false;
    }

    public boolean isOdd(){
        if(number % 2 != 0) return true;
        else return false;
    }

    public boolean isNaturalNumber(){
        if(number > 0) return true;
        else return false;
    }

    public boolean isBuzz(){
        if(number % 10 == 7 || number % 7 == 0) return true;
        else return false;
    }

    public boolean isDuck(){
        String isDuckNum = String.valueOf(number);
        if(isDuckNum.startsWith("0")){
            isDuckNum = isDuckNum.substring(1);
        }
        if (isDuckNum.contains("0")) return true;
        else return false;
    }

    public boolean isPalindrome(){
        long reversedNumber = 0;
        long check = number;
        while (check != 0){
            long remainder = check % 10;
            reversedNumber = reversedNumber * 10 + remainder;
            check /= 10;
        }
        return reversedNumber == number;
    }

    public boolean isGapful(){
        if (!(number < 100)) {
            String numberToString = Long.toString(number);
            String firstLastDigit =
                    numberToString.charAt(0) + String.valueOf(numberToString.charAt(numberToString.length() - 1));
            long divisor = Long.parseLong(firstLastDigit);
            return number % divisor == 0;
        }
        return false;
    }

    public boolean isSpy(){
        long sum = 0;
        long product = 1;
        long tempNum = this.number;
        while (tempNum != 0){
            long digit = tempNum % 10;
            sum += digit;
            product *= digit;
            tempNum /= 10;
        }
        return sum == product;
    }

    public boolean isSquare(long number){
        return number == ((long) Math.sqrt(number) * Math.sqrt(number));
    }

    public boolean isJumping(){
        ArrayList<Long> digits = new ArrayList<>();
        long tempNum = this.number;
        while (tempNum != 0) {
            digits.add(tempNum % 10);
            tempNum /= 10;
        }

        return isFlagJumping(digits);
    }

    private boolean isFlagJumping(ArrayList<Long> digits) {
        boolean flag = true;
        for (int i = 0; i < digits.size() - 1; i++) {
            if (Math.abs(digits.get(i) - digits.get(i + 1)) != 1) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public boolean isHappy(){
        ArrayList<Long> sequence = new ArrayList<>();
        sequence.add(number);

        if (number != 1) {
            for (int i = 0; i < sequence.size(); i++) {
                long sum = 0;
                if (sequence.get(i) == 1) {
                    return true;
                }

                long check = sequence.get(i);
                while (check != 0) {
                    sum += Math.pow(check % 10, 2);
                    check /= 10;
                }

                if (sum == 145 || sum == 3 || sum == 4 || sum == 5 || sum == 6) {
                    return false;
                }

                sequence.add(sum);
            }
        }
        return true;
    }
}
