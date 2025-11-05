package main;
import playable.Cleric;
import playable.Knight;
import playable.Thief;

// Controls the threading of the game characters
public class PlayerThreadControl {
    public static void main(String[] args) {
        Knight knight = new Knight(0, "Knight");
        Cleric cleric = new Cleric(0, "Cleric");
        Thief thief = new Thief(0, "Thief");

        Thread knightThread = new Thread(knight);
        Thread clericThread = new Thread(cleric);
        Thread thiefThread = new Thread(thief);

        knightThread.start();
        clericThread.start();
        thiefThread.start();

        try {
            knightThread.join();
            clericThread.join();
            thiefThread.join();
        } catch (InterruptedException e) {
            System.err.println("Adventure interrupted!");
        }

        System.out.println("The adventure has concluded!");
    }
}