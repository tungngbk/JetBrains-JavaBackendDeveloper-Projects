package carsharing;

public class Main {

    public static void main(String[] args) {
        // write your code here
        CarSharingSystem carSharingSystem = new CarSharingSystem(args[1]);
        while (!carSharingSystem.isExitSystem()){
            carSharingSystem.run();
        }
    }
}