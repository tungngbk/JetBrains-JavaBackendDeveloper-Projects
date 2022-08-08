package bullscows;

public class Grade {
    private int bulls;
    private int cows;

    public Grade() {
        this.bulls = 0;
        this.cows = 0;
    }

    @Override
    public String toString() {
        String text = "Grade: ";
        if (bulls > 0 && cows > 0) {
            return text + bulls + (bulls == 1 ? " bull" : " bull(s)") + " and "
                    + cows + (cows == 1 ? " cow." : " cow(s).");
        }
        if (bulls > 0) {
            return text + bulls + (bulls == 1 ? " bull" : " bull(s)") + ".";
        }
        if (cows > 0) {
            return text + cows + (cows == 1 ? " cow." : " cow(s).");
        }
        return text + "None.";
    }

    public int getBulls() {
        return bulls;
    }

    public void setBulls(int bulls) {
        this.bulls = bulls;
    }

    public int getCows() {
        return cows;
    }

    public void setCows(int cows) {
        this.cows = cows;
    }

    public void resetGrade(){
        this.bulls = 0;
        this.cows = 0;
    }
}
